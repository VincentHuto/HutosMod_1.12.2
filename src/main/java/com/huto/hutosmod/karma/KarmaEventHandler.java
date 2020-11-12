package com.huto.hutosmod.karma;

import net.minecraft.entity.INpc;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class KarmaEventHandler {
	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		IKarma karma = player.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
		String message = String
				.format(TextFormatting.RED + "Hello there, you have " + (int) karma.getKarma() + " karma left.");
		player.sendMessage(new TextComponentString(message));

	}

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		EntityPlayer player = event.getEntityPlayer();
		IKarma karma = player.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
		IKarma oldKarma = event.getOriginal().getCapability(KarmaProvider.KARMA_CAPABILITY, null);
		karma.set(oldKarma.getKarma());
	}

	@SubscribeEvent
	public void onPlayerKillsEntity(LivingDeathEvent event) {
		if (event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) event.getSource().getTrueSource();
			IKarma karma = p.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
			if (event.getEntity() instanceof EntityAnimal) {
				karma.consume(1);
			} else if (event.getEntity() instanceof INpc) {
				karma.consume(10);
			} else if (event.getEntity() instanceof EntityMob || event.getEntity() instanceof EntitySlime) {
				karma.add(1);
			}
		}
	}

	@SubscribeEvent
	public void applyKarmaBuffs(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		IKarma karma = player.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
		// messing with caabilites gets sorta sticky because they dont return back to
		// normal... change later
		if (karma.getKarma() >= 1.0F) {
			// player.capabilities.setPlayerWalkSpeed(0.1F);

		}

		if (karma.getKarma() >= 20.0F) {
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1));
			// player.capabilities.setPlayerWalkSpeed(1F);

		}
		if (karma.getKarma() >= 40.0F) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2));
			// player.capabilities.setPlayerWalkSpeed(0.2F);

		}
		if (karma.getKarma() >= 60.0F) {
			// player.capabilities.setPlayerWalkSpeed(0.3F);

		}
		if (karma.getKarma() >= 80.0F) {
			player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 1));

		}
		if (karma.getKarma() >= 100.0F) {
			// player.capabilities.isFlying= true;
		}

	}

	@SubscribeEvent
	public void applyKarmaDebuffs(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		IKarma karma = player.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
		if (karma.getKarma() <= -150.0F) {
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1));
		}
	}

}
