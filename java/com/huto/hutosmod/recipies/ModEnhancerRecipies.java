package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Blocks;
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
	public static RecipeEnhancer recipeEnhancedHelm;
	public static RecipeEnhancer recipeEnhancedChestplate;
	public static RecipeEnhancer recipeEnhancedPants;
	public static RecipeEnhancer recipeEnhancedBoots;
	public static RecipeEnhancer recipeManaGem;
	public static RecipeEnhancer recipeChannelingRod;
	public static RecipeEnhancer recipeManaDust;
	public static RecipeEnhancer recipeEnchantedEarth;
	public static RecipeEnhancer recipeEnchantedStone;
	public static RecipeEnhancer recipeEnchantedMedia;
	public static RecipeEnhancer recipeEnchantedSapling;

	public static void init() {
		recipeEnhancedMagatama = registerEnhancerRecipe(new ItemStack(ItemRegistry.enhancedmagatama, 1), 30,
				EnumEssecenceType.KARMIC, new ItemStack(ItemRegistry.magatamabead, 1));
		recipeNullRod = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_rod, 1), 50, EnumEssecenceType.KARMIC,
				new ItemStack(ItemRegistry.channeling_rod, 1));
		recipeNullCrystal = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_crystal, 1), 50,
				EnumEssecenceType.KARMIC, new ItemStack(ItemRegistry.mana_crystal, 1));
		recipeNullPowder = registerEnhancerRecipe(new ItemStack(ItemRegistry.nullifying_powder, 1), 50,
				EnumEssecenceType.KARMIC, new ItemStack(ItemRegistry.mana_powder, 1));
		recipeEnhancedPick = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_pickaxe, 1), 100,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_PICKAXE, 1));
		recipeEnhancedSword = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_sword, 1), 100,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_SWORD, 1));
		recipeEnhancedShovel = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_shovel, 1), 100,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_SHOVEL, 1));
		recipeEnhancedAxe = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_axe, 1), 100,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_AXE, 1));
		recipeManaGem = registerEnhancerRecipe(new ItemStack(ItemRegistry.mana_crystal, 1), 15, EnumEssecenceType.MANA,
				new ItemStack(ItemRegistry.grey_crystal, 1));
		recipeManaDust = registerEnhancerRecipe(new ItemStack(ItemRegistry.mana_powder, 1), 10, EnumEssecenceType.MANA,
				new ItemStack(ItemRegistry.grey_powder, 1));
		recipeEnchantedStone = registerEnhancerRecipe(new ItemStack(BlockRegistry.enchanted_stone, 1), 10,
				EnumEssecenceType.MANA, new ItemStack(Blocks.STONE, 1));
		recipeEnchantedEarth = registerEnhancerRecipe(new ItemStack(BlockRegistry.Mystic_Earth, 1), 10,
				EnumEssecenceType.MANA, new ItemStack(Blocks.GRASS, 1));
		recipeEnchantedMedia = registerEnhancerRecipe(new ItemStack(BlockRegistry.Mystic_Media, 1), 10,
				EnumEssecenceType.MANA, new ItemStack(Blocks.COBBLESTONE, 1));
		recipeEnchantedSapling = registerEnhancerRecipe(new ItemStack(BlockRegistry.Mystic_Sapling, 1), 10,
				EnumEssecenceType.MANA, new ItemStack(Blocks.SAPLING, 1));
		recipeEnhancedHelm = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_helmet, 1), 200,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_HELMET, 1));
		recipeEnhancedChestplate = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_chestplate, 1), 200,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_CHESTPLATE, 1));
		recipeEnhancedPants = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_leggings, 1), 200,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_LEGGINGS, 1));
		recipeEnhancedBoots = registerEnhancerRecipe(new ItemStack(ItemRegistry.null_boots, 1), 200,
				EnumEssecenceType.KARMIC, new ItemStack(Items.DIAMOND_BOOTS, 1));

	}

	public static RecipeEnhancer registerEnhancerRecipe(ItemStack output, float mana, EnumEssecenceType type,
			Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 1);
		RecipeEnhancer recipe = new RecipeEnhancer(output, mana, type, inputs);
		enhancerRecipies.add(recipe);
		return recipe;
	}

}
