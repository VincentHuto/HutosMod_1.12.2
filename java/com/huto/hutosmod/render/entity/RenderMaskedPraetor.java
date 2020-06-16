package com.huto.hutosmod.render.entity;

import com.huto.hutosmod.entities.EntityMaskedPraetor;
import com.huto.hutosmod.models.ModelMaskedPraetor;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)

public class RenderMaskedPraetor extends RenderLiving<EntityMaskedPraetor> {

	public static final ResourceLocation Texture = new ResourceLocation(
			Reference.MODID + ":textures/entity/ModelMaskedPraetor.png");

	public RenderMaskedPraetor(RenderManager manager) {
		super(manager, new ModelMaskedPraetor(), 0.5F);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMaskedPraetor entity) {
		// TODO Auto-generated method stub
		return Texture;
	}

}
