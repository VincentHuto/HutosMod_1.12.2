package com.huto.hutosmod.container;

import com.huto.hutosmod.tileentity.TileEntityFusionFurnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFusionFurnaceFuel extends Slot {

	public SlotFusionFurnaceFuel(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {

		return TileEntityFusionFurnace.isItemFuel(stack);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {

		return super.getItemStackLimit(stack);
	}

}
