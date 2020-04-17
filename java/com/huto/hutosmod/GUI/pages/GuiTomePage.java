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
public class GuiTomePage extends GuiScreen {

	final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/gui/book.png");
	int guiWidth = 175;
	int guiHeight = 228;

	GuiButton button1;
	GuiButtonArrowForward arrowF;
	GuiButtonArrowBackward arrowB;
	GuiTextField textBox;
	String title = "Default title";
	int pageNum = Integer.parseInt(getClass().getSimpleName().substring(11));
	final int BUTTON1 = 0, ARROWF = 1, ARROWB = 2;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		int centerX = (width / 2) - guiWidth / 2;
		int centerY = (height / 2) - guiHeight / 2;
		// drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
		// drawString(fontRendererObj, "Tutorial", centerX, centerY, 0x6028ff);
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
			GlStateManager.scale(2, 2, 2);
			fontRenderer.drawString(title, 0, 0, 0x6028ff);
		}
		GlStateManager.popMatrix();
		// super.drawScreen(mouseX, mouseY, partialTicks);
		button1.drawButton(mc, mouseX, mouseY, 111);
		arrowF.drawButton(mc, mouseX, mouseY, 111);
		arrowB.drawButton(mc, mouseX, mouseY, 211);

		GlStateManager.translate(3, 0, 0);
		ItemStack icon = new ItemStack(ItemRegistry.channeling_ingot);
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
		//text.add(icon.getDisplayName());
		drawTooltip(text, mouseX, mouseY, centerX, centerY, 16 * 2, 16 * 2);
	}

	public void drawTooltip(List<String> lines, int mouseX, int mouseY, int posX, int posY, int width, int height) {
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
			drawHoveringText(lines, mouseX, mouseY);
		}
	}
    int i = (this.guiWidth-192) / 2;
    int j = (this.guiHeight - 192) / 2;

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(button1 = new GuiButton(BUTTON1, (width / 2) - 100 / 2, height - 50, 100, 20, "Close"));
		buttonList.add(arrowF = new GuiButtonArrowForward(ARROWF, i+290, j+200));
		buttonList.add(arrowB = new GuiButtonArrowBackward(ARROWB, i+140, j+200));
		textBox = new GuiTextField(0, fontRenderer, 0, 0, 100, 20);

		updateButtons();
		if (!RegistryHandler.pageList.contains(getClass().getSimpleName().replace("Gui", "").replace("Tome", " "))) {
			RegistryHandler.pageList.add(getClass().getSimpleName().replace("Gui", "").replace("Tome", " "));
		}
		super.initGui();

	}

	public void updateButtons() {
		button1.enabled = true;
	}

	public void updateTextBoxes() {
		if (!textBox.getText().isEmpty()) {
			if (!textBox.isFocused()) {
				String page = textBox.getText();

				if (page.contains("0")) {
					mc.displayGuiScreen(new GuiPageTome0());

				} else if (page.contains("1")) {
					mc.displayGuiScreen(new GuiPageTome1());

				} else if (page.contains("2")) {
					mc.displayGuiScreen(new GuiPageTome2());

				} else if (page.contains("3")) {
					mc.displayGuiScreen(new GuiPageTome3());

				} else if (page.contains("4")) {
					mc.displayGuiScreen(new GuiPageTome4());

				}
			}
		}
		updateButtons();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		switch (button.id) {

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