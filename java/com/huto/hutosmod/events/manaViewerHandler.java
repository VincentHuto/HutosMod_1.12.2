package com.huto.hutosmod.events;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.render.manaViewerHud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ManaViewerHandler {
	public ManaViewerHandler(manaViewerHud i_HUDrenderer) {
		manaViewerHud = i_HUDrenderer;
	}

	private manaViewerHud manaViewerHud;

	/*
	 * The RenderGameOverlayEvent.Pre event is called before each game overlay
	 * element is rendered. It is called multiple times. A list of existing overlay
	 * elements can be found in
	 * net.minecraftforge.client.event.RenderGameOverlayEvent.
	 *
	 * If you want something to be rendered under an existing vanilla element, you
	 * would render it here.
	 *
	 * Note that you can entirely remove the vanilla rendering by cancelling the
	 * event here.
	 */

	@SubscribeEvent(receiveCanceled = true)
	public void onEvent(RenderGameOverlayEvent.Pre event) {
		EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;

		if (entityPlayerSP == null)
			return; // just in case

		boolean foundOnHead = false;
		for (int i = 0; i < 103; ++i) {
			ItemStack slotItemStack = entityPlayerSP.inventory.getStackInSlot(39);
			if (slotItemStack.getItem() == ItemRegistry.mana_viewer) {
				foundOnHead = true;
				break;
			}
		}
		if (!foundOnHead)
			return;

		switch (event.getType()) {
		case ALL:
			manaViewerHud.renderStatusBar(event.getResolution().getScaledWidth(),
					event.getResolution().getScaledHeight(), entityPlayerSP.world,
					entityPlayerSP); /* Call a helper method so that this method stays organized */

		default: // If it's not one of the above cases, do nothing
			break;
		}
	}
}