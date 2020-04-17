package com.huto.hutosmod.keybinds;

import com.huto.hutosmod.GUI.GuiRuneStation;
import com.huto.hutosmod.GUI.pages.GuiPageTome0;
import com.huto.hutosmod.mindrunes.network.PacketOpenRuneInventory;
import com.huto.hutosmod.mindrunes.network.RunesPacketHandler;
import com.huto.hutosmod.proxy.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class KeyInputEvents {

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (KeyBindRegistry.hello.isPressed()) {
			System.out.println("HELLO PLAYER");

		}
	}

	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) {
			if (KeyBindRegistry.KEY_RUNES.isPressed() && FMLClientHandler.instance().getClient().inGameHasFocus && event.side == Side.CLIENT) {
				System.out.println("OPENING INVENTORY");
				RunesPacketHandler.INSTANCE.sendToServer(new PacketOpenRuneInventory());
			}
		}
	
}
