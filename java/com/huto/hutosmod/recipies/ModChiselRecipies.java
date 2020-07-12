package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ModChiselRecipies {
	public static final List<RecipeRuneChisel> runeRecipies = new ArrayList<>();

	public static RecipeRuneChisel recipeClawMark;
	public static RecipeRuneChisel recipeBeastContract;

	public static void init() {
		recipeClawMark = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_clawmark, 1),
				new ArrayList<Integer>() {
					{
						add(1);
						add(2);
						add(3);
					}
				}, new ItemStack(ItemRegistry.rune_blank, 1));
		
		recipeBeastContract = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_beast_c, 1),
				new ArrayList<Integer>() {
					{
						add(4);
						add(5);
						add(6);
					}
				}, new ItemStack(ItemRegistry.rune_clawmark, 1), new ItemStack(Items.DIAMOND, 1));

	}

	public static RecipeRuneChisel registerRuneAltarRecipe(ItemStack output, ArrayList<Integer> runesIn,
			Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 2);
		RecipeRuneChisel recipe = new RecipeRuneChisel(output, runesIn, inputs);
		runeRecipies.add(recipe);
		return recipe;
	}

}
