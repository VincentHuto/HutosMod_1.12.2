package com.huto.hutosmod.models;

import com.huto.hutosmod.entities.EntityMaskedPraetor;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)

public class ModelMaskedPraetor extends ModelBase	{
	// fields
	ModelRenderer LeftBand;
	ModelRenderer Abs;
	ModelRenderer RightShoulder2;
	ModelRenderer leftShoulder2;
	ModelRenderer leftlegBrace;
	ModelRenderer leftleg;
	ModelRenderer righthand;
	ModelRenderer lefthand;
	ModelRenderer Cape;
	ModelRenderer Mask;
	ModelRenderer leftBand2;
	ModelRenderer head;
	ModelRenderer body;
	ModelRenderer BackBand;
	ModelRenderer rightarm;
	ModelRenderer leftarm;
	ModelRenderer RightShoulder3;
	ModelRenderer leftShoulder9;
	ModelRenderer leftShoulder5;
	ModelRenderer leftShoulder3;
	ModelRenderer Pecs;
	ModelRenderer leftShoulder1;
	ModelRenderer leftShoulder7;
	ModelRenderer RightShoulder1;
	ModelRenderer RightShoulder4;
	ModelRenderer RightShoulder6;
	ModelRenderer RightBand;
	ModelRenderer leftBand1;
	ModelRenderer RightBand2;
	ModelRenderer leftBand3;
	ModelRenderer RightBand1;
	ModelRenderer RightBand3;
	ModelRenderer rightleg;
	ModelRenderer rightlegBrace;
	ModelRenderer RightShoulder5;
	ModelRenderer RightShoulder7;
	ModelRenderer leftShoulder4;
	ModelRenderer leftShoulder6;
	ModelRenderer leftarmbrace;
	ModelRenderer rightarmBrace;
	ModelRenderer RightShoulder9;
	ModelRenderer RightShoulder10;
	ModelRenderer RightShoulder8;
	ModelRenderer leftShoulder10;
	ModelRenderer leftShoulder8;
	ModelRenderer leftShoulder;
	ModelRenderer RightShoulder;

