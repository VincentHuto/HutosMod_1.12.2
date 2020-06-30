package com.huto.hutosmod.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports

public class ModelMemoryFlicker extends ModelBiped {
	private final ModelRenderer face;
	private final ModelRenderer body;
	private final ModelRenderer tail;
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
	private final ModelRenderer leg3;
	private final ModelRenderer leg4;
	private final ModelRenderer leg5;
	private final ModelRenderer leg6;
	private final ModelRenderer leg7;
	private final ModelRenderer leg8;

	public ModelMemoryFlicker() {
		textureWidth = 64;
		textureHeight = 64;

		face = new ModelRenderer(this);
		face.setRotationPoint(4.0F, 19.0F, -8.0F);
		face.cubeList.add(new ModelBox(face, 2, 2, -8.0F, -8.0F, 2.0F, 8, 8, 3, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 13, -3.0F, -12.0F, -3.0F, 6, 6, 4, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 24.0F, 0.0F);
		tail.cubeList.add(new ModelBox(tail, 16, 16, -1.0F, -10.0F, 0.0F, 2, 2, 9, 0.0F, false));

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg1.cubeList.add(new ModelBox(leg1, 15, 19, -1.0F, -13.0F, 0.0F, 2, 2, 5, 0.0F, false));

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg2.cubeList.add(new ModelBox(leg2, 15, 19, -4.0F, -7.0F, -2.0F, 2, 2, 5, 0.0F, false));

		leg3 = new ModelRenderer(this);
		leg3.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg3.cubeList.add(new ModelBox(leg3, 15, 19, -4.0F, -10.0F, 0.0F, 2, 2, 5, 0.0F, false));

		leg4 = new ModelRenderer(this);
		leg4.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg4.cubeList.add(new ModelBox(leg4, 15, 19, -4.0F, -13.0F, -2.0F, 2, 2, 5, 0.0F, false));

		leg5 = new ModelRenderer(this);
		leg5.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg5.cubeList.add(new ModelBox(leg5, 15, 19, 2.0F, -13.0F, -2.0F, 2, 2, 5, 0.0F, false));

		leg6 = new ModelRenderer(this);
		leg6.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg6.cubeList.add(new ModelBox(leg6, 15, 19, -1.0F, -7.0F, 0.0F, 2, 2, 5, 0.0F, false));

		leg7 = new ModelRenderer(this);
		leg7.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg7.cubeList.add(new ModelBox(leg7, 15, 19, 2.0F, -10.0F, 0.0F, 2, 2, 5, 0.0F, false));

		leg8 = new ModelRenderer(this);
		leg8.setRotationPoint(0.0F, 24.0F, 0.0F);
		leg8.cubeList.add(new ModelBox(leg8, 15, 19, 2.0F, -7.0F, -2.0F, 2, 2, 5, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		face.render(f5);
		body.render(f5);
		tail.render(f5);
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		leg5.render(f5);
		leg6.render(f5);
		leg7.render(f5);
		leg8.render(f5);
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {

	}
}