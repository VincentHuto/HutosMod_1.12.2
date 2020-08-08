package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModResonatorRecipies {
	public static final List<RecipeResonator> resonatorRecipies = new ArrayList<>();
	public static RecipeResonator recipeAntiTear, recipeResonantFuel, recipeEnhancedMagatama, recipeNullRod,
			recipeNullCrystal, recipeNullPowder, recipeEnhancedPick, recipeEnhancedSword, recipeEnhancedShovel,
			recipeEnhancedAxe, recipeEnhancedHoe, recipeEnhancedHelm, recipeEnhancedChestplate, recipeEnhancedPants,
			recipeEnhancedBoots, recipeManaGem, recipeChannelingRod, recipeManaDust, recipeEnchantedEarth,
			recipeEnchantedStone, recipeEnchantedMedia, recipeEnchantedSapling;
	// ReversionRecipies
	public static RecipeResonator recipeAntiTearREVERT, recipeResonantFuelREVERT;

	public static void init() {
		recipeAntiTear = registerEnhancerRecipe(new ItemStack(ItemRegistry.anti_tear, 1), 30, EnumEssecenceType.KARMIC,
				new ItemStack(ItemRegistry.essence_drop, 1));
		recipeResonantFuel = registerEnhancerRecipe(new ItemStack(ItemRegistry.resonant_fuel, 1), 30,
				EnumEssecenceType.MANA, new ItemStack(Items.COAL, 1));
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

		// Reversion Recipies
		recipeAntiTearREVERT = registerEnhancerRecipe(new ItemStack(ItemRegistry.essence_drop, 1), 60,
				EnumEssecenceType.REVERT, new ItemStack(ItemRegistry.anti_tear, 1));
		recipeResonantFuelREVERT = registerEnhancerRecipe(new ItemStack(Items.COAL, 1), 60, EnumEssecenceType.REVERT,
				new ItemStack(ItemRegistry.resonant_fuel, 1));

	}

	public static RecipeResonator registerEnhancerRecipe(ItemStack output, float mana, EnumEssecenceType type,
			Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 1);
		RecipeResonator recipe = new RecipeResonator(output, mana, type, inputs);
		resonatorRecipies.add(recipe);
		return recipe;
	}

}
