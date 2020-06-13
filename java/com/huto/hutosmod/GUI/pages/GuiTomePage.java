package com.huto.hutosmod.gui.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.font.ModTextFormatting;
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
	int left, top;
	final int BUTTONCLOSE = 0, ARROWF = 1, ARROWB = 2, TITLEBUTTON = 3;

	GuiButton buttonclose;
	GuiButtonArrowForward arrowF;
	GuiButtonArrowBackward arrowB;
	GuiButtonTextured buttonTitle;
	GuiTextField textBox;

	int pageNum;
	String title;
	String subtitle;
	ItemStack icon;
	String text;
	EnumTomeCatagories catagory;

	public GuiTomePage(int pageNumIn, EnumTomeCatagories catagoryIn, String titleIn, String subtitleIn,
			ItemStack iconIn, String textIn) {
		this.title = titleIn;
		this.subtitle = subtitleIn;
		this.icon = iconIn;
		this.text = textIn;
		this.pageNum = pageNumIn;
		this.catagory = catagoryIn;
	}

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
			GlStateManager.translate((width / 2)  -40, centerY + 10, 0);
			GlStateManager.scale(1, 1, 1);
			fontRenderer.drawString("Pg." + String.valueOf(pageNum+1), 90, 0, 0000000);
			fontRenderer.drawString(title, 0, 0, 8060954);
			fontRenderer.drawString(subtitle, 0, 10, 8060954);

		}
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		{
			GlStateManager.translate((width / 2) - 20, centerY + 10, 0);
			GlStateManager.scale(0.9, 1, 1);
			GlStateManager.translate(-65, 20, 0);
			//This statment allows switching all text from english to alko
			/*if(false) {
			this.fontRenderer = ModTextFormatting.getAkloFont();
			}*/
			fontRenderer.drawSplitString(text, 0, 0, 175, 0);

		}
		GlStateManager.popMatrix();
		buttonclose.drawButton(mc, mouseX, mouseY, 111);
		if (this.catagory == EnumTomeCatagories.INTRO) {
			if (pageNum != (TomePageLib.IntroPageList.size() - 1)) {
				arrowF.drawButton(mc, mouseX, mouseY, 111);
			}
		}
		if (this.catagory == EnumTomeCatagories.EQUIPS) {
			if (pageNum != (TomePageLib.ArmorPageList.size() - 1)) {
				arrowF.drawButton(mc, mouseX, mouseY, 111);
			}
		}
		if (this.catagory == EnumTomeCatagories.KARMA) {
			if (pageNum != (TomePageLib.KarmaPageList.size() - 1)) {
				arrowF.drawButton(mc, mouseX, mouseY, 111);
			}
		}
		if (this.catagory == EnumTomeCatagories.WORLDGEN) {
			if (pageNum != (TomePageLib.WorldGenPageList.size() - 1)) {
				arrowF.drawButton(mc, mouseX, mouseY, 111);
			}
		}
		if (this.catagory == EnumTomeCatagories.MACHINES) {
			if (pageNum != (TomePageLib.BlocksPageList.size() - 1)) {
				arrowF.drawButton(mc, mouseX, mouseY, 111);
			}
		}
		if (this.catagory == EnumTomeCatagories.RUNES) {
			if (pageNum != (TomePageLib.RunesPageList.size() - 1)) {
				arrowF.drawButton(mc, mouseX, mouseY, 111);
			}
		}
		if (this.catagory == EnumTomeCatagories.WANDS) {
			if (pageNum != (TomePageLib.WandsPageList.size() - 1)) {
				arrowF.drawButton(mc, mouseX, mouseY, 111);
			}
		}
		if (pageNum != 0) {

			arrowB.drawButton(mc, mouseX, mouseY, 211);
		}
		buttonTitle.drawButton(mc, mouseX, mouseY, 311);
		GlStateManager.translate(3, 0, 0);
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(centerX, centerY, 0);
			GlStateManager.translate(3, 3, 0);
			GlStateManager.scale(1.9, 1.7, 1.9);
			mc.getRenderItem().renderItemAndEffectIntoGUI(icon, 0, 0);
		}
		GlStateManager.popMatrix();
		textBox.drawTextBox();
		List<String> text = new ArrayList<String>();
		text.add(I18n.format(icon.getDisplayName()));
		text.add(I18n.format("gui.tooltip69", mc.world.provider.getDimension()));
		// text.add(icon.getDisplayName());
		drawTooltip(text, mouseX, mouseY, centerX, centerY, 16 * 2, 16 * 2);

		List<String> titlePage = new ArrayList<String>();
		titlePage.add(I18n.format("Title"));
		titlePage.add(I18n.format("Return to Catagories"));
		drawTooltip(titlePage, mouseX, mouseY, left - guiWidth + 157, top + guiHeight - 209, 16, 16);
	}

	public void drawTooltip(List<String> lines, int mouseX, int mouseY, int posX, int posY, int width, int height) {
		if (mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height) {
			drawHoveringText(lines, mouseX, mouseY);
		}
	}

	int i = (this.width - 175) / 2;
	int j = this.height / 2;

	@Override
	public void initGui() {
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
		buttonList.clear();
		buttonList.add(buttonclose = new GuiButton(BUTTONCLOSE, left + guiWidth - 135, top + guiHeight - 21, 100, 20,
				"Close"));
		if (this.catagory == EnumTomeCatagories.INTRO) {
			if (pageNum != (TomePageLib.IntroPageList.size() - 1)) {
				buttonList.add(arrowF = new GuiButtonArrowForward(ARROWF, left + guiWidth - 18, top + guiHeight - 10));
			}
			if (pageNum != 0) {
				buttonList.add(arrowB = new GuiButtonArrowBackward(ARROWB, left, top + guiHeight - 10));
			}
		}

		if (this.catagory == EnumTomeCatagories.EQUIPS) {
			if (pageNum != (TomePageLib.ArmorPageList.size() - 1)) {
				buttonList.add(arrowF = new GuiButtonArrowForward(ARROWF, left + guiWidth - 18, top + guiHeight - 10));
			}
			if (pageNum != 0) {
				buttonList.add(arrowB = new GuiButtonArrowBackward(ARROWB, left, top + guiHeight - 10));
			}
		}

		if (this.catagory == EnumTomeCatagories.KARMA) {
			if (pageNum != (TomePageLib.KarmaPageList.size() - 1)) {
				buttonList.add(arrowF = new GuiButtonArrowForward(ARROWF, left + guiWidth - 18, top + guiHeight - 10));
			}
			if (pageNum != 0) {
				buttonList.add(arrowB = new GuiButtonArrowBackward(ARROWB, left, top + guiHeight - 10));
			}
		}

		if (this.catagory == EnumTomeCatagories.MACHINES) {
			if (pageNum != (TomePageLib.BlocksPageList.size() - 1)) {
				buttonList.add(arrowF = new GuiButtonArrowForward(ARROWF, left + guiWidth - 18, top + guiHeight - 10));
			}
			if (pageNum != 0) {
				buttonList.add(arrowB = new GuiButtonArrowBackward(ARROWB, left, top + guiHeight - 10));
			}
		}
		if (this.catagory == EnumTomeCatagories.WANDS) {
			if (pageNum != (TomePageLib.WandsPageList.size() - 1)) {
				buttonList.add(arrowF = new GuiButtonArrowForward(ARROWF, left + guiWidth - 18, top + guiHeight - 10));
			}
			if (pageNum != 0) {
				buttonList.add(arrowB = new GuiButtonArrowBackward(ARROWB, left, top + guiHeight - 10));
			}
		}
		if (this.catagory == EnumTomeCatagories.WORLDGEN) {
			if (pageNum != (TomePageLib.WorldGenPageList.size() - 1)) {
				buttonList.add(arrowF = new GuiButtonArrowForward(ARROWF, left + guiWidth - 18, top + guiHeight - 10));
			}
			if (pageNum != 0) {
				buttonList.add(arrowB = new GuiButtonArrowBackward(ARROWB, left, top + guiHeight - 10));
			}
		}

		buttonList.add(buttonTitle = new GuiButtonTextured(texture, TITLEBUTTON, left - guiWidth + 157,
				top + guiHeight - 209, 16, 16, 174, 64));

		textBox = new GuiTextField(0, fontRenderer, left - guiWidth + 155, top + guiHeight - 227, 14, 14);
		updateButtons();
		super.initGui();

	}

	public void updateButtons() {
		buttonclose.enabled = true;
	}

	public void updateTextBoxes() {
		if (!textBox.getText().isEmpty()) {
			if (!textBox.isFocused()) {
				int searchNum = (Integer.parseInt(textBox.getText())-1);

				if (this.catagory == EnumTomeCatagories.INTRO) {
					if (searchNum < TomePageLib.IntroPageList.size()) {
						mc.displayGuiScreen(TomePageLib.IntroPageList.get(searchNum));
					} else if (searchNum >= TomePageLib.IntroPageList.size()) {
						mc.displayGuiScreen(TomePageLib.IntroPageList.get(TomePageLib.IntroPageList.size() - 1));
					}
				}

				if (this.catagory == EnumTomeCatagories.WORLDGEN) {
					if (searchNum < TomePageLib.WorldGenPageList.size()) {
						mc.displayGuiScreen(TomePageLib.WorldGenPageList.get(searchNum));
					} else if (searchNum >= TomePageLib.WorldGenPageList.size()) {
						mc.displayGuiScreen(TomePageLib.WorldGenPageList.get(TomePageLib.WorldGenPageList.size() - 1));
					}
				}

				if (this.catagory == EnumTomeCatagories.EQUIPS) {
					if (searchNum < TomePageLib.ArmorPageList.size()) {
						mc.displayGuiScreen(TomePageLib.ArmorPageList.get(searchNum));
					} else if (searchNum >= TomePageLib.ArmorPageList.size()) {
						mc.displayGuiScreen(TomePageLib.ArmorPageList.get(TomePageLib.ArmorPageList.size() - 1));
					}
				}

				if (this.catagory == EnumTomeCatagories.KARMA) {
					if (searchNum < TomePageLib.KarmaPageList.size()) {
						mc.displayGuiScreen(TomePageLib.KarmaPageList.get(searchNum));
					} else if (searchNum >= TomePageLib.KarmaPageList.size()) {
						mc.displayGuiScreen(TomePageLib.KarmaPageList.get(TomePageLib.KarmaPageList.size() - 1));
					}
				}

				if (this.catagory == EnumTomeCatagories.MACHINES) {
					if (searchNum < TomePageLib.BlocksPageList.size()) {
						mc.displayGuiScreen(TomePageLib.BlocksPageList.get(searchNum));
					} else if (searchNum >= TomePageLib.BlocksPageList.size()) {
						mc.displayGuiScreen(TomePageLib.BlocksPageList.get(TomePageLib.BlocksPageList.size() - 1));
					}
				}

				if (this.catagory == EnumTomeCatagories.WANDS) {
					if (searchNum < TomePageLib.WandsPageList.size()) {
						mc.displayGuiScreen(TomePageLib.WandsPageList.get(searchNum));
					} else if (searchNum >= TomePageLib.WandsPageList.size()) {
						mc.displayGuiScreen(TomePageLib.WandsPageList.get(TomePageLib.RunesPageList.size() - 1));
					}
				}

				if (this.catagory == EnumTomeCatagories.RUNES) {
					if (searchNum < TomePageLib.RunesPageList.size()) {
						mc.displayGuiScreen(TomePageLib.RunesPageList.get(searchNum));
					} else if (searchNum >= TomePageLib.RunesPageList.size()) {
						mc.displayGuiScreen(TomePageLib.RunesPageList.get(TomePageLib.RunesPageList.size() - 1));
					}
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
		case ARROWF:
			if (this.catagory == EnumTomeCatagories.INTRO) {
				if (pageNum != (TomePageLib.IntroPageList.size() - 1)) {
					mc.displayGuiScreen(TomePageLib.IntroPageList.get((pageNum + 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.IntroPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.EQUIPS) {
				if (pageNum != (TomePageLib.ArmorPageList.size() - 1)) {
					mc.displayGuiScreen(TomePageLib.ArmorPageList.get((pageNum + 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.ArmorPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.KARMA) {
				if (pageNum != (TomePageLib.KarmaPageList.size() - 1)) {
					mc.displayGuiScreen(TomePageLib.KarmaPageList.get((pageNum + 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.KarmaPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.MACHINES) {
				if (pageNum != (TomePageLib.BlocksPageList.size() - 1)) {
					mc.displayGuiScreen(TomePageLib.BlocksPageList.get((pageNum + 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.BlocksPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.WANDS) {
				if (pageNum != (TomePageLib.WandsPageList.size() - 1)) {
					mc.displayGuiScreen(TomePageLib.WandsPageList.get((pageNum + 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.WandsPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.WORLDGEN) {
				if (pageNum != (TomePageLib.WorldGenPageList.size() - 1)) {
					mc.displayGuiScreen(TomePageLib.WorldGenPageList.get((pageNum + 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.WorldGenPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.RUNES) {
				if (pageNum != (TomePageLib.RunesPageList.size() - 1)) {
					mc.displayGuiScreen(TomePageLib.RunesPageList.get((pageNum + 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.RunesPageList.get((pageNum)));
					break;
				}
			}
		case ARROWB:
			if (this.catagory == EnumTomeCatagories.INTRO) {
				if (pageNum != 0) {
					mc.displayGuiScreen(TomePageLib.IntroPageList.get((pageNum - 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.IntroPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.EQUIPS) {
				if (pageNum != 0) {
					mc.displayGuiScreen(TomePageLib.ArmorPageList.get((pageNum - 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.ArmorPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.KARMA) {
				if (pageNum != 0) {
					mc.displayGuiScreen(TomePageLib.KarmaPageList.get((pageNum - 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.KarmaPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.MACHINES) {
				if (pageNum != 0) {
					mc.displayGuiScreen(TomePageLib.BlocksPageList.get((pageNum - 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.BlocksPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.WANDS) {
				if (pageNum != 0) {
					mc.displayGuiScreen(TomePageLib.WandsPageList.get((pageNum - 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.WandsPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.WORLDGEN) {
				if (pageNum != 0) {
					mc.displayGuiScreen(TomePageLib.WorldGenPageList.get((pageNum - 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.WorldGenPageList.get((pageNum)));
					break;
				}
			}
			if (this.catagory == EnumTomeCatagories.RUNES) {
				if (pageNum != 0) {
					mc.displayGuiScreen(TomePageLib.RunesPageList.get((pageNum - 1)));
					break;
				} else {
					mc.displayGuiScreen(TomePageLib.RunesPageList.get((pageNum)));
					break;
				}
			}

		case TITLEBUTTON:
			mc.displayGuiScreen(new GuiTomeTitleNew());
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