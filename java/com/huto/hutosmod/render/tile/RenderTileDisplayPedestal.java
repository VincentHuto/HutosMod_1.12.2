package com.huto.hutosmod.render.tile;

import java.text.DecimalFormat;

import javax.annotation.Nonnull;

import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.tileentity.TileEntityDisplayPedestal;
import com.huto.hutosmod.tileentity.TileEntityWandMaker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class RenderTileDisplayPedestal extends TileEntitySpecialRenderer<TileEntityDisplayPedestal> {
	@Override
	public void render(@Nonnull TileEntityDisplayPedestal te, double x, double y, double z, float partticks, int digProgress,
			float unused) {

		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);

		int items = 0;
		for (int i = 0; i < te.getSizeInventory(); i++)
			if (te.getItemHandler().getStackInSlot(i).isEmpty())
				break;
			else
				items++;
		float[] angles = new float[te.getSizeInventory()];

		float anglePer = 360F / items;
		float totalAngle = 0F;
		for (int i = 0; i < angles.length; i++)
			angles[i] = totalAngle += anglePer;

		double time = ClientTickHandler.ticksInGame + partticks;

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		for (int i = 0; i < te.getSizeInventory(); i++) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.5F, 1.25F, 0.5F);
			GlStateManager.rotate(angles[i] + (float) time, 0F, 1F, 0F);

			// Edit True Radius
			GlStateManager.translate(0.025F, -0.5F, 0.025F);
			GlStateManager.rotate(90F, 0F, 1F, 0F);
			// Edit Radius Movement
			GlStateManager.translate(0D, 0.175D + i * 0.25, 0F);
			// Block/Item Scale
			GlStateManager.scale(0.5, 0.5, 0.5);
			ItemStack stack = te.getItemHandler().getStackInSlot(i);
			Minecraft mc = Minecraft.getMinecraft();
			if (!stack.isEmpty()) {
				mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
			}
			GlStateManager.popMatrix();
		}

		GlStateManager.disableAlpha();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5F, .85F, 0.5F);
		GlStateManager.rotate(180F, 1F, 0F, 1F);
		GlStateManager.scale(0.25F, 0.25F, 0.25F);

		GlStateManager.enableAlpha();

		GlStateManager.popMatrix();

		GlStateManager.enableAlpha();

		String text =te.getItemHandler().getStackInSlot(0).getDisplayName();
		GlStateManager.scale(0.3, 0.3, 0.3);
		GlStateManager.translate(0, 4, 0.5);
		GlStateManager.rotate(180, 1, 0, 1);
		GlStateManager.scale(0.1, 0.1, 0.1);
		FontRenderer fontRenderer = this.getFontRenderer();
		FontRenderer akloRenderer = ModTextFormatting.getAkloFont();
		fontRenderer.drawSplitString(text, 0, (int) (fontRenderer.FONT_HEIGHT),50, 0xFFFF00FF);
		GlStateManager.popMatrix();

	}
}
