package com.huto.hutosmod.container;

import javax.annotation.Nonnull;

import com.huto.hutosmod.items.runes.ItemRune;
import com.huto.hutosmod.mindrunes.cap.IRune;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRuneInput extends Slot {

	public SlotRuneInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack) {
		if (stack.getItem() instanceof ItemRune ||stack.getItem() instanceof IRune) {
			return true;
		} else {
			
			return false;
		}
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}