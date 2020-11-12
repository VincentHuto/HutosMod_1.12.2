package com.huto.hutosmod.container;

import javax.annotation.Nonnull;

import com.huto.hutosmod.items.runes.ItemContractRuneBeast;
import com.huto.hutosmod.items.runes.ItemContractRuneCorrupt;
import com.huto.hutosmod.items.runes.ItemContractRuneImpure;
import com.huto.hutosmod.items.runes.ItemContractRuneMilkweed;
import com.huto.hutosmod.items.runes.ItemContractRuneRadiance;
import com.huto.hutosmod.items.runes.ItemRune;
import com.huto.hutosmod.mindrunes.cap.IRune;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRuneOutput extends Slot {

	public SlotRuneOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack) {

		return false;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}