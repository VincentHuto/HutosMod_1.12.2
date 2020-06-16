package com.huto.hutosmod.render.layer;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.entities.EntityElemental;
import com.huto.hutosmod.models.ModelElemental;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.render.entity.RenderElemental;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerElemental implements LayerRenderer<EntityElemental> {
	private static final ResourceLocation Elemental_Glow = new ResourceLocation(
			Reference.MODID + ":textures/entity/modelelemental.png");
	private final RenderElemental colinRenderer;
	private final ModelElemental elemModel = new ModelElemental();

	public LayerElemental(RenderElemental colinRendererIn) {
		this.colinRenderer = colinRendererIn;
	}

	public void doRenderLayer(EntityElemental entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

			Random rand = new Random();

			GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());

			this.colinRenderer.bindTexture(Elemental_Glow);
			GL11.glScaled(1.65, .25, 1.65);
			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();

			// Calls Instance of the game to bind the texture, then sclaes it
			Minecraft mc = Minecraft.getMinecraft();
			mc.getTextureManager().getTexture(Elemental_Glow);
//			GL11.glScalef(3.0F, 3.0F, 3.0F);

			// Gets the variables needs for translation
			float mX = (float) entitylivingbaseIn.ticksExisted + partialTicks;
			float mY = MathHelper.sin(mX * (rand.nextFloat() * 0.00000000000000000000000001F) * 0.002F) * 2.0F;
			float mZ = mX * 0.0011F;
			// Gets the variables needed for rotation
			float rX = (float) entitylivingbaseIn.ticksExisted + partialTicks;
			float rY = MathHelper.cos(mX * 0.002F) * (float) entitylivingbaseIn.ticksExisted + partialTicks;
			// Makes the texture move
		//	GlStateManager.translate(mY, mZ, 0.0F);
		//	GlStateManager.rotate(rX, mY, mZ, rY);

			GlStateManager.matrixMode(5888);
			GlStateManager.enableBlend();

			// Overall Scale how far it floats off the model
			GlStateManager.scale(1.1F, 1.1F, 1.1F);
			//GlStateManager.translate(-0.053F, -0.02F, -0.003F);
			// Maybe Changing Color?
			// for (int i = 0; i < ageInTicks; i++) {}
			GlStateManager.color(0.5F, 0.5F, 1.5F, 1.0F);
			// Adds lighting of some form?

			GlStateManager.disableLighting();
			// Also brightens it deals with transparency of some type
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

			// Makes the aura move with the model
			this.elemModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, scale, headPitch,
					entitylivingbaseIn);
			// Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
			this.elemModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
					scale);

			Minecraft.getMinecraft().entityRenderer.setupFogColor(false);

			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			GlStateManager.matrixMode(5888);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();

		
	}

	@Override
	public boolean shouldCombineTextures() {
		
		return false;
	}
}