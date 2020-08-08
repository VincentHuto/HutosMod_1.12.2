package com.huto.hutosmod.mana;

import com.huto.hutosmod.commands.Teleport;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.karma.IKarma;
import com.huto.hutosmod.karma.KarmaProvider;
import com.huto.hutosmod.network.PacketGetManaLimit;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ManaEventHandler {
	public static int sync;
	public static float manaLimit;

	@SubscribeEvent
	public void manaCheckMax(PlayerTickEvent event) {

		// Reminder to self: This is gross and sorta a dumb fix to prevent the max mana
		// check from taking place before the update packet is sent on game reload, it
		// works, the only possible issue is that if you have may have more usable mana
		// for the first like 0.5 seconds of the game, for most computers however this
		// is gonna be a load screen anyways; look into a fix later...

		// Potentially oly place that check when a mana adding item is dropped on death
		// or when an item/achievment is added etc because those are the only times it
		// would change anyways
		PacketHandler.INSTANCE.sendToServer(
				new PacketGetManaLimit(manaLimit, "com.huto.hutosmod.mana.ManaEventHandler", "manaLimit"));
		IMana mana = event.player.getCapability(ManaProvider.MANA_CAP, null);
		sync++;
		// 130 is a changable value, the long it is the longer delay till the first
		// check.
		if (sync > 130 && mana.getMana() > manaLimit) {
			mana.set(manaLimit);
		}
		Iterable<ItemStack> armor = event.player.getArmorInventoryList();
		for (ItemStack stack : armor) {
			checkArmor(stack, event.player);

		}
	}

	@SubscribeEvent
	public static void checkArmor(ItemStack stack, EntityPlayer player) {
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);

		boolean foundOnHead = false;
		ItemStack slotItemStack = player.inventory.armorItemInSlot(3);
		if (slotItemStack.getItem() == ItemRegistry.mysterious_mask) {
			foundOnHead = true;
		}
		if (player.dimension == -403 || player.dimension == -404) {
			if (!foundOnHead) {
				if (!player.world.isRemote) {
					Teleport.teleportToDimention(player, 0, player.getPosition().getX(), 255,
							player.getPosition().getZ());
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 250,250, true, true));

				}
			}
		}

	}

	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event) {

		EntityPlayer player = event.player;
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);

		String message = String
				.format(TextFormatting.BLUE + "Hello there, you have " + (int) mana.getMana() + " mana left.");

		player.sendMessage(new TextComponentString(message));

	}

	@SubscribeEvent
	public void onPlayerSleep(PlayerSleepInBedEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		IKarma karma = player.getCapability(KarmaProvider.KARMA_CAPABILITY,null);
		if (player.world.isRemote)
			return;
		// teleports to dreamscape if wearing viewer
		boolean foundOnHead = false;
		ItemStack slotItemStack = player.inventory.armorItemInSlot(3);
		if (slotItemStack.getItem() == ItemRegistry.mysterious_mask) {
			foundOnHead = true;
		}
		if (foundOnHead && player.dimension == 0) {
			if(karma.getKarma()>=0) {
			Teleport.teleportToDimention(player, -403, player.getPosition().getX(), 255, player.getPosition().getZ());
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 250,250, true, true));
			}else {
				Teleport.teleportToDimention(player, -404, player.getPosition().getX(), 255, player.getPosition().getZ());
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 250,250, true, true));
			}
		}
	}

/*	@SubscribeEvent
	public void onPlayerFalls(LivingFallEvent event) {
		Entity entity = event.getEntity();

		if (entity.world.isRemote || !(entity instanceof EntityPlayerMP) || event.getDistance() < 3)
			return;

		EntityPlayer player = (EntityPlayer) entity;
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);

		float points = mana.getMana();
		float cost = event.getDistance() * 2;

		if (points > cost) {
			mana.consume(cost);

			String message = String.format(
					"You absorbed fall damage. It costed §7%d§r mana, you have §7%d§r mana left.", (int) cost,
					(int) mana.getMana());
			player.sendMessage(new TextComponentString(message));

			event.setCanceled(true);
		}
	}*/

	/**
	 * Copy data from dead player to the new player
	 */
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {

		EntityPlayer player = event.getEntityPlayer();
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
		IMana oldMana = event.getOriginal().getCapability(ManaProvider.MANA_CAP, null);

		mana.set(oldMana.getMana());
	}

}
