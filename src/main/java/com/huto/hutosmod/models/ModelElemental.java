package com.huto.hutosmod.models;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelElemental extends ModelBase {
	// fields
	ModelRenderer base;
	ModelRenderer[] orbital = new ModelRenderer[30];

	public ModelElemental() {
		textureWidth = 64;
		textureHeight = 32;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(-1F, -1F, -1F, 3, 3, 3);
		base.setRotationPoint(0F, 0F, 0F);
		base.setTextureSize(64, 32);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		
		
		for (int i = 0; i < this.orbital.length; ++i) {
			this.orbital[i] = new ModelRenderer(this, 0, 16);
			this.orbital[i].addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
		}

	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		//super.render(entity, f, f1, f2, f3, f4, f5);
		//setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		base.render(f5);
		for (ModelRenderer modelrenderer : this.orbital) {
			modelrenderer.render(f5);
		}

	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		
		Random rand = new Random();
		float speedMult = (float) (rand.nextFloat()*0.00003);
		
		float f = ageInTicks * (float) Math.PI * -0.07F;

		for (int i = 0; i < 6; ++i) {
			this.orbital[i].rotationPointY = -2.0F + MathHelper.cos(((float) (i * 2) + ageInTicks) * 0.25F);
			this.orbital[i].rotationPointX =speedMult+  2+ MathHelper.cos(f) * 5.0F;
			this.orbital[i].rotationPointZ =speedMult+  2+ MathHelper.sin(f) * 5.0F;
			++f;
		}

		f  = ageInTicks * (float) Math.PI * -0.07F;

		for (int j = 6; j < 12; ++j) {
			this.orbital[j].rotationPointY = 2.0F + MathHelper.cos(((float) (j * 2) + ageInTicks) * 0.25F);
			this.orbital[j].rotationPointX = speedMult+ MathHelper.cos(f) * 7.0F;
			this.orbital[j].rotationPointZ = speedMult+ MathHelper.sin(f) * 7.0F;
			++f;
		}

		f  = ageInTicks * (float) Math.PI * -0.07F;

		for (int k = 12; k < 18; ++k) {
			this.orbital[k].rotationPointY = -4.0F + MathHelper.cos(((float) k * 1.5F + ageInTicks) * 0.25F);
			this.orbital[k].rotationPointX = speedMult+ MathHelper.cos(f) * 7.0F;
			this.orbital[k].rotationPointZ = speedMult+ MathHelper.sin(f) * 7.0F;
			++f;
		}
		
		f  = ageInTicks * (float) Math.PI * -0.07F;

		for (int k = 18; k < 24; ++k) {
			this.orbital[k].rotationPointY = -8.0F + MathHelper.cos(((float) k * 1.5F + ageInTicks) * 0.25F);
			this.orbital[k].rotationPointX = speedMult+ MathHelper.cos(f) * 2.0F;
			this.orbital[k].rotationPointZ = speedMult+ MathHelper.sin(f) * 7.0F;
			++f;
		}
		
		for (int k = 24; k < 30; ++k) {
			this.orbital[k].rotationPointY = 6.0F + MathHelper.cos(((float) k * 1.5F + ageInTicks) * 0.25F);
			this.orbital[k].rotationPointX = speedMult+ MathHelper.cos(f) * 2.0F;
			this.orbital[k].rotationPointZ = speedMult+ MathHelper.sin(f) * 7.0F;
			++f;
		}

		this.base.rotateAngleY =speedMult* netHeadYaw * 0.017453292F;
		this.base.rotateAngleX = speedMult*headPitch * 0.017453292F;
		this.base.rotateAngleZ = rand.nextFloat()*speedMult*headPitch*netHeadYaw * 0.017453292F;
	}
}
