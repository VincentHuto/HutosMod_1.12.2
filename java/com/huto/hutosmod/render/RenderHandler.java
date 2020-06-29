package com.huto.hutosmod.render;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.entities.EntityColin;
import com.huto.hutosmod.entities.EntityDreamWalker;
import com.huto.hutosmod.entities.EntityElemental;
import com.huto.hutosmod.entities.EntityMaskedPraetor;
import com.huto.hutosmod.entities.EntityTestMob;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.render.entity.RenderColin;
import com.huto.hutosmod.render.entity.RenderDreamWalker;
import com.huto.hutosmod.render.entity.RenderElemental;
import com.huto.hutosmod.render.entity.RenderMaskedPraetor;
import com.huto.hutosmod.render.entity.RenderTestMob;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHandler {
	
	
	public static void registerCustomMeshesAndStates()
	{
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(BlockRegistry.WHITE_WATER_FLUID), new ItemMeshDefinition() 
		{	
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) 
			{
				return new ModelResourceLocation(Reference.MODID + ":white_water", "fluid");
			}
		});
		
		
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(BlockRegistry.primal_ooze_fluid), new ItemMeshDefinition() 
		{	
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) 
			{
				return new ModelResourceLocation(Reference.MODID + ":primal_ooze", "fluid");
			}
		});
		
		
		ModelLoader.setCustomStateMapper(BlockRegistry.WHITE_WATER_FLUID, new StateMapperBase() 
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) 
			{
				return new ModelResourceLocation(Reference.MODID + ":white_water", "fluid");
			}
		});
		
		
		ModelLoader.setCustomStateMapper(BlockRegistry.primal_ooze_fluid, new StateMapperBase() 
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) 
			{
				return new ModelResourceLocation(Reference.MODID + ":primal_ooze", "fluid");
			}
		});
	}

	
	
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
		RenderingRegistry.registerEntityRenderingHandler(EntityDreamWalker.class, new IRenderFactory<EntityDreamWalker>() {
			@Override
			public Render<? super EntityDreamWalker> createRenderFor(RenderManager manager) {
				return new RenderDreamWalker(manager);
			}
		});
	}
}
