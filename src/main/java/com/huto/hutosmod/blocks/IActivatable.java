
package com.huto.hutosmod.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IActivatable {

	/**
	 * Called when the block is used by an activatpr. Note that the player parameter can
	 * be null if this function is called from a dispenser.
	 */
	public boolean onUsedByActivator(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side);

}
