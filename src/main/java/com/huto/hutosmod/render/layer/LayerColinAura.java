package com.huto.hutosmod.render.layer;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.entities.EntityColin;
import com.huto.hutosmod.models.ModelColin;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.render.RenderShapes;
import com.huto.hutosmod.render.entity.RenderColin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerColinAura implements LayerRenderer<EntityColin> {
	private static final ResourceLocation COLIN_ARMOR = new ResourceLocation(
			Reference.MODID + ":textures/entity/colin_armor2.png");
	private final RenderColin colinRenderer;
	private final ModelColin colinModel = new ModelColin();

	public LayerColinAura(RenderColin colinRendererIn) {
		this.colinRenderer = colinRendererIn;
	}

	public void doRenderLayer(EntityColin entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (entitylivingbaseIn.isArmored()) {

			Random rand = new Random();
			GlStateManager.color(1F, 0F, 1F, 1F);

			GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
			this.colinRenderer.bindTexture(COLIN_ARMOR);
			// GL11.glScaled(.25, .25, .25);
			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			//RenderShapes.drawSphere(10, 100, 100);

			// Calls Instance of the game to bind the texture, then sclaes it
			Minecraft mc = Minecraft.getMinecraft();
			mc.getTextureManager().getTexture(COLIN_ARMOR);
			GL11.glScalef(3.0F, 3.0F, 3.0F);

			// Gets the variables needs for translation
			float mX = (float) entitylivingbaseIn.ticksExisted + partialTicks;
			float mY = MathHelper.sin(mX * (rand.nextFloat() * 0.00000000000000000000000001F) * 0.002F) * 2.0F;
			float mZ = mX * 0.0011F;
			
			// Gets the variables needed for rotation
			float rX = (float) entitylivingbaseIn.ticksExisted + partialTicks;
			float rY = MathHelper.cos(mX * 0.002F) * (float) entitylivingbaseIn.ticksExisted + partialTicks;
			// Makes the texture move
			GlStateManager.translate(mY, mZ, 0.0F);
			GlStateManager.rotate(rX, mY, mZ, rY);

			GlStateManager.matrixMode(5888);
			GlStateManager.enableBlend();

			// Overall Scale how far it floats off the model
			GlStateManager.scale(1.1F, 1.0F, 1.1F);
			// Maybe Changing Color?
			// for (int i = 0; i < ageInTicks; i++) {}
			GlStateManager.color(0.5F, 0.5F, 1.5F, 1.0F);
			// Adds lighting of some form?

			GlStateManager.disableLighting();
			// Also brightens it deals with transparency of some type
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

			// Makes the aura move with the model
			this.colinModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, scale, headPitch,
					entitylivingbaseIn);
			// Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
			this.colinModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
					scale);

			Minecraft.getMinecraft().entityRenderer.setupFogColor(false);

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