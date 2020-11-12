
package com.huto.hutosmod.jei.catagories;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.core.util.SystemNanoClock;

import com.google.common.collect.Lists;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.gui.pages.GuiButtonTextured;
import com.huto.hutosmod.jei.wrappers.ChiselStationRecipeWrapper;
import com.huto.hutosmod.reference.Reference;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
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

	public GuiButtonTextured[][] runeButtonArray = new GuiButtonTextured[8][8];
	protected List<GuiButton> buttonList = Lists.<GuiButton>newArrayList();
	int left, top;
	int guiWidth = 176;
	int guiHeight = 186;
	private static final ResourceLocation GUI_Chisel = new ResourceLocation(
			Reference.MODID + ":textures/gui/chisel_station.png");

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		overlay.draw(minecraft);

		for (int i = 0; i < buttonList.size(); i++) {
			buttonList.get(i).drawButton(minecraft, 111, 111, 10);
		}

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
		GlStateManager.pushMatrix();
		buttonList.clear();
		int inc = 0;
		for (int i = 0; i < runeButtonArray.length; i++) {
			for (int j = 0; j < runeButtonArray.length; j++) {
				buttonList.add(runeButtonArray[i][j] = new GuiButtonTextured(GUI_Chisel, inc,
						left + guiWidth - (guiWidth - 38 - (i * 8)), top + guiHeight - (160 - (j * 8)), 8, 8, 176, 0,
						false));
				inc++;
			}
		}
		for (int l = 0; l < runeButtonArray.length; l++) {
			for (int m = 0; m < runeButtonArray.length; m++) {
				for (int k = 0; k < recipeWrapper.getRunes().size(); k++) {
					if (runeButtonArray[l][m].getId() == recipeWrapper.getRunes().get(k)) {
						runeButtonArray[l][m].setState(true);
					}
				}
			}
		}
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
