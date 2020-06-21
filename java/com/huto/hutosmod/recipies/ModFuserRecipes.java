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
	public static RecipeManaFuser recipeGreyBar;
	public static RecipeManaFuser recipeKarmicBar;
	public static RecipeManaFuser recipePhantasmalPane;

	public static RecipeManaFuser recipeUpgradeBlock;
	public static RecipeManaFuser recipeUpgradePeople;
	public static RecipeManaFuser recipeUpgradeMob;
	public static RecipeManaFuser recipeUpgradeAnimal;
	public static RecipeManaFuser recipeElderTome;

	public static void init() {
		recipeGrandPurgingStone = registerRuneAltarRecipe(new ItemStack(ItemRegistry.grand_purging_stone, 1), 100,
				new ItemStack(ItemRegistry.purging_stone, 1), new ItemStack(ItemRegistry.karmic_drop, 1));
		recipeGreyBar = registerRuneAltarRecipe(new ItemStack(ItemRegistry.grey_ingot, 1), 10,
				new ItemStack(ItemRegistry.channeling_ingot, 1), new ItemStack(ItemRegistry.null_ingot, 1));
		recipeKarmicBar = registerRuneAltarRecipe(new ItemStack(ItemRegistry.karmic_bar, 1), 10,
				new ItemStack(ItemRegistry.karmic_drop, 1), new ItemStack(ItemRegistry.grey_ingot, 1));
		recipePhantasmalPane = registerRuneAltarRecipe(new ItemStack(ItemRegistry.phantasmal_pane, 1), 10,
				new ItemStack(ItemRegistry.karmic_drop, 1), new ItemStack(ItemRegistry.readied_pane, 1));

		recipeUpgradeBlock = registerRuneAltarRecipe(new ItemStack(ItemRegistry.upgrade_block, 1), 50,
				new ItemStack(ItemRegistry.upgrade_blank, 1), new ItemStack(ItemRegistry.mana_crystal, 1));
		recipeUpgradePeople = registerRuneAltarRecipe(new ItemStack(ItemRegistry.upgrade_player, 1), 50,
				new ItemStack(ItemRegistry.upgrade_blank, 1), new ItemStack(Items.NETHER_STAR, 1));
		recipeUpgradeMob = registerRuneAltarRecipe(new ItemStack(ItemRegistry.upgrade_mob, 1), 50,
				new ItemStack(ItemRegistry.upgrade_blank, 1), new ItemStack(Items.EMERALD, 1));
		recipeElderTome = registerRuneAltarRecipe(new ItemStack(ItemRegistry.elder_tome, 1), 50,
				new ItemStack(ItemRegistry.mystic_tome, 1), new ItemStack(ItemRegistry.karmic_bar, 1));

	}

	public static RecipeManaFuser registerRuneAltarRecipe(ItemStack output, float mana, Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 4);
		RecipeManaFuser recipe = new RecipeManaFuser(output, mana, inputs);
		manaFuserRecipies.add(recipe);
		return recipe;
	}

}
