package com.huto.hutosmod.renders;

import com.huto.hutosmod.entities.EntityColin;
import com.huto.hutosmod.models.ModelColin;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.renders.layers.LayerColinAttack;
import com.huto.hutosmod.renders.layers.LayerColinAura;
import com.huto.hutosmod.renders.layers.LayerColinDeath;
import com.huto.hutosmod.renders.layers.LayerColinKeys;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)

public class RenderColin extends RenderLiving<EntityColin>
{

	public static final ResourceLocation Texture = new ResourceLocation(Reference.MODID + ":textures/entity/ModelColin.png");
			
	
	public RenderColin(RenderManager manager) {
		super(manager, new ModelColin(), 1.5F);
        this.addLayer(new LayerColinAura(this));
        this.addLayer(new LayerColinAttack(this));
        this.addLayer(new LayerColinDeath());
        this.addLayer(new LayerColinKeys(this));

        
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityColin entity) {
		// TODO Auto-generated method stub
		return Texture;
	}

    
}
