package com.huto.hutosmod.gui;

import java.awt.image.ImageProducer;

import com.huto.hutosmod.container.ContainerRuneStation;
import com.huto.hutosmod.items.runes.ItemContractRuneBeast;
import com.huto.hutosmod.items.runes.ItemContractRuneCorrupt;
import com.huto.hutosmod.items.runes.ItemContractRuneImpure;
import com.huto.hutosmod.items.runes.ItemContractRuneMilkweed;
import com.huto.hutosmod.items.runes.ItemContractRuneRadiance;
import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntityRuneStation;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class GuiRuneStation extends GuiContainer {
	private static final ResourceLocation GUI_Rune_Station = new ResourceLocation(
			Reference.MODID + ":textures/gui/rune_station.png");
	private final InventoryPlayer playerInv;
	private final TileEntityRuneStation te;

	public GuiRuneStation(InventoryPlayer playerInv, TileEntityRuneStation chestInv, EntityPlayer player) {
		super(new ContainerRuneStation(playerInv, chestInv, player));
		this.playerInv = playerInv;
		this.te = chestInv;

		this.xSize = 175;
		this.ySize = 186;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		GlStateManager.color(1.0f, 1.0f, 1.0f);
		this.drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(GUI_Rune_Station);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(this.te.getDisplayName().getUnformattedText(), 8, 6, 65444444);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 000000);
		String manaString = String.format("%.2f", this.te.getManaValue());
		this.fontRenderer.drawString("Mana: " + manaString, 100, 6, 471711);
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
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		this.allowUserInput = true;
	}

}
