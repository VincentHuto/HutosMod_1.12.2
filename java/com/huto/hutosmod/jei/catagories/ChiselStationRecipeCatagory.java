
package com.huto.hutosmod.jei.catagories;

import java.awt.Point;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.core.util.SystemNanoClock;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.jei.wrappers.ChiselStationRecipeWrapper;
import com.huto.hutosmod.reference.Reference;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ChiselStationRecipeCatagory implements IRecipeCategory<ChiselStationRecipeWrapper> {

	public static final String UID = "chisel_station";
	private final IDrawable background;
	private final String localizedName;
	private final IDrawable overlay;

	public ChiselStationRecipeCatagory(IGuiHelper guiHelper) {
		background = guiHelper.createBlankDrawable(150, 110);
		localizedName = I18n.format("hutosmod.jei.chisel_station");
		overlay = guiHelper.createDrawable(new ResourceLocation("hutosmod", "textures/gui/chiselOverlay.png"), 0, 0,
				150, 110);
	}

	@Nonnull
	@Override
	public String getUid() {
		return UID;
	}

	@Nonnull
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		overlay.draw(minecraft);
		GlStateManager.disableBlend();
		GlStateManager.disableAlpha();
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull ChiselStationRecipeWrapper recipeWrapper,
			@Nonnull IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 22, 0);
		GlStateManager.pushMatrix();
		recipeLayout.getItemStacks().set(0, new ItemStack(BlockRegistry.runic_chiselstation));
		GlStateManager.popMatrix();

		int runeIn = 1;
		int secondaryIn = 2;
		int outputRune = 3;
		if (ingredients.getInputs(VanillaTypes.ITEM).size() == 1) {
			recipeLayout.getItemStacks().init(runeIn, true, 3, 32);
			recipeLayout.getItemStacks().set(runeIn, ingredients.getInputs(VanillaTypes.ITEM).get(0));
			recipeLayout.getItemStacks().init(outputRune, false, 114, 40);
			recipeLayout.getItemStacks().set(outputRune, ingredients.getOutputs(VanillaTypes.ITEM).get(0));

		} else if (ingredients.getInputs(VanillaTypes.ITEM).size() > 1) {
			recipeLayout.getItemStacks().init(runeIn, true, 3, 32);
			recipeLayout.getItemStacks().set(runeIn, ingredients.getInputs(VanillaTypes.ITEM).get(0));
			recipeLayout.getItemStacks().init(secondaryIn, true, 3, 53);
			recipeLayout.getItemStacks().set(secondaryIn, ingredients.getInputs(VanillaTypes.ITEM).get(1));
			recipeLayout.getItemStacks().init(outputRune, false, 114, 40);
			recipeLayout.getItemStacks().set(outputRune, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		}
	}

	@Nonnull
	@Override
	public String getModName() {
		return Reference.MODID;
	}

}
