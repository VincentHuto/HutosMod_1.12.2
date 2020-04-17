package com.huto.hutosmod.renders;

import com.huto.hutosmod.entities.EntityTestMob;
import com.huto.hutosmod.models.ModelTestMob;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)

public class RenderTestMob extends RenderLiving<EntityTestMob>
{

	public static final ResourceLocation Texture = new ResourceLocation(Reference.MODID + ":textures/entity/TestMob.png");
			
	
	public RenderTestMob(RenderManager manager) {
		super(manager, new ModelTestMob(), 0.5F);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTestMob entity) {
		// TODO Auto-generated method stub
		return Texture;
	}

}
