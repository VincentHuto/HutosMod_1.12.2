package com.huto.hutosmod.GUI.pages;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class GuiButtonArrowBackward extends GuiButton {

	final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/gui/book.png");

	int buttonWidth = 16;
	int buttonHeight = 14;
	int u = 192;
	int v = 1;

	public GuiButtonArrowBackward(int buttonId, int x, int y) {
		super(buttonId, x, y, 16, 14, "");
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY,float partialTicks) {
		if (visible) {
			mc.renderEngine.bindTexture(texture);
			if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
				hovered = true;
			} else {
				hovered = false;
			}
			if (hovered) {
				v = 18;
			} else {
				v = 1;
			}
			drawTexturedModalRect(x, y, u, v, width, height);
		}
	}
}