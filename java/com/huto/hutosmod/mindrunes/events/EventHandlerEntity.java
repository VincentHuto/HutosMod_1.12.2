package com.huto.hutosmod.mindrunes.events;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.huto.hutosmod.mindrunes.RuneApi;
import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.mindrunes.cap.RuneCapabilities;
import com.huto.hutosmod.mindrunes.cap.RunesContainerProvider;
import com.huto.hutosmod.mindrunes.container.RunesContainer;
import com.huto.hutosmod.mindrunes.network.PacketSync;
import com.huto.hutosmod.mindrunes.network.RunesPacketHandler;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandlerEntity {

	private HashMap<UUID,ItemStack[]> runesSync = new HashMap<UUID,ItemStack[]>();

	@SubscribeEvent
	public void cloneCapabilitiesEvent(PlayerEvent.Clone event)
	{
		try {
			RunesContainer rco = (RunesContainer) RuneApi.getRuneHandler(event.getOriginal());
			NBTTagCompound nbt = rco.serializeNBT();
			RunesContainer rcn = (RunesContainer) RuneApi.getRuneHandler(event.getEntityPlayer());
			rcn.deserializeNBT(nbt);
		} catch (Exception e) {
			Reference.log.error("Could not clone player ["+event.getOriginal().getName()+"] runes when changing dimensions");
		}
	}

	@SubscribeEvent
	public void attachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(Reference.MODID,"container"),
					new RunesContainerProvider(new RunesContainer()));
		}
	}

	@SubscribeEvent
	public void playerJoin(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) entity;
			syncSlots(player, Collections.singletonList(player));
		}
	}

	@SubscribeEvent
	public void onStartTracking(PlayerEvent.StartTracking event) {
		Entity target = event.getTarget();
		if (target instanceof EntityPlayerMP) {
			syncSlots((EntityPlayer) target, Collections.singletonList(event.getEntityPlayer()));
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerLoggedOutEvent event)
	{
		runesSync.remove(event.player.getUniqueID());
	}

	@SubscribeEvent
	public void playerTick(TickEvent.PlayerTickEvent event) {
		// player events
		if (event.phase == TickEvent.Phase.END) {
			EntityPlayer player = event.player;
			IRunesItemHandler runes = RuneApi.getRuneHandler(player);
			for (int i = 0; i < runes.getSlots(); i++) {
				ItemStack stack = runes.getStackInSlot(i);
				IRune rune = stack.getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null);
				if (rune != null) {
					rune.onWornTick(stack, player);
				}
			}
			if (!player.world.isRemote) {
				syncRunes(player, runes);
			}
		}
	}

	private void syncRunes(EntityPlayer player, IRunesItemHandler runes) {
		ItemStack[] items = runesSync.get(player.getUniqueID());
		if (items == null) {
			items = new ItemStack[runes.getSlots()];
			Arrays.fill(items, ItemStack.EMPTY);
			runesSync.put(player.getUniqueID(), items);
		}
		if (items.length != runes.getSlots()) {
			ItemStack[] old = items;
			items = new ItemStack[runes.getSlots()];
			System.arraycopy(old, 0, items, 0, Math.min(old.length, items.length));
			runesSync.put(player.getUniqueID(), items);
		}
		Set<EntityPlayer> receivers = null;
		for (int i = 0; i < runes.getSlots(); i++) {
			ItemStack stack = runes.getStackInSlot(i);
			IRune rune = stack.getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null);
			if (runes.isChanged(i) || rune != null && rune.willAutoSync(stack, player) && !ItemStack.areItemStacksEqual(stack, items[i])) {
				if (receivers == null) {
					receivers = new HashSet<>(((WorldServer) player.world).getEntityTracker().getTrackingPlayers(player));
					receivers.add(player);
				}
				syncSlot(player, i, stack, receivers);
				runes.setChanged(i,false);
				items[i] = stack == null ? ItemStack.EMPTY : stack.copy();
			}
		}
	}

	private void syncSlots(EntityPlayer player, Collection<? extends EntityPlayer> receivers) {
		IRunesItemHandler runes = RuneApi.getRuneHandler(player);
		for (int i = 0; i < runes.getSlots(); i++) {
			syncSlot(player, i, runes.getStackInSlot(i), receivers);
		}
	}

	private void syncSlot(EntityPlayer player, int slot, ItemStack stack, Collection<? extends EntityPlayer> receivers) {
		PacketSync pkt = new PacketSync(player, slot, stack);
		for (EntityPlayer receiver : receivers) {
			RunesPacketHandler.INSTANCE.sendTo(pkt, (EntityPlayerMP) receiver);
		}
	}

	@SubscribeEvent
	public void playerDeath(PlayerDropsEvent event) {
		if (event.getEntity() instanceof EntityPlayer
				&& !event.getEntity().world.isRemote
				&& !event.getEntity().world.getGameRules().getBoolean("keepInventory")) {
			dropItemsAt(event.getEntityPlayer(),event.getDrops(),event.getEntityPlayer());
		}
	}

	public void dropItemsAt(EntityPlayer player, List<EntityItem> drops, Entity e) {
		IRunesItemHandler runes = RuneApi.getRuneHandler(player);
		for (int i = 0; i < runes.getSlots(); ++i) {
			if (runes.getStackInSlot(i) != null && !runes.getStackInSlot(i).isEmpty()) {
				EntityItem ei = new EntityItem(e.world,
						e.posX, e.posY + e.getEyeHeight(), e.posZ,
						runes.getStackInSlot(i).copy());
				ei.setPickupDelay(40);
				float f1 = e.world.rand.nextFloat() * 0.5F;
				float f2 = e.world.rand.nextFloat() * (float) Math.PI * 2.0F;
				ei.motionX = (double) (-MathHelper.sin(f2) * f1);
				ei.motionZ = (double) (MathHelper.cos(f2) * f1);
				ei.motionY = 0.20000000298023224D;
				drops.add(ei);
				runes.setStackInSlot(i, ItemStack.EMPTY);
			}
		}
	}
}
