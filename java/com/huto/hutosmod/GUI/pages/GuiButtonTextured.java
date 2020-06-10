package com.huto.hutosmod.gui.pages;

import javax.swing.plaf.ButtonUI;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonTextured extends GuiButton {
	final ResourceLocation texture;
	int id, posX, posY, buttonWidth, buttonHeight, u, v, adjV, newV;

	public GuiButtonTextured(ResourceLocation texIn, int idIn, int posXIn, int posYIn, int buttonWidthIn,
			int buttonHeightIn, int uIn, int vIn) {
		super(idIn, posXIn, posYIn, "");

		this.texture = texIn;
		this.id = idIn;
		this.posX = posXIn;
		this.posY = posYIn;
		this.width = buttonWidthIn;
		this.height = buttonHeightIn;
		this.u = uIn;
		this.v = vIn;
		this.adjV = vIn + buttonHeightIn;
		this.newV = vIn;

	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (visible) {
			mc.renderEngine.bindTexture(texture);
			if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
				hovered = true;
			} else {
				hovered = false;
			}
			if (hovered) {
				v = newV;
			} else if (!hovered) {
				newV = v;
			}
			if (hovered) {
				drawTexturedModalRect(posX, posY, u, adjV, width, height);
			} else {
				drawTexturedModalRect(posX, posY, u, v, width, height);

			}
		}
	}
}