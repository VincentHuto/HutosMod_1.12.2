package com.huto.hutosmod.events;

import java.util.Random;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.proxy.Vector3;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

	@SubscribeEvent
	public void onPlayerUseWand(PlayerTickEvent e) {
		/*
		 * IMana manaCap = e.player.getCapability(ManaProvider.MANA_CAP, null);
		 * 
		 * if (manaCap != null) { Item heldItem =
		 * e.player.getHeldItemMainhand().getItem(); PacketHandler.INSTANCE
		 * .sendToServer(new PacketGetMana(mana,
		 * "com.huto.hutosmod.events.ModEventHandler", "mana")); mana =
		 * manaCap.getMana(); if (heldItem == ItemRegistry.wand_lightning && mana > 100)
		 * { RayTraceResult resu = e.player.rayTrace(100, 10); Vector3 hitVec = new
		 * Vector3(resu.getBlockPos().getX(), resu.getBlockPos().getY(),
		 * resu.getBlockPos().getZ()); Vector3 vec =
		 * Vector3.fromEntityCenter(e.player).add(0, 1, 0);
		 * MainClass.proxy.lightningFX(vec, hitVec, 1F, System.nanoTime(),
		 * Reference.black, Reference.green); // manaCap.consume(1); } }
		 */
	}

	@SubscribeEvent
	public void onPlayerUseWandInteraction(PlayerInteractEvent e) {
		/*
		 * Item heldItem = e.getEntityPlayer().getHeldItemMainhand().getItem(); if
		 * (heldItem == ItemRegistry.wand_greatstorm) { RayTraceResult resu =
		 * e.getEntityPlayer().rayTrace(100, 10); Vector3 hitVec = new
		 * Vector3(resu.getBlockPos().getX(), resu.getBlockPos().getY(),
		 * resu.getBlockPos().getZ()); Vector3 vec =
		 * Vector3.fromEntityCenter(e.getEntityPlayer());
		 * MainClass.proxy.lightningFX(vec, hitVec, 1F, System.nanoTime(),
		 * Reference.black, Reference.white); }
		 */
	}

	@SubscribeEvent
	public void onPlayerUseWandInteraction(LivingEntityUseItemEvent e) {
		if (e.getEntity() instanceof EntityPlayer) {
			Item heldItem = ((EntityPlayer) e.getEntity()).getHeldItemMainhand().getItem();
			if (heldItem == ItemRegistry.wand_lightning) {
				Vector3 vec = Vector3.fromEntityCenter(e.getEntity());

				RayTraceResult result = e.getEntity().rayTrace(10, 0);
				Vector3 endVec = new Vector3(result.getBlockPos().getX(), result.getBlockPos().getY(),
						result.getBlockPos().getZ());

				int x1 = (int) vec.x, y1 = (int) vec.y, z1 = (int) vec.z;
				int x2 = result.getBlockPos().getX(), y2 = result.getBlockPos().getY(),
						z2 = result.getBlockPos().getZ();
				/*
				 * PacketHandler.INSTANCE .sendToAll(new PacketReturnLightning(x1, x2, y1, y2,
				 * z1, z2, 1, Reference.black, Reference.oxblood));
				 * PacketHandler.INSTANCE.sendToServer( new PacketSendLightningEffect(x1, x2,
				 * y1, y2, z1, z2, 1, Reference.black, Reference.oxblood));
				 */
			}
		}
	}

	@SubscribeEvent
	public void onKillWithBloodySword(LivingDeathEvent e) {
		if (e.getSource() == ItemRegistry.NullSwordDamageSource) {
			// System.out.println("Killed with null Damage");
			Random rand = new Random();
			e.getEntity().dropItem(ItemRegistry.anti_tear, rand.nextInt(3));
		}
	}

}
