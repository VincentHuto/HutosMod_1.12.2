/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Jan 21, 2014, 9:56:24 PM (GMT)]
 */
package com.huto.hutosmod.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.huto.hutosmod.items.DynamicItemStackHandler;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;

public abstract class TileManaSimpleInventory extends TileModMana  {

	protected SimpleItemStackHandler itemHandler = createItemHandler();
	private static final String TAG_MANA = "mana";
	
	@Override
	public void readPacketNBT(NBTTagCompound par1NBTTagCompound) {
		itemHandler = createItemHandler();
		itemHandler.deserializeNBT(par1NBTTagCompound);
		manaValue = par1NBTTagCompound.getFloat(TAG_MANA);
	}

	@Override
	public void writePacketNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.merge(itemHandler.serializeNBT());
		par1NBTTagCompound.setFloat(TAG_MANA, manaValue);

	}
	public boolean addItem(@Nullable EntityPlayer player, ItemStack stack, @Nullable EnumHand hand) {
		boolean did = false;
		for (int i = 0; i < getSizeInventory(); i++)
			if (itemHandler.getStackInSlot(i).isEmpty()) {
				did = true;
				ItemStack stackToAdd = stack.copy();
				stackToAdd.setCount(1);
				itemHandler.setStackInSlot(i, stackToAdd);
				if (player == null || !player.capabilities.isCreativeMode) {
					stack.shrink(1);
				}
				break;
			}

		if (did)
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);

		return true;
	}
	public abstract int getSizeInventory();

	protected SimpleItemStackHandler createItemHandler() {
		return new SimpleItemStackHandler(this, true);
	}

	public IItemHandlerModifiable getItemHandler() {
		return itemHandler;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, EnumFacing side) {
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(cap, side);
	}

	@Override
	public <T> T getCapability(@Nonnull Capability<T> cap, EnumFacing side) {
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		return super.getCapability(cap, side);
	}

	public IBlockState getState() {
		return world.getBlockState(pos);
	}

	public void setBlockToUpdate() {
		sendUpdates();
	}

	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		markDirty();
	}
	
	/* Extension of ItemStackHandler that uses our own slot array, allows for control of writing,
	   allows control over stack limits, and allows for itemstack-slot validation */
	protected static class SimpleItemStackHandler extends ItemStackHandler {

		private final boolean allowWrite;
		private final TileManaSimpleInventory tile;

		public SimpleItemStackHandler(TileManaSimpleInventory inv, boolean allowWrite) {
			super(inv.getSizeInventory());
			this.allowWrite = allowWrite;
			tile = inv;
		}

		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			if(allowWrite) {
				return super.insertItem(slot, stack, simulate);
			} else return stack;
		}

		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if(allowWrite) {
				
				return super.extractItem(slot, amount, simulate);
			} else return ItemStack.EMPTY;
		}

		@Override
		public void onContentsChanged(int slot) {
			tile.markDirty();
		}
	}
}
