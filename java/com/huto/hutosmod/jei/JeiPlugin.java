package com.huto.hutosmod.jei;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.jei.catagories.ChiselStationRecipeCatagory;
import com.huto.hutosmod.jei.catagories.EssenceEnhancerRecipeCatagory;
import com.huto.hutosmod.jei.catagories.FuserRecipeCatagory;
import com.huto.hutosmod.jei.catagories.WandMakerRecipeCatagory;
import com.huto.hutosmod.jei.wrappers.ChiselStationRecipeWrapper;
import com.huto.hutosmod.jei.wrappers.EnhancerRecipeWrapper;
import com.huto.hutosmod.jei.wrappers.FuserRecipeWrapper;
import com.huto.hutosmod.jei.wrappers.WandMakerRecipeWrapper;
import com.huto.hutosmod.recipies.ModChiselRecipies;
import com.huto.hutosmod.recipies.ModEnhancerRecipies;
import com.huto.hutosmod.recipies.ModFuserRecipes;
import com.huto.hutosmod.recipies.ModWandRecipies;
import com.huto.hutosmod.recipies.RecipeEnhancer;
import com.huto.hutosmod.recipies.RecipeManaFuser;
import com.huto.hutosmod.recipies.RecipeRuneChisel;
import com.huto.hutosmod.recipies.RecipeWandMaker;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {
		IModPlugin.super.register(registry);
		registry.handleRecipes(RecipeEnhancer.class, EnhancerRecipeWrapper::new, EssenceEnhancerRecipeCatagory.UID);
		registry.addRecipes(ModEnhancerRecipies.enhancerRecipies, EssenceEnhancerRecipeCatagory.UID);
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.essecence_enhancer), EssenceEnhancerRecipeCatagory.UID);

		registry.handleRecipes(RecipeManaFuser.class, FuserRecipeWrapper::new, FuserRecipeCatagory.UID);
		registry.addRecipes(ModFuserRecipes.manaFuserRecipies, FuserRecipeCatagory.UID);
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.mana_fuser), FuserRecipeCatagory.UID);

		registry.handleRecipes(RecipeWandMaker.class, WandMakerRecipeWrapper::new, WandMakerRecipeCatagory.UID);
		registry.addRecipes(ModWandRecipies.wandMakerRecipies, WandMakerRecipeCatagory.UID);
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.wand_maker), WandMakerRecipeCatagory.UID);

		registry.handleRecipes(RecipeRuneChisel.class,ChiselStationRecipeWrapper::new, ChiselStationRecipeCatagory.UID);
		registry.addRecipes(ModChiselRecipies.runeRecipies, ChiselStationRecipeCatagory.UID);
		registry.addRecipeCatalyst(new ItemStack(BlockRegistry.runic_chiselstation), ChiselStationRecipeCatagory.UID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new EssenceEnhancerRecipeCatagory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new FuserRecipeCatagory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new WandMakerRecipeCatagory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new ChiselStationRecipeCatagory(registry.getJeiHelpers().getGuiHelper()));

	}
}
