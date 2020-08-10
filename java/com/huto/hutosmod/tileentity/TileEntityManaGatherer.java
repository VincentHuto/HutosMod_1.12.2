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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityManaGatherer extends TileModMana implements ITickable {
	public int count = 0;
	public static final String TAG_MANA = "mana";

	@Override
	public void update() {
		Random rand = new Random();
		double xpos = pos.getX() + 0.5 + ((rand.nextDouble() - rand.nextDouble()) * .3);
		double ypos = pos.getY() + 1.3;
		double zpos = pos.getZ() + 0.5 + ((rand.nextDouble() - rand.nextDouble()) * .3);
		double velocityX = 0, velocityY = -0.2, velocityZ = 0;
		ManaParticle newEffect = new ManaParticle(world, xpos, ypos, zpos, velocityX, velocityY, velocityZ, 0.0F, 0.9F,
				0.9F, 70, 1);
		count++;
		int mod = 3 + rand.nextInt(10);
		if (this.getManaValue() < 1000) {
			if (count % mod == 0) {

				this.addManaValue(0.2F);
			}
			if (world.getBiome(this.getPos()) == BiomeRegistry.DREAMSCAPE || checkStructure()) {
				this.addManaValue(0.4F);
			}

			if (world.isRemote) {
				Vector3 vec = Vector3.fromTileEntityCenter(this).add(0, 0.3, 0);
				Vector3 endVec = vec.add(0, 0.5, 0);
				if (count % 10 == 0) {
					MainClass.proxy.lightningFX(vec, endVec, 15F, System.nanoTime(), Reference.blue, Reference.white);
					Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);


				}
				

			}
			if(count>10) {
			count = 0;
			}
			this.sendUpdates();

		}
	}

	public boolean checkStructure() {
		BlockPos adj = getPos().offset(EnumFacing.DOWN);
		IBlockState blockState = world.getBlockState(adj);
		Block block = blockState.getBlock();
		if (block == BlockRegistry.enchanted_stone) {
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
