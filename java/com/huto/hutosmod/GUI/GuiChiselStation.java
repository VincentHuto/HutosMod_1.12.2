package com.huto.hutosmod.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.container.ContainerChiselStation;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.gui.pages.GuiButtonTextured;
import com.huto.hutosmod.items.runes.ItemContractRuneBeast;
import com.huto.hutosmod.items.runes.ItemContractRuneCorrupt;
import com.huto.hutosmod.items.runes.ItemContractRuneImpure;
import com.huto.hutosmod.items.runes.ItemContractRuneMilkweed;
import com.huto.hutosmod.items.runes.ItemContractRuneRadiance;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntityChiselStation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class GuiChiselStation extends GuiContainer {
	private static final ResourceLocation GUI_Chisel = new ResourceLocation(
			Reference.MODID + ":textures/gui/chisel_station.png");
	private final InventoryPlayer playerInv;
	private final TileEntityChiselStation te;
	int left, top;
	int guiWidth = 176;
	int guiHeight = 186;
	final int BUTTONTEST = 0;

	FontRenderer akloRenderer = ModTextFormatting.getAkloFont();

	public GuiChiselStation(InventoryPlayer playerInv, TileEntityChiselStation chestInv, EntityPlayer player) {
		super(new ContainerChiselStation(playerInv, chestInv, player));
		this.playerInv = playerInv;
		this.te = chestInv;
		this.xSize = 176;
		this.ySize = 186;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.allowUserInput = true;
		Minecraft.getMinecraft().renderEngine.bindTexture(GUI_Chisel);
		int centerX = (width / 2) - guiWidth / 2;
		int centerY = (height / 2) - guiHeight / 2;
		GlStateManager.pushMatrix();
		{
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.color(1, 1, 1, 1);
			Minecraft.getMinecraft().renderEngine.bindTexture(GUI_Chisel);
		}
		GlStateManager.popMatrix();
		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).drawButton(mc, mouseX, mouseY, 10);
		}

		List<String> cat9 = new ArrayList<String>();
		cat9.add(I18n.format("Close Book"));
	//	drawTooltip(cat9, mouseX, mouseY, left + guiWidth - (guiWidth - 70), top + guiHeight - 130, 16, 16, false);
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);

	}

	public void drawTooltip(List<String> lines, int mouseX, int mouseY, int posX, int posY, int width, int height,
			boolean obscu) {
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
			if (obscu) {
				drawAlkoHoveringText(lines, mouseX, mouseY, akloRenderer);
			} else {
				drawHoveringText(lines, mouseX, mouseY, fontRenderer);
			}
		}
	}

	protected void drawAlkoHoveringText(List<String> textLines, int x, int y, FontRenderer font) {
		net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(textLines, x, y, width, height, -1, font);
		if (!textLines.isEmpty()) {
			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			int i = 0;

			for (String s : textLines) {
				int j = font.getStringWidth(s);

				if (j > i) {
					i = j;
				}
			}

			int l1 = x + 12;
			int i2 = y - 12;
			int k = 8;

			if (textLines.size() > 1) {
				k += 2 + (textLines.size() - 1) * 10;
			}

			if (l1 + i > this.width) {
				l1 -= 28 + i;
			}

			if (i2 + k + 6 > this.height) {
				i2 = this.height - k - 6;
			}

			this.zLevel = 300.0F;
			this.itemRender.zLevel = 300.0F;
			int l = -267386864;
			this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
			this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
			this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
			this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
			this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
			int i1 = 1347420415;
			int j1 = 1344798847;
			this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
			this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
			this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
			this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);

			for (int k1 = 0; k1 < textLines.size(); ++k1) {
				String s1 = textLines.get(k1);
				font.drawStringWithShadow(s1, (float) l1, (float) i2, -1);

				if (k1 == 0) {
					i2 += 2;
				}

				i2 += 10;
			}

			this.zLevel = 0.0F;
			this.itemRender.zLevel = 0.0F;
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.enableRescaleNormal();
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
		int sideLoc = left + guiWidth;
		int verticalLoc = top + guiHeight;

		buttonList.clear();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				buttonList.add(new GuiButtonTextured(GUI_Chisel, BUTTONTEST,
						left + guiWidth - (guiWidth - 50 - (i * 8)), top + guiHeight - (160 - (j * 8)), 8, 8, 176, 0));
			}
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(GUI_Chisel);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(this.te.getDisplayName().getUnformattedText(), 8, 6, 65444444);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 000000);

		for (int i = 0; i < te.getSizeInventory(); i++) {
			int color;
			if (this.te.getStackInSlot(i).getItem() instanceof ItemContractRuneBeast) {

				color = 55555555;
			} else if (this.te.getStackInSlot(i).getItem() instanceof ItemContractRuneImpure) {

				color = 55555555;

			} else if (this.te.getStackInSlot(i).getItem() instanceof ItemContractRuneCorrupt) {

				color = 55555555;
			} else if (this.te.getStackInSlot(i).getItem() instanceof ItemContractRuneMilkweed) {

				color = 55555555;
			} else if (this.te.getStackInSlot(i).getItem() instanceof ItemContractRuneRadiance) {

				color = 55555555;
			} else {

				color = 10000000;
			}

			if (this.te.getStackInSlot(i).getItem() != Items.AIR) { // ysize-yheight-(item spacing)-initial offset
				this.fontRenderer.drawString(this.te.getStackInSlot(i).getDisplayName(), 27,
						this.ySize - (186 - (18 * i) - 18), color);

			}
		}

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case BUTTONTEST:
			mc.displayGuiScreen(null);
			break;
		}
		super.actionPerformed(button);

	}

}
