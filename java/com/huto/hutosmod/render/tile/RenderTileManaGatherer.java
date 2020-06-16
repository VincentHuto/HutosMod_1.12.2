package com.huto.hutosmod.render.tile;

import java.text.DecimalFormat;

import javax.annotation.Nonnull;

import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.models.ModelMagatama;
import com.huto.hutosmod.tileentity.TileEntityManaGatherer;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class RenderTileManaGatherer extends TileEntitySpecialRenderer<TileEntityManaGatherer> {

	final ModelMagatama magatamas = new ModelMagatama();
	@Override
	public void render(@Nonnull TileEntityManaGatherer te, double x, double y, double z, float partticks, int digProgress, float unused) {

		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);		
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
