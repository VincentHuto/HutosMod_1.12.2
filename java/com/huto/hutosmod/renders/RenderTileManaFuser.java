package com.huto.hutosmod.renders;

import java.text.DecimalFormat;

import javax.annotation.Nonnull;

import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.models.ModelMagatama;
import com.huto.hutosmod.tileentity.TileEntityManaFuser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class RenderTileManaFuser extends TileEntitySpecialRenderer<TileEntityManaFuser> {

	final ModelMagatama magatamas = new ModelMagatama();
	@Override
	public void render(@Nonnull TileEntityManaFuser te, double x, double y, double z, float partticks, int digProgress, float unused) {
		
		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);

		int items = 0;
		for(int i = 0; i < te.getSizeInventory(); i++)
			if(te.getItemHandler().getStackInSlot(i).isEmpty())
				break;
			else items++;
		float[] angles = new float[te.getSizeInventory()];

		float anglePer = 360F / items;
		float totalAngle = 0F;
		for(int i = 0; i < angles.length; i++)
			angles[i] = totalAngle += anglePer;

		double time = ClientTickHandler.ticksInGame + partticks;

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		for(int i = 0; i < te.getSizeInventory(); i++) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.5F, 1.5F, 0.5F);
			GlStateManager.rotate(angles[i] + (float) time, 0F, 1F, 0F);
			
			//Edit True Radius
			GlStateManager.translate(.1, -1.4F, 0.2F);
			GlStateManager.rotate(90F, 0F, 1F, 0F);
			//Edit Radius Movement
			GlStateManager.translate(0D, 0.175D+0.25, 0F);
			//Block/Item Scale
			GlStateManager.scale(0.4, 0.4, 0.4);
			ItemStack stack = te.getItemHandler().getStackInSlot(i);
			Minecraft mc = Minecraft.getMinecraft();
			if(!stack.isEmpty()) {
				mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
				//	System.out.println(altar.getItemHandler().getStackInSlot(i).getDisplayName());
			}
			GlStateManager.popMatrix();
		}

		GlStateManager.disableAlpha();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5F, 1.85F, 0.5F);
		GlStateManager.rotate(180F, 1F, 0F, 1F);
		GlStateManager.scale(0.25F, 0.25F, 0.25F);

		//int repeat = 20;
		int repeat = (int)te.getManaValue()/9;

		//magatamas.renderMagatamas(9 ,repeat, repeat);
		GlStateManager.enableAlpha();

		GlStateManager.popMatrix();
		
		GlStateManager.enableAlpha();
		
		DecimalFormat df = new DecimalFormat("0.00");
		String text = df.format(te.getManaValue());
		GlStateManager.translate(0, 1.75, -0.5);
		GlStateManager.rotate(180, 1, 0, 1);;
		GlStateManager.scale(0.1, 0.1, 0.1);
        FontRenderer fontRenderer = this.getFontRenderer();
        FontRenderer akloRenderer = ModTextFormatting.getAkloFont();
        akloRenderer.drawString(text, 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFF00FF);

		GlStateManager.popMatrix();
	}
}
