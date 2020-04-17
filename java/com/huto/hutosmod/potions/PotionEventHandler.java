package com.huto.hutosmod.potions;

import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PotionEventHandler {

	@SubscribeEvent

	public void runicPotionActive(PlayerTickEvent event) {

		if (event.player.isPotionActive(PotionInit.runicProtectionEffect)) {
			event.player.addExperienceLevel(5);
		}

		if (event.player.isPotionActive(PotionInit.manaBurnEffect)) {
			IMana mana = event.player.getCapability(ManaProvider.MANA_CAP, null);
			mana.setLimit((float) (mana.manaLimit() * 0.4));

		}

	}

}
