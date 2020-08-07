package com.huto.hutosmod.render.tile;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Random;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.models.ModelEnhancerCubeCW;
import com.huto.hutosmod.models.ModelFloatingDial;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntityCelestialActuator;
import com.huto.hutosmod.tileentity.TileModMana;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileCelestialActuator extends TileEntitySpecialRenderer<TileEntityCelestialActuator> {
	private static final Random RANDOM = new Random();
	final ModelFloatingDial dialModel = new ModelFloatingDial();
	private static final ResourceLocation dialTex = new ResourceLocation(
			Reference.MODID + ":" + "textures/blocks/enchanted_stone_smooth.png");

	@Override
	public void render(@Nonnull TileEntityCelestialActuator te, double x, double y, double z, float partticks,
			int digProgress, float unused) {
		if (!(te instanceof TileEntityCelestialActuator))
			return; // should never happen
		TileEntityCelestialActuator celest = (TileEntityCelestialActuator) te;
		double powerLevel = celest.getSmoothedNeedlePosition();

		float rot = 0.0F;
		if (te.getWorld() != null) {
			rot = te.getWorld().getWorldTime() % 360L;
		}
		final double ZERO_ANGLE = 0;
		final double MAX_ANGLE = -360;
		float needleAngle = (float) interpolate(powerLevel, 0, 1, MAX_ANGLE, ZERO_ANGLE);
		double speedMult = 0.1;
		double ticks = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks;
		double xMod = (float) Math.sin(ticks * speedMult) / 2F;
		double zMod = (float) Math.cos(ticks * speedMult) / 2F;
		double yMod = (float) Math.cos(ticks * speedMult) / 2F;

		this.bindTexture(dialTex);
		GlStateManager.color(1, 1, 1);
		GlStateManager.pushMatrix();
		GlStateManager.disableAlpha();
		GlStateManager.translate(x + 0.5, y + .7, z + 0.5);
		GlStateManager.scale(0.5, 1, 0.8);
		GlStateManager.rotate(180, 1, 0, 0);
		// if rotateDir is 1 then move clockwise else counterclockwise(backwards mode)

		int rotateDir = 0;
		if (te.state == false) {
			rotateDir = 1;
		} else {
			rotateDir = -1;
			rotateDir = -rotateDir;
		}
		GlStateManager.rotate(needleAngle, 0, rotateDir, 0);
		dialModel.renderThis();
		GlStateManager.popMatrix();

		// RENDER MANA VALUE
		GlStateManager.pushMatrix();

		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);
		DecimalFormat df = new DecimalFormat("0.00");
		String text = df.format(te.getManaValue());
		GlStateManager.translate(0, 1.75, -0.5);
		GlStateManager.rotate(180, 1, 0, 1);
		GlStateManager.scale(0.1, 0.1, 0.1);
		FontRenderer fontRenderer = this.getFontRenderer();
		FontRenderer akloRenderer = ModTextFormatting.getAkloFont();
		if (isPlayerHoverWithDebug(te.getWorld())) {

			akloRenderer.drawString(text, 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFF00FF);
		}
		GlStateManager.popMatrix();
	}

	public static double interpolate(double x, double x1, double x2, double y1, double y2) {
		if (x1 > x2) {
			double temp = x1;
			x1 = x2;
			x2 = temp;
			temp = y1;
			y1 = y2;
			y2 = temp;
		}

		if (x <= x1)
			return y1;
		if (x >= x2)
			return y2;
		double xFraction = (x - x1) / (x2 - x1);
		return y1 + xFraction * (y2 - y1);
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
