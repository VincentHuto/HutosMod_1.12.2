package com.huto.hutosmod.models;

import com.huto.hutosmod.entities.EntityColin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelColin - Either Mojang or a mod author Created using Tabula 7.1.0
 */
@SideOnly(Side.CLIENT)
public class ModelColin extends ModelBase {
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer rightarm;
	public ModelRenderer bipedBody;
	public ModelRenderer leftleg;
	public ModelRenderer body;
	public ModelRenderer bipedHead;
	public ModelRenderer head;
	public ModelRenderer bipedRightLeg;
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer rightleg;
	public ModelRenderer bipedHeadwear;
	public ModelRenderer leftarm;

	public ModelColin() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.leftleg = new ModelRenderer(this, 0, 16);
		this.leftleg.mirror = true;
		this.leftleg.setRotationPoint(2.0F, 12.0F, 0.0F);
		this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.setRotationPoint(-1.899999976158142F, 12.0F, 0.0F);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		this.body = new ModelRenderer(this, 16, 16);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
		this.leftarm = new ModelRenderer(this, 40, 16);
		this.leftarm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.leftarm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(leftarm, 1.401298464324817E-45F, 0.0F, 0.0F);
		this.rightleg = new ModelRenderer(this, 0, 16);
		this.rightleg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(rightleg, 1.401298464324817E-45F, 0.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 40, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.setRotateAngle(head, 7.017453193664551F, 0.0F, 0.0F);
		this.rightarm = new ModelRenderer(this, 40, 16);
		this.rightarm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rightarm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(rightarm, -1.401298464324817E-45F, 0.0F, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.leftleg.render(f5);
		// this.bipedRightLeg.render(f5);
		this.body.render(f5);
		// this.bipedBody.render(f5);
		//this.bipedLeftLeg.render(f5);
		// this.bipedHeadwear.render(f5);
		this.leftarm.render(f5);
		this.rightleg.render(f5);
		// this.bipedLeftArm.render(f5);
		// this.bipedHead.render(f5);
		this.head.render(f5);
		this.rightarm.render(f5);
		// this.bipedRightArm.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float scaleFactor, float headPitch, Entity entityIn) {
		boolean flag = entityIn instanceof EntityColin && ((EntityColin) entityIn).getTicksElytraFlying() > 4;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;

		if (flag) {
			this.head.rotateAngleX = -((float) Math.PI / 4F);
		} else {
			this.head.rotateAngleX = 7.017453292F;
			this.head.offsetY = -0.1F;

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
			// Change f to make limbs move faster or slower
			f = f / 30.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.rightarm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount
				* 0.5F / f;
		this.leftarm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		this.rightarm.rotateAngleZ = 0.0F;
		this.leftarm.rotateAngleZ = 0.0F;
		this.rightleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.6F * limbSwingAmount / f;
		this.leftleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.6F * limbSwingAmount / f;
		this.rightleg.rotateAngleY = 0.0F;
		this.leftleg.rotateAngleY = 0.0F;
		this.rightleg.rotateAngleZ = 0.0F;
		this.leftleg.rotateAngleZ = 0.0F;

		// Head bob POC
		/*
		 * this.head.rotationPointY = MathHelper.cos(ageInTicks)* 1.25F;
		 * this.head.rotationPointX = MathHelper.cos(ageInTicks) * 1.25F;
		 * this.head.rotationPointZ = MathHelper.sin(ageInTicks) * 1.25F; ++ageInTicks;
		 */

	}

}
