package com.huto.hutosmod.render.tile;

import java.text.DecimalFormat;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.models.ModelMagatama;
import com.huto.hutosmod.tileentity.TileEntityChiselStation;
import com.huto.hutosmod.tileentity.TileEntityRuneStation;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileChiselStation extends TileEntitySpecialRenderer<TileEntityChiselStation> {

	@Override
	public void render(@Nonnull TileEntityChiselStation te, double x, double y, double z, float partticks,
			int digProgress, float unused) {
		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);

		int items = 0;
		for (int i = 0; i < te.getSizeInventory(); i++)
			if (te.chestContents.get(i).isEmpty())
				break;
			else
				items++;

		double time = ClientTickHandler.ticksInGame + partticks;

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		for (int i = 0; i < te.getSizeInventory(); i++) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.5F, 1.25F, 0.5F);
			// GlStateManager.rotate(angles[i] + (float) time, 0F, 1F, 0F);

			// Edit True Radius
			GlStateManager.translate(0.025F, -0.5F, 0.025F);
			GlStateManager.rotate(90F, 1F, 0F, 0F);
			GlStateManager.rotate(270F, 0F, 0F, 1F);
			GlStateManager.translate(0F, -0.1F, 0.3F);

			// Edit Radius Movement
			// Block/Item Scale
			GlStateManager.scale(0.5, 0.5, 0.5);
			ItemStack stack = te.chestContents.get(i);
			Minecraft mc = Minecraft.getMinecraft();
			if (!stack.isEmpty()) {
				mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
			}
			GlStateManager.popMatrix();
		}
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.3, 0.3, 0.3);
		GlStateManager.translate(0, 4, 0.5);
		GlStateManager.rotate(180, 1, 0, 1);
		GlStateManager.scale(0.1, 0.1, 0.1);
		if(te.getRuneList() != null){
		String text1 = te.getRuneList().toString();
		FontRenderer fontRenderer = this.getFontRenderer();
		// fontRenderer.drawString(text, 0, (int) (fontRenderer.FONT_HEIGHT),
		// 0xFFFF00FF);
		GlStateManager.translate(0, -10, 0);
		//System.out.println(text1);
		fontRenderer.drawString(text1, 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFF00FF);
		}
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
	}
}