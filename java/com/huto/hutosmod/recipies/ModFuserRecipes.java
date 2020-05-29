package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModFuserRecipes {
	public static final List<RecipeManaFuser> manaFuserRecipies = new ArrayList<>();
	public static RecipeManaFuser recipeGrandPurgingStone;


	public static void init() {
		recipeGrandPurgingStone = registerRuneAltarRecipe(new ItemStack(ItemRegistry.grand_purging_stone, 1),100,
				new ItemStack(ItemRegistry.purging_stone, 1), new ItemStack(ItemRegistry.karmic_drop, 1));
		
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
	public static RecipeManaFuser registerRuneAltarRecipe(ItemStack output,float mana, Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 4);
		RecipeManaFuser recipe = new RecipeManaFuser(output,mana, inputs);
		manaFuserRecipies.add(recipe);
		return recipe;
	}

}
