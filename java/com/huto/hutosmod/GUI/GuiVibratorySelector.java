package com.huto.hutosmod.gui;

import java.awt.image.ImageProducer;
import java.io.IOException;

import com.huto.hutosmod.container.ContainerVibratorySelector;
import com.huto.hutosmod.gui.pages.GuiButtonTextured;
import com.huto.hutosmod.items.runes.ItemContractRuneBeast;
import com.huto.hutosmod.items.runes.ItemContractRuneCorrupt;
import com.huto.hutosmod.items.runes.ItemContractRuneImpure;
import com.huto.hutosmod.items.runes.ItemContractRuneMilkweed;
import com.huto.hutosmod.items.runes.ItemContractRuneRadiance;
import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.network.PacketChiselCraftingEvent;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.network.PacketUpdateChiselRunes;
import com.huto.hutosmod.network.PacketUpdateFrequency;
import com.huto.hutosmod.network.PacketVibratoryCraftingEvent;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntityRuneStation;
import com.huto.hutosmod.tileentity.TileEntityVibratorySelector;
import com.ibm.icu.text.DecimalFormat;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class GuiVibratorySelector extends GuiContainer {
	private static final ResourceLocation GUI_Vibratory_Selector = new ResourceLocation(
			Reference.MODID + ":textures/gui/rune_station.png");
	private final InventoryPlayer playerInv;
	private final TileEntityVibratorySelector te;
	GuiTextField textBox;
	int RESONATEBUTTON = 101;
	GuiButton buttonResonate;
	int guiWidth = 175;
	int guiHeight = 228;
	int left, top;

	public GuiVibratorySelector(InventoryPlayer playerInv, TileEntityVibratorySelector chestInv, EntityPlayer player) {
		super(new ContainerVibratorySelector(playerInv, chestInv, player));
		this.playerInv = playerInv;
		this.te = chestInv;

		this.xSize = 175;
		this.ySize = 186;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(GUI_Vibratory_Selector);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(this.te.getDisplayName().getUnformattedText(), 8, 6, 0);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 000000);

		for (int i = 0; i < 1; i++) {
			int color;

			if (this.te.getStackInSlot(i).getItem() != Items.AIR) {
				this.fontRenderer.drawString(this.te.getStackInSlot(0).getDisplayName(), 27,
						this.ySize - (186 - (18 * i) - 18), 0);

			}

		}

		String manaString = String.format("%.2f", this.te.getManaValue());
		this.fontRenderer.drawString("Mana: " + manaString, 27, this.ySize - (186 - (18) - 10), 471711);

		if (this.te.selectedFrequency > 0) {
			this.fontRenderer.drawString("Frequency: " + String.valueOf(this.te.getSelectedFrequency()), 27,
					this.ySize - (186 - (18) - 18), 3093247);
		} else if (this.te.selectedFrequency < 0) {
			this.fontRenderer.drawString("Frequency: " + String.valueOf(this.te.getSelectedFrequency()), 27,
					this.ySize - (186 - (18) - 18), 7798784);
		} else {
			this.fontRenderer.drawString("Frequency: " + String.valueOf(this.te.getSelectedFrequency()), 27,
					this.ySize - (186 - (18) - 18), 16777215);
		}

	}

	public void updateTextBoxes() {
		if (!textBox.getText().isEmpty()) {
			if (!textBox.isFocused()) {
				float searchNum;
				if (textBox.getText().matches("-[0-9]+")) {
					searchNum = (Float.parseFloat(textBox.getText()));
					te.setSelectedFrequency(searchNum);
					PacketHandler.INSTANCE.sendToServer(new PacketUpdateFrequency(searchNum));

				} else if (textBox.getText().matches("[0-9]+")) {

					searchNum = (Float.parseFloat(textBox.getText()));
					te.setSelectedFrequency(searchNum);
					PacketHandler.INSTANCE.sendToServer(new PacketUpdateFrequency(searchNum));

				} else {
					te.setSelectedFrequency(0);
					PacketHandler.INSTANCE.sendToServer(new PacketUpdateFrequency(0));
				}
			}
		}
		updateButtons();

	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		textBox.mouseClicked(mouseX, mouseY, mouseButton);
		updateTextBoxes();
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public void updateButtons() {

	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		textBox.textboxKeyTyped(typedChar, keyCode);
		updateTextBoxes();
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void initGui() {
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
		textBox = new GuiTextField(0, fontRenderer, left - guiWidth + 205, top + guiHeight - 151, 50, 14);

		buttonList.add(buttonResonate = new GuiButton(RESONATEBUTTON, left + guiWidth - (guiWidth - 85),
				top + guiHeight - (151), 55, 16, "RESONATE"));

		super.initGui();

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		this.allowUserInput = true;
		GlStateManager.disableLighting();
		textBox.drawTextBox();

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == RESONATEBUTTON) {
			System.out.println("CRAFTING");
			PacketHandler.INSTANCE.sendToServer(new PacketVibratoryCraftingEvent());
		}

	}

}
