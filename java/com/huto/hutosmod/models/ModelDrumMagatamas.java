package com.huto.hutosmod.models;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
public class ModelDrumMagatamas extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape3;
    public ModelRenderer shape4;
    public ModelRenderer shape5;
    public ModelRenderer shape6;
    public ModelRenderer shape7;
    public ModelRenderer shape8;

    public ModelDrumMagatamas() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(-2.0F, 23.0F, 0.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.shape4 = new ModelRenderer(this, 14, 0);
        this.shape4.setRotationPoint(-3.0F, 19.0F, 0.0F);
        this.shape4.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.shape8 = new ModelRenderer(this, 0, 0);
        this.shape8.setRotationPoint(-2.0F, 17.0F, 0.0F);
        this.shape8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.shape2 = new ModelRenderer(this, 4, 0);
        this.shape2.setRotationPoint(-1.0F, 20.0F, 0.0F);
        this.shape2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.shape5 = new ModelRenderer(this, 24, 0);
        this.shape5.setRotationPoint(-3.0F, 17.0F, 0.0F);
        this.shape5.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.shape3 = new ModelRenderer(this, 0, 0);
        this.shape3.setRotationPoint(-1.0F, 22.0F, 0.0F);
        this.shape3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.shape7 = new ModelRenderer(this, 15, 2);
        this.shape7.setRotationPoint(-2.0F, 16.0F, 0.0F);
        this.shape7.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.shape6 = new ModelRenderer(this, 9, 2);
        this.shape6.setRotationPoint(-1.0F, 17.0F, 0.0F);
        this.shape6.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
    }
    
    public void renderMagatamas(int cubes, int repeat, int origRepeat) {
		GlStateManager.disableTexture2D();

		final float modifier = 6F;
		final float rotationModifier = 0.2F;
		final float radiusBase = 0.35F;
		final float radiusMod = 0.05F;

		double ticks = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks - 1.3 * (origRepeat - repeat);
		float offsetPerCube = 360 / cubes;
		double slowticks = 0.6*(ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks - 1.3 * (origRepeat - repeat));

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.025F, 0.85F, -0.025F);
		for(int i = 0; i < cubes; i++) {
			float offset = offsetPerCube * i;
			float deg = (int) (slowticks / rotationModifier % 360F + offset);
			float rad = deg * (float) Math.PI / 180F;
			float radiusX = 1+(float) (radiusBase + radiusMod * Math.sin(ticks / modifier));
			float radiusZ = 1+(float) (radiusBase + radiusMod * Math.cos(ticks / modifier));
			float x =  (float) (radiusX * Math.cos(rad));
			float z = (float) (radiusZ * Math.sin(rad));
			float y = (float) Math.cos((ticks + 50 * i) / 5F) / 10F;

			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			float xRotate = 0;
			float yRotate = (float) Math.max(0.6F, Math.sin(ticks * 0.1F) / 2F + 0.5F);
			float zRotate = 0;
			Random rand = new Random();
			GlStateManager.rotate(deg, xRotate, yRotate, zRotate);
			if(repeat < origRepeat) {
				GlStateManager.color(.3F, 0.2F, 0.0F, (float) repeat / (float) origRepeat * 0.4F);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.disableAlpha();
			} else GlStateManager.color(0.2F, 0F, 0.0F, 0F);

			int light = 15728880;
			int lightmapX = light % 65536;
			int lightmapY = light / 65536;

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
			shape1.render(1F / 16F);
			shape2.render(1F / 16F);
			shape3.render(1F / 16F);
			shape4.render(1F / 16F);
			shape5.render(1F / 16F);
			shape6.render(1F / 16F);
			shape7.render(1F / 16F);
			shape8.render(1F / 16F);

			if(repeat < origRepeat) {
				GlStateManager.disableBlend();
				GlStateManager.enableAlpha();
			}

			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
		GlStateManager.enableTexture2D();

		if(repeat != 0)
			renderMagatamas(cubes, repeat - 1, origRepeat);
	}
    
}
