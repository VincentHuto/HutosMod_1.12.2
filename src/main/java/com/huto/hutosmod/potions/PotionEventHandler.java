package com.huto.hutosmod.potions;

import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketGetManaLimit;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionEventHandler {

	public static float manaLimit;
	public static float preManaLimit;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void runicPotionActive(PlayerTickEvent event) {
		PacketHandler.INSTANCE.sendToServer(
				new PacketGetManaLimit(manaLimit, "com.huto.hutosmod.potions.PotionEventHandler", "manaLimit"));
		IMana mana = event.player.getCapability(ManaProvider.MANA_CAP, null);

		preManaLimit = 300;

		if (event.player.isPotionActive(PotionInit.manaBurnEffect)) {

			/*
			 * if (event.player.isPotionActive(PotionInit.runicProtectionEffect)) {
			 * event.player.addExperienceLevel(5); }
			 */

			System.out.println(manaLimit);
			if (manaLimit > 100) {
				mana.setLimit(((float) (manaLimit * 0.4)));
			}
		} else {
			mana.setLimit(300);

		}
	}

}
