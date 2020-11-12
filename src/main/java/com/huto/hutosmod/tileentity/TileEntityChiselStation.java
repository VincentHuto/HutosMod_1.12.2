package com.huto.hutosmod.tileentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import com.huto.hutosmod.container.ContainerChiselStation;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModChiselRecipies;
import com.huto.hutosmod.recipies.RecipeRuneChisel;
import com.huto.hutosmod.reference.Reference;

import mezz.jei.api.IGuiHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);

	}

	@Override
	public void openInventory(EntityPlayer player) {
		++this.numPlayersUsing;
		this.world.addBlockEvent(pos, this.getBlockType(), 1, this.numPlayersUsing);
		this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
		this.sendUpdates();
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		this.cleartRuneList();
		--this.numPlayersUsing;
		this.world.addBlockEvent(pos, this.getBlockType(), 1, this.numPlayersUsing);
		this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
		this.sendUpdates();

	}

	@Override
	public void update() {

	}

	public void craftEvent() {
		List<ItemStack> chestStuff = new ArrayList<ItemStack>();
		chestStuff.add(chestContents.get(0));
		chestStuff.add(chestContents.get(1));

		RecipeRuneChisel recipe = null;

		if (currentRecipe != null)
			recipe = currentRecipe;
		else
			for (RecipeRuneChisel recipe_ : ModChiselRecipies.runeRecipies) {

				List<Object> recipieInObjFirst = recipe_.getInputs();
				List<ItemStack> recipieInputsFirst = (List<ItemStack>) (Object) recipieInObjFirst;
				ItemStack input2 = this.chestContents.get(0);
				if (chestStuff.get(0).getItem() == recipieInputsFirst.get(0).getItem()
						&& recipieInputsFirst.size() == 1) {
					recipe = recipe_;

					break;
				} else if (chestStuff.get(0).getItem() == recipieInputsFirst.get(0).getItem()
						&& chestStuff.get(1).getItem() == recipieInputsFirst.get(1).getItem()
						&& recipieInputsFirst.size() == 2) {
					recipe = recipe_;
					break;

				}
			}

		if (recipe != null && chestContents.get(2).isEmpty()) {

			List<Object> recipieInObj = recipe.getInputs();
			List<ItemStack> recipieInputs = (List<ItemStack>) (Object) recipieInObj;

			List<Integer> list1 = recipe.getActivatedRunes();
			List<Integer> list2 = this.getRuneList();
			// These two make sure that even if you click the buttons in the wrong order
			// they still work.
			Collections.sort(list1);
			Collections.sort(list2);
			// Checks if the two inventories have the exact same values
			boolean matcher = false;
			if (chestStuff.get(0).getItem() == recipieInputs.get(0).getItem() && recipieInputs.size() == 1) {

				matcher = true;
			}

			else if (chestStuff.get(0).getItem() == recipieInputs.get(0).getItem()
					&& chestStuff.get(1).getItem() == recipieInputs.get(1).getItem() && recipieInputs.size() == 2) {

				matcher = true;
			}

			if (list1.equals(list2) && matcher) {
				{

					ItemStack output = recipe.getOutput().copy();
					EntityItem outputItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5,
							output);
					if (world.isRemote) {

						world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX(), pos.getY(), pos.getZ(), 0.0D, 0.0D,
								0.0D);
					}
					chestContents.set(0, output);
					currentRecipe = null;
					for (int i = 0; i < getSizeInventory(); i++) {
						ItemStack stack = chestContents.get(i);
						if (!stack.isEmpty()) {
						}
						chestContents.set(i, ItemStack.EMPTY);
						chestContents.set(2, output);
						runesList.clear();
						this.sendUpdates();
						VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);

					}
				}
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
		this.sendUpdates();

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
	public SPacketUpdateTileEntity getUpdatePacket() {
		super.getUpdatePacket();
		NBTTagList tagList = new NBTTagList();
		NBTTagCompound tag = new NBTTagCompound();
		tagList.appendTag(tag);
		if (runesList != null) {
			for (int i = 0; i < runesList.size(); i++) {
				Integer s = runesList.get(i);
				if (s != null) {
					tagList.appendTag(tag);
				}
			}
			writeToNBT(tag);
		}
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);

		NBTTagList tag = pkt.getNbtCompound().getTagList(TAG_RUNELIST, Constants.NBT.TAG_INT);
		List<Integer> test = new ArrayList<Integer>();
		for (int i = 0; i < tag.tagCount(); i++) {
			test.add(tag.getIntAt(i));
			test.set(i, tag.getIntAt(i));
		}
		this.runesList = test;

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, chestContents);
		}
		if (compound.hasKey("CustomName", 8)) {
			compound.setString("CustomName", this.customName);
		}
		NBTTagList tagList = new NBTTagList();
		if (runesList != null) {
			for (int i = 0; i < runesList.size(); i++) {
				Integer s = runesList.get(i);
				if (s != null) {
					NBTTagInt tag = new NBTTagInt(s);
					tagList.appendTag(tag);
				}
			}

			compound.setTag(TAG_RUNELIST, tagList);
		}
		return compound;

	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.chestContents = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(compound)) {
			ItemStackHelper.loadAllItems(compound, chestContents);
		}

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}
		NBTTagList tagList = compound.getTagList(TAG_RUNELIST, Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			int s = tag.getInteger("ListPos " + i);
			runesList.add(i, s);
			runesList.set(i, s);
		}
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

	@Override
	public boolean receiveClientEvent(int id, int param) {
		return super.receiveClientEvent(id, param);
	}

}
