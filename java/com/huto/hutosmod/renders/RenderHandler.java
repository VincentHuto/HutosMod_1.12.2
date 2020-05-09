package com.huto.hutosmod.renders;

import com.huto.hutosmod.entities.EntityColin;
import com.huto.hutosmod.entities.EntityElemental;
import com.huto.hutosmod.entities.EntityMaskedPraetor;
import com.huto.hutosmod.entities.EntityTestMob;
import com.huto.hutosmod.renders.layers.LayerPlayerAura;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHandler {
	
	public static void registerEntityRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityTestMob.class, new IRenderFactory<EntityTestMob>() {
			@Override
			public Render<? super EntityTestMob> createRenderFor(RenderManager manager) {
				return new RenderTestMob(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityColin.class, new IRenderFactory<EntityColin>() {
			@Override
			public Render<? super EntityColin> createRenderFor(RenderManager manager) {
				return new RenderColin(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityMaskedPraetor.class, new IRenderFactory<EntityMaskedPraetor>() {
			@Override
			public Render<? super EntityMaskedPraetor> createRenderFor(RenderManager manager) {
				return new RenderMaskedPraetor(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityElemental.class, new IRenderFactory<EntityElemental>() {
			@Override
			public Render<? super EntityElemental> createRenderFor(RenderManager manager) {
				return new RenderElemental(manager);
			}
		});
		
	}
}
