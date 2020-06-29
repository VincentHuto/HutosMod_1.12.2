package com.huto.hutosmod.render.entity;

import javax.annotation.Nonnull;

import com.huto.hutosmod.entities.EntityDreamWalker;
import com.huto.hutosmod.render.layer.LayerColinAura;
import com.huto.hutosmod.render.layer.LayerDreamWalkerMask;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)

public class RenderDreamWalker extends RenderBiped<EntityDreamWalker> {

	public RenderDreamWalker(RenderManager manager) {
		super(manager, new ModelPlayer(0.0F, false), 0F);
        this.addLayer(new LayerDreamWalkerMask(this));

	}
	@Nonnull
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityDreamWalker entity) {
		Minecraft mc = Minecraft.getMinecraft();

		if (!(mc.getRenderViewEntity() instanceof AbstractClientPlayer))
			return DefaultPlayerSkin.getDefaultSkinLegacy();

		return ((AbstractClientPlayer) mc.getRenderViewEntity()).getLocationSkin();
	}

}
