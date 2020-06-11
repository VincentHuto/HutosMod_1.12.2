package com.huto.hutosmod.gui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.reference.RegistryHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTomeTitle extends GuiScreen {

	final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/gui/book.png");

	int guiWidth = 175;
	int guiHeight = 228;
	int left, top;
	final int BUTTONCLOSE = 0;
	final int BUTTONRED = 1;
	final int BUTTONYELLOW = 2;
	final int BUTTONBLUE = 3;
	final int BUTTONGREEN = 4;
	final int BUTTONWHITE = 5;
	FontRenderer akloRenderer = ModTextFormatting.getAkloFont();

	GuiButton buttonclose;
	GuiButtonTextured redButton, yellowButton, blueButton, greenButton, whiteButton;
	String title = "Table of Contents";
	String subtitle = "Hutos Mod";
	ItemStack icon = new ItemStack(ItemRegistry.mana_crystal);

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		drawDefaultBackground();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		int centerX = (width / 2) - guiWidth / 2;
		int centerY = (height / 2) - guiHeight / 2;
		GlStateManager.pushMatrix();
		{
			GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.color(1, 1, 1, 1);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
		}
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate((width / 2) - fontRenderer.getStringWidth(title) / 2, centerY + 10, 0);
			GlStateManager.scale(1, 1, 1);
			fontRenderer.drawString(ModTextFormatting.stringToGolden(title, 0, false), 0, 0, 8060954);
			fontRenderer.drawString(ModTextFormatting.stringToGolden(subtitle, 5, false), 0, 10, 8060954);

		}
		GlStateManager.popMatrix();

		// super.drawScreen(mouseX, mouseY, partialTicks);
		buttonclose.drawButton(mc, mouseX, mouseY, 16);
		redButton.drawButton(mc, mouseX, mouseY, 16);
		yellowButton.drawButton(mc, mouseX, mouseY, 16);
		blueButton.drawButton(mc, mouseX, mouseY, 16);
		greenButton.drawButton(mc, mouseX, mouseY, 16);
		whiteButton.drawButton(mc, mouseX, mouseY, 16);

		GlStateManager.translate(3, 0, 0);
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(centerX, centerY, 0);
			GlStateManager.scale(2, 2, 2);
			mc.getRenderItem().renderItemAndEffectIntoGUI(icon, 0, 0);
		}
		GlStateManager.popMatrix();
		List<String> text = new ArrayList<String>();
		text.add(I18n.format(icon.getDisplayName()));
		text.add(I18n.format("gui.tooltip69", mc.world.provider.getDimension()));
		// text.add(icon.getDisplayName());
		drawTooltip(text, mouseX, mouseY, centerX, centerY, 16 * 2, 16 * 2, false);

		List<String> cat1 = new ArrayList<String>();
		cat1.add(I18n.format("Introduction"));
		cat1.add(I18n.format("Magic and its basis"));
		drawTooltip(cat1, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174, 16, 16, true);

		List<String> cat2 = new ArrayList<String>();
		cat2.add(I18n.format("The World"));
		cat2.add(I18n.format("Mana All Around Us"));
		drawTooltip(cat2, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174 + 18, 16, 16, true);

		List<String> cat3 = new ArrayList<String>();
		cat3.add(I18n.format("Equipables"));
		cat3.add(I18n.format("Mystical Wearables"));
		drawTooltip(cat3, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174 + 36, 16, 16, true);

		List<String> cat4 = new ArrayList<String>();
		cat4.add(I18n.format("Wands"));
		cat4.add(I18n.format("Magical Conduction"));
		drawTooltip(cat4, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174 + 54, 16, 16, true);

		List<String> cat5 = new ArrayList<String>();
		cat5.add(I18n.format("Runes"));
		cat5.add(I18n.format("Chiseling your own mind"));
		drawTooltip(cat5, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174 + 72, 16, 16, true);
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
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
		int sideLoc = left + guiWidth - 88 - 72;
		int verticalLoc = top + guiHeight - 174;

		buttonList.clear();
		buttonList.add(buttonclose = new GuiButton(BUTTONCLOSE, left + guiWidth - 135, top + guiHeight - 21, 100, 20,
				"Close"));
		buttonList.add(
				redButton = new GuiButtonTextured(texture, BUTTONRED, sideLoc, top + guiHeight - 174, 16, 16, 175, 32));
		buttonList.add(yellowButton = new GuiButtonTextured(texture, BUTTONYELLOW, sideLoc, verticalLoc + 18, 16, 16,
				191, 32));
		buttonList.add(
				blueButton = new GuiButtonTextured(texture, BUTTONBLUE, sideLoc, verticalLoc + 36, 16, 16, 207, 32));
		buttonList.add(
				greenButton = new GuiButtonTextured(texture, BUTTONGREEN, sideLoc, verticalLoc + 54, 16, 16, 223, 32));
		buttonList.add(
				whiteButton = new GuiButtonTextured(texture, BUTTONWHITE, sideLoc, verticalLoc + 72, 16, 16, 239, 32));

	}

	public void updateButtons() {
		buttonclose.enabled = true;

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case BUTTONCLOSE:
			mc.displayGuiScreen(null);
			break;
		case BUTTONRED:
			mc.displayGuiScreen(TomePageLib.IntroPageList.get(0));
			break;
		case BUTTONYELLOW:
			mc.displayGuiScreen(TomePageLib.WorldGenPageList.get(0));
			break;
		case BUTTONBLUE:
			mc.displayGuiScreen(TomePageLib.ArmorPageList.get(0));
			break;
		case BUTTONGREEN:
			mc.displayGuiScreen(TomePageLib.WandsPageList.get(0));
			break;
		case BUTTONWHITE:
			mc.displayGuiScreen(TomePageLib.RunesPageList.get(0));
			break;

		}

		updateButtons();
		super.actionPerformed(button);

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}