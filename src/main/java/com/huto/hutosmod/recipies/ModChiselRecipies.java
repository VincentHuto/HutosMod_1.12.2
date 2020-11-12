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
	public static RecipeRuneChisel recipeMetamorphosis;
	public static RecipeRuneChisel recipeOedon;
	public static RecipeRuneChisel recipeLake;
	public static RecipeRuneChisel recipeRapture;
	public static RecipeRuneChisel recipeBeast;
	public static RecipeRuneChisel recipeCommunion;
	public static RecipeRuneChisel recipeEye;
	public static RecipeRuneChisel recipeGuidance;
	public static RecipeRuneChisel recipeHeir;
	public static RecipeRuneChisel recipeMetamorphosisCW;
	public static RecipeRuneChisel recipeMoon;

	public static RecipeRuneChisel recipeBeastContract;
	public static RecipeRuneChisel recipeImpurityContract;
	public static RecipeRuneChisel recipeCorruptionContract;
	public static RecipeRuneChisel recipeMilkweedContract;
	public static RecipeRuneChisel recipeRadianceContract;
	public static RecipeRuneChisel recipeHunterContract;

	public static void init() {

		recipeMoon = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_moon, 1), new ArrayList<Integer>() {
			{
				add(3);
				add(10);
				add(11);
				add(12);
				add(13);
				add(17);
				add(19);
				add(22);
				add(24);
				add(25);
				add(26);
				add(27);
				add(28);
				add(29);
				add(30);
				add(31);
				add(32);
				add(33);
				add(34);
				add(35);
				add(36);
				add(37);
				add(38);
				add(39);
				add(41);
				add(43);
				add(46);
				add(51);
				add(53);
			}
		}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.REDSTONE, 1));
		recipeMetamorphosisCW = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_metamorphosis_cw, 1),
				new ArrayList<Integer>() {
					{
						add(3);
						add(10);
						add(19);
						add(21);
						add(22);
						add(24);
						add(28);
						add(31);
						add(32);
						add(35);
						add(39);
						add(41);
						add(42);
						add(44);
						add(53);
						add(60);
					}
				}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.GUNPOWDER, 1));

		recipeHeir = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_heir, 1), new ArrayList<Integer>() {
			{
				add(0);
				add(1);
				add(8);
				add(10);
				add(14);
				add(16);
				add(19);
				add(21);
				add(23);
				add(24);
				add(25);
				add(26);
				add(27);
				add(28);
				add(29);
				add(30);
				add(31);
				add(32);
				add(33);
				add(34);
				add(35);
				add(36);
				add(37);
				add(38);
				add(39);
				add(40);
				add(43);
				add(45);
				add(47);
				add(48);
				add(50);
				add(54);
				add(56);
				add(57);
			}
		}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.STRING, 1));
		recipeGuidance = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_guidance, 1),
				new ArrayList<Integer>() {
					{
						add(2);
						add(11);
						add(16);
						add(19);
						add(21);
						add(26);
						add(27);
						add(28);
						add(30);
						add(34);
						add(35);
						add(39);
						add(42);
						add(45);
						add(47);
						add(48);
						add(49);
						add(54);
					}
				}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.ENDER_EYE, 1));

		recipeEye = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_eye, 1), new ArrayList<Integer>() {
			{
				add(2);
				add(7);
				add(10);
				add(11);
				add(13);
				add(14);
				add(18);
				add(19);
				add(20);
				add(21);
				add(24);
				add(25);
				add(26);
				add(27);
				add(28);
				add(32);
				add(33);
				add(34);
				add(35);
				add(36);
				add(42);
				add(43);
				add(44);
				add(45);
				add(50);
				add(51);
				add(53);
				add(54);
				add(58);
				add(63);
				
			}
		}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(ItemRegistry.mana_powder, 1));
		recipeCommunion = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_communion, 1),
				new ArrayList<Integer>() {
					{
						add(5);
						add(10);
						add(11);
						add(17);
						add(20);
						add(24);
						add(29);
						add(30);
						add(31);
						add(32);
						add(37);
						add(38);
						add(39);
						add(41);
						add(44);
						add(50);
						add(51);
						add(61);
					}
				}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(ItemRegistry.grey_powder, 1));

		recipeBeast = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_beast, 1), new ArrayList<Integer>() {
			{
				add(9);
				add(18);
				add(24);
				add(26);
				add(27);
				add(28);
				add(30);
				add(33);
				add(34);
				add(35);
				add(37);
				add(39);
				add(43);
				add(49);
				add(50);
			}
		}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.BEEF, 1));

		recipeClawMark = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_clawmark, 1),
				new ArrayList<Integer>() {
					{
						add(7);
						add(11);
						add(12);
						add(14);
						add(21);
						add(28);
						add(30);
						add(31);
						add(32);
						add(33);
						add(35);
						add(42);
						add(49);
						add(51);
						add(52);
						add(56);
					}
				}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.FLINT, 1));

		recipeMetamorphosis = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_metamorphosis, 1),
				new ArrayList<Integer>() {
					{
						add(4);
						add(13);
						add(17);
						add(18);
						add(20);
						add(24);
						add(27);
						add(31);
						add(32);
						add(36);
						add(39);
						add(43);
						add(45);
						add(46);
						add(50);
						add(59);
					}
				}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.SUGAR, 1));
		recipeOedon = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_oedon, 1), new ArrayList<Integer>() {
			{
				add(1);
				add(10);
				add(16);
				add(18);
				add(19);
				add(20);
				add(21);
				add(22);
				add(23);
				add(27);
				add(35);
				add(40);
				add(42);
				add(43);
				add(44);
				add(45);
				add(46);
				add(47);
				add(50);
				add(57);
			}
		}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.REDSTONE, 1));

		recipeLake = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_lake, 1), new ArrayList<Integer>() {

			{
				add(4);
				add(12);
				add(13);
				add(14);
				add(20);
				add(28);
				add(29);
				add(30);
				add(34);
				add(35);
				add(36);
				add(44);
				add(51);
				add(52);
				add(53);
				add(60);
			}
		}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.WATER_BUCKET, 1));

		recipeRapture = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_rapture, 1), new ArrayList<Integer>() {
			{
				add(0);
				add(3);
				add(4);
				add(7);
				add(9);
				add(11);
				add(12);
				add(14);
				add(18);
				add(21);
				add(24);
				add(25);
				add(26);
				add(29);
				add(30);
				add(31);
				add(32);
				add(33);
				add(34);
				add(37);
				add(38);
				add(39);
				add(42);
				add(45);
				add(49);
				add(51);
				add(52);
				add(54);
				add(56);
				add(59);
				add(60);
				add(63);

			}
		}, new ItemStack(ItemRegistry.rune_blank, 1), new ItemStack(Items.GLOWSTONE_DUST, 1));

		// Contract runes

		recipeBeastContract = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_beast_c, 1),
				new ArrayList<Integer>() {
					{
						add(12);
						add(14);
						add(21);
						add(28);
						add(30);
						add(33);
						add(34);
						add(39);
						add(40);
						add(43);
						add(47);
						add(49);
						add(54);
						add(58);
						add(59);
						add(60);
						add(61);
					}
				}, new ItemStack(ItemRegistry.rune_beast, 1), new ItemStack(Items.DIAMOND, 1));

		recipeImpurityContract = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_impurity_c, 1),
				new ArrayList<Integer>() {
					{
						add(1);
						add(10);
						add(12);
						add(14);
						add(19);
						add(24);
						add(25);
						add(27);
						add(28);
						add(29);
						add(30);
						add(31);
						add(32);
						add(33);
						add(35);
						add(36);
						add(37);
						add(38);
						add(39);
						add(43);
						add(50);
						add(52);
						add(54);
						add(57);
					}
				}, new ItemStack(ItemRegistry.rune_oedon, 1), new ItemStack(Items.DIAMOND, 1));
		recipeCorruptionContract = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_corruption_c, 1),
				new ArrayList<Integer>() {
					{
						add(1);
						add(10);
						add(12);
						add(14);
						add(19);
						add(24);
						add(25);
						add(27);
						add(28);
						add(29);
						add(30);
						add(31);
						add(32);
						add(33);
						add(35);
						add(36);
						add(37);
						add(38);
						add(39);
						add(43);
						add(50);
						add(52);
						add(54);
						add(57);
					}
				}, new ItemStack(ItemRegistry.rune_clawmark, 1), new ItemStack(Items.DIAMOND, 1));

		recipeMilkweedContract = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_milkweed_c, 1),
				new ArrayList<Integer>() {
					{
						add(1);
						add(2);
						add(3);
						add(4);
						add(5);
						add(6);
						add(11);
						add(16);
						add(17);
						add(18);
						add(19);
						add(20);
						add(21);
						add(22);
						add(27);
						add(33);
						add(34);
						add(35);
						add(36);
						add(37);
						add(38);
						add(43);
						add(49);
						add(50);
						add(51);
						add(52);
						add(53);
						add(54);
						add(55);
						add(57);
						add(58);
						add(59);
					}
				}, new ItemStack(ItemRegistry.rune_lake, 1), new ItemStack(Items.DIAMOND, 1));

		recipeHunterContract = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_hunter_c, 1),
				new ArrayList<Integer>() {
					{
						add(6);
						add(13);
						add(15);
						add(20);
						add(24);
						add(25);
						add(26);
						add(27);
						add(28);
						add(29);
						add(30);
						add(31);
						add(32);
						add(33);
						add(34);
						add(35);
						add(36);
						add(37);
						add(38);
						add(39);
						add(44);
						add(53);
						add(55);
						add(62);
					}
				}, new ItemStack(ItemRegistry.rune_guidance, 1), new ItemStack(Items.DIAMOND, 1));

		recipeRadianceContract = registerRuneAltarRecipe(new ItemStack(ItemRegistry.rune_radiance_c, 1),
				new ArrayList<Integer>() {
					{
						add(4);
						add(5);
						add(6);
						add(7);
						add(10);
						add(11);
						add(15);
						add(17);
						add(19);
						add(20);
						add(23);
						add(24);
						add(27);
						add(29);
						add(31);
						add(32);
						add(35);
						add(37);
						add(39);
						add(41);
						add(43);
						add(44);
						add(47);
						add(50);
						add(51);
						add(55);
						add(60);
						add(61);
						add(62);
						add(63);
					}
				}, new ItemStack(ItemRegistry.rune_rapture, 1), new ItemStack(Items.NETHER_STAR, 1));
	}

	public static RecipeRuneChisel registerRuneAltarRecipe(ItemStack output, ArrayList<Integer> runesIn,
			Object... inputs) {
		Preconditions.checkArgument(inputs.length <= 2);
		RecipeRuneChisel recipe = new RecipeRuneChisel(output, runesIn, inputs);
		runeRecipies.add(recipe);
		return recipe;
	}

}
