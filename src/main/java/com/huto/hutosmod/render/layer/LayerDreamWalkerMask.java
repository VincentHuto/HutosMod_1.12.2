package com.huto.hutosmod.render.layer;

import com.huto.hutosmod.entities.EntityDreamWalker;
import com.huto.hutosmod.models.ModelMysteriousMask2;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.render.entity.RenderDreamWalker;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerDreamWalkerMask implements LayerRenderer<EntityDreamWalker> {
	private final RenderDreamWalker render;
	ModelMysteriousMask2 mask = new ModelMysteriousMask2();
	public static final ResourceLocation texture = new ResourceLocation(Reference.MODID + ":textures/models/armor/mysterious_mask_layer_1.png");

	public LayerDreamWalkerMask(RenderDreamWalker render) {
		this.render = render;
	}
	@Override
	public void doRenderLayer(EntityDreamWalker entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		render.bindTexture(texture);

		for (int j = 0; j < 1; ++j) {
			float f10 = entitylivingbaseIn.prevRotationYawHead + (entitylivingbaseIn.rotationYawHead - entitylivingbaseIn.prevRotationYawHead) * partialTicks - (entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks);
			float f11 = entitylivingbaseIn.prevRotationPitch + (entitylivingbaseIn.rotationPitch - entitylivingbaseIn.prevRotationPitch) * partialTicks;
			GlStateManager.pushMatrix();
			if (entitylivingbaseIn.isSneaking())
				GlStateManager.translate(0.0F, 0.24F, 0.0F);
			GlStateManager.rotate(f10, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(f11, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(0, 0.0F, -0.01);
			mask.renderMask(0.0625F, entitylivingbaseIn);
			GlStateManager.popMatrix();
		}
		
	}

	@Override
	public boolean shouldCombineTextures() {
		// TODO Auto-generated method stub
		return false;
	}

}
