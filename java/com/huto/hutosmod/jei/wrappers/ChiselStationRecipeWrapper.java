
package com.huto.hutosmod.jei.wrappers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.huto.hutosmod.recipies.RecipeManaFuser;
import com.huto.hutosmod.recipies.RecipeRuneChisel;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ChiselStationRecipeWrapper implements IRecipeWrapper {

	private final List<List<ItemStack>> input;
	private final ItemStack output;
	private final ArrayList<Integer> runes;
	private RecipeRuneChisel currentRecipe;

	public ChiselStationRecipeWrapper(RecipeRuneChisel recipe) {
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
		runes = recipe.getActivatedRunes();
		currentRecipe= recipe;
	}

	@Override
	public void getIngredients(@Nonnull IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, input);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}
	
	
	public ArrayList<Integer> getRunes() {
		return runes;
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		GlStateManager.enableAlpha();
		GlStateManager.translate(35, 110, 0);
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		fontRenderer.drawSplitString("Runes to Activate", 0, (int) (fontRenderer.FONT_HEIGHT),90, 0);
		GlStateManager.translate(0, 10, 0);
		fontRenderer.drawSplitString(runes.toString(), 0, (int) (fontRenderer.FONT_HEIGHT),90, 0);
		
		GlStateManager.disableAlpha();
	}

}
