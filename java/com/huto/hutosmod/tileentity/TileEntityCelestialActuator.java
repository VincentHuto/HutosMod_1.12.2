package com.huto.hutosmod.tileentity;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;

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
	public static final String TAG_STATE = "state";
	public static boolean state = false;

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
		state = tag.getBoolean(TAG_STATE);

	}

	@Override
	public void writePacketNBT(NBTTagCompound tag) {
		tag.setFloat(TAG_MANA, manaValue);
		tag.setBoolean(TAG_STATE, state);

	}

	@Override
	public void update() {
		// System.out.println(state);

		if (cooldown > 0) {
			cooldown--;
			System.out.println(cooldown);
		}

	}

	public void changeState() {
		if (state == false) {
			state = true;
		} else {
			state = false;
		}
	}

	public void onWanded(EntityPlayer player, ItemStack wand) {
		if (world.isRemote)
			return;

		if (checkCooldown()) {
			if (this.manaValue >= 1) {
				Vector3 vec = Vector3.fromTileEntityCenter(this).add(0, 0.1, 0);
				Vector3 endVec = vec.add(0, 0.5, 0);
				this.setManaValue(manaValue - 10);
				cooldown = 10;
				System.out.println("WANDED: " + this.getClass().getSimpleName());
				if (state == false) {
					world.setWorldTime(world.getWorldTime() + 1000);
					MainClass.proxy.lightningFX(vec, endVec, 1F, System.nanoTime(), Reference.orange, Reference.yellow);

				} else {
					world.setWorldTime(world.getWorldTime() - 1000);
					MainClass.proxy.lightningFX(vec, endVec, 1F, System.nanoTime(), Reference.purple,
							Reference.oxblood);

				}
				this.sendUpdates();
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
		// System.out.println(world.getCelestialAngle(1.0F));

		double targetNeedlePosition = world.getCelestialAngle(1.0F) / 1;
		smoothNeedleMovement.setTargetNeedlePosition(targetNeedlePosition, false);

		return smoothNeedleMovement.getSmoothedNeedlePosition();
	}

}
