/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Feb 3, 2014, 9:59:17 PM (GMT)]
 */
package com.huto.hutosmod.models;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.MODID)
public final class ClientTickHandler {

	private ClientTickHandler() {
	}

	public static int ticksWithLexicaOpen = 0;
	public static int pageFlipTicks = 0;
	public static int ticksInGame = 0;
	public static float partialTicks = 0;
	public static float delta = 0;
	public static float total = 0;

	private static void calcDelta() {
		float oldTotal = total;
		total = ticksInGame + partialTicks;
		delta = total - oldTotal;
	}

	@SubscribeEvent
	public static void renderTick(RenderTickEvent event) {
		if (event.phase == Phase.START)
			partialTicks = event.renderTickTime;
		else {
			calcDelta();
		}
	}

	@SubscribeEvent
	public static void clientTickEnd(ClientTickEvent event) {
		if (event.phase == Phase.END) {
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;
			if (gui == null || !gui.doesGuiPauseGame()) {
				ticksInGame++;
				partialTicks = 0;

				EntityPlayer player = Minecraft.getMinecraft().player;
				if (player != null) {

				}
			}
		}

		calcDelta();
	}

}
