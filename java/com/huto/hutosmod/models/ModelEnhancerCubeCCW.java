/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Feb 2, 2014, 6:37:00 PM (GMT)]
 */
package com.huto.hutosmod.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;

public class ModelEnhancerCubeCCW extends ModelBase {

	final ModelRenderer spinningCube;

	public ModelEnhancerCubeCCW() {
		spinningCube = new ModelRenderer(this, 42, 0);
		spinningCube.addBox(0F, 0F, 0F, 1, 1, 1);
		spinningCube.setRotationPoint(0F, 0F, 0F);
		spinningCube.setTextureSize(64, 64);
	}

	public void renderSpinningCubes(int cubes, int repeat, int origRepeat) {
		GlStateManager.disableTexture2D();

		final float modifier = 6F;
		final float rotationModifier = 0.9F;
		final float radiusBase = 0.35F;
		final float radiusMod = 0.09F;

		double ticks =ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks - 1.3 * (origRepeat - repeat);
		float offsetPerCube = 360 / cubes;

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.025F, 0.85F, -0.025F);
		for(int i = 0; i < cubes; i++) {
			float offset = offsetPerCube * i;
			float deg = (int) (ticks / rotationModifier % 360F + offset);
			float rad = deg * (float) Math.PI / 60F;
			float radiusX = (float) (radiusBase + radiusMod * Math.sin(ticks / modifier));
			float radiusZ = (float) (radiusBase + radiusMod * Math.cos(ticks / modifier));
			//flip the x and z sin and cos to make it spin cw or ccw
			float x =  (float) (radiusX * Math.sin(rad));
			float z = (float) (radiusZ * Math.cos(rad));
			float y = (float) Math.cos((ticks + 50 * i) / 5F) / 20F;

			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			float xRotate = (float) Math.sin(ticks * rotationModifier) / 2F;
			float yRotate = (float) Math.max(0.6F, Math.sin(ticks * 0.9F) / 2F + 0.5F);
			float zRotate = (float) Math.cos(ticks * rotationModifier) / 2F;

			GlStateManager.rotate(deg, xRotate, yRotate, zRotate);
			if(repeat < origRepeat) {
				GlStateManager.color(0.3F, 0F, 0F, (float) repeat / (float) origRepeat * 0.4F);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.disableAlpha();
			} else GlStateManager.color(1F, 0F, 0F, 1F);

			int light = 15728880;
			int lightmapX = light % 65536;
			int lightmapY = light / 65536;

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
			spinningCube.render(1F / 16F);

			if(repeat < origRepeat) {
				GlStateManager.disableBlend();
				GlStateManager.enableAlpha();
			}

			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
		GlStateManager.enableTexture2D();

		if(repeat != 0)
			renderSpinningCubes(cubes, repeat - 1, origRepeat);
	}

}
