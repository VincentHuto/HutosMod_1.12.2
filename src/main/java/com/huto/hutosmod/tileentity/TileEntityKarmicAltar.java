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
	int cooldown = 0;
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

	public int count = 0;
	boolean consumed = false;

	@Override
	public void update() {

		Random rand = new Random();
		double xpos = pos.getX() + 0.5 + ((rand.nextDouble() - rand.nextDouble()) * .3);
		double ypos = pos.getY() + 1.3;
		double zpos = pos.getZ() + 0.5 + ((rand.nextDouble() - rand.nextDouble()) * .3);
		int mod = 3 + rand.nextInt(10);

		// Grabs the item above the block
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class,
				new AxisAlignedBB(pos, pos.add(1, 1, 1)));
		for (EntityItem item : items)
			if (!item.isDead && !item.getItem().isEmpty()) {
				ItemStack stack = item.getItem();
				addItem(null, stack, null);
			}

		// After the cooldown counter is done consume the item and add mana
		if (cooldown == 0) {
			if (itemHandler.getStackInSlot(0) != ItemStack.EMPTY) {
				itemHandler.setStackInSlot(0, ItemStack.EMPTY);
				sendUpdates();
				this.addManaValue(30);
			}

		}

		// System.out.println(cooldown);
		count++;
		if (cooldown > 0) {
			if (count % 10 == 0) {
				count = 0;
				if (world.isRemote) {
					Vector3 vecabove = Vector3.fromTileEntityCenter(this).add(0, 1, 0);
					Vector3 belowVec = Vector3.fromTileEntityCenter(this);
					MainClass.proxy.lightningFX(vecabove, belowVec, 15F, System.nanoTime(), Reference.white,
							Reference.black);
				}
			}

		}

		// Decement the cooldown
		if (cooldown > 0) {
			cooldown--;
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

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
}
