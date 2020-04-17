package com.huto.hutosmod.mindrunes.events;

import com.huto.hutosmod.mindrunes.GUI.GuiMindRunes;
import com.huto.hutosmod.mindrunes.GUI.GuiRuneButton;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiEvents {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void guiPostInit(GuiScreenEvent.InitGuiEvent.Post event) {
		if (event.getGui() instanceof GuiInventory || event.getGui() instanceof GuiMindRunes) {
			GuiContainer gui = (GuiContainer) event.getGui();
			event.getButtonList().add(new GuiRuneButton(55, gui, 64, 9, 10, 10,
					I18n.format((event.getGui() instanceof GuiInventory) ? "button.runes" : "button.inventory")));
		}
	}
}
