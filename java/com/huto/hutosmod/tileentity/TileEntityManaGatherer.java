package com.huto.hutosmod.tileentity;

import java.util.Random;

import com.huto.hutosmod.particles.ManaParticle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityManaGatherer extends TileModMana implements ITickable{
	public int count=0;
	public static final String TAG_MANA = "mana";
	@Override
	public void update() {
		Random rand = new Random();
		double xpos = pos.getX() + 0.5+((rand.nextDouble() -rand.nextDouble())*.3);
		double ypos = pos.getY() + 1.3;
		double zpos = pos.getZ() + 0.5+((rand.nextDouble() -rand.nextDouble())*.3);
		double velocityX = 0, velocityY = -0.2, velocityZ = 0;
		ManaParticle newEffect = new ManaParticle(world, xpos, ypos, zpos, velocityX, velocityY, velocityZ,0.0F,0.9F,0.9F);
		//System.out.println(manaValue);
		count++;
		int mod = 3+rand.nextInt(10);
		if(count%mod==0) {
			this.setManaValue(manaValue+0.1F);

		if(world.isRemote) {
		
			Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
			}
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

	private IBlockState getState() {
		return world.getBlockState(pos);
	}

	private void setBlockToUpdate() {
		sendUpdates();
	}

	private void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		markDirty();
	}
	
}
