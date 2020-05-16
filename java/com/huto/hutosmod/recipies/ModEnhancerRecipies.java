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
	public static RecipeEnhancer recipeEnhancedPick;
	public static RecipeEnhancer recipeEnhancedSword;
	public static RecipeEnhancer recipeEnhancedShovel;
	public static RecipeEnhancer recipeEnhancedAxe;
	public static RecipeEnhancer recipeEnhancedHoe;

	public static void init() {

		recipeEnhancedMagatama = registerEnhancerRecipe(new ItemStack(ItemRegistry.enhancedmagatama, 1), 30,
				new ItemStack(ItemRegistry.magatamabead, 1));
		recipeNullRod = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_rod, 1), 50,
				new ItemStack(ItemRegistry.channeling_rod, 1));

		recipeEnhancedPick = registerEnhancerRecipe(new ItemStack(ItemRegistry.blood_pickaxe, 1), 100,
				new ItemStack(Items.DIAMOND_PICKAXE, 1));
		recipeEnhancedSword = registerEnhancerRecipe(new ItemStack(ItemRegistry.blood_sword, 1), 100,
				new ItemStack(Items.DIAMOND_SWORD, 1));
		recipeEnhancedShovel = registerEnhancerRecipe(new ItemStack(ItemRegistry.blood_shovel, 1), 100,
				new ItemStack(Items.DIAMOND_SHOVEL, 1));
		recipeEnhancedAxe = registerEnhancerRecipe(new ItemStack(ItemRegistry.blood_axe, 1), 100,
				new ItemStack(Items.DIAMOND_AXE, 1));
		recipeEnhancedHoe = registerEnhancerRecipe(new ItemStack(ItemRegistry.blood_hoe, 1), 100,
				new ItemStack(Items.DIAMOND_HOE, 1));
	}

	public static RecipeEnhancer registerEnhancerRecipe(ItemStack output, float mana, Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 2);
		RecipeEnhancer recipe = new RecipeEnhancer(output, mana, inputs);
		enhancerRecipies.add(recipe);
		return recipe;
	}

}
