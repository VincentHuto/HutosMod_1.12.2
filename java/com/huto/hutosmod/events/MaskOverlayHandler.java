package com.huto.hutosmod.events;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.renders.runicHealthRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class MaskOverlayHandler {
	public MaskOverlayHandler(runicHealthRenderer i_HUDrenderer) {
		runicHealthRenderer = i_HUDrenderer;
	}

	private runicHealthRenderer runicHealthRenderer;

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
			if (slotItemStack.getItem() == ItemRegistry.mysterious_mask) {
				foundOnHead = true;
				break;
			}
		}
		if (!foundOnHead)
			return;

		switch (event.getType()) {
		case HEALTH:
			runicHealthRenderer.renderStatusBar(event.getResolution().getScaledWidth(), event.getResolution()
					.getScaledHeight()); /* Call a helper method so that this method stays organized */
			/* Don't render the vanilla heart bar */
			event.setCanceled(true);
			break;

		case ARMOR:
			/*
			 * Don't render the vanilla armor bar, it's part of the status bar in the HEALTH
			 * event
			 */
			event.setCanceled(true);
			break;

		default: // If it's not one of the above cases, do nothing
			break;
		}
	}

	/*
	 * If you want something to be rendered over an existing vanilla element, you
	 * would render it here.
	 */
	@SubscribeEvent(receiveCanceled = true)
	public void onEvent(RenderGameOverlayEvent.Post event) {

		switch (event.getType()) {
		case HEALTH:
			break;
		default: // If it's not one of the above cases, do nothing
			break;
		}
	}
}