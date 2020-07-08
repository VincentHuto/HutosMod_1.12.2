package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ModChiselRecipies {
	public static final List<RecipeRuneChisel> runeRecipies = new ArrayList<>();

	public static RecipeRuneChisel recipeClawMark;
	public static RecipeRuneChisel recipeBeastContract;

	public static void init() {
		recipeClawMark = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_clawmark, 1),new int[]{1,2,3},new ItemStack(ItemRegistry.rune_blank, 1));
		recipeBeastContract = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_beast_c, 1),new int[]{4,5,6},new ItemStack(ItemRegistry.rune_clawmark, 1));

	}
	public static RecipeRuneChisel registerRuneAltarRecipe(ItemStack output,int[] runesIn, Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 2);
		RecipeRuneChisel recipe = new RecipeRuneChisel(output, runesIn, inputs);
		runeRecipies.add(recipe);
		return recipe;
	}

}
