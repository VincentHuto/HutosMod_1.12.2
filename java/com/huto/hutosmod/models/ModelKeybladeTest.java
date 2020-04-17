package com.huto.hutosmod.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class ModelKeybladeTest extends ModelBase
{
  //fields
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape1;
    ModelRenderer Shape111;
    ModelRenderer Shape22;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape222;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
    ModelRenderer Shape10;
    ModelRenderer Shape11;
    ModelRenderer Shape12;
    ModelRenderer Shape13;
  
  public ModelKeybladeTest()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      Shape2 = new ModelRenderer(this, 0, 0);
      Shape2.addBox(-1F, 0F, 0F, 3, 1, 1);
      Shape2.setRotationPoint(0F, 22F, 0F);
      Shape2.setTextureSize(128, 128);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 0);
      Shape3.addBox(0F, 0F, 0F, 1, 5, 1);
      Shape3.setRotationPoint(0F, 17F, 0F);
      Shape3.setTextureSize(128, 128);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 5, 1, 1);
      Shape1.setRotationPoint(2F, 22F, 0F);
      Shape1.setTextureSize(128, 128);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, -1.22173F);
      Shape111 = new ModelRenderer(this, 0, 0);
      Shape111.addBox(0F, -1F, 0F, 5, 1, 1);
      Shape111.setRotationPoint(-1F, 22F, 0F);
      Shape111.setTextureSize(128, 128);
      Shape111.mirror = true;
      setRotation(Shape111, 0F, 0F, -1.919862F);
      Shape22 = new ModelRenderer(this, 0, 0);
      Shape22.addBox(0F, 0F, 0F, 3, 1, 1);
      Shape22.setRotationPoint(-1F, 16F, 0F);
      Shape22.setTextureSize(128, 128);
      Shape22.mirror = true;
      setRotation(Shape22, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 0);
      Shape4.addBox(0F, 0F, 0F, 1, 1, 1);
      Shape4.setRotationPoint(1F, 15F, 0F);
      Shape4.setTextureSize(128, 128);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 0, 0);
      Shape5.addBox(0F, 0F, 0F, 1, 1, 1);
      Shape5.setRotationPoint(-1F, 15F, 0F);
      Shape5.setTextureSize(128, 128);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape222 = new ModelRenderer(this, 0, 0);
      Shape222.addBox(0F, 0F, 0F, 3, 1, 1);
      Shape222.setRotationPoint(-1F, 14F, 0F);
      Shape222.setTextureSize(128, 128);
      Shape222.mirror = true;
      setRotation(Shape222, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 0, 0);
      Shape6.addBox(0F, 0F, 0F, 1, 11, 1);
      Shape6.setRotationPoint(0F, 3F, 0F);
      Shape6.setTextureSize(128, 128);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 0, 0);
      Shape7.addBox(0F, 0F, 0F, 3, 1, 1);
      Shape7.setRotationPoint(-1F, 2F, 0F);
      Shape7.setTextureSize(128, 128);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 0, 0);
      Shape8.addBox(0F, 0F, 0F, 1, 1, 1);
      Shape8.setRotationPoint(-1F, 1F, 0F);
      Shape8.setTextureSize(128, 128);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 0, 0);
      Shape9.addBox(0F, 0F, 0F, 1, 1, 1);
      Shape9.setRotationPoint(1F, 1F, 0F);
      Shape9.setTextureSize(128, 128);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
      Shape10 = new ModelRenderer(this, 0, 0);
      Shape10.addBox(0F, 0F, 0F, 3, 1, 1);
      Shape10.setRotationPoint(-1F, 0F, 0F);
      Shape10.setTextureSize(128, 128);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, 0F);
      Shape11 = new ModelRenderer(this, 0, 0);
      Shape11.addBox(0F, 0F, 0F, 1, 1, 1);
      Shape11.setRotationPoint(-2F, -1F, 0F);
      Shape11.setTextureSize(128, 128);
      Shape11.mirror = true;
      setRotation(Shape11, 0F, 0F, -110F);
      Shape12 = new ModelRenderer(this, 0, 0);
      Shape12.addBox(0F, 0F, 0F, 3, 1, 1);
      Shape12.setRotationPoint(2F, 0F, 0F);
      Shape12.setTextureSize(128, 128);
      Shape12.mirror = true;
      setRotation(Shape12, 0F, 0F, -0.7853982F);
      Shape13 = new ModelRenderer(this, 0, 0);
      Shape13.addBox(0F, 0F, 0F, 4, 1, 1);
      Shape13.setRotationPoint(3F, -5F, 0F);
      Shape13.setTextureSize(128, 128);
      Shape13.mirror = true;
      setRotation(Shape13, 0F, 0F, 1.047198F);
  }
  

  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void renderSpinningCubes(int cubes, int repeat, int origRepeat) {
		GlStateManager.disableTexture2D();

		final float modifier = 6F;
		final float rotationModifier = 0.2F;
		final float radiusBase = 0.35F;
		final float radiusMod = 0.05F;

		double ticks = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks - 1.3 * (origRepeat - repeat);
		float offsetPerCube = 360 / cubes;

		GlStateManager.pushMatrix();
		GlStateManager.translate(-0.025F, 0.85F, -0.025F);
		for(int i = 0; i < cubes; i++) {
			float offset = offsetPerCube * i;
			float deg = (int) (ticks / rotationModifier % 360F + offset);
			float rad = deg * (float) Math.PI / 180F;
			float radiusX = (float) (radiusBase + radiusMod * Math.sin(ticks / modifier));
			float radiusZ = (float) (radiusBase + radiusMod * Math.cos(ticks / modifier));
			float x =  (float) (radiusX * Math.cos(rad));
			float z = (float) (radiusZ * Math.sin(rad));
			float y = (float) Math.cos((ticks + 50 * i) / 5F) / 10F;

			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			float xRotate = (float) Math.sin(ticks * rotationModifier) / 2F;
			float yRotate = (float) Math.max(0.6F, Math.sin(ticks * 0.1F) / 2F + 0.5F);
			float zRotate = (float) Math.cos(ticks * rotationModifier) / 2F;

			GlStateManager.rotate(deg, xRotate, yRotate, zRotate);
			if(repeat < origRepeat) {
				//Shadow
				GlStateManager.color(0.6F,0.1F, 0.8F, (float) repeat / (float) origRepeat * 0.4F);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.disableAlpha();
				//Key
			} else GlStateManager.color(0.5F, 0F, 0F);

			int light = 15728880;
			int lightmapX = light % 65536;
			int lightmapY = light / 65536;

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
			Shape2.render(1F / 64F);
			Shape3.render(1F / 64F);
			Shape1.render(1F / 64F);
			Shape11.render(1F / 64F);
			Shape22.render(1F / 64F);
			Shape4.render(1F / 64F);
			Shape5.render(1F / 64F);
			Shape222.render(1F / 64F);
			Shape6.render(1F / 64F);
			Shape7.render(1F / 64F);
			Shape8.render(1F / 64F);
			Shape9.render(1F / 64F);
			Shape10.render(1F / 64F);
			Shape11.render(1F / 64F);
			Shape12.render(1F / 64F);
			Shape13.render(1F / 64F);

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
