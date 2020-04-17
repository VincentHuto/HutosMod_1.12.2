package com.huto.hutosmod.events;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.renders.karmaViewHud;
import com.huto.hutosmod.renders.manaViewerHud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class karmaViewHandler {
	public karmaViewHandler(karmaViewHud i_HUDrenderer) {
		karmaViewHud = i_HUDrenderer;
	}
	private karmaViewHud karmaViewHud;
	@SubscribeEvent(receiveCanceled = true)
	public void onEvent(RenderGameOverlayEvent.Pre event) {
		EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;
			switch (event.getType()) {
			case ALL:
				karmaViewHud.renderStatusBar(event.getResolution().getScaledWidth(),
						event.getResolution().getScaledHeight(), entityPlayerSP.world,
						entityPlayerSP); /* Call a helper method so that this method stays organized */

			default: // If it's not one of the above cases, do nothing
				break;
			}

	}

}