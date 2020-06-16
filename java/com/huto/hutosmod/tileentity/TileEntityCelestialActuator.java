package com.huto.hutosmod.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityCelestialActuator extends TileModMana implements ITickable {
	public static int cooldown = 0;
	public static final String TAG_MANA = "mana";
	@Override
	public void onLoad() {
		super.onLoad();
		this.setMaxMana(300);
	}

	public int count = 0;

	public static boolean checkCooldown() {
		if (cooldown > 0) {
			return false;
		} else {
			return true;
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

	@Override
	public void update() {
		if (cooldown > 0) {
			cooldown--;
			System.out.println(cooldown);
		}

	}

	public void onWanded(EntityPlayer player, ItemStack wand) {
		if (world.isRemote)
			return;
		if (!checkCooldown()) {
			if (this.manaValue >= 40) {
				this.setManaValue(manaValue - 40);
				cooldown = 60;
				System.out.println("WANDED: " + this.getClass().getSimpleName());
			}
		}
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

	private int lastPowerLevel = -1;
	private final double NEEDLE_ACCELERATION = 0.4; // acceleration in units per square second
	private final double NEEDLE_MAX_SPEED = 0.4; // maximum needle movement speed in units per second
	private SmoothNeedleMovement smoothNeedleMovement = new SmoothNeedleMovement(NEEDLE_ACCELERATION, NEEDLE_MAX_SPEED);

	public int getPowerLevelClient() {
		int maxPowerFound = 0;
		for (EnumFacing whichFace : EnumFacing.HORIZONTALS) {
			BlockPos neighborPos = pos.offset(whichFace);
			int powerLevel = this.world.getRedstonePower(neighborPos, whichFace);
			maxPowerFound = Math.max(powerLevel, maxPowerFound);
		}
		return maxPowerFound;
	}

	// return the smoothed position of the needle, based on the power level
	public double getSmoothedNeedlePosition() {
		double targetNeedlePosition = manaValue / maxMana;
		smoothNeedleMovement.setTargetNeedlePosition(targetNeedlePosition, false);

		return smoothNeedleMovement.getSmoothedNeedlePosition();
	}

}
