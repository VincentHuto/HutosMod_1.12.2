package com.huto.hutosmod.tileentity;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.particles.RingParticle;
import com.huto.hutosmod.particles.SphereParticle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;

public class TileEntityDisplayPedestal extends TileEntitySimpleInventory implements ITickable {

	@Override
	public boolean addItem(@Nullable EntityPlayer player, ItemStack stack, @Nullable EnumHand hand) {
		boolean did = false;

		for (int i = 0; i < getSizeInventory(); i++)
			if (itemHandler.getStackInSlot(i).isEmpty()) {
				did = true;
				ItemStack stackToAdd = stack.copy();
				stackToAdd.setCount(1);
				itemHandler.setStackInSlot(i, stackToAdd);
				if (player == null || !player.capabilities.isCreativeMode) {
					stack.shrink(1);
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

	public static int count = 0;
	public static int spiralCount = 0;

	@Override
	public void update() {
		Random rand = new Random();
		count++;

	//	spiralCount++;
		if (spiralCount == 360) {
			while (spiralCount > 0) {
				spiralCount--;
			}
			
		}else {
			spiralCount++;

		}
		double ypos = pos.getY() + 1.2;
		double velocityX = 0, velocityBlueY = 0.005,velocityRedY = -0.005, velocityZ = 0;
		double redValue = 0;
		double blueValue = 0;
		double xMod = Math.sin(spiralCount);
		double zMod = Math.cos(spiralCount);
		int mod = 3 + rand.nextInt(10);
		int age =200;
		if (count > 0 && count % 3 == 0) {
			RingParticle newEffect = new RingParticle(world, pos.getX() + 0.5 + xMod * 0.5, ypos,
					pos.getZ() + 0.5 + zMod * 0.5, (xMod * 0.1) * 0.01, velocityBlueY, (zMod * 0.1) * 0.01, 0.0F, 0.0F,
					2.0F, age, 0.1f);
			Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);

			RingParticle newEffect1 = new RingParticle(world, pos.getX() + 0.5 + xMod * 0.5, ypos,
					pos.getZ() + 0.5 + zMod * 0.5, (xMod * 0.1) * 0.01, velocityRedY, (zMod * 0.1) * 0.01, 2.0F, 0.0F,
					0.0F, age, 0.1f);
			 Minecraft.getMinecraft().effectRenderer.addEffect(newEffect1);

			count = 0;
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
