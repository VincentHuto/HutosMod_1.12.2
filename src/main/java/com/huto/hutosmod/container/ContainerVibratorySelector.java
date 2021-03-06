package com.huto.hutosmod.container;

import com.huto.hutosmod.tileentity.TileEntityRuneStation;
import com.huto.hutosmod.tileentity.TileEntityStorageDrum;
import com.huto.hutosmod.tileentity.TileEntityVibratorySelector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerVibratorySelector extends Container {
	private final int numRows;
	private final TileEntityVibratorySelector chestInventory;

	public ContainerVibratorySelector(InventoryPlayer playerInv, TileEntityVibratorySelector chestInventory,
			EntityPlayer player) {
		this.chestInventory = chestInventory;
		this.numRows = 4;
		chestInventory.openInventory(player);
		// SLOTS
		this.addSlotToContainer(new Slot(chestInventory, 0, 8, 54));
		//Output
		this.addSlotToContainer(new SlotSelectorOutput(chestInventory, 1, 145,  54));
		// INVENTORY
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 104 + y * 18));
			}
		}
		// HOTBAR
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 162));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.chestInventory.isUsableByPlayer(playerIn);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		chestInventory.closeInventory(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemStack = slot.getStack();
			stack = itemStack.copy();
			if (index < this.numRows * 9) {
				if (!this.mergeItemStack(itemStack, this.numRows * 9, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemStack, 0, this.numRows * 9, false)) {
				return ItemStack.EMPTY;
			}
			if (itemStack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return stack;
	}

	public TileEntityVibratorySelector getChestInventory() {
		return this.chestInventory;
	}
}
