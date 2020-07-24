
package com.huto.hutosmod.recipies;

import com.huto.hutosmod.tileentity.TileEntitySimpleInventory;
import com.huto.hutosmod.tileentity.TileManaSimpleInventory;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class ModInventoryHelper {

	public static InvWithLocation getInventoryWithLocation(World world, BlockPos pos, EnumFacing side) {
		IItemHandler ret = getInventory(world, pos, side);
		if(ret == null)
			return null;
		else return new InvWithLocation(ret, world, pos);
	}

	public static IItemHandler getInventory(World world, BlockPos pos, EnumFacing side) {
		TileEntity te = world.getTileEntity(pos);

		if(te == null)
			return null;

		IItemHandler ret = te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) ?
				te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) : null;

				if(ret == null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
					ret = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

				return ret;
	}

	public static void dropInventory(TileManaSimpleInventory inv, World world, IBlockState state, BlockPos pos) {
		if(inv != null) {
			for(int j1 = 0; j1 < inv.getSizeInventory(); ++j1) {
				ItemStack itemstack = inv.getItemHandler().getStackInSlot(j1);

				if(!itemstack.isEmpty()) {
					net.minecraft.inventory.InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
				}
			}

			world.updateComparatorOutputLevel(pos, state.getBlock());
		}
	}
	
	public static void dropInventory(TileEntitySimpleInventory inv, World world, IBlockState state, BlockPos pos) {
		if(inv != null) {
			for(int j1 = 0; j1 < inv.getSizeInventory(); ++j1) {
				ItemStack itemstack = inv.getItemHandler().getStackInSlot(j1);

				if(!itemstack.isEmpty()) {
					net.minecraft.inventory.InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
				}
			}

			world.updateComparatorOutputLevel(pos, state.getBlock());
		}
	}

	public static void withdrawFromInventory(TileManaSimpleInventory inv, EntityPlayer player) {
		for(int i = inv.getSizeInventory() - 1; i >= 0; i--) {
			ItemStack stackAt = inv.getItemHandler().getStackInSlot(i);
			if(!stackAt.isEmpty()) {
				ItemStack copy = stackAt.copy();
				ItemHandlerHelper.giveItemToPlayer(player, copy);
				inv.getItemHandler().setStackInSlot(i, ItemStack.EMPTY);
				player.world.updateComparatorOutputLevel(inv.getPos(), null);
				break;
			}
		}
	}
	
	public static void withdrawFromInventory(TileEntitySimpleInventory inv, EntityPlayer player) {
		for(int i = inv.getSizeInventory() - 1; i >= 0; i--) {
			ItemStack stackAt = inv.getItemHandler().getStackInSlot(i);
			if(!stackAt.isEmpty()) {
				ItemStack copy = stackAt.copy();
				ItemHandlerHelper.giveItemToPlayer(player, copy);
				inv.getItemHandler().setStackInSlot(i, ItemStack.EMPTY);
				player.world.updateComparatorOutputLevel(inv.getPos(), null);
				break;
			}
		}
	}
	public static void withdrawFromInventoryToInventory(TileManaSimpleInventory inv, IItemHandler inventory) {
		for(int i = inv.getSizeInventory() - 1; i >= 0; i--) {
			ItemStack stackAt = inv.getItemHandler().getStackInSlot(i);
			if(!stackAt.isEmpty()) {
				ItemStack copy = stackAt.copy();
				ItemHandlerHelper.insertItem(inventory, stackAt, false);
				inv.getItemHandler().setStackInSlot(i, ItemStack.EMPTY);
				break;
			}
		}
	}
}
