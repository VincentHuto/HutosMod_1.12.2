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
	public static RecipeEnhancer recipeNullCrystal;
	public static RecipeEnhancer recipeNullPowder;
	public static RecipeEnhancer recipeEnhancedPick;
	public static RecipeEnhancer recipeEnhancedSword;
	public static RecipeEnhancer recipeEnhancedShovel;
	public static RecipeEnhancer recipeEnhancedAxe;
	public static RecipeEnhancer recipeEnhancedHoe;
	public static RecipeEnhancer recipeManaGem;
	public static RecipeEnhancer recipeChannelingRod;
	public static RecipeEnhancer recipeManaDust;

	public static void init() {
		recipeEnhancedMagatama = registerEnhancerRecipe(new ItemStack(ItemRegistry.enhancedmagatama, 1), 30,
				EnumEssecenceType.KARMIC, new ItemStack(ItemRegistry.magatamabead, 1));
		recipeNullRod = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_rod, 1), 50, EnumEssecenceType.KARMIC,
				new ItemStack(ItemRegistry.channeling_rod, 1));
		recipeNullCrystal = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_crystal, 1), 50,
				EnumEssecenceType.KARMIC, new ItemStack(ItemRegistry.mana_crystal, 1));
		recipeNullPowder = registerEnhancerRecipe(new ItemStack(ItemRegistry.nullifying_powder, 1), 50,
				EnumEssecenceType.KARMIC, new ItemStack(ItemRegistry.mana_powder, 1));
		recipeEnhancedPick = registerEnhancerRecipe(new ItemStack(ItemRegistry.blood_pickaxe, 1), 100,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_PICKAXE, 1));
		recipeEnhancedSword = registerEnhancerRecipe(new ItemStack(ItemRegistry.blood_sword, 1), 100,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_SWORD, 1));
		recipeEnhancedShovel = registerEnhancerRecipe(new ItemStack(ItemRegistry.blood_shovel, 1), 100,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_SHOVEL, 1));
		recipeEnhancedAxe = registerEnhancerRecipe(new ItemStack(ItemRegistry.blood_axe, 1), 100,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_AXE, 1));
		recipeManaGem = registerEnhancerRecipe(new ItemStack(ItemRegistry.mana_crystal, 1), 15, EnumEssecenceType.MANA,
				new ItemStack(ItemRegistry.grey_crystal, 1));
		recipeManaDust = registerEnhancerRecipe(new ItemStack(ItemRegistry.mana_powder, 1), 10, EnumEssecenceType.MANA,
				new ItemStack(ItemRegistry.grey_powder, 1));
	}

	public static RecipeEnhancer registerEnhancerRecipe(ItemStack output, float mana, EnumEssecenceType type,
			Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 2);
		RecipeEnhancer recipe = new RecipeEnhancer(output, mana, type, inputs);
		enhancerRecipies.add(recipe);
		return recipe;
	}

}
