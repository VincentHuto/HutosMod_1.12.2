package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModWandRecipies {
	public static final List<RecipeWandMaker> wandMakerRecipies = new ArrayList<>();
	public static RecipeWandMaker recipeFireballWand;
	public static RecipeWandMaker recipeGreatFireballWand;
	public static RecipeWandMaker recipeNullCrystal;

	public static void init() {
		recipeFireballWand = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_fireball, 1),100,
				new ItemStack(ItemRegistry.channeling_ingot, 1), new ItemStack(ItemRegistry.channeling_rod, 1),
				new ItemStack(ItemRegistry.null_crystal, 1), new ItemStack(Items.FIRE_CHARGE, 1));
		
		recipeGreatFireballWand = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_greatfireball, 1),200,
				new ItemStack(ItemRegistry.channeling_ingot, 1),new ItemStack(ItemRegistry.wand_fireball, 1), new ItemStack(Items.FIRE_CHARGE, 1));

		recipeNullCrystal = registerRuneAltarRecipe(new ItemStack(ItemRegistry.null_crystal, 1),30,
				new ItemStack(Items.DIAMOND, 1),new ItemStack(Items.ENDER_EYE, 1), new ItemStack(Items.GUNPOWDER, 1));
	}

	/**
	 * Registers a Rune Altar Recipe.
	 * 
	 * @param output The ItemStack to craft.
	 * @param inputs The objects for crafting. Can be ItemStack,
	 *               MappableStackWrapper or String (case for Ore Dictionary). The
	 *               array can't be larger than 4.
	 * @return The recipe created.
	 */
	public static RecipeWandMaker registerRuneAltarRecipe(ItemStack output,float mana, Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 4);
		RecipeWandMaker recipe = new RecipeWandMaker(output,mana, inputs);
		wandMakerRecipies.add(recipe);
		return recipe;
	}

}
