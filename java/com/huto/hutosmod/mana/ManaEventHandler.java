package com.huto.hutosmod.mana;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.network.PacketGetManaLimit;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
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
		// or when an item/achievment is added etc because those are the only times it would change anyways
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

		int fullArmor = 0;

		if (stack.getItem() == ItemRegistry.mana_viewer) {

			fullArmor = 1;
		}
		if (stack.getItem() == ItemRegistry.mana_viewer || stack.getItem() == ItemRegistry.blood_chestplate) {

			fullArmor = 2;
		}
		if (stack.getItem() == ItemRegistry.mana_viewer || stack.getItem() == ItemRegistry.blood_chestplate
				|| stack.getItem() == ItemRegistry.blood_leggings) {

			fullArmor = 3;
		}
		if (stack.getItem() == ItemRegistry.mana_viewer || stack.getItem() == ItemRegistry.blood_chestplate
				|| stack.getItem() == ItemRegistry.blood_leggings || stack.getItem() == ItemRegistry.blood_boots) {

			fullArmor = 4;
		}

		switch (fullArmor) {

		case (0):
			if (mana.getMana() > 300) {
				if (mana.manaLimit() <= 300) {
					mana.setLimit(300);

					System.out.println("Set to 300");
				}

			}

		case (1):
			if (mana.getMana() > 400) {
				if (mana.manaLimit() <= 400) {
					mana.setLimit(400);

					System.out.println("Set to 400");
				}
			}
		case (2):
			if (mana.getMana() > 500) {
				// mana.setLimit(500);

				// System.out.println("Set to 500");
			}
		case (3):
			if (mana.getMana() > 600) {
				// mana.setLimit(600);

				// System.out.println("Set to 600");
			}
		case (4):
			if (mana.getMana() > 900) {
				// mana.setLimit(900);

				// System.out.println("Set to 900");
			}

		}

	}

	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event) {

		EntityPlayer player = event.player;
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);

		String message = String.format("Hello there, you have §9%d§r mana left.", (int) mana.getMana());
		player.sendMessage(new TextComponentString(message));

	}

	@SubscribeEvent
	public void onPlayerSleep(PlayerSleepInBedEvent event) {
		EntityPlayer player = event.getEntityPlayer();

		if (player.world.isRemote)
			return;
		if (!player.world.isDaytime()) {
			IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);

			mana.fill(10);

			String message = String.format(
					"You refreshed yourself in the bed. You received 10 mana, you have §7%d§r mana left.",
					(int) mana.getMana());
			// System.out.println(String.valueOf(mana.getMana()));

			player.sendMessage(new TextComponentString(message));
		}
	}

	@SubscribeEvent
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
	}

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
