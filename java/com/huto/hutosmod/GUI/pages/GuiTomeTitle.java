package com.huto.hutosmod.gui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.reference.RegistryHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
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

	GuiButton buttonclose;
	GuiButtonTextured redButton, yellowButton, blueButton, greenButton, whiteButton;
	GuiTextField textBox;
	String title = "TITLE";
	String subtitle = "HUTOSMOD";
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
			GlStateManager.translate((width / 2) - fontRenderer.getStringWidth(title), centerY + 10, 0);
			GlStateManager.scale(1, 1, 1);
			fontRenderer.drawString(title, 0, 0, 8060954);
			fontRenderer.drawString(subtitle, 0, 10, 8060954);

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
		textBox.drawTextBox();
		List<String> text = new ArrayList<String>();
		text.add(I18n.format(icon.getDisplayName()));
		text.add(I18n.format("gui.tooltip69", mc.world.provider.getDimension()));
		// text.add(icon.getDisplayName());
		drawTooltip(text, mouseX, mouseY, centerX, centerY, 16 * 2, 16 * 2);

		List<String> cat1 = new ArrayList<String>();
		cat1.add(I18n.format("Introduction"));
		cat1.add(I18n.format("Magic and its basis"));
		drawTooltip(cat1, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174, 16, 16);

		List<String> cat2 = new ArrayList<String>();
		cat2.add(I18n.format("The World"));
		cat2.add(I18n.format("Mana All Around Us"));
		drawTooltip(cat2, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174 + 18, 16, 16);

		List<String> cat3 = new ArrayList<String>();
		cat3.add(I18n.format("Equipables"));
		cat3.add(I18n.format("Mystical Wearables"));
		drawTooltip(cat3, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174 + 36, 16, 16);

		List<String> cat4 = new ArrayList<String>();
		cat4.add(I18n.format("Wands"));
		cat4.add(I18n.format("Magical Conduction"));
		drawTooltip(cat4, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174 + 54, 16, 16);

		List<String> cat5 = new ArrayList<String>();
		cat5.add(I18n.format("Runes"));
		cat5.add(I18n.format("Chiseling your own mind"));
		drawTooltip(cat5, mouseX, mouseY, left + guiWidth - 88 - 72, top + guiHeight - 174 + 72, 16, 16);
	}

	public void drawTooltip(List<String> lines, int mouseX, int mouseY, int posX, int posY, int width, int height) {
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
			drawHoveringText(lines, mouseX, mouseY);
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
		
		textBox = new GuiTextField(0, fontRenderer, left - guiWidth + 155, top + guiHeight - 227, 14, 14);

	}

	public void updateButtons() {
		buttonclose.enabled = true;

	}

	public void updateTextBoxes() {

		if (!textBox.getText().isEmpty()) {
			if (!textBox.isFocused()) {
				int searchNum = Integer.parseInt(textBox.getText());
				if (searchNum < RegistryHandler.IntroPageList.size()) {
					mc.displayGuiScreen(RegistryHandler.IntroPageList.get(searchNum));
				} else if (searchNum >= RegistryHandler.IntroPageList.size()) {
					mc.displayGuiScreen(RegistryHandler.IntroPageList.get(RegistryHandler.IntroPageList.size() - 1));
				}

			}
		}

		updateButtons();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case BUTTONCLOSE:
			mc.displayGuiScreen(null);
			break;
		case BUTTONRED:
			mc.displayGuiScreen(RegistryHandler.IntroPageList.get(0));
			break;
		case BUTTONYELLOW:
			mc.displayGuiScreen(RegistryHandler.IntroPageList.get(1));
			break;
		case BUTTONBLUE:
			mc.displayGuiScreen(RegistryHandler.IntroPageList.get(2));
			break;
		case BUTTONGREEN:
			mc.displayGuiScreen(RegistryHandler.IntroPageList.get(3));
			break;
		case BUTTONWHITE:
			mc.displayGuiScreen(RegistryHandler.IntroPageList.get(4));
			break;

		}

		updateButtons();
		super.actionPerformed(button);

	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		textBox.textboxKeyTyped(typedChar, keyCode);
		updateTextBoxes();
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		textBox.mouseClicked(mouseX, mouseY, mouseButton);
		updateTextBoxes();
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}