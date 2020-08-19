package com.huto.hutosmod.tileentity;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.karma.IKarma;
import com.huto.hutosmod.karma.KarmaProvider;
import com.huto.hutosmod.particles.ManaParticle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
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
		if (world.isRemote) {
			ManaParticle part1 = new ManaParticle(world, this.getPos().getX() + .5, this.getPos().getY() + .8,
					this.getPos().getZ() + .5, 0, -0.1, 0, 0, 0, 0, 6, 10);
			Minecraft.getMinecraft().effectRenderer.addEffect(part1);
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

	public void onWanded(EntityPlayer player, ItemStack stack) {
		if (world.isRemote)
			return;

		IKarma karma = player.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
		EntityItem outputItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5,
				new ItemStack(ItemRegistry.karmic_drop));
		if (karma.getKarma() >= 1) {
			karma.consume(1);
			addManaValue(20);
			world.spawnEntity(outputItem);

		}
		if (karma.getKarma() < 0) {
			karma.add(1);
			addManaValue(10);
			world.spawnEntity(outputItem);

		}
		this.sendUpdates();
	}
}
