package com.huto.hutosmod.render.tile;

import java.text.DecimalFormat;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.models.ModelMagatama;
import com.huto.hutosmod.tileentity.TileEntityManaGatherer;
import com.huto.hutosmod.tileentity.TileModMana;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileManaGatherer extends TileEntitySpecialRenderer<TileEntityManaGatherer> {

	final ModelMagatama magatamas = new ModelMagatama();

	@Override
	public void render(@Nonnull TileEntityManaGatherer te, double x, double y, double z, float partticks,
			int digProgress, float unused) {

		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);
		GlStateManager.enableAlpha();

		DecimalFormat df = new DecimalFormat("0.00");
		String text = df.format(te.getManaValue());
		GlStateManager.translate(0, 1.75, -0.5);
		GlStateManager.rotate(180, 1, 0, 1);
		;
		GlStateManager.scale(0.1, 0.1, 0.1);
		FontRenderer fontRenderer = this.getFontRenderer();
		FontRenderer akloRenderer = ModTextFormatting.getAkloFont();
		if (isPlayerHoverWithDebug(te.getWorld())) {

			akloRenderer.drawString(text, 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFF00FF);
		}
		GlStateManager.popMatrix();

	}

	public static boolean isPlayerHoverWithDebug(World world) {
		if (world.isRemote) {
			EntityPlayer player = MainClass.proxy.getClientPlayer();
			RayTraceResult result = player.rayTrace(5, 10);
			BlockPos pos = result.getBlockPos();
			TileModMana te = (TileModMana) player.getEntityWorld().getTileEntity(pos);
			ItemStack stack = player.getHeldItemMainhand();
			if (te instanceof TileModMana && te != null) {

				if (stack.getItem() == ItemRegistry.mana_debugtool) {
					return true;
				}
			}
		}
		return false;
	}

}
