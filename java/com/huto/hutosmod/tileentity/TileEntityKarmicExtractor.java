package com.huto.hutosmod.tileentity;

import com.huto.hutosmod.particles.ManaParticle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityKarmicExtractor extends TileModMana implements ITickable {
	public static final String TAG_MANA = "mana";

	@SideOnly(Side.CLIENT)
	public boolean shouldRenderFace(EnumFacing face) {
		return true;
	}

	@Override
	public void onLoad() {
		super.onLoad();
		this.setMaxMana(300);
	}

	@Override
	public void readPacketNBT(NBTTagCompound tag) {
		manaValue = tag.getFloat(TAG_MANA);

	}

	@Override
	public void writePacketNBT(NBTTagCompound tag) {
		tag.setFloat(TAG_MANA, manaValue);

	}

	@Override
	public void update() {
		ManaParticle part1 = new ManaParticle(world, this.getPos().getX() + .5, this.getPos().getY() + .8,
				this.getPos().getZ() + .5, 0, 0.2, 0, 0, 0, 0, 20, 3);
		Minecraft.getMinecraft().effectRenderer.addEffect(part1);
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