	public ModelMaskedPraetor() {
		textureWidth = 128;
		textureHeight = 64;

		LeftBand = new ModelRenderer(this, 80, 0);
		LeftBand.addBox(4F, -7F, -5F, 1, 1, 10);
		LeftBand.setRotationPoint(0F, 0F, 0F);
		LeftBand.setTextureSize(128, 64);
		LeftBand.mirror = true;
		setRotation(LeftBand, 0F, 0F, 0F);
		
		Abs = new ModelRenderer(this, 4, 35);
		Abs.addBox(-2F, 6F, -3F, 4, 6, 1);
		Abs.setRotationPoint(0F, 0F, 0F);
		Abs.setTextureSize(128, 64);
		Abs.mirror = true;
		setRotation(Abs, 0F, 0F, 0F);
		RightShoulder2 = new ModelRenderer(this, 105, 52);
		RightShoulder2.addBox(-6F, -3F, -1F, 1, 6, 2);
		RightShoulder2.setRotationPoint(-5F, 2F, 0F);
		RightShoulder2.setTextureSize(128, 64);
		RightShoulder2.mirror = true;
		setRotation(RightShoulder2, 0F, 0F, 0.8552113F);
		leftShoulder2 = new ModelRenderer(this, 76, 52);
		leftShoulder2.addBox(5F, -3F, -1F, 1, 6, 2);
		leftShoulder2.setRotationPoint(5F, 2F, 0F);
		leftShoulder2.setTextureSize(128, 64);
		leftShoulder2.mirror = true;
		setRotation(leftShoulder2, 0F, 0F, -0.8552113F);
		leftlegBrace = new ModelRenderer(this, 0, 43);
		leftlegBrace.addBox(-1F, 3F, -3F, 2, 7, 1);
		leftlegBrace.setRotationPoint(2F, 12F, 0F);
		leftlegBrace.setTextureSize(128, 64);
		leftlegBrace.mirror = true;
		setRotation(leftlegBrace, 0F, 0F, 0F);
		leftleg = new ModelRenderer(this, 0, 16);
		leftleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		leftleg.setRotationPoint(2F, 12F, 0F);
		leftleg.setTextureSize(128, 64);
		leftleg.mirror = true;
		setRotation(leftleg, 0F, 0F, 0F);
		righthand = new ModelRenderer(this, 57, 55);
		righthand.addBox(-4F, 10F, -2F, 3, 1, 4);
		righthand.setRotationPoint(-5F, 2F, 0F);
		righthand.setTextureSize(128, 64);
		righthand.mirror = true;
		setRotation(righthand, 0F, 0F, 0F);
		lefthand = new ModelRenderer(this, 57, 55);
		lefthand.addBox(1F, 10F, -2F, 3, 1, 4);
		lefthand.setRotationPoint(5F, 2F, 0F);
		lefthand.setTextureSize(128, 64);
		lefthand.mirror = true;
		setRotation(lefthand, 0F, 0F, 0F);
		Cape = new ModelRenderer(this, 16, 47);
		Cape.addBox(-4F, 0F, 2F, 8, 15, 1);
		Cape.setRotationPoint(0F, 0F, 0F);
		Cape.setTextureSize(128, 64);
		Cape.mirror = true;
		setRotation(Cape, 0.1396263F, 0F, 0F);
		Mask = new ModelRenderer(this, 50, 34);
		Mask.addBox(-4F, -8F, -5F, 8, 8, 1);
		Mask.setRotationPoint(0F, 0F, 0F);
		Mask.setTextureSize(128, 64);
		Mask.mirror = true;
		setRotation(Mask, 0F, 0F, 0F);
		leftBand2 = new ModelRenderer(this, 70, 7);
		leftBand2.addBox(4F, -6F, -1F, 1, 4, 2);
		leftBand2.setRotationPoint(0F, 0F, 0F);
		leftBand2.setTextureSize(128, 64);
		leftBand2.mirror = true;
		setRotation(leftBand2, 0F, 0F, 0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(128, 64);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		body = new ModelRenderer(this, 16, 16);
		body.addBox(-4F, 0F, -2F, 8, 12, 4);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(128, 64);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		BackBand = new ModelRenderer(this, 50, 0);
		BackBand.addBox(-4F, -7F, 4F, 8, 1, 1);
		BackBand.setRotationPoint(0F, 0F, 0F);
		BackBand.setTextureSize(128, 64);
		BackBand.mirror = true;
		setRotation(BackBand, 0F, 0F, 0F);
		rightarm = new ModelRenderer(this, 40, 16);
		rightarm.addBox(-3F, -2F, -2F, 4, 12, 4);
		rightarm.setRotationPoint(-5F, 2F, 0F);
		rightarm.setTextureSize(128, 64);
		rightarm.mirror = true;
		setRotation(rightarm, 0F, 0F, 0F);
		leftarm = new ModelRenderer(this, 40, 16);
		leftarm.addBox(-1F, -2F, -2F, 4, 12, 4);
		leftarm.setRotationPoint(5F, 2F, 0F);
		leftarm.setTextureSize(128, 64);
		leftarm.mirror = true;
		setRotation(leftarm, 0F, 0F, 0F);
		RightShoulder3 = new ModelRenderer(this, 105, 39);
		RightShoulder3.addBox(-6F, -3F, -3F, 1, 6, 1);
		RightShoulder3.setRotationPoint(-5F, 2F, 0F);
		RightShoulder3.setTextureSize(128, 64);
		RightShoulder3.mirror = true;
		setRotation(RightShoulder3, 0F, 0F, 0.8552113F);
		leftShoulder9 = new ModelRenderer(this, 105, 47);
		leftShoulder9.addBox(0F, -3F, 3F, 3, 4, 1);
		leftShoulder9.setRotationPoint(5F, 2F, 0F);
		leftShoulder9.setTextureSize(128, 64);
		leftShoulder9.mirror = true;
		setRotation(leftShoulder9, 0F, 0F, 0F);
		leftShoulder5 = new ModelRenderer(this, 76, 39);
		leftShoulder5.addBox(5F, -3F, 1F, 1, 6, 1);
		leftShoulder5.setRotationPoint(5F, 2F, 0F);
		leftShoulder5.setTextureSize(128, 64);
		leftShoulder5.mirror = true;
		setRotation(leftShoulder5, 0F, 0F, -0.4363323F);
		leftShoulder3 = new ModelRenderer(this, 76, 39);
		leftShoulder3.addBox(5F, -3F, 2F, 1, 6, 1);
		leftShoulder3.setRotationPoint(5F, 2F, 0F);
		leftShoulder3.setTextureSize(128, 64);
		leftShoulder3.mirror = true;
		setRotation(leftShoulder3, 0F, 0F, -0.8552113F);
		Pecs = new ModelRenderer(this, 16, 35);
		Pecs.addBox(-4F, 0F, -3F, 8, 6, 1);
		Pecs.setRotationPoint(0F, 0F, 0F);
		Pecs.setTextureSize(128, 64);
		Pecs.mirror = true;
		setRotation(Pecs, 0F, 0F, 0F);
		leftShoulder1 = new ModelRenderer(this, 76, 39);
		leftShoulder1.addBox(5F, -3F, -3F, 1, 6, 1);
		leftShoulder1.setRotationPoint(5F, 2F, 0F);
		leftShoulder1.setTextureSize(128, 64);
		leftShoulder1.mirror = true;
		setRotation(leftShoulder1, 0F, 0F, -0.8552113F);
		leftShoulder7 = new ModelRenderer(this, 76, 39);
		leftShoulder7.addBox(5F, -3F, 1F, 1, 3, 1);
		leftShoulder7.setRotationPoint(5F, 2F, 0F);
		leftShoulder7.setTextureSize(128, 64);
		leftShoulder7.mirror = true;
		setRotation(leftShoulder7, 0F, 0F, 0.4886922F);
		RightShoulder1 = new ModelRenderer(this, 105, 39);
		RightShoulder1.addBox(-6F, -3F, 2F, 1, 6, 1);
		RightShoulder1.setRotationPoint(-5F, 2F, 0F);
		RightShoulder1.setTextureSize(128, 64);
		RightShoulder1.mirror = true;
		setRotation(RightShoulder1, 0F, 0F, 0.8552113F);
		RightShoulder4 = new ModelRenderer(this, 105, 39);
		RightShoulder4.addBox(-6F, -3F, 1F, 1, 6, 1);
		RightShoulder4.setRotationPoint(-5F, 2F, 0F);
		RightShoulder4.setTextureSize(128, 64);
		RightShoulder4.mirror = true;
		setRotation(RightShoulder4, 0F, 0F, 0.4363323F);
		RightShoulder6 = new ModelRenderer(this, 105, 39);
		RightShoulder6.addBox(-6F, -3F, 1F, 1, 3, 1);
		RightShoulder6.setRotationPoint(-5F, 2F, 0F);
		RightShoulder6.setTextureSize(128, 64);
		RightShoulder6.mirror = true;
		setRotation(RightShoulder6, 0F, 0F, -0.4886922F);
		RightBand = new ModelRenderer(this, 80, 0);
		RightBand.addBox(-5F, -7F, -5F, 1, 1, 10);
		RightBand.setRotationPoint(0F, 0F, 0F);
		RightBand.setTextureSize(128, 64);
		RightBand.mirror = true;
		setRotation(RightBand, 0F, 0F, 0F);
		leftBand1 = new ModelRenderer(this, 70, 0);
		leftBand1.addBox(4F, -6F, -3F, 1, 4, 1);
		leftBand1.setRotationPoint(0F, 0F, 0F);
		leftBand1.setTextureSize(128, 64);
		leftBand1.mirror = true;
		setRotation(leftBand1, 0F, 0F, 0F);
		RightBand2 = new ModelRenderer(this, 70, 7);
		RightBand2.addBox(-5F, -6F, -1F, 1, 4, 2);
		RightBand2.setRotationPoint(0F, 0F, 0F);
		RightBand2.setTextureSize(128, 64);
		RightBand2.mirror = true;
		setRotation(RightBand2, 0F, 0F, 0F);
		leftBand3 = new ModelRenderer(this, 70, 0);
		leftBand3.addBox(4F, -6F, 2F, 1, 4, 1);
		leftBand3.setRotationPoint(0F, 0F, 0F);
		leftBand3.setTextureSize(128, 64);
		leftBand3.mirror = true;
		setRotation(leftBand3, 0F, 0F, 0F);
		RightBand1 = new ModelRenderer(this, 70, 0);
		RightBand1.addBox(-5F, -6F, -3F, 1, 4, 1);
		RightBand1.setRotationPoint(0F, 0F, 0F);
		RightBand1.setTextureSize(128, 64);
		RightBand1.mirror = true;
		setRotation(RightBand1, 0F, 0F, 0F);
		RightBand3 = new ModelRenderer(this, 70, 0);
		RightBand3.addBox(-5F, -6F, 2F, 1, 4, 1);
		RightBand3.setRotationPoint(0F, 0F, 0F);
		RightBand3.setTextureSize(128, 64);
		RightBand3.mirror = true;
		setRotation(RightBand3, 0F, 0F, 0F);
		rightleg = new ModelRenderer(this, 0, 16);
		rightleg.addBox(-2F, 0F, -2F, 4, 12, 4);
		rightleg.setRotationPoint(-2F, 12F, 0F);
		rightleg.setTextureSize(128, 64);
		rightleg.mirror = true;
		setRotation(rightleg, 0F, 0F, 0F);
		rightlegBrace = new ModelRenderer(this, 0, 43);
		rightlegBrace.addBox(-1F, 3F, -3F, 2, 7, 1);
		rightlegBrace.setRotationPoint(-2F, 12F, 0F);
		rightlegBrace.setTextureSize(128, 64);
		rightlegBrace.mirror = true;
		setRotation(rightlegBrace, 0F, 0F, 0F);
		RightShoulder5 = new ModelRenderer(this, 105, 39);
		RightShoulder5.addBox(-6F, -3F, -2F, 1, 6, 1);
		RightShoulder5.setRotationPoint(-5F, 2F, 0F);
		RightShoulder5.setTextureSize(128, 64);
		RightShoulder5.mirror = true;
		setRotation(RightShoulder5, 0F, 0F, 0.4363323F);
		RightShoulder7 = new ModelRenderer(this, 105, 39);
		RightShoulder7.addBox(-6F, -3F, -2F, 1, 3, 1);
		RightShoulder7.setRotationPoint(-5F, 2F, 0F);
		RightShoulder7.setTextureSize(128, 64);
		RightShoulder7.mirror = true;
		setRotation(RightShoulder7, 0F, 0F, -0.4886922F);
		leftShoulder4 = new ModelRenderer(this, 76, 39);
		leftShoulder4.addBox(5F, -3F, -2F, 1, 6, 1);
		leftShoulder4.setRotationPoint(5F, 2F, 0F);
		leftShoulder4.setTextureSize(128, 64);
		leftShoulder4.mirror = true;
		setRotation(leftShoulder4, 0F, 0F, -0.4363323F);
		leftShoulder6 = new ModelRenderer(this, 76, 39);
		leftShoulder6.addBox(5F, -3F, -2F, 1, 3, 1);
		leftShoulder6.setRotationPoint(5F, 2F, 0F);
		leftShoulder6.setTextureSize(128, 64);
		leftShoulder6.mirror = true;
		setRotation(leftShoulder6, 0F, 0F, 0.4886922F);
		leftarmbrace = new ModelRenderer(this, 40, 48);
		leftarmbrace.addBox(3F, 3F, -2F, 1, 7, 4);
		leftarmbrace.setRotationPoint(5F, 2F, 0F);
		leftarmbrace.setTextureSize(128, 64);
		leftarmbrace.mirror = true;
		setRotation(leftarmbrace, 0F, 0F, 0F);
		rightarmBrace = new ModelRenderer(this, 40, 48);
		rightarmBrace.addBox(-4F, 3F, -2F, 1, 7, 4);
		rightarmBrace.setRotationPoint(-5F, 2F, 0F);
		rightarmBrace.setTextureSize(128, 64);
		rightarmBrace.mirror = true;
		setRotation(rightarmBrace, 0F, 0F, 0F);
		RightShoulder9 = new ModelRenderer(this, 105, 47);
		RightShoulder9.addBox(-3F, -3F, 3F, 3, 4, 1);
		RightShoulder9.setRotationPoint(-5F, 2F, 0F);
		RightShoulder9.setTextureSize(128, 64);
		RightShoulder9.mirror = true;
		setRotation(RightShoulder9, 0F, 0F, 0F);
		RightShoulder10 = new ModelRenderer(this, 105, 29);
		RightShoulder10.addBox(-5F, -3F, -2F, 1, 5, 4);
		RightShoulder10.setRotationPoint(-5F, 2F, 0F);
		RightShoulder10.setTextureSize(128, 64);
		RightShoulder10.mirror = true;
		setRotation(RightShoulder10, 0F, 0F, 0F);
		RightShoulder8 = new ModelRenderer(this, 105, 47);
		RightShoulder8.addBox(-3F, -3F, -4F, 3, 4, 1);
		RightShoulder8.setRotationPoint(-5F, 2F, 0F);
		RightShoulder8.setTextureSize(128, 64);
		RightShoulder8.mirror = true;
		setRotation(RightShoulder8, 0F, 0F, 0F);
		leftShoulder10 = new ModelRenderer(this, 76, 29);
		leftShoulder10.addBox(4F, -3F, -2F, 1, 5, 4);
		leftShoulder10.setRotationPoint(5F, 2F, 0F);
		leftShoulder10.setTextureSize(128, 64);
		leftShoulder10.mirror = true;
		setRotation(leftShoulder10, 0F, 0F, 0F);
		leftShoulder8 = new ModelRenderer(this, 77, 47);
		leftShoulder8.addBox(0F, -3F, -4F, 3, 4, 1);
		leftShoulder8.setRotationPoint(5F, 2F, 0F);
		leftShoulder8.setTextureSize(128, 64);
		leftShoulder8.mirror = true;
		setRotation(leftShoulder8, 0F, 0F, 0F);
		leftShoulder = new ModelRenderer(this, 76, 16);
		leftShoulder.addBox(-1F, -4F, -3F, 5, 6, 6);
		leftShoulder.setRotationPoint(5F, 2F, 0F);
		leftShoulder.setTextureSize(128, 64);
		leftShoulder.mirror = true;
		setRotation(leftShoulder, 0F, 0F, 0F);
		RightShoulder = new ModelRenderer(this, 105, 16);
		RightShoulder.addBox(-4F, -4F, -3F, 5, 6, 6);
		RightShoulder.setRotationPoint(-5F, 2F, 0F);
		RightShoulder.setTextureSize(128, 64);
		RightShoulder.mirror = true;
		setRotation(RightShoulder, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		
		 super.render(entity, f, f1, f2, f3, f4, f5);
		 this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		 
		LeftBand.render(f5);
		Abs.render(f5);
		RightShoulder2.render(f5);
		leftShoulder2.render(f5);
		leftlegBrace.render(f5);
		leftleg.render(f5);
		righthand.render(f5);
		lefthand.render(f5);
		Cape.render(f5);
		Mask.render(f5);
		//leftBand2.render(f5);
		head.render(f5);
		body.render(f5);
		BackBand.render(f5);
		rightarm.render(f5);
		leftarm.render(f5);
		RightShoulder3.render(f5);
		leftShoulder9.render(f5);
		leftShoulder5.render(f5);
		leftShoulder3.render(f5);
		Pecs.render(f5);
		leftShoulder1.render(f5);
		leftShoulder7.render(f5);
		RightShoulder1.render(f5);
		RightShoulder4.render(f5);
		RightShoulder6.render(f5);
		RightBand.render(f5);
		leftBand1.render(f5);
		RightBand2.render(f5);
		leftBand3.render(f5);
		RightBand1.render(f5);
		RightBand3.render(f5);
		rightleg.render(f5);
		rightlegBrace.render(f5);
		RightShoulder5.render(f5);
		RightShoulder7.render(f5);
		leftShoulder4.render(f5);
		leftShoulder6.render(f5);
		leftarmbrace.render(f5);
		rightarmBrace.render(f5);
		RightShoulder9.render(f5);
		RightShoulder10.render(f5);
		RightShoulder8.render(f5);
		leftShoulder10.render(f5);
		leftShoulder8.render(f5);
		leftShoulder.render(f5);
		RightShoulder.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float scaleFactor, float headPitch, Entity entityIn) {
		
		boolean flag = entityIn instanceof EntityMaskedPraetor && ((EntityMaskedPraetor) entityIn).getTicksElytraFlying() > 4;
		//Head
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		this.Mask.rotateAngleY = netHeadYaw * 0.017453292F;
		this.LeftBand.rotateAngleY = netHeadYaw * 0.017453292F;
		this.RightBand.rotateAngleY = netHeadYaw * 0.017453292F;
		this.BackBand.rotateAngleY = netHeadYaw * 0.017453292F;


		if (flag) {
			this.head.rotateAngleX = -((float) Math.PI / 4F);
			this.Mask.rotateAngleX = -((float) Math.PI / 4F);
			this.Mask.rotateAngleX = -((float) Math.PI / 4F);
			this.Mask.rotateAngleX = -((float) Math.PI / 4F);
			this.Mask.rotateAngleX = -((float) Math.PI / 4F);


		} else {
			

		}
		
		
		
		
		this.body.rotateAngleY = 0.0F;
		this.rightarm.rotationPointZ = 0.0F;
		this.rightarm.rotationPointX = -5.0F;
		this.leftarm.rotationPointZ = 0.0F;
		this.leftarm.rotationPointX = 5.0F;
		float f = 1.0F;

		if (flag) {
			f = (float) (entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY
					+ entityIn.motionZ * entityIn.motionZ);
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.rightarm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount* 0.5F / f;
		this.leftarm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.rightarm.rotateAngleZ = 0.0F;
		this.leftarm.rotateAngleZ = 0.0F;
		this.rightleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
		this.leftleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
		this.rightleg.rotateAngleY = 0.0F;
		this.leftleg.rotateAngleY = 0.0F;
		this.rightleg.rotateAngleZ = 0.0F;
		this.leftleg.rotateAngleZ = 0.0F;
		
	}

}
