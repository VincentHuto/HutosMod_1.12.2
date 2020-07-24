
package com.huto.hutosmod.recipies;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeEnhancer{

	private final ItemStack output;
	private final ImmutableList<Object> inputs;
	private final float mana;
	private EnumEssecenceType type;
	public RecipeEnhancer(ItemStack output,float mana,EnumEssecenceType type, Object... inputs) {
		this.output = output;
		this.mana = mana;
		this.type = type;
		ImmutableList.Builder<Object> inputsToSet = ImmutableList.builder();
		for(Object obj : inputs) {
			if(obj instanceof String || obj instanceof ItemStack)
				inputsToSet.add(obj);
			else throw new IllegalArgumentException("Invalid input");
		}

		this.inputs = inputsToSet.build();
	}

	public boolean matches(IItemHandler inv) {
		List<Object> inputsMissing = new ArrayList<>(inputs);

		for(int i = 0; i < inv.getSlots(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty())
				break;

			int stackIndex = -1, oredictIndex = -1;

			for(int j = 0; j < inputsMissing.size(); j++) {
				Object input = inputsMissing.get(j);
				if(input instanceof String) {
					boolean found = false;
					for(ItemStack ostack : OreDictionary.getOres((String) input, false)) {
						if(OreDictionary.itemMatches(ostack, stack, false)) {
							oredictIndex = j;
							found = true;
							break;
						}
					}


					if(found)
						break;
				} else if(input instanceof ItemStack && compareStacks((ItemStack) input, stack)) {
					stackIndex = j;
					break;
				}
			}

			if(stackIndex != -1)
				inputsMissing.remove(stackIndex);
			else if(oredictIndex != -1)
				inputsMissing.remove(oredictIndex);
			else return false;
		}

		return inputsMissing.isEmpty();
	}

	private boolean compareStacks(ItemStack recipe, ItemStack supplied) {
		return recipe.getItem() == supplied.getItem() && recipe.getItemDamage() == supplied.getItemDamage() && ItemNBTHelper.matchTag(recipe.getTagCompound(), supplied.getTagCompound());
	}

	public List<Object> getInputs() {
		return inputs;
	}

	public ItemStack getOutput() {
		return output;
	}
	
	public float getMana() {
		return mana;
	}
	public EnumEssecenceType getRecipeType() {
		return type;
	}
}
