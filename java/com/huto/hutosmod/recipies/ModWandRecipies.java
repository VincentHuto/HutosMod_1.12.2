package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModWandRecipies {
	public static final List<RecipeWandMaker> wandMakerRecipies = new ArrayList<>();
	
	public static RecipeWandMaker recipeManaConsumption;
	public static RecipeWandMaker recipeManaAbsorbtion;
	public static RecipeWandMaker recipeSacrifice;
	public static RecipeWandMaker recipeRending;
	public static RecipeWandMaker recipeHealSelf;
	public static RecipeWandMaker recipeHealOthers;
	public static RecipeWandMaker recipeFireballWand;
	public static RecipeWandMaker recipeGreatFireballWand;
	public static RecipeWandMaker recipeLightningWand;

	public static void init() {
		recipeFireballWand = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_fireball, 1),100,
				new ItemStack(ItemRegistry.channeling_ingot, 1), new ItemStack(ItemRegistry.channeling_rod, 1),
				new ItemStack(ItemRegistry.null_crystal, 1), new ItemStack(Items.FIRE_CHARGE, 1));
		
		recipeGreatFireballWand = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_greatfireball, 1),200,
				new ItemStack(ItemRegistry.channeling_ingot, 1),new ItemStack(ItemRegistry.wand_fireball, 1), new ItemStack(Items.FIRE_CHARGE, 1));

		recipeManaConsumption = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_consume_mana, 1),50,
				new ItemStack(ItemRegistry.channeling_ingot, 1),new ItemStack(ItemRegistry.channeling_rod, 1), new ItemStack(ItemRegistry.null_crystal, 1));
		recipeManaAbsorbtion = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_gain_mana, 1),50,
				new ItemStack(ItemRegistry.channeling_ingot, 1),new ItemStack(ItemRegistry.channeling_rod, 1), new ItemStack(ItemRegistry.mana_crystal, 1));
		
		recipeSacrifice = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_sacrifice, 1),220,
				new ItemStack(ItemRegistry.channeling_ingot, 1),new ItemStack(ItemRegistry.channeling_ingot, 1), new ItemStack(ItemRegistry.null_crystal, 1), new ItemStack(ItemRegistry.mind_spike, 1));
		recipeRending = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_rending, 1),220,
				new ItemStack(ItemRegistry.channeling_ingot, 1),new ItemStack(ItemRegistry.channeling_rod, 1), new ItemStack(ItemRegistry.null_crystal, 1), new ItemStack(ItemRegistry.null_sword, 1));
		
		recipeHealSelf = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_healself, 1),250,
				new ItemStack(ItemRegistry.channeling_ingot, 1),new ItemStack(ItemRegistry.channeling_rod, 1), new ItemStack(ItemRegistry.mana_crystal, 1), new ItemStack(Items.GOLDEN_APPLE, 1));
		recipeHealOthers = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_healother, 1),250,
				new ItemStack(ItemRegistry.channeling_ingot, 1),new ItemStack(ItemRegistry.channeling_rod, 1), new ItemStack(ItemRegistry.mana_crystal, 1), new ItemStack(Items.GOLDEN_CARROT, 1));
	
		recipeLightningWand = registerRuneAltarRecipe(new ItemStack(ItemRegistry.wand_lightning, 1),300,
				new ItemStack(ItemRegistry.channeling_ingot, 1),new ItemStack(ItemRegistry.channeling_rod, 1), new ItemStack(ItemRegistry.energy_focus, 1), new ItemStack(Items.END_CRYSTAL, 1));
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
