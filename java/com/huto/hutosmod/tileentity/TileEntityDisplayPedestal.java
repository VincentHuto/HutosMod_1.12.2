package com.huto.hutosmod.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModEnhancerRecipies;
import com.huto.hutosmod.recipies.ModWandRecipies;
import com.huto.hutosmod.recipies.RecipeEnhancer;
import com.huto.hutosmod.recipies.RecipeWandMaker;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileEntityDisplayPedestal extends TileEntitySimpleInventory implements ITickable {

	@Override
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

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	protected SimpleItemStackHandler createItemHandler() {
		return new SimpleItemStackHandler(this, false) {
			@Override
			protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
				return 1;
			}
		};
	}

	public boolean isEmpty() {
		for (int i = 0; i < getSizeInventory(); i++)
			if (!itemHandler.getStackInSlot(i).isEmpty())
				return false;

		return true;
	}

	@Override
	public void update() {

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

}
