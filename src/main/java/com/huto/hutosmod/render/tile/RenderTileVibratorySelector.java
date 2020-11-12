package com.huto.hutosmod.render.tile;

import java.text.DecimalFormat;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.tileentity.TileEntityDisplayPedestal;
import com.huto.hutosmod.tileentity.TileEntityVibratorySelector;
import com.huto.hutosmod.tileentity.TileEntityWandMaker;
import com.huto.hutosmod.tileentity.TileModMana;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class RenderTileVibratorySelector extends TileEntitySpecialRenderer<TileEntityVibratorySelector> {
	@Override
	public void render(@Nonnull TileEntityVibratorySelector te, double x, double y, double z, float partticks,
			int digProgress, float unused) {

		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);

		int items = 1;
		for (int i = 0; i < te.getSizeInventory(); i++)
			if (te.getStackInSlot(i).isEmpty())
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
		for (int i = 0; i < 1; i++) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.5F, 0.9F, 0.5F);
			GlStateManager.rotate(angles[i] + (float) time, 0F, 1F, 0F);

			// Edit True Radius
			GlStateManager.translate(0.025F, -0.5F, 0.025F);
			GlStateManager.rotate(90F, 0F, 1F, 0F);
			// Edit Radius Movement
			GlStateManager.translate(0D, 0.175D + i * 0.25, 0F);
			// Block/Item Scale
			GlStateManager.scale(0.5, 0.5, 0.5);
			ItemStack stack = te.getStackInSlot(0);
			Minecraft mc = Minecraft.getMinecraft();
			if (!stack.isEmpty()) {
				mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
				te.sendUpdates();
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
		DecimalFormat df = new DecimalFormat("0.00");
		String text1 = df.format(te.getManaValue());
		String text = te.getStackInSlot(0).getDisplayName();
		GlStateManager.scale(0.3, 0.3, 0.3);
		GlStateManager.translate(0, 4, 0.5);
		GlStateManager.rotate(180, 1, 0, 1);
		GlStateManager.scale(0.1, 0.1, 0.1);
		FontRenderer fontRenderer = this.getFontRenderer();
		FontRenderer akloRenderer = ModTextFormatting.getAkloFont();

		if (isPlayerHoverWithDebug(te.getWorld())) {

			fontRenderer.drawSplitString(text, 0, (int) (fontRenderer.FONT_HEIGHT), 50, 0xFFFF00FF);
			GlStateManager.translate(0, -6, 0.5);
			fontRenderer.drawSplitString(String.valueOf(te.getSelectedFrequency()), 0, (int) (fontRenderer.FONT_HEIGHT),
					50, 0xFFFF00FF);
			GlStateManager.translate(0, -7, 0.5);

			fontRenderer.drawSplitString(text1, 0, (int) (fontRenderer.FONT_HEIGHT),
					50, 0xFFFF00FF);
		}
		GlStateManager.popMatrix();

	}
	public static boolean isPlayerHoverWithDebug(World world) {
		if (world.isRemote) {

			EntityPlayer player = MainClass.proxy.getClientPlayer();
			RayTraceResult result = player.rayTrace(5, 10);
			BlockPos pos = result.getBlockPos();
			if (player.getEntityWorld().getTileEntity(pos) instanceof TileModMana) {

				TileModMana te = (TileModMana) player.getEntityWorld().getTileEntity(pos);
				ItemStack stack = player.getHeldItemMainhand();
/*
				boolean foundOnHead = false;
				ItemStack slotItemStack = player.inventory.armorItemInSlot(3);
				if (slotItemStack.getItem() == ItemRegistry.mana_viewer) {
					foundOnHead = true;
				}*/

				if (te instanceof TileModMana && te != null) {
					if (stack.getItem() == ItemRegistry.mana_debugtool) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
