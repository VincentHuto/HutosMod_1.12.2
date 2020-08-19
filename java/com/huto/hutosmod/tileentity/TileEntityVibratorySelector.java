package com.huto.hutosmod.tileentity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.container.ContainerVibratorySelector;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.particles.SphereParticle;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.recipies.EnumEssecenceType;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityVibratorySelector extends TileEntityLockableLoot implements ITickable {
	private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	public float selectedFrequency;
	float manaValue;
	public final String TAG_FREQUENCY = "FREQUENCY";
	public static final String TAG_MANA = "mana";
	private int blockMetadata = -1;
	private static final int craft_event = 3;
	private static final int decraft_event = 4;
	public static int maxMana = 1000;
	public static boolean active = false;
	public static int cooldown = 0;

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : this.chestContents) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "Vibratory Selector";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerVibratorySelector(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {
		return Reference.MODID + ":vibratory_selector";
	}

	public static int count = 0;
	public static int spiralCount = 0;

	@Override
	public void update() {
		Random rand = new Random();
		count++;

		// spiralCount++;
		if (spiralCount == 360) {
			while (spiralCount > 0) {
				spiralCount--;
			}

		} else {
			spiralCount++;

		}
		double ypos = pos.getY() + 0.65;
		double velocityX = 0, velocityBlueY = 0.0015, velocityRedY = -0.0015, velocityZ = 0;
		double redValue = 0;
		double blueValue = 0;
		double xMod = Math.sin(spiralCount);
		double zMod = Math.cos(spiralCount);
		int mod = 3 + rand.nextInt(10);
		int age = 100;
		if (count > 0 && count % 6 == 0) {
			if (world.isRemote) {

				SphereParticle newEffect = new SphereParticle(world, pos.getX() + 0.5 + xMod * 0.25, ypos,
						pos.getZ() + 0.5 + zMod * 0.25, (xMod * 0.1) * 0.01, velocityBlueY, (zMod * 0.1) * 0.01, 0.0F,
						0.0F, 2.0F, age, 0.1f);

				MainClass.proxy.spawnEffect(newEffect);
			}
			if (world.isRemote) {

				SphereParticle newEffect1 = new SphereParticle(world, pos.getX() + 0.5 + xMod * 0.25, ypos,
						pos.getZ() + 0.5 + zMod * 0.25, (xMod * 0.1) * 0.01, velocityRedY, (zMod * 0.1) * 0.01, 2.0F,
						0.0F, 0.0F, age, 0.1f);
				MainClass.proxy.spawnEffect(newEffect1);
			}
			count = 0;
		}
	}

	public void deresonateEvent() {
		ItemStack inputStack = this.getStackInSlot(0);
		ItemStack outputStack = this.getStackInSlot(0).copy();

		// Copies the item and adds both a float value of the Frequency and a displaying
		// string tag with the lore

		if (this.getStackInSlot(0) != ItemStack.EMPTY && this.getStackInSlot(1) == ItemStack.EMPTY) {
			if (inputStack != ItemStack.EMPTY) {

				if (inputStack.hasTagCompound()) {
					NBTTagCompound compound = outputStack.getTagCompound();

					compound.removeTag("Lore");
					compound.removeTag("display");
					compound.removeTag(TAG_FREQUENCY);
					outputStack.setTagCompound(null);
					world.addBlockEvent(getPos(), getBlockType(), decraft_event, 0);
					this.setInventorySlotContents(1, outputStack);
					this.setInventorySlotContents(0, ItemStack.EMPTY);
					this.sendUpdates();
					this.setManaValue(manaValue - Math.abs(selectedFrequency));
				}
			}
			this.sendUpdates();
		}

	}

	public void craftEvent() {
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);

		ItemStack inputStack = this.getStackInSlot(0);
		ItemStack outputStack = this.getStackInSlot(0).copy();

		// Copies the item and adds both a float value of the Frequency and a displaying
		// string tag with the lore

		if (this.getManaValue() >= Math.abs(selectedFrequency) && this.getStackInSlot(1) == ItemStack.EMPTY) {
			if (inputStack != ItemStack.EMPTY) {

				if (!inputStack.hasTagCompound()) {
					outputStack.setTagCompound(new NBTTagCompound());
				}
				NBTTagCompound compound = outputStack.getTagCompound();
				compound.setFloat(TAG_FREQUENCY, selectedFrequency);
				NBTTagList lore = new NBTTagList();
				lore.appendTag(new NBTTagString("Frequency: "
						+ ModTextFormatting.stringToResonant((String.valueOf(selectedFrequency))) + "Hz"));
				NBTTagCompound display = new NBTTagCompound();
				display.setTag("Lore", lore);
				compound.setTag("display", display);
				world.addBlockEvent(getPos(), getBlockType(), craft_event, 0);

				this.setInventorySlotContents(1, outputStack);

				this.setInventorySlotContents(0, ItemStack.EMPTY);
				this.sendUpdates();
				this.setManaValue(manaValue - Math.abs(selectedFrequency));

			}

			this.sendUpdates();
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);

		}

	}

	@Override
	public boolean receiveClientEvent(int id, int param) {
		switch (id) {
		case craft_event: {
			System.out.println("CALLED EVENT");
			Vector3 vec = Vector3.fromTileEntityCenter(this).add(0, 0, 0);
			Vector3 blueVec = Vector3.fromTileEntityCenter(this).add(0, 2, 0);
			if (selectedFrequency > 0) {
				MainClass.proxy.lightningFX(vec, blueVec, 25F, System.nanoTime(), Reference.blue, Reference.white);
			} else if (selectedFrequency < 0) {
				MainClass.proxy.lightningFX(vec, blueVec, 25F, System.nanoTime(), Reference.red, Reference.white);

			} else {
				MainClass.proxy.lightningFX(vec, blueVec, 25F, System.nanoTime(), Reference.white, Reference.white);

			}

			return true;

		}
		case decraft_event: {
			System.out.println("CALLED EVENT");
			Vector3 vec = Vector3.fromTileEntityCenter(this).add(0, 0, 0);
			Vector3 blueVec = Vector3.fromTileEntityCenter(this).add(0, 2, 0);
			MainClass.proxy.lightningFX(vec, blueVec, 25F, System.nanoTime(), Reference.white, Reference.black);
			return true;

		}

		default:
			return super.receiveClientEvent(id, param);
		}
	}

	public float getSelectedFrequency() {
		sendUpdates();
		this.sendUpdates();

		return selectedFrequency;
	}

	public void setSelectedFrequency(float selectedFrequency) {
		this.sendUpdates();

		this.selectedFrequency = selectedFrequency;
	}

	public float getManaValue() {
		return manaValue;
	}

	public void setManaValue(float manaValue) {
		this.manaValue = manaValue;
	}

	public void addManaValue(float manaValue) {
		this.manaValue += manaValue;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}

	public IBlockState getState() {
		return world.getBlockState(pos);
	}

	@Nonnull
	@Override
	public final NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readPacketNBT(compound);

		this.chestContents = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(compound)) {
			ItemStackHelper.loadAllItems(compound, chestContents);
		}

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagCompound ret = super.writeToNBT(compound);
		writePacketNBT(ret);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, chestContents);
		}
		if (compound.hasKey("CustomName", 8)) {
			compound.setString("CustomName", this.customName);
		}
		return compound;
	}

	public void readPacketNBT(NBTTagCompound tag) {
		selectedFrequency = tag.getFloat(TAG_FREQUENCY);
		manaValue = tag.getFloat(TAG_MANA);

	}

	public void writePacketNBT(NBTTagCompound tag) {
		tag.setFloat(TAG_FREQUENCY, selectedFrequency);
		tag.setFloat(TAG_MANA, manaValue);

	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writePacketNBT(tag);
		return new SPacketUpdateTileEntity(pos, -999, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readPacketNBT(packet.getNbtCompound());
	}

	// Update Stuff

	public void markDirty() {
		if (this.world != null) {
			IBlockState iblockstate = this.world.getBlockState(this.pos);
			this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
			this.world.markChunkDirty(this.pos, this);

			if (this.getBlockType() != Blocks.AIR) {
				this.world.updateComparatorOutputLevel(this.pos, this.getBlockType());
			}
		}
	}

	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		markDirty();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState,
			@Nonnull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

}
