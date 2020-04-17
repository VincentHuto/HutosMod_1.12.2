package com.huto.hutosmod.mindrunes.container;

import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.mindrunes.cap.RuneCapabilities;
import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class RunesContainer extends ItemStackHandler implements IRunesItemHandler {

	private final static int RUNE_SLOTS = 4;
	private boolean[] changed = new boolean[RUNE_SLOTS];
	private boolean blockEvents=false;	
	private EntityLivingBase player;

	public RunesContainer()
	{
		super(RUNE_SLOTS);
	}

	@Override
	public void setSize(int size)
	{
		if (size<RUNE_SLOTS) size = RUNE_SLOTS;
		super.setSize(size);
		boolean[] old = changed;
		changed = new boolean[size];
		for(int i = 0;i<old.length && i<changed.length;i++)
		{
			changed[i] = old[i];
		}
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack, EntityLivingBase player) {
		if (stack==null || stack.isEmpty() || !stack.hasCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null))
			return false;
		IRune rune = stack.getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null);
		return rune.canEquip(stack, player) && rune.getRuneType(stack).hasSlot(slot);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if (stack==null || stack.isEmpty() || this.isItemValidForSlot(slot, stack, player)) {
			super.setStackInSlot(slot, stack);
		}
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (!this.isItemValidForSlot(slot, stack, player)) return stack;
		return super.insertItem(slot, stack, simulate);
	}

	@Override
	public boolean isEventBlocked() {
		return blockEvents;
	}

	@Override
	public void setEventBlock(boolean blockEvents) {
		this.blockEvents = blockEvents;
	}

	@Override
	protected void onContentsChanged(int slot)
	{
		setChanged(slot,true);
	}

	@Override
	public boolean isChanged(int slot) {
		if (changed==null) {
			changed = new boolean[this.getSlots()];
		}
		return changed[slot];
	}

	@Override
	public void setChanged(int slot, boolean change) {
		if (changed==null) {
			changed = new boolean[this.getSlots()];
		}
		this.changed[slot] = change;
	}

	@Override
	public void setPlayer(EntityLivingBase player) {
		this.player=player;
	}
}
