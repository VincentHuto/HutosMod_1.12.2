package com.huto.hutosmod.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class ModelFloatingDial extends ModelBase
{
  //fields
   final ModelRenderer Base;
   final  ModelRenderer Shape1;
  
  public ModelFloatingDial()
  {
    textureWidth = 16;
    textureHeight = 16;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(0F, 0F, 0F, 1, 1, 4);
      Base.setRotationPoint(0F, 0F, 0F);
      Base.setTextureSize(16, 16);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Shape1 = new ModelRenderer(this, 0, 0);
      
      
      Shape1.addBox(0F, 0F, 0F, 1, 1, 4);
      Shape1.setRotationPoint(0F, 0F, 0F);
      Shape1.setTextureSize(16, 16);
      Shape1.mirror = true;
      setRotation(Shape1, 0.6246007F, 0F, 0F);
  }

  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

  
	public void renderThis() {
		GlStateManager.pushMatrix();
		Shape1.render(1F / 16F);
		Base.render(1F / 16F);
		GlStateManager.popMatrix();

	}

}
