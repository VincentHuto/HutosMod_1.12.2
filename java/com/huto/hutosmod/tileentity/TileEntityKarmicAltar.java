package com.huto.hutosmod.tileentity;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityKarmicAltar extends TileManaSimpleInventory implements ITickable {
	public static int cooldown = 0;
	private static final int SET_COOLDOWN_EVENT = 1;

	@Override
	public void onLoad() {
		super.onLoad();
		this.setMaxMana(100);
	}

	@Override
	public boolean addItem(@Nullable EntityPlayer player, ItemStack stack, @Nullable EnumHand hand) {
		if (cooldown > 0 || stack.getItem() == ItemRegistry.maker_activator)
			return false;

		boolean did = false;
		if (stack.getItem() instanceof ItemFood)

			for (int i = 0; i < getSizeInventory(); i++)
				if (itemHandler.getStackInSlot(i).isEmpty()) {
					did = true;
					ItemStack stackToAdd = stack.copy();
					stackToAdd.setCount(1);
					itemHandler.setStackInSlot(i, stackToAdd);
					if (player == null || !player.capabilities.isCreativeMode) {
						stack.shrink(1);
						cooldown = 90;
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

	private EntityPlayer getNearestTargetablePlayer(World world, double xpos, double ypos, double zpos) {
		final double TARGETING_DISTANCE = 16;
		AxisAlignedBB targetRange = new AxisAlignedBB(xpos - TARGETING_DISTANCE, ypos, zpos - TARGETING_DISTANCE,
				xpos + TARGETING_DISTANCE, ypos + TARGETING_DISTANCE, zpos + TARGETING_DISTANCE);
		List<EntityPlayer> allNearbyMobs = world.getEntitiesWithinAABB(EntityPlayer.class, targetRange);
		EntityPlayer nearestMob = null;
		double closestDistance = Double.MAX_VALUE;
		for (EntityPlayer nextMob : allNearbyMobs) {
			double nextClosestDistance = nextMob.getDistanceSq(xpos, ypos, zpos);
			if (nextClosestDistance < closestDistance) {
				closestDistance = nextClosestDistance;
				nearestMob = nextMob;
			}
		}
		return nearestMob;
	}

	private EntityLiving getNearestTargetableMob(World world, double xpos, double ypos, double zpos) {
		final double TARGETING_DISTANCE = 16;
		AxisAlignedBB targetRange = new AxisAlignedBB(xpos - TARGETING_DISTANCE, ypos, zpos - TARGETING_DISTANCE,
				xpos + TARGETING_DISTANCE, ypos + TARGETING_DISTANCE, zpos + TARGETING_DISTANCE);
		List<EntityLiving> allNearbyMobs = world.getEntitiesWithinAABB(EntityLiving.class, targetRange);
		EntityLiving nearestMob = null;
		double closestDistance = Double.MAX_VALUE;
		for (EntityLiving nextMob : allNearbyMobs) {
			double nextClosestDistance = nextMob.getDistanceSq(xpos, ypos, zpos);
			if (nextClosestDistance < closestDistance) {
				closestDistance = nextClosestDistance;
				nearestMob = nextMob;
			}
		}
		return nearestMob;
	}

	public int count = 0;
	boolean consumed = false;

	public static boolean checkConsumed() {
		if (cooldown > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void update() {
		Random rand = new Random();
		double xpos = pos.getX() + 0.5 + ((rand.nextDouble() - rand.nextDouble()) * .3);
		double ypos = pos.getY() + 1.3;
		double zpos = pos.getZ() + 0.5 + ((rand.nextDouble() - rand.nextDouble()) * .3);
		int mod = 3 + rand.nextInt(10);

		// Decement the cooldown
		if (cooldown > 0) {
			cooldown--;
		}
		// Grabs the item above the block
		if (!world.isRemote) {
			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class,
					new AxisAlignedBB(pos, pos.add(1, 1, 1)));
			for (EntityItem item : items)
				if (!item.isDead && !item.getItem().isEmpty()) {
					ItemStack stack = item.getItem();
					addItem(null, stack, null);
				}
		}

		// After the cooldown counter is done consume the item and add mana
		if (cooldown == 0) {
			if (itemHandler.getStackInSlot(0) != ItemStack.EMPTY) {
				itemHandler.setStackInSlot(0, ItemStack.EMPTY);
				sendUpdates();
				this.addManaValue(30);
			}

		}

		if (checkConsumed()) {
			EntityPlayer playerTarget = getNearestTargetablePlayer(world, pos.getX(), pos.getY(), pos.getZ());
			EntityLiving mobTarget = getNearestTargetableMob(world, pos.getX(), pos.getY(), pos.getZ());
			Vector3 vec = Vector3.fromTileEntityCenter(this).add(0, 0, 0);
			Vector3 endVec = vec.add(0, 1, 0);
			Vector3 trackingendVec = vec.fromEntity(playerTarget).add(0, 1, 0);
			Vector3 vecabove = Vector3.fromTileEntityCenter(this).add(0, 1, 0);
			Vector3 belowVec = vec.add(0, 0, 0);

			if (mod % 6 == 0 && playerTarget != null) {
				for (int i = 0; i < 10; i++) {
					world.spawnParticle(EnumParticleTypes.PORTAL, xpos, ypos - .5, zpos, 0, 0.05, 0);
				}
//				MainClass.proxy.lightningFX(vec, endVec, 15F, System.nanoTime(), Reference.white, Reference.black);
				MainClass.proxy.lightningFX(vecabove, belowVec, 15F, System.nanoTime(), Reference.white,
						Reference.black);

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

}
