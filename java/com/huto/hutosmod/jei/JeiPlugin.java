package com.huto.hutosmod.jei;

import com.huto.hutosmod.jei.catagories.EssenceEnhancerRecipeCatagory;
import com.huto.hutosmod.jei.catagories.FuserRecipeCatagory;
import com.huto.hutosmod.jei.catagories.WandMakerRecipeCatagory;
import com.huto.hutosmod.jei.wrappers.EnhancerRecipeWrapper;
import com.huto.hutosmod.jei.wrappers.FuserRecipeWrapper;
import com.huto.hutosmod.jei.wrappers.WandMakerRecipeWrapper;
import com.huto.hutosmod.recipies.ModEnhancerRecipies;
import com.huto.hutosmod.recipies.ModFuserRecipes;
import com.huto.hutosmod.recipies.ModWandRecipies;
import com.huto.hutosmod.recipies.RecipeEnhancer;
import com.huto.hutosmod.recipies.RecipeManaFuser;
import com.huto.hutosmod.recipies.RecipeWandMaker;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {
		IModPlugin.super.register(registry);
		registry.handleRecipes(RecipeEnhancer.class, EnhancerRecipeWrapper::new, EssenceEnhancerRecipeCatagory.UID);
		registry.addRecipes(ModEnhancerRecipies.enhancerRecipies, EssenceEnhancerRecipeCatagory.UID);

		registry.handleRecipes(RecipeManaFuser.class, FuserRecipeWrapper::new, FuserRecipeCatagory.UID);
		registry.addRecipes(ModFuserRecipes.manaFuserRecipies, FuserRecipeCatagory.UID);

		registry.handleRecipes(RecipeWandMaker.class, WandMakerRecipeWrapper::new, WandMakerRecipeCatagory.UID);
		registry.addRecipes(ModWandRecipies.wandMakerRecipies, WandMakerRecipeCatagory.UID);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new EssenceEnhancerRecipeCatagory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new FuserRecipeCatagory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new WandMakerRecipeCatagory(registry.getJeiHelpers().getGuiHelper()));

	}
}
