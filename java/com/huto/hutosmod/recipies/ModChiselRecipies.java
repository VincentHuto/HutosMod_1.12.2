package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModChiselRecipies {
	public static final List<RecipeRuneChisel> runeRecipies = new ArrayList<>();

	public static RecipeRuneChisel recipeTestRune;

	public static void init() {
		recipeTestRune = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_beast_c, 1),new ItemStack(ItemRegistry.rune_blank, 1));
		
	}
	public static RecipeRuneChisel registerRuneAltarRecipe(ItemStack output, Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 2);
		RecipeRuneChisel recipe = new RecipeRuneChisel(output, inputs);
		runeRecipies.add(recipe);
		return recipe;
	}

}
