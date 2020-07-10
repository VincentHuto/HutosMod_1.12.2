package com.huto.hutosmod.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.huto.hutosmod.container.ContainerChiselStation;
import com.huto.hutosmod.recipies.ModChiselRecipies;
import com.huto.hutosmod.recipies.RecipeRuneChisel;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

public class TileEntityChiselStation extends TileEntityLockableLoot implements ITickable {
	public NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	public int numPlayersUsing = 0;
	public int ticksSinceSync;
	public float lidAngle, prevLidAngle;
	public static final String TAG_RUNELIST = "RUNELIST";
	public List<Integer> runesList;
	RecipeRuneChisel currentRecipe;
	private int blockMetadata = -1;

	@Override
	public void onLoad() {
		this.runesList = new ArrayList<Integer>();
	}

	@Override
	public void update() {
	
		//System.out.println(this.getUpdatePacket().getNbtCompound().getTag(TAG_RUNELIST));
		//System.out.println(this.runesList);

		// this.runesList =this.getUpdatePacket().getNbtCompound().getTag(TAG_RUNELIST);
		RecipeRuneChisel recipe = null;
		if (currentRecipe != null)
			recipe = currentRecipe;
		else
			for (RecipeRuneChisel recipe_ : ModChiselRecipies.runeRecipies) {
				ItemStack input1 = (ItemStack) recipe_.getInputs().get(0);
				ItemStack input2 = this.chestContents.get(0);
				List<Integer> list1 = recipe_.getActivatedRunes();
				 List<Integer> list2= this.getRuneList();
				 
				if (input1.getItem() == input2.getItem()) {
					System.out.println(list1);
					System.out.println(list2);

					recipe = recipe_;

					break;
				}
			}
		if (recipe != null && chestContents.get(2).isEmpty()) {

			ItemStack output = recipe.getOutput().copy();
			EntityItem outputItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, output);
			world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX(), pos.getY(), pos.getZ(), 0.0D, 0.0D, 0.0D);
			chestContents.set(0, output);
			currentRecipe = null;
			for (int i = 0; i < getSizeInventory(); i++) {
				ItemStack stack = chestContents.get(i);
				if (!stack.isEmpty()) {
				}
				chestContents.set(i, ItemStack.EMPTY);
				chestContents.set(2, output);
			}
		}

	}

	public List<Integer> getRuneList() {
		return runesList;
	}

	public void cleartRuneList() {
		this.sendUpdates();
		if (runesList != null) {
			runesList.clear();
		}
	}

	public void setRuneList(List<Integer> runesIn) {
		runesList = runesIn;

	}

	@Override
	public int getSizeInventory() {
		return 3;
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
		return this.hasCustomName() ? this.customName : "Rune Chisel Station";
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ItemStackHelper.saveAllItems(compound, chestContents);
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < runesList.size(); i++) {
			Integer s = runesList.get(i);
			if (s != null) {
				NBTTagInt tag = new NBTTagInt(s);
				tagList.appendTag(tag);
			}
		}
		compound.setTag(TAG_RUNELIST, tagList);
		return compound;

	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.chestContents = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.chestContents);
		NBTTagList tagList = compound.getTagList(TAG_RUNELIST, Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			int s = tag.getInteger("ListPos " + i);
			System.out.println("Current Runelist is: " + getRuneList());
			runesList.add(i, s);
		}
		System.out.println(runesList.toString());

	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		SPacketUpdateTileEntity pack = new SPacketUpdateTileEntity(this.pos, 0, tagCompound);
		return pack;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerChiselStation(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {
		return Reference.MODID + ":rune_station";
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}

	public IBlockState getState() {
		return world.getBlockState(pos);
	}

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

}
