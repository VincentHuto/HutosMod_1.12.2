/**
 * This class was created by <williewillus>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p/>
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package com.huto.hutosmod.jei.wrappers;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.huto.hutosmod.recipies.RecipeResonator;
import com.huto.hutosmod.tileentity.TileEntityManaResonator;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ManaResonatorRecipeWrapper implements IRecipeWrapper {

	private final List<List<ItemStack>> input;
	private final ItemStack output;
	private final float manaUsage;
	private RecipeResonator currentRecipe;

	public ManaResonatorRecipeWrapper(RecipeResonator recipe) {
		ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
		for(Object o : recipe.getInputs()) {
			if(o instanceof ItemStack) {
				builder.add(ImmutableList.of((ItemStack) o));
			}
			if(o instanceof String) {
				builder.add(OreDictionary.getOres((String) o));
			}
		}
		input = builder.build();
		output = recipe.getOutput();
		manaUsage = recipe.getMana();
		currentRecipe= recipe;
	}

	@Override
	public void getIngredients(@Nonnull IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, input);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		GlStateManager.enableAlpha();
		GlStateManager.translate(65, 90, 0);
		TileEntityManaResonator te = new TileEntityManaResonator();
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		//FontRenderer akloRenderer = TextFormating.getAkloFont();
		fontRenderer.drawString(Float.toString(manaUsage), 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFFFFFF);
		GlStateManager.translate(-40, 0, 0);
		fontRenderer.drawString(currentRecipe.getRecipeType().toString(), 0, (int) (fontRenderer.FONT_HEIGHT), 0xFFFFFFFF);
		GlStateManager.disableAlpha();
	}

}
