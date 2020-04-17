package com.huto.hutosmod.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.reference.Reference;

/**
 * ModelKingdomKey - huto Created using Tabula 7.1.0
 */
public class ModelKingdomKey extends ModelBase {
	public ModelRenderer blade;
	public ModelRenderer topGuardLeft;
	public ModelRenderer handle;
	public ModelRenderer rightGuard;
	public ModelRenderer leftGuard;
	public ModelRenderer toothTop;
	public ModelRenderer toothBottom;
	public ModelRenderer baseRight;
	public ModelRenderer toothtopMid;
	public ModelRenderer baseleft;
	public ModelRenderer baseMid;
	public ModelRenderer topGuardRight;
	public ModelRenderer guardMid;
	public ModelRenderer keyChain;
	public ModelRenderer toothBottomMid;

	public ModelKingdomKey() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.handle = new ModelRenderer(this, 10, 10);
		this.handle.setRotationPoint(0.0F, 15.0F, 0.0F);
		this.handle.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.baseMid = new ModelRenderer(this, 25, 30);
		this.baseMid.setRotationPoint(-0.3F, 19.0F, -0.3F);
		this.baseMid.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
		this.topGuardRight = new ModelRenderer(this, 25, 0);
		this.topGuardRight.setRotationPoint(1.1F, 14.0F, 0.0F);
		this.topGuardRight.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.toothBottom = new ModelRenderer(this, 0, 20);
		this.toothBottom.setRotationPoint(1.0F, 0.8F, 0.0F);
		this.toothBottom.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.baseRight = new ModelRenderer(this, 36, 0);
		this.baseRight.setRotationPoint(-3.2F, 19.0F, 0.0F);
		this.baseRight.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
		this.baseleft = new ModelRenderer(this, 36, 0);
		this.baseleft.setRotationPoint(1.1F, 19.0F, 0.0F);
		this.baseleft.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);
		this.leftGuard = new ModelRenderer(this, 25, 10);
		this.leftGuard.setRotationPoint(2.5F, 15.0F, 0.0F);
		this.leftGuard.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.toothBottomMid = new ModelRenderer(this, 0, 20);
		this.toothBottomMid.setRotationPoint(1.0F, -0.1F, 0.0F);
		this.toothBottomMid.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.keyChain = new ModelRenderer(this, 25, 50);
		this.keyChain.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.keyChain.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.topGuardLeft = new ModelRenderer(this, 25, 0);
		this.topGuardLeft.setRotationPoint(-2.0F, 14.0F, 0.0F);
		this.topGuardLeft.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.blade = new ModelRenderer(this, 0, 0);
		this.blade.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.blade.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1, 0.0F);
		this.toothTop = new ModelRenderer(this, 0, 20);
		this.toothTop.setRotationPoint(1.0F, -2.0F, 0.0F);
		this.toothTop.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
		this.guardMid = new ModelRenderer(this, 25, 30);
		this.guardMid.setRotationPoint(-0.3F, 14.0F, -0.3F);
		this.guardMid.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
		this.rightGuard = new ModelRenderer(this, 25, 10);
		this.rightGuard.setRotationPoint(-2.5F, 15.0F, 0.0F);
		this.rightGuard.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.toothtopMid = new ModelRenderer(this, 0, 20);
		this.toothtopMid.setRotationPoint(1.0F, -1.1F, 0.0F);
		this.toothtopMid.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.handle.render(f5);
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.baseMid.offsetX, this.baseMid.offsetY, this.baseMid.offsetZ);
		GlStateManager.translate(this.baseMid.rotationPointX * f5, this.baseMid.rotationPointY * f5,
				this.baseMid.rotationPointZ * f5);
		GlStateManager.scale(0.5D, 1.0D, 0.5D);
		GlStateManager.translate(-this.baseMid.offsetX, -this.baseMid.offsetY, -this.baseMid.offsetZ);
		GlStateManager.translate(-this.baseMid.rotationPointX * f5, -this.baseMid.rotationPointY * f5,
				-this.baseMid.rotationPointZ * f5);
		//this.baseMid.render(f5);
		GlStateManager.popMatrix();
		this.topGuardRight.render(f5);
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.toothBottom.offsetX, this.toothBottom.offsetY, this.toothBottom.offsetZ);
		GlStateManager.translate(this.toothBottom.rotationPointX * f5, this.toothBottom.rotationPointY * f5,
				this.toothBottom.rotationPointZ * f5);
		GlStateManager.scale(0.5D, 0.7D, 1.0D);
		GlStateManager.translate(-this.toothBottom.offsetX, -this.toothBottom.offsetY, -this.toothBottom.offsetZ);
		GlStateManager.translate(-this.toothBottom.rotationPointX * f5, -this.toothBottom.rotationPointY * f5,
				-this.toothBottom.rotationPointZ * f5);
		this.toothBottom.render(f5);
		GlStateManager.popMatrix();
		this.baseRight.render(f5);
		this.baseleft.render(f5);
		this.leftGuard.render(f5);
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.toothBottomMid.offsetX, this.toothBottomMid.offsetY, this.toothBottomMid.offsetZ);
		GlStateManager.translate(this.toothBottomMid.rotationPointX * f5, this.toothBottomMid.rotationPointY * f5,
				this.toothBottomMid.rotationPointZ * f5);
		GlStateManager.scale(0.7D, 0.7D, 1.0D);
		GlStateManager.translate(-this.toothBottomMid.offsetX, -this.toothBottomMid.offsetY,
				-this.toothBottomMid.offsetZ);
		GlStateManager.translate(-this.toothBottomMid.rotationPointX * f5, -this.toothBottomMid.rotationPointY * f5,
				-this.toothBottomMid.rotationPointZ * f5);
		this.toothBottomMid.render(f5);
		GlStateManager.popMatrix();
		this.keyChain.render(f5);
		this.topGuardLeft.render(f5);
		this.blade.render(f5);
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.toothTop.offsetX, this.toothTop.offsetY, this.toothTop.offsetZ);
		GlStateManager.translate(this.toothTop.rotationPointX * f5, this.toothTop.rotationPointY * f5,
				this.toothTop.rotationPointZ * f5);
		GlStateManager.scale(0.5D, 0.7D, 1.0D);
		GlStateManager.translate(-this.toothTop.offsetX, -this.toothTop.offsetY, -this.toothTop.offsetZ);
		GlStateManager.translate(-this.toothTop.rotationPointX * f5, -this.toothTop.rotationPointY * f5,
				-this.toothTop.rotationPointZ * f5);
		this.toothTop.render(f5);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.guardMid.offsetX, this.guardMid.offsetY, this.guardMid.offsetZ);
		GlStateManager.translate(this.guardMid.rotationPointX * f5, this.guardMid.rotationPointY * f5,
				this.guardMid.rotationPointZ * f5);
		GlStateManager.scale(0.5D, 1.0D, 0.5D);
		GlStateManager.translate(-this.guardMid.offsetX, -this.guardMid.offsetY, -this.guardMid.offsetZ);
		GlStateManager.translate(-this.guardMid.rotationPointX * f5, -this.guardMid.rotationPointY * f5,
				-this.guardMid.rotationPointZ * f5);
		//this.guardMid.render(f5);
		GlStateManager.popMatrix();
		this.rightGuard.render(f5);
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.toothtopMid.offsetX, this.toothtopMid.offsetY, this.toothtopMid.offsetZ);
		GlStateManager.translate(this.toothtopMid.rotationPointX * f5, this.toothtopMid.rotationPointY * f5,
				this.toothtopMid.rotationPointZ * f5);
		GlStateManager.scale(0.7D, 0.7D, 1.0D);
		GlStateManager.translate(-this.toothtopMid.offsetX, -this.toothtopMid.offsetY, -this.toothtopMid.offsetZ);
		GlStateManager.translate(-this.toothtopMid.rotationPointX * f5, -this.toothtopMid.rotationPointY * f5,
				-this.toothtopMid.rotationPointZ * f5);
		this.toothtopMid.render(f5);
		GlStateManager.popMatrix();
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	private static final ResourceLocation COLIN_ARMOR = new ResourceLocation(
			Reference.MODID + ":textures/entity/modelelemental.png");
	
	public void renderSpinningKey(int keys, int repeat, int origRepeat) {
		
		GlStateManager.disableTexture2D();

		final float modifier = 6F;
		final float rotationModifier = 0.2F;
		final float radiusBase = 0.35F;
		final float radiusMod = 0.05F;

		double ticks = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks - 1.3 * (origRepeat - repeat);
		float offsetPerCube = 360 / keys;
		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.025F, 0.85F, -0.025F);
		for (int i = 0; i < keys; i++) {
			float offset = offsetPerCube * i;
			float deg = (int) (ticks / rotationModifier % 360F + offset);
			float rad = deg * (float) Math.PI / 180F;
			float radiusX = (float) (radiusBase + radiusMod * Math.sin(ticks / modifier));
			float radiusZ = (float) (radiusBase + radiusMod * Math.cos(ticks / modifier));
			float x = (float) (radiusX * Math.cos(rad));
			float z = (float) (radiusZ * Math.sin(rad));
			float y = (float) Math.cos((ticks + 50 * i) / 5F) / 10F;

			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			float xRotate = (float) Math.sin(ticks * rotationModifier) / 2F;
			float yRotate = (float) Math.max(0.6F, Math.sin(ticks * 0.1F) / 2F + 0.5F);
			float zRotate = (float) Math.cos(ticks * rotationModifier) / 2F;

			GlStateManager.rotate(deg, xRotate, yRotate, zRotate);
			if (repeat < origRepeat) {
				// Shadow
				GlStateManager.color(1F, 1F, 1F, (float) repeat / (float) origRepeat * 0.4F);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.disableAlpha();
				// Key
			} else
				GlStateManager.color(1F, 1F, 0F);

			int light = 15728880;
			int lightmapX = light % 65536;
			int lightmapY = light / 65536;

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);

			blade.render(1F / 64F);
			topGuardLeft.render(1F / 64F);
			handle.render(1F / 64F);
			rightGuard.render(1F / 64F);
			leftGuard.render(1F / 64F);
			toothTop.render(1F / 64F);
			toothBottom.render(1F / 64F);
			baseRight.render(1F / 64F);
			toothtopMid.render(1F / 64F);
			baseleft.render(1F / 64F);
			//baseMid.render(1F / 64F);
			topGuardRight.render(1F / 64F);
			//guardMid.render(1F / 64F);
			keyChain.render(1F / 64F);
			toothBottomMid.render(1F / 64F);

			if (repeat < origRepeat) {
				GlStateManager.disableBlend();
				GlStateManager.enableAlpha();
			}

			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
		GlStateManager.enableTexture2D();

		if (repeat != 0)
			renderSpinningKey(keys, repeat - 1, origRepeat);

	}
}
