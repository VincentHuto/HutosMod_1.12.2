package com.huto.hutosmod.tileentity;

import java.util.List;

import com.huto.hutosmod.container.ContainerChiselStation;
import com.huto.hutosmod.container.ContainerRuneStation;
import com.huto.hutosmod.network.PacketGetChiselData;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.network.PacketReturnChiselGui;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityChiselStation extends TileEntityLockableLoot implements ITickable {
	private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	public int numPlayersUsing, ticksSinceSync;
	public float lidAngle, prevLidAngle;
	public  List<Integer> runesList;

	
	
	public List<Integer> getRuneList() {
		return runesList;
	}

	public void setRuneList(List<Integer> runesIn) {
		this.runesList = runesIn;
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
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
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
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, chestContents);
		}
		if (compound.hasKey("CustomName", 8)) {
			compound.setString("CustomName", this.customName);
		}
		return compound;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerChiselStation(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {
		return Reference.MODID + ":rune_station";
	}

	@SuppressWarnings("unused")
	@Override
	public void update() {
	//	PacketHandler.INSTANCE.getPacketFrom(new PacketGetChiselData(runesList, "com.huto.hutosmod.tileentity.TileEntityChiselStation", "runesList"));
		//System.out.println(runesList);
		
		if (!this.world.isRemote && this.numPlayersUsing != 0
				&& (this.ticksSinceSync + pos.getX() + pos.getY() + pos.getZ()) % 200 == 0) {
			this.numPlayersUsing = 0;
			float f = 5.0F;

			for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class,
					new AxisAlignedBB((double) ((float) pos.getX() - 5.0F), (double) ((float) pos.getY() - 5.0F),
							(double) ((float) pos.getZ() - 5.0F), (double) ((float) (pos.getX() + 1) + 5.0F),
							(double) ((float) (pos.getY() + 1) + 5.0F), (double) ((float) (pos.getZ() + 1) + 5.0F)))) {
				if (entityplayer.openContainer instanceof ContainerChiselStation) {
					if (((ContainerChiselStation) entityplayer.openContainer).getChestInventory() == this) {
						++this.numPlayersUsing;
					}
				}
			}
		}

		this.prevLidAngle = this.lidAngle;
		float f1 = 0.1F;

		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
			double d1 = (double) pos.getX() + 0.5D;
			double d2 = (double) pos.getZ() + 0.5D;
			this.world.playSound((EntityPlayer) null, d1, (double) pos.getY() + 0.5D, d2,
					SoundEvents.BLOCK_ENDERCHEST_OPEN, SoundCategory.BLOCKS, 0.5F,
					this.world.rand.nextFloat() * 0.1F + 0.9F);
		}

		
	}
	
	
/*	

	 *//**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     *//*
    public void smeltItem()
    {
      
            ItemStack runeInput = this.chestContents.get(0);
            ItemStack secondaryInput = FurnaceRecipes.instance().getSmeltingResult(itemstack);
            ItemStack outputStack = this.chestContents.get(2);
            
            
            else if (itemstack2.getItem() == itemstack1.getItem())
            {
                itemstack2.grow(itemstack1.getCount());
            }


            itemstack.shrink(1);
        
    }*/
	
	
	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.chestContents;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		++this.numPlayersUsing;
		this.world.addBlockEvent(pos, this.getBlockType(), 1, this.numPlayersUsing);
		this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		--this.numPlayersUsing;
		this.world.addBlockEvent(pos, this.getBlockType(), 1, this.numPlayersUsing);
		this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
	}
}
