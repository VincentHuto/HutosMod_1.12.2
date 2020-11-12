package com.huto.hutosmod.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)

public class ModelManaViewer extends ModelBiped
{
  //fields
    ModelRenderer frontBand;
    ModelRenderer leftBand;
    ModelRenderer rightBand;
    ModelRenderer gem;
    ModelRenderer backBand;
  
  public ModelManaViewer()
  {
    textureWidth = 64;
    textureHeight = 128;
    
      frontBand = new ModelRenderer(this,0, 120);
      frontBand.addBox(-4F, -8F, -5F, 8, 1, 1);
      frontBand.setRotationPoint(0F, 0F, 0F);
      frontBand.setTextureSize(64, 128);
      frontBand.mirror = true;
      setRotation(frontBand, 0F, 0F, 0F);
      leftBand = new ModelRenderer(this, 20, 100);
      leftBand.addBox(4F, -7F, -4F, 1, 1, 8);
      leftBand.setRotationPoint(0F, 0F, 0F);
      leftBand.setTextureSize(64, 128);
      leftBand.mirror = true;
      setRotation(leftBand, 0F, 0F, 0F);
      rightBand = new ModelRenderer(this, 20, 100);
      rightBand.addBox(-5F, -7F, -4F, 1, 1, 8);
      rightBand.setRotationPoint(0F, 0F, 0F);
      rightBand.setTextureSize(64, 128);
      rightBand.mirror = true;
      setRotation(rightBand, 0F, 0F, 0F);
      gem = new ModelRenderer(this, 0, 100);
      gem.addBox(-1F, -7F, -5F, 2, 2, 1);
      gem.setRotationPoint(0F, 0F, 0F);
      gem.setTextureSize(64, 128);
      gem.mirror = true;
      setRotation(gem, 0F, 0F, 0F);
      backBand = new ModelRenderer(this, 0, 120);
      backBand.addBox(-4F, -6F, 4F, 8, 1, 1);
      backBand.setRotationPoint(0F, 0F, 0F);
      backBand.setTextureSize(64, 128);
      backBand.mirror = true;
      setRotation(backBand, 0F, 0F, 0F);
      

      this.bipedHead.addChild(gem);
      this.bipedHead.addChild(leftBand);
      this.bipedHead.addChild(rightBand);
      this.bipedHead.addChild(frontBand);
      this.bipedHead.addChild(backBand);
      
      
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
 /*   setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    frontBand.render(f5);
    leftBand.render(f5);
    rightBand.render(f5);
    gem.render(f5);
    backBand.render(f5);*/
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

}
