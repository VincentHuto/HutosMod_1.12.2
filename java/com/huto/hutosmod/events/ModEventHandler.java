package com.huto.hutosmod.events;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.items.tools.ToolBloodSword;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileModMana;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ModEventHandler {

	public static float mana;

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.player.world.isRemote)

		{
			System.out.println("Logged");
		}
	}

	@SubscribeEvent
	public void onPlayerPickupXP(PlayerPickupXpEvent e) {
	}
	
	//Replaced with InRender Hover function
/*
	@SubscribeEvent
	public void playerHoverWithDebug(PlayerTickEvent e) {
		RayTraceResult result = e.player.rayTrace(5, 10);
		BlockPos pos = result.getBlockPos();
		TileModMana te = (TileModMana) e.player.getEntityWorld().getTileEntity(pos);
		ItemStack stack = e.player.getHeldItemMainhand();

		if (te instanceof TileModMana && te != null) {
			if (stack.getItem() == ItemRegistry.mana_debugtool) {
			//	System.out.println(te.getManaValue());
			}
		}
	}*/

	@SubscribeEvent
	public void onPlayerUseWand(PlayerTickEvent e) {
		IMana manaCap = e.player.getCapability(ManaProvider.MANA_CAP, null);

		if (manaCap != null) {
			Item heldItem = e.player.getHeldItemMainhand().getItem();
			PacketHandler.INSTANCE
					.sendToServer(new PacketGetMana(mana, "com.huto.hutosmod.events.ModEventHandler", "mana"));
			mana = manaCap.getMana();
			if (heldItem == ItemRegistry.wand_lightning && mana > 100) {
				RayTraceResult resu = e.player.rayTrace(100, 10);
				Vector3 hitVec = new Vector3(resu.getBlockPos().getX(), resu.getBlockPos().getY(),
						resu.getBlockPos().getZ());
				Vector3 vec = Vector3.fromEntityCenter(e.player).add(0, 1, 0);
				MainClass.proxy.lightningFX(vec, hitVec, 1F, System.nanoTime(), Reference.black, Reference.green);
				// manaCap.consume(1);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerUseWandInteraction(PlayerInteractEvent e) {
		Item heldItem = e.getEntityPlayer().getHeldItemMainhand().getItem();
		if (heldItem == ItemRegistry.wand_lightning) {
			RayTraceResult resu = e.getEntityPlayer().rayTrace(100, 10);
			Vector3 hitVec = new Vector3(resu.getBlockPos().getX(), resu.getBlockPos().getY(),
					resu.getBlockPos().getZ());
			Vector3 vec = Vector3.fromEntityCenter(e.getEntityPlayer());
			MainClass.proxy.lightningFX(vec, hitVec, 1F, System.nanoTime(), Reference.black, Reference.white);
		}

	}

	@SubscribeEvent
	public void onPlayerUseWandInteraction(LivingEntityUseItemEvent e) {
		if (e.getEntity() instanceof EntityPlayer) {
			Item heldItem = ((EntityPlayer) e.getEntity()).getHeldItemMainhand().getItem();
			if (heldItem == ItemRegistry.wand_lightning) {
				RayTraceResult resu = ((EntityPlayer) e.getEntity()).rayTrace(100, 10);
				Vector3 hitVec = new Vector3(resu.getBlockPos().getX(), resu.getBlockPos().getY(),
						resu.getBlockPos().getZ());
				Vector3 vec = Vector3.fromEntityCenter(((EntityPlayer) e.getEntity()));
				MainClass.proxy.lightningFX(vec, hitVec, 1F, System.nanoTime(), Reference.black, Reference.red);
				System.out.println("CSLLED");
			}
		}
	}

	@SubscribeEvent
	public void onKillWithBloodySword(LivingDeathEvent e) {
		if (e.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e.getSource().getTrueSource();
			if (p.getHeldItemMainhand() != null && p.getHeldItemMainhand().getItem() instanceof ToolBloodSword) {
				System.out.println("Killed with Bloodsword");
				Random rand = new Random();
				e.getEntity().dropItem(ItemRegistry.essence_drop, /* rand.nextInt(3) **/ 1);
			}
		}

	}

}
