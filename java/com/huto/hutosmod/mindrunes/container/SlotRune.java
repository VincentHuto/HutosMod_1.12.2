package com.huto.hutosmod.mindrunes.container;

import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.mindrunes.cap.RuneCapabilities;
import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class SlotRune extends SlotItemHandler
{
	int runeSlot;
	EntityPlayer player;

	public SlotRune(EntityPlayer player, IRunesItemHandler itemHandler, int slot, int par4, int par5)
	{
		super(itemHandler, slot, par4, par5);
		this.runeSlot = slot;
		this.player = player;
	}

	/**
	 * Check if the stack is a valid item for this slot.
	 */
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return ((IRunesItemHandler)getItemHandler()).isItemValidForSlot(runeSlot, stack, player);
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		ItemStack stack = getStack();
		if(stack.isEmpty())
			return false;

		IRune rune = stack.getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null);
		return rune.canUnequip(stack, player);
	}

	@Override
	public ItemStack onTake(EntityPlayer playerIn, ItemStack stack) {
		if (!getHasStack() && !((IRunesItemHandler)getItemHandler()).isEventBlocked() &&
				stack.hasCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null)) {
			stack.getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null).onUnequipped(stack, playerIn);
		}
		super.onTake(playerIn, stack);
		return stack;
	}

	@Override
	public void putStack(ItemStack stack) {
		if (getHasStack() && !ItemStack.areItemStacksEqual(stack,getStack()) &&
				!((IRunesItemHandler)getItemHandler()).isEventBlocked() &&
				getStack().hasCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null)) {
			getStack().getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null).onUnequipped(getStack(), player);
		}

		ItemStack oldstack = getStack().copy();
		super.putStack(stack);

		if (getHasStack() && !ItemStack.areItemStacksEqual(oldstack,getStack())
				&& !((IRunesItemHandler)getItemHandler()).isEventBlocked() &&
				getStack().hasCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null)) {
			getStack().getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null).onEquipped(getStack(), player);
		}
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
