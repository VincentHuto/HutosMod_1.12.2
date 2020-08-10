package com.huto.hutosmod.tileentity;

import java.util.List;
import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.biomes.BiomeRegistry;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketGetManaLimit;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.particles.ManaParticle;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityWaveGatherer extends TileModMana implements ITickable {
	public int count = 0;
	public static final String TAG_MANA = "mana";

	@Override
	public void update() {
		if (checkStructure() && this.getManaValue() < 100) {
			this.addManaValue(0.2F);
		}

		// Ease of use variables
		int x = this.pos.getX();
		int y = this.pos.getY();
		int z = this.pos.getZ();
		// Search radius for blocks and tiles
		Iterable<BlockPos> radiusPositions = BlockPos.getAllInBox(this.pos.add(3.0f, 3.0f, 3.0f),
				this.pos.add(-3.0f, -3.0f, -3.0f)); // 3x3x3 range
		// Checks block positions in a 3x3x3
		for (BlockPos pos : radiusPositions) {
			TileEntity tile = world.getTileEntity(pos);
			// Makes sure it doesnt run uselessly
			if (tile != null && this.getPos() != pos) {
				if (tile instanceof TileEntityStorageDrum || tile instanceof TileEntityManaCapacitor) {
					TileModMana manaStor = (TileModMana) tile;
					// This doesnt need the && this.manaValue > manaStor.getManaValue() because as a
					// generator it doesnt matter if it has more or less than the tank
					if (this.manaValue >= 30) {
						if (manaStor.getManaValue() <= manaStor.getMaxMana()) {
							this.setManaValue(manaValue - 0.4f);
							manaStor.addManaValue(0.4f);
						}
					}

				}
			}
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
