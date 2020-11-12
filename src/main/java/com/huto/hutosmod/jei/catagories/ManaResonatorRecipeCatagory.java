
package com.huto.hutosmod.jei.catagories;

import java.awt.Point;
import java.util.List;

import javax.annotation.Nonnull;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.jei.wrappers.ManaResonatorRecipeWrapper;
import com.huto.hutosmod.recipies.EnumEssecenceType;
import com.huto.hutosmod.reference.Reference;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ManaResonatorRecipeCatagory implements IRecipeCategory<ManaResonatorRecipeWrapper> {

	public static final String UID = "resonator";
	private final IDrawable background;
	private final String localizedName;
	private final IDrawable overlay;

	public ManaResonatorRecipeCatagory(IGuiHelper guiHelper) {
		background = guiHelper.createBlankDrawable(150, 110);
		localizedName = I18n.format("hutosmod.jei.mana_resonator");
		overlay = guiHelper.createDrawable(new ResourceLocation("hutosmod", "textures/gui/resonatoroverlay.png"), 0, 0,
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
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull ManaResonatorRecipeWrapper recipeWrapper,
			@Nonnull IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 64, 52);
		recipeLayout.getItemStacks().init(3, false, 64, 70);

		GlStateManager.pushMatrix();
		GlStateManager.scale(1.2, 4.2, 1.2);

		Block catalyst;
		if (recipeWrapper.getCurrentRecipe().getRecipeType() == EnumEssecenceType.MANA) {
			catalyst = BlockRegistry.enchanted_stone_smooth;
		} else if (recipeWrapper.getCurrentRecipe().getRecipeType() == EnumEssecenceType.KARMIC) {
			catalyst = BlockRegistry.activated_obsidian;

		} else if (recipeWrapper.getCurrentRecipe().getRecipeType() == EnumEssecenceType.REVERT) {
			catalyst = BlockRegistry.reversion_catalyst;

		} else if (recipeWrapper.getCurrentRecipe().getRecipeType() == EnumEssecenceType.GREY) {
			catalyst = BlockRegistry.mindfog;

		} else if (recipeWrapper.getCurrentRecipe().getRecipeType() == EnumEssecenceType.BOTH) {
			catalyst = BlockRegistry.primal_ooze_fluid;

		} else {
			catalyst = Blocks.AIR;

		}
		ItemStack catalystStack = new ItemStack(catalyst);

		recipeLayout.getItemStacks().set(0, new ItemStack(BlockRegistry.mana_resonator));
		recipeLayout.getItemStacks().set(3, catalystStack);
		GlStateManager.popMatrix();

		int index = 1;
		double angleBetweenEach = 360.0 / ingredients.getInputs(VanillaTypes.ITEM).size();
		Point point = new Point(64, 20), center = new Point(64, 52);

		for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
			recipeLayout.getItemStacks().init(index, true, point.x, point.y);
			recipeLayout.getItemStacks().set(index, o);
			index += 1;
			point = rotatePointAbout(point, center, angleBetweenEach);
		}

		recipeLayout.getItemStacks().init(index, false, 103, 17);
		recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));

	}

	private Point rotatePointAbout(Point in, Point about, double degrees) {
		double rad = degrees * Math.PI / 180.0;
		double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
		double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
		return new Point((int) newX, (int) newY);
	}

	@Nonnull
	@Override
	public String getModName() {
		return Reference.MODID;
	}

}
