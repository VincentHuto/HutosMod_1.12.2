package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.item.ItemStack;

public class ModResonatorRecipies {
	public static final List<RecipeResonator> resonatorRecipies = new ArrayList<>();
	public static RecipeResonator recipeAntiTear;

	public static void init() {
		recipeAntiTear = registerEnhancerRecipe(new ItemStack(ItemRegistry.anti_tear, 1), 30, EnumEssecenceType.KARMIC,
				new ItemStack(ItemRegistry.essence_drop, 1));

	}

	public static RecipeResonator registerEnhancerRecipe(ItemStack output, float mana, EnumEssecenceType type,
			Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 1);
		RecipeResonator recipe = new RecipeResonator(output, mana, type, inputs);
		resonatorRecipies.add(recipe);
		return recipe;
	}

}
