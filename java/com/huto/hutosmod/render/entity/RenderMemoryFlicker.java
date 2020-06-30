package com.huto.hutosmod.render.entity;

import com.huto.hutosmod.entities.EntityMemoryFlicker;
import com.huto.hutosmod.models.ModelMemoryFlicker;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)

public class RenderMemoryFlicker extends RenderLiving<EntityMemoryFlicker> {

	public static final ResourceLocation Texture = new ResourceLocation(
			Reference.MODID + ":textures/entity/modelmemoryflicker.png");

	public RenderMemoryFlicker(RenderManager manager) {
		super(manager, new ModelMemoryFlicker(), 0.5F);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMemoryFlicker entity) {
		// TODO Auto-generated method stub
		return Texture;
	}

}
