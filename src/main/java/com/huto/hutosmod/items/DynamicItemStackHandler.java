package com.huto.hutosmod.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;



/*


		CURRENT ISSUES:
			1. creating the nbt tag doesnt update the size or items
			2. after its updated make sure it sinks with client and server
			3. Something with the addSize function because it is only intializing it not adding to it
			4. FOR RIGHT NOW, Using base one with size of 4




						*/

public class DynamicItemStackHandler implements IItemHandler, IItemHandlerModifiable, INBTSerializable<NBTTagCompound> {
	protected List<ItemStack> stacks;

	public DynamicItemStackHandler() {
		this(1);
		stacks = new ArrayList<ItemStack>();
		stacks.add(0, ItemStack.EMPTY);
	}

	public DynamicItemStackHandler(int size) {
		stacks = new ArrayList<ItemStack>();
		stacks.add(0, ItemStack.EMPTY);
		for (int i = 0; i <= size; i++) {
			stacks.add(i, ItemStack.EMPTY);
		}
	}

	public DynamicItemStackHandler(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	public void addSize(int size) {
		for (int i = 0; i <= size; i++) {
			 stacks.add(i, ItemStack.EMPTY);
		}
	}

	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
		validateSlotIndex(slot);
		this.stacks.set(slot, stack);
		onContentsChanged(slot);
	}

	@Override
	public int getSlots() {
		return stacks.size();
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(int slot) {
		validateSlotIndex(slot);
		return this.stacks.get(slot);
	}

	@Override
	@Nonnull
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;

		validateSlotIndex(slot);

		ItemStack existing = this.stacks.get(slot);

		int limit = getStackLimit(slot, stack);

		if (!existing.isEmpty()) {
			if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
				return stack;

			limit -= existing.getCount();
		}

		if (limit <= 0)
			return stack;

		boolean reachedLimit = stack.getCount() > limit;

		if (!simulate) {
			if (existing.isEmpty()) {
				this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
			} else {
				existing.grow(reachedLimit ? limit : stack.getCount());
			}
			onContentsChanged(slot);
		}

		return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
	}

	@Override
	@Nonnull
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (amount == 0)
			return ItemStack.EMPTY;

		validateSlotIndex(slot);

		ItemStack existing = this.stacks.get(slot);

		if (existing.isEmpty())
			return ItemStack.EMPTY;

		int toExtract = Math.min(amount, existing.getMaxStackSize());

		if (existing.getCount() <= toExtract) {
			if (!simulate) {
				this.stacks.set(slot, ItemStack.EMPTY);
				onContentsChanged(slot);
			}
			return existing;
		} else {
			if (!simulate) {
				this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
				onContentsChanged(slot);
			}

			return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
		}
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
		return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
	}

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		return true;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		System.out.println("serializeNBT" + stacks.toString() );
		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < stacks.size(); i++) {
			if (!stacks.get(i).isEmpty()) {
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setInteger("Slot", i);
				stacks.get(i).writeToNBT(itemTag);
				nbtTagList.appendTag(itemTag);
			}
		}
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Items", nbtTagList);
		nbt.setInteger("Size", stacks.size());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		//addSize(nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : stacks.size());
		nbt.getInteger("Size");
		NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
			int slot = itemTags.getInteger("Slot");

			if (slot >= 0 && slot < stacks.size()) {
				stacks.set(slot, new ItemStack(itemTags));
			}
		}
		onLoad();
	}

	protected void validateSlotIndex(int slot) {
		if (slot < 0 || slot >= stacks.size())
			throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
	}

	protected void onLoad() {

	}

	protected void onContentsChanged(int slot) {

	}
}