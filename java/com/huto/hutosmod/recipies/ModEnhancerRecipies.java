package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModEnhancerRecipies {
	public static final List<RecipeEnhancer> enhancerRecipies = new ArrayList<>();
	public static RecipeEnhancer recipeEnhancedMagatama;
	public static RecipeEnhancer recipeNullRod;


	public static void init() {

		recipeEnhancedMagatama = registerEnhancerRecipe(new ItemStack(ItemRegistry.enhancedmagatama, 1),30,
				new ItemStack(ItemRegistry.magatamabead, 1));
		recipeNullRod = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_rod, 1),50,
				new ItemStack(ItemRegistry.channeling_rod, 1));
	}

	public static RecipeEnhancer registerEnhancerRecipe(ItemStack output,float mana, Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 2);
		RecipeEnhancer recipe = new RecipeEnhancer(output,mana, inputs);
		enhancerRecipies.add(recipe);
		return recipe;
	}

}
