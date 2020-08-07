package com.huto.hutosmod.render.tile;

import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.Random;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.models.ModelDrumMagatamas;
import com.huto.hutosmod.tileentity.TileEntityStorageDrum;
import com.huto.hutosmod.tileentity.TileModMana;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTileStorageDrum extends TileEntitySpecialRenderer<TileEntityStorageDrum> {
	public static float sync = 0;

	private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
	private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
	private static final Random RANDOM = new Random(31100L);
	private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
	private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
	private final FloatBuffer buffer = GLAllocation.createDirectFloatBuffer(16);
	final ModelDrumMagatamas magatamas = new ModelDrumMagatamas();

	@Override
	public void render(@Nonnull TileEntityStorageDrum te, double x, double y, double z, float partticks,
			int digProgress, float unused) {
		// IMana manaCap = te.getCapability(ManaProvider.MANA_CAP, null);
		float mana = te.getManaValue();

		GlStateManager.disableLighting();
		RANDOM.setSeed(31100L);
		GlStateManager.getFloat(2982, MODELVIEW);
		GlStateManager.getFloat(2983, PROJECTION);
		double d0 = x * x + y * y + z * z;
		double heightModifier = 0;
		// Check System that increments the mana based on amount and color
		if (mana > 0 && mana <= 300)
			heightModifier = mana / 300 * 1.7;
		if (mana > 300 && mana <= 600)
			heightModifier = ((mana - 280) / 300) * 1.4;
		if (mana > 600 && mana <= 900) {
			heightModifier = ((mana - 580) / 300) * 1.4;
		} else if (mana > 900) {
			heightModifier = ((mana - 880) / 300) * 1.4;

		}

		double manaRatioColor = mana / 300;

		// Prevents Overflow
		if (heightModifier > 1.4)
			heightModifier = 1.4;
		// System.out.println(heightModifier);
		int i = this.getPasses(d0);
		float f = this.getOffset();
		boolean flag = false;

		for (int j = 0; j < i; ++j) {
			GlStateManager.pushMatrix();
			float f1 = 2.0F / (float) (18 - j);

			if (j == 0) {
				this.bindTexture(END_SKY_TEXTURE);
				// Higher F1 = more blue
				if (manaRatioColor <= 1) {
					f1 = 0.5F;
				} else if (manaRatioColor > 1 && manaRatioColor <= 2) {
					f1 = 5.0F;

				} else if (manaRatioColor > 2 && manaRatioColor <= 3) {
					f1 = 12.4F;

				}
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
						GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			}

			if (j >= 1) {
				this.bindTexture(END_PORTAL_TEXTURE);
				flag = true;
				Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
			}

			if (j == 1) {
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			}

			GlStateManager.texGen(GlStateManager.TexGen.S, 9216);
			GlStateManager.texGen(GlStateManager.TexGen.T, 9216);
			GlStateManager.texGen(GlStateManager.TexGen.R, 9216);
			GlStateManager.texGen(GlStateManager.TexGen.S, 9474, this.getBuffer(1.0F, 0.0F, 0.0F, 0.0F));
			GlStateManager.texGen(GlStateManager.TexGen.T, 9474, this.getBuffer(0.0F, 1.0F, 0.0F, 0.0F));
			GlStateManager.texGen(GlStateManager.TexGen.R, 9474, this.getBuffer(0.0F, 0.0F, 1.0F, 0.0F));
			GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
			GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
			GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.loadIdentity();
			GlStateManager.translate(0.5F, 0.5F, 0.0F);
			GlStateManager.scale(0.5F, 0.5F, 1.0F);
			float f2 = (float) (j + 1);
			GlStateManager.translate(17.0F / f2,
					(2.0F + f2 / 1.5F) * ((float) Minecraft.getSystemTime() % 800000.0F / 800000.0F), 0.0F);
			GlStateManager.rotate((f2 * f2 * 4321.0F + f2 * 9.0F) * 2.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.scale(4.5F - f2 / 4.0F, 4.5F - f2 / 4.0F, 1.0F);
			GlStateManager.multMatrix(PROJECTION);
			GlStateManager.multMatrix(MODELVIEW);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
			float f3 = (RANDOM.nextFloat() * 0.5F + 0.1F) * f1;
			float f4 = (RANDOM.nextFloat() * 0.5F + 0.4F) * f1;
			float f5 = (RANDOM.nextFloat() * 0.5F + 0.5F) * f1;

			if (te.shouldRenderFace(EnumFacing.SOUTH)) {
				bufferbuilder.pos(x + .125D, y + 0.0625D, z + 0.875).color(f3, f4, f5, 1.0F).endVertex();// 001
				bufferbuilder.pos(x + .875, y + 0.0625D, z + 0.875).color(f3, f4, f5, 1.0F).endVertex();// 101
				bufferbuilder.pos(x + .875, y + heightModifier, z + 0.875).color(f3, f4, f5, 1.0F).endVertex();// 111
				bufferbuilder.pos(x + .125D, y + heightModifier, z + 0.875).color(f3, f4, f5, 1.0F).endVertex();// 011
			}

			if (te.shouldRenderFace(EnumFacing.NORTH)) {
				bufferbuilder.pos(x + .125D, y + heightModifier, z + 0.125D).color(f3, f4, f5, 1.0F).endVertex(); // 010
				bufferbuilder.pos(x + 0.875, y + heightModifier, z + 0.125D).color(f3, f4, f5, 1.0F).endVertex();// 101
				bufferbuilder.pos(x + 0.875, y + 0.0625D, z + 0.125D).color(f3, f4, f5, 1.0F).endVertex();// 100
				bufferbuilder.pos(x + .125D, y + 0.0625D, z + 0.125D).color(f3, f4, f5, 1.0F).endVertex();// 000
			}

			if (te.shouldRenderFace(EnumFacing.EAST)) {
				bufferbuilder.pos(x + 0.875, y + heightModifier, z + 0.125D).color(f3, f4, f5, 1.0F).endVertex();// 110
				bufferbuilder.pos(x + 0.875, y + heightModifier, z + 0.9375D).color(f3, f4, f5, 1.0F).endVertex();// 111
				bufferbuilder.pos(x + 0.875, y + 0.0625D, z + 0.9375D).color(f3, f4, f5, 1.0F).endVertex();// 101
				bufferbuilder.pos(x + 0.875, y + 0.0625D, z + 0.125D).color(f3, f4, f5, 1.0F).endVertex();// 100
			}

			if (te.shouldRenderFace(EnumFacing.WEST)) {
				bufferbuilder.pos(x + .125D, y + 0.0625D, z + 0.125).color(f3, f4, f5, 1.0F).endVertex();// 000
				bufferbuilder.pos(x + .125D, y + 0.0625D, z + 0.875).color(f3, f4, f5, 1.0F).endVertex();// 001
				bufferbuilder.pos(x + .125D, y + heightModifier, z + 0.875).color(f3, f4, f5, 1.0F).endVertex();// 011
				bufferbuilder.pos(x + .125D, y + heightModifier, z + 0.125).color(f3, f4, f5, 1.0F).endVertex();// 010
			}

			if (te.shouldRenderFace(EnumFacing.DOWN)) {
				bufferbuilder.pos(x + .125D, y, z + .125D).color(f3, f4, f5, 1.0F).endVertex();// 000
				bufferbuilder.pos(x + 0.875D, y, z + .125D).color(f3, f4, f5, 1.0F).endVertex();// 100
				bufferbuilder.pos(x + 0.875D, y + 0.0625D, z + 0.75D).color(f3, f4, f5, 1.0F).endVertex();// 101
				bufferbuilder.pos(x + .125D, y + 0.0625D, z + 0.75D).color(f3, f4, f5, 1.0F).endVertex();// 001
			}

			if (te.shouldRenderFace(EnumFacing.UP)) {
				bufferbuilder.pos(x + 0.125, y + heightModifier, z + 0.875).color(f3, f4, f5, 1.0F).endVertex();
				bufferbuilder.pos(x + 0.875, y + heightModifier, z + 0.875).color(f3, f4, f5, 1.0F).endVertex();
				bufferbuilder.pos(x + 0.875, y + heightModifier, z + 0.125).color(f3, f4, f5, 1.0F).endVertex();
				bufferbuilder.pos(x + 0.125, y + heightModifier, z + 0.125).color(f3, f4, f5, 1.0F).endVertex();
			}

			tessellator.draw();

			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
			this.bindTexture(END_SKY_TEXTURE);

		}

		GlStateManager.disableBlend();
		GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
		GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
		GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
		GlStateManager.enableLighting();

		if (flag) {
			Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
		}
		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);

		// Add items above block
		int items = 0;
		for (int k = 0; k < te.getSizeInventory(); k++)
			if (te.getItemHandler().getStackInSlot(k).isEmpty())
				break;
			else
				items++;
		float[] angles = new float[te.getSizeInventory()];
		float anglePer = 360F / items;
		float totalAngle = 0F;
		for (int k = 0; k < angles.length; k++)
			angles[k] = totalAngle += anglePer;
		double time = ClientTickHandler.ticksInGame * 14 + partticks;
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		for (int k = 0; k < te.getSizeInventory(); k++) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.5F, 2F, 0.5F);
			GlStateManager.rotate(angles[k] + (float) time, (float) Math.cos(k), (float) Math.cos(k),
					(float) Math.cos(k));
			// Edit True Radius
			GlStateManager.translate(0.025F, -0.5F, 0.025F);
			GlStateManager.rotate(90F, 0F, 1F, 0F);
			// Edit Radius Movement
			GlStateManager.translate(0D, 0.175D + k * 0.25, 0F);
			// Block/Item Scale
			GlStateManager.scale(0.5, 0.5, 0.5);
			ItemStack stack = te.getItemHandler().getStackInSlot(k);
			Minecraft mc = Minecraft.getMinecraft();
			if (!stack.isEmpty()) {
				mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
			}
			GlStateManager.popMatrix();
		}

		// Draws the debug stats
		GlStateManager.pushMatrix();
		DecimalFormat df = new DecimalFormat("0.00");
		String text = df.format(te.getManaValue());
		String text1 = Float.toString(te.getMaxMana()) + "Max";
		String text2 = Float.toString(te.getTankLevel()) + "Level";
		String text3 = Integer.toString(te.getSizeInventory()) + "Size";

		GlStateManager.translate(0, 2, 0);
		GlStateManager.rotate(180, 1, 0, 1);

		GlStateManager.scale(0.08, 0.08, 0.08);
		FontRenderer fontRenderer = this.getFontRenderer();
		FontRenderer akloRenderer = ModTextFormatting.getAkloFont();
		if (isPlayerHoverWithDebug(te.getWorld())) {

			fontRenderer.drawString(text, 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFF00FF);
			GlStateManager.translate(0, -10, 0);
			fontRenderer.drawString(text1, 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFF00FF);
			GlStateManager.translate(0, -11, 0);
			fontRenderer.drawString(text2, 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFF00FF);
			GlStateManager.translate(0, -12, 0);
			fontRenderer.drawString(text3, 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFF00FF);
		}
		GlStateManager.popMatrix();

		GlStateManager.disableAlpha();
		GlStateManager.translate(0.5F, 2F, 0.5F);
		GlStateManager.rotate(180F, 1F, 0F, 1F);
		GlStateManager.scale(0.55F, 0.55F, 0.55F);

		int repeat = 30;
		if (te.getTankLevel() > 0) {
			magatamas.renderMagatamas(te.getTankLevel(), repeat, repeat);

		}

		GlStateManager.popMatrix();
		GlStateManager.enableAlpha();

	}

	protected int getPasses(double p_191286_1_) {
		int i;

		if (p_191286_1_ > 36864.0D) {
			i = 1;
		} else if (p_191286_1_ > 25600.0D) {
			i = 3;
		} else if (p_191286_1_ > 16384.0D) {
			i = 5;
		} else if (p_191286_1_ > 9216.0D) {
			i = 7;
		} else if (p_191286_1_ > 4096.0D) {
			i = 9;
		} else if (p_191286_1_ > 1024.0D) {
			i = 11;
		} else if (p_191286_1_ > 576.0D) {
			i = 13;
		} else if (p_191286_1_ > 256.0D) {
			i = 14;
		} else {
			i = 15;
		}

		return i;
	}

	protected float getOffset() {
		return 0.75F;
	}

	private FloatBuffer getBuffer(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_) {
		this.buffer.clear();
		this.buffer.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
		this.buffer.flip();
		return this.buffer;
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