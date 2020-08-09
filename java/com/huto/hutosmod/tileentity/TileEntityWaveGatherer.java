package com.huto.hutosmod.tileentity;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.biomes.BiomeRegistry;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.particles.ManaParticle;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityWaveGatherer extends TileModMana implements ITickable {
	public int count = 0;
	public static final String TAG_MANA = "mana";

	@Override
	public void update() {
		if(checkStructure() && this.getManaValue()<100) {
			this.addManaValue(0.2F);
		}
	}

	public boolean checkStructure() {
		BlockPos adj = getPos().offset(EnumFacing.DOWN);
		IBlockState blockState = world.getBlockState(adj);
		Block block = blockState.getBlock();
		if (block == Blocks.WATER) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void readPacketNBT(NBTTagCompound tag) {
		manaValue = tag.getFloat(TAG_MANA);

	}

	@Override
	public void writePacketNBT(NBTTagCompound tag) {
		tag.setFloat(TAG_MANA, manaValue);

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
