package com.huto.hutosmod.render.layer;

import java.text.DecimalFormat;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.entities.EntityColin;
import com.huto.hutosmod.models.ModelColin;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.render.entity.RenderColin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerColinAttack implements LayerRenderer<EntityColin> {
	private static final ResourceLocation COLIN_ATTACK = new ResourceLocation(
			Reference.MODID + ":textures/entity/colin_attack.png");

	private final RenderColin colinRenderer;
	private final ModelColin colinModel = new ModelColin();

	public LayerColinAttack(RenderColin colinRendererIn) {
		this.colinRenderer = colinRendererIn;
	}

	public void doRenderLayer(EntityColin entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (entitylivingbaseIn.isBurning()){

			Random rand = new Random();
			GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
			this.colinRenderer.bindTexture(COLIN_ATTACK);
			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			// Calls Instance of the game to bind the texture, then sclaes it
			Minecraft mc = Minecraft.getMinecraft();
			mc.getTextureManager().getTexture(COLIN_ATTACK);
			GL11.glScalef(7.0F, 7.0F, 7.0F);

			GlStateManager.translate(-1.0F, -1.0F, 0.0F);

			// Gets the variables needed for rotation
			float rX = (float) entitylivingbaseIn.ticksExisted + partialTicks;
			float rY = MathHelper.cos(rX * 0.002F) * (float) entitylivingbaseIn.ticksExisted + partialTicks;
			// Makes the texture move
			GlStateManager.translate(-1.0F, -1.0F, 0.0F);
			GlStateManager.rotate(rX, 0, 0, rY);

			GlStateManager.matrixMode(5888);
			GlStateManager.enableBlend();
			// Overall Scale how far it floats off the model
			GlStateManager.scale(1.1F, 1.1F, 1.1F);

			/*
			 * //System.out.println(ageInTicks); DecimalFormat aT = new DecimalFormat("0");
			 * String trimmedTicks = new String(); trimmedTicks =
			 * Float.toString(ageInTicks); aT.format(trimmedTicks);
			 * System.out.println(trimmedTicks);
			 */

			// System.out.println(ageInTicks);
			int trimmedTicks = (int) ageInTicks;
			// System.out.println(trimmedTicks);
			GlStateManager.translate(0.0F, -1.0F, 0.5F);

			if (trimmedTicks % 5 == 0 && trimmedTicks % 2 != 0) {

				// GlStateManager.rotate(rX, 0, 0, rY);
				// GlStateManager.color(0.5F,0.5F, 0.5F, 1.0F);

				// GlStateManager.color(rand.nextFloat()* 0.5F,rand.nextFloat()* 0.5F,
				// rand.nextFloat()*0.5F, 1.0F);
				// System.out.println("FIRST LOOP");
			} else {

				// System.out.println("SECOND LOOP");
				GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);

			}

			// Adds lighting of some form?
			GlStateManager.disableLighting();
			// Also brightens it deals with transparency of some type
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			// Makes the aura move with the model
			this.colinModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, scale, headPitch,
					entitylivingbaseIn);
			this.colinModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
					scale);

			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			GlStateManager.matrixMode(5888);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();

		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}