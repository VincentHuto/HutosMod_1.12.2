package com.huto.hutosmod.renders;

import com.huto.hutosmod.entities.EntityElemental;
import com.huto.hutosmod.entities.EntityMaskedPraetor;
import com.huto.hutosmod.models.ModelElemental;
import com.huto.hutosmod.models.ModelMaskedPraetor;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.renders.layers.LayerColinAura;
import com.huto.hutosmod.renders.layers.LayerElemental;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderElemental extends RenderLiving<EntityElemental> {

	public static final ResourceLocation Texture = new ResourceLocation(
			Reference.MODID + ":textures/entity/ModelElemental.png");

	public RenderElemental(RenderManager manager) {
		super(manager, new ModelElemental(), 0.5F);
        this.addLayer(new LayerElemental(this));

		// TODO Auto-generated constructor stub
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityElemental entity) {
		// TODO Auto-generated method stub
		return Texture;
	}

}
