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
		this.buttonWidth = buttonWidthIn;
		this.buttonHeight = buttonHeightIn;
		this.u = uIn;
		this.v = vIn;
		this.adjV = vIn + buttonHeightIn;
		this.newV = vIn;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (visible) {
			mc.renderEngine.bindTexture(texture);
			if (mouseX >= posX && mouseX <= posX + buttonWidth && mouseY >= posY && mouseY <= posY + buttonHeight) {
				hovered = true;
			} else {
				hovered = false;
			}
			if (hovered) {
				newV = adjV;
			} else if (!hovered) {
				newV = v;
			}
			drawTexturedModalRect(posX, posY, u, newV, buttonWidth, buttonHeight);
		}
	}
}