package com.huto.hutosmod.models;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class ModelRedTuningFork extends ModelBase {
	// fields
	ModelRenderer Stem;
	ModelRenderer LeftFork;
	ModelRenderer RightFork;

	
	public ModelRedTuningFork() {
		textureWidth = 64;
		textureHeight = 32;

		Stem = new ModelRenderer(this, 0, 0);
		Stem.addBox(0F, 0F, 0F, 1, 5, 2);
		Stem.setRotationPoint(-1F, 19F, -1F);
		Stem.setTextureSize(64, 32);
		Stem.mirror = true;
		LeftFork = new ModelRenderer(this, 0, 0);
		LeftFork.addBox(0F, 0F, 0F, 1, 7, 2);
		LeftFork.setRotationPoint(0F, 12F, -1F);
		LeftFork.setTextureSize(64, 32);
		LeftFork.mirror = true;
		RightFork = new ModelRenderer(this, 0, 0);
		RightFork.addBox(0F, 0F, 0F, 1, 7, 2);
		RightFork.setRotationPoint(-2F, 12F, -1F);
		RightFork.setTextureSize(64, 32);
		RightFork.mirror = true;
	}

	public void renderForks(int cubes, int repeat,int origRepeat) {
		GlStateManager.disableTexture2D();

		final float modifier = 6F;
		final float rotationModifier = 0.2F;
		final float radiusBase = 0.35F;
		final float radiusMod = 0.05F;
		double ticks = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks - 1.3 * (origRepeat - repeat);
		float offsetPerCube = 360 / cubes;
		double slowticks = 0.6
				* (ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks - 1.3 * (origRepeat - repeat));

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.025F, 0.85F, -0.025F);
		for (int i = 0; i < cubes; i++) {
			float offset = offsetPerCube * i;
			float deg = (int) (slowticks / rotationModifier % 360F + offset);
			float rad = deg * (float) Math.PI / 180F;
			double radiusX = 0.3 + (float) (radiusBase + radiusMod * Math.sin(ticks / modifier));
			double radiusZ = 0.3 + (float) (radiusBase + radiusMod * Math.cos(ticks / modifier));
			float x = (float) (radiusX * Math.cos(rad));
			float z = (float) (radiusZ * Math.sin(rad));
			float y = (float) Math.cos((ticks + 50 * i) / 5F) / 10F;

			GlStateManager.pushMatrix();
			GlStateManager.translate(-x, y-0.4, -z);
			float xRotate = 0;
			float yRotate = (float) Math.max(0.6F, Math.sin(ticks * 0.1F) / 2F + 0.5F);
			float zRotate = 0;
			Random rand = new Random();
			GlStateManager.rotate(deg, xRotate, yRotate, zRotate);
			if (repeat < origRepeat) {
				GlStateManager.color(.3F, 0.2F, 0.3F, (float) repeat / (float) origRepeat * 0.4F);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.disableAlpha();
			} else
				GlStateManager.color(0.2F, 0F, 0.0F, 0F);

			int light = 15728880;
			int lightmapX = light % 65536;
			int lightmapY = light / 65536;

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
			Stem.render(1F / 16F);
			LeftFork.render(1F / 16F);
			RightFork.render(1F / 16F);

			if (repeat < origRepeat) {
				GlStateManager.disableBlend();
				GlStateManager.enableAlpha();
			}

			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
		GlStateManager.enableTexture2D();

		if (repeat != 0)
			renderForks(cubes, repeat - 1,origRepeat);
	}

}
