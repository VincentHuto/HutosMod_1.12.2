package com.huto.hutosmod.tileentity;

import java.util.List;

import javax.annotation.Nullable;
import javax.swing.plaf.DesktopIconUI;

import org.apache.commons.lang3.tuple.Pair;

import com.huto.hutosmod.blocks.BlockManaHopper;
import com.huto.hutosmod.recipies.ModInventoryHelper;

import it.unimi.dsi.fastutil.Stack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityManaHopper extends TileEntityLockableLoot implements IHopper, ITickable {
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(5, ItemStack.EMPTY);
	private int transferCooldown = -1;
	private long tickedGameTime;

	public static void registerFixesHopper(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY,
				new ItemStackDataLists(TileEntityManaHopper.class, new String[] { "Items" }));
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

		if (!this.checkLootAndRead(compound)) {
			ItemStackHelper.loadAllItems(compound, this.inventory);
		}

		if (compound.hasKey("CustomName", 8)) {
			this.customName = compound.getString("CustomName");
		}

		this.transferCooldown = compound.getInteger("TransferCooldown");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.inventory);
		}

		compound.setInteger("TransferCooldown", this.transferCooldown);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		return this.inventory.size();
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns
	 * them in a new stack.
	 */
	public ItemStack decrStackSize(int index, int count) {
		this.fillWithLoot((EntityPlayer) null);
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.getItems(), index, count);
		return itemstack;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.fillWithLoot((EntityPlayer) null);
		this.getItems().set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.hopper";
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64,
	 * possibly will be extended.
	 */
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * Like the old updateEntity(), except more generic.
	 */
	public void update() {
		if (this.world != null && !this.world.isRemote) {
			--this.transferCooldown;
			this.tickedGameTime = this.world.getTotalWorldTime();

			if (!this.isOnTransferCooldown()) {
				this.setTransferCooldown(0);
				this.updateHopper();
			}
		}
	}

	/**
	 * Gets the inventory that the provided hopper will transfer items from.
	 */
	public static IInventory getSourceInventoryChest(IHopper hopper) {
		return (IInventory) getInventoryAtPosition(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0D,
				hopper.getZPos());
	}

	/**
	 * Insert the specified stack to the specified inventory and return any leftover
	 * items
	 */
	private static ItemStack insertStackChest(IInventory source, IInventory destination, ItemStack stack, int index,
			EnumFacing direction) {
		ItemStack itemstack = destination.getStackInSlot(index);

		if (canInsertItemInSlotChest(destination, stack, index, direction)) {
			boolean flag = false;
			boolean flag1 = destination.isEmpty();

			if (itemstack.isEmpty()) {
				destination.setInventorySlotContents(index, stack);
				stack = ItemStack.EMPTY;
				flag = true;
			} else if (canCombine(itemstack, stack)) {
				int i = stack.getMaxStackSize() - itemstack.getCount();
				int j = Math.min(stack.getCount(), i);
				stack.shrink(j);
				itemstack.grow(j);
				flag = j > 0;
			}

			if (flag) {
				if (flag1 && destination instanceof TileEntityManaHopper) {
					TileEntityManaHopper tileentityhopper1 = (TileEntityManaHopper) destination;

					if (!tileentityhopper1.mayTransfer()) {
						int k = 0;

						if (source != null && source instanceof TileEntityManaHopper) {
							TileEntityManaHopper tileentityhopper = (TileEntityManaHopper) source;

							if (tileentityhopper1.tickedGameTime >= tileentityhopper.tickedGameTime) {
								k = 1;
							}
						}

						tileentityhopper1.setTransferCooldown(8 - k);
					}
				}

				destination.markDirty();
			}
		}

		return stack;
	}

	/**
	 * Attempts to place the passed stack in the inventory, using as many slots as
	 * required. Returns leftover items
	 */
	public static ItemStack putStackInInventoryAllSlotsChest(IInventory source, IInventory destination, ItemStack stack,
			@Nullable EnumFacing direction) {
		if (destination instanceof ISidedInventory && direction != null) {
			ISidedInventory isidedinventory = (ISidedInventory) destination;
			int[] aint = isidedinventory.getSlotsForFace(direction);

			for (int k = 0; k < aint.length && !stack.isEmpty(); ++k) {
				stack = insertStackChest(source, destination, stack, aint[k], direction);
			}
		} else {
			int i = destination.getSizeInventory();

			for (int j = 0; j < i && !stack.isEmpty(); ++j) {
				stack = insertStackChest(source, destination, stack, j, direction);
			}
		}

		return stack;
	}

	/**
	 * Attempts to place the passed stack in the inventory, using as many slots as
	 * required. Returns leftover items
	 */
	public static ItemStack removeStackInInventoryAllSlots(TileManaSimpleInventory source, IInventory destination,
			ItemStack stack, @Nullable EnumFacing direction) {
		if (destination instanceof ISidedInventory && direction != null) {
			ISidedInventory isidedinventory = (ISidedInventory) destination;
			int[] aint = isidedinventory.getSlotsForFace(direction);

			for (int k = 0; k < aint.length && !stack.isEmpty(); ++k) {
				stack = insertStack(destination, source, stack, aint[k], direction);
			}
		} else {
			int i = destination.getSizeInventory();

			for (int j = 0; j < i && !stack.isEmpty(); ++j) {
				stack = insertStack(destination, source, stack, j, direction);
			}
		}

		return stack;
	}

	/**
	 * Gets the inventory that the provided hopper will transfer items from.
	 */
	public static TileManaSimpleInventory getSourceInventory(IHopper hopper) {
		return getInventoryAtPosition(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos());
	}

	/**
	 * Pull dropped {@link net.minecraft.entity.item.EntityItem EntityItem}s from
	 * the world above the hopper and items from any inventory attached to this
	 * hopper into the hopper's inventory.
	 * 
	 * @param hopper the hopper in question
	 * @return whether any items were successfully added to the hopper
	 */
	public boolean pullItems(IHopper hopper) {
		/*
		 * Boolean ret = ManaHopperCodeHooks.extractHook(hopper); if (ret != null) {
		 * return ret; }
		 */
		TileManaSimpleInventory iinventory = getSourceInventoryMana(hopper);
		//TileEntityEssecenceEnhancer enchancer = (TileEntityEssecenceEnhancer) iinventory;
		if (iinventory != null) {
			EnumFacing enumfacing = EnumFacing.DOWN;
			if (isInventoryEmptyMana(iinventory, enumfacing)) {
			}

			if (iinventory instanceof TileManaSimpleInventory) {
				// if(enchancer.hasValidRecipe()==true){
				//System.out.println(enchancer.getCurrentRecipe().getMana());

				// This condition is eventuallt to check if the recipie is craftable and then
				// pulls out the result after crafing it
				// if(enchancer.getManaValue() >= enchancer.getCurrentRecipe().getMana()) {
				ModInventoryHelper.withdrawFromInventoryToInventory(iinventory,
						getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, enumfacing));
				iinventory.sendUpdates();
				// }
				if (pullItemFromSlot(hopper, iinventory, 0, enumfacing)) {

					return true;

				} else {
					int j = iinventory.getSizeInventory();
					for (int k = 0; k < j; ++k) {
						if (pullItemFromSlot(hopper, iinventory, k, enumfacing)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Can this hopper extract the specified item from the specified slot on the
	 * specified side?
	 */
	private static boolean canExtractItemFromSlotChest(IInventory inventoryIn, ItemStack stack, int index,
			EnumFacing side) {
		return !(inventoryIn instanceof ISidedInventory)
				|| ((ISidedInventory) inventoryIn).canExtractItem(index, stack, side);
	}

	/**
	 * Pulls from the specified slot in the inventory and places in any available
	 * slot in the hopper. Returns true if the entire stack was moved
	 */
	private static boolean pullItemFromSlot(IInventory hopper, TileManaSimpleInventory extractie, int index,
			EnumFacing direction) {
		ItemStack itemstack = extractie.getItemHandler().getStackInSlot(index);

		if (!itemstack.isEmpty()) {

			ItemStack itemstack1 = itemstack.copy();
			ItemStack itemstack2 = putStackInInventoryAllSlots(extractie, hopper,
					extractie.getItemHandler().getStackInSlot(index), (EnumFacing) null);

			if (itemstack2.isEmpty()) {
				extractie.markDirty();
				return true;
			}

			extractie.getItemHandler().setStackInSlot(0, itemstack1);
		}
		return false;
	}

	/**
	 * Pulls from the specified slot in the inventory and places in any available
	 * slot in the hopper. Returns true if the entire stack was moved
	 */
	private static boolean pullItemFromSlotChest(IHopper hopper, IInventory inventoryIn, int index,
			EnumFacing direction) {
		ItemStack itemstack = inventoryIn.getStackInSlot(index);

		if (!itemstack.isEmpty() && canExtractItemFromSlotChest(inventoryIn, itemstack, index, direction)) {
			ItemStack itemstack1 = itemstack.copy();
			ItemStack itemstack2 = putStackInInventoryAllSlotsChest(inventoryIn, hopper,
					inventoryIn.decrStackSize(index, 1), (EnumFacing) null);

			if (itemstack2.isEmpty()) {
				inventoryIn.markDirty();
				return true;
			}

			inventoryIn.setInventorySlotContents(index, itemstack1);
		}

		return false;
	}

	/**
	 * Pull dropped {@link net.minecraft.entity.item.EntityItem EntityItem}s from
	 * the world above the hopper and items from any inventory attached to this
	 * hopper into the hopper's inventory.
	 * 
	 * @param hopper the hopper in question
	 * @return whether any items were successfully added to the hopper
	 */
	public static boolean pullItemsChest(IHopper hopper) {
		Boolean ret = ManaHopperCodeHooks.extractHook(hopper);
		if (ret != null)
			return ret;
		IInventory iinventory = getSourceInventoryChest(hopper);

		if (iinventory != null) {
			EnumFacing enumfacing = EnumFacing.DOWN;

			if (isInventoryEmptyChest(iinventory, enumfacing)) {
				return false;
			}

			if (iinventory instanceof ISidedInventory) {
				ISidedInventory isidedinventory = (ISidedInventory) iinventory;
				int[] aint = isidedinventory.getSlotsForFace(enumfacing);

				for (int i : aint) {
					if (pullItemFromSlotChest(hopper, iinventory, i, enumfacing)) {
						return true;
					}
				}
			} else {
				int j = iinventory.getSizeInventory();

				for (int k = 0; k < j; ++k) {
					if (pullItemFromSlotChest(hopper, iinventory, k, enumfacing)) {
						return true;
					}
				}
			}
		} else {
			for (EntityItem entityitem : getCaptureItems(hopper.getWorld(), hopper.getXPos(), hopper.getYPos(),
					hopper.getZPos())) {
				if (putDropInInventoryAllSlotsChest((IInventory) null, hopper, entityitem)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Attempts to place the passed EntityItem's stack into the inventory using as
	 * many slots as possible. Returns false if the stackSize of the drop was not
	 * depleted.
	 */
	public static boolean putDropInInventoryAllSlotsChest(IInventory source, IInventory destination,
			EntityItem entity) {
		boolean flag = false;

		if (entity == null) {
			return false;
		} else {
			ItemStack itemstack = entity.getItem().copy();
			ItemStack itemstack1 = putStackInInventoryAllSlotsChest(source, destination, itemstack, (EnumFacing) null);

			if (itemstack1.isEmpty()) {
				flag = true;
				entity.setDead();
			} else {
				entity.setItem(itemstack1);
			}

			return flag;
		}
	}

	public static List<EntityItem> getCaptureItems(World worldIn, double p_184292_1_, double p_184292_3_,
			double p_184292_5_) {
		return worldIn.<EntityItem>getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(p_184292_1_ - 0.5D,
				p_184292_3_, p_184292_5_ - 0.5D, p_184292_1_ + 0.5D, p_184292_3_ + 1.5D, p_184292_5_ + 0.5D),
				EntitySelectors.IS_ALIVE);
	}

	protected boolean updateHopper() {
		if (this.world != null && !this.world.isRemote) {
			if (!this.isOnTransferCooldown() && BlockManaHopper.isEnabled(this.getBlockMetadata())) {
				boolean flag = false;
				boolean flag1 = false;

				if (!this.isInventoryEmpty()) {
					flag = this.transferItemsOut();
					flag1 = this.transferItemsOut();
				}

				if (!this.isFull()) {
					flag = pullItemsChest(this) || flag;
					flag1 = pullItems(this) || flag1;
				}

				if (flag || flag1) {
					this.setTransferCooldown(8);
					this.markDirty();
					return true;
				}
			}

			return false;
		} else {
			return false;
		}
	}

	private boolean isInventoryEmpty() {
		for (ItemStack itemstack : this.inventory) {
			if (!itemstack.isEmpty()) {

				return false;
			}
		}

		return true;
	}

	public boolean isEmpty() {
		return this.isInventoryEmpty();
	}

	private boolean isFull() {
		for (ItemStack itemstack : this.inventory) {
			if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize()) {
				return false;
			}
		}

		return true;
	}

	private boolean transferItemsOut() {
		if (ManaHopperCodeHooks.insertHook(this)) {
			return true;
		}
		TileManaSimpleInventory iinventory = this.getInventoryForHopperTransfer();

		if (iinventory == null) {
			return false;
		} else {
			EnumFacing enumfacing = BlockManaHopper.getFacing(this.getBlockMetadata()).getOpposite();

			if (this.isInventoryFull(iinventory, enumfacing)) {
				return false;
			} else {

				for (int i = 0; i < this.getSizeInventory(); ++i) {

					if (!this.getStackInSlot(i).isEmpty()) {

						ItemStack itemstack = this.getStackInSlot(i).copy();
						ItemStack itemstack1 = putStackInInventoryAllSlots(iinventory, this, this.decrStackSize(i, 1),
								enumfacing);

						if (itemstack1.isEmpty()) {
							iinventory.markDirty();
							return true;
						}

						this.setInventorySlotContents(i, itemstack);
					}
				}

				return false;
			}
		}
	}

	/**
	 * Returns false if the inventory has any room to place items in
	 */
	private boolean isInventoryFull(TileManaSimpleInventory inventoryIn, EnumFacing side) {
		if (inventoryIn instanceof TileManaSimpleInventory) {
			/*
			 * TileManaSimpleInventory isidedinventory = (TileManaSimpleInventory)
			 * inventoryIn; int[] aint = isidedinventory.getSlotsForFace(side);
			 */

			int i = inventoryIn.getSizeInventory();

			for (int j = 0; j < i; ++j) {
				ItemStack itemstack = inventoryIn.getItemHandler().getStackInSlot(j);

				if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns false if the specified IInventory contains any items
	 */
	private static boolean isInventoryEmptyMana(TileManaSimpleInventory inventoryIn, EnumFacing side) {
		if (inventoryIn instanceof TileManaSimpleInventory) {
			int aint = inventoryIn.getItemHandler().getSlots();

			for (int i = 0; i < aint; i++) {
				if (!inventoryIn.getItemHandler().getStackInSlot(i).isEmpty()) {
					return false;
				}
			}
		} else {
			int j = inventoryIn.getSizeInventory();

			for (int k = 0; k < j; ++k) {
				if (!inventoryIn.getItemHandler().getStackInSlot(k).isEmpty()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns false if the specified IInventory contains any items
	 */
	private static boolean isInventoryEmptyChest(IInventory inventoryIn, EnumFacing side) {
		if (inventoryIn instanceof ISidedInventory) {
			ISidedInventory isidedinventory = (ISidedInventory) inventoryIn;
			int[] aint = isidedinventory.getSlotsForFace(side);

			for (int i : aint) {
				if (!isidedinventory.getStackInSlot(i).isEmpty()) {
					return false;
				}
			}
		} else {
			int j = inventoryIn.getSizeInventory();

			for (int k = 0; k < j; ++k) {
				if (!inventoryIn.getStackInSlot(k).isEmpty()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Attempts to place the passed EntityItem's stack into the inventory using as
	 * many slots as possible. Returns false if the stackSize of the drop was not
	 * depleted.
	 */
	public static boolean putDropInInventoryAllSlots(IInventory source, TileManaSimpleInventory destination,
			EntityItem entity) {
		boolean flag = false;

		if (entity == null) {
			return false;
		} else {
			ItemStack itemstack = entity.getItem().copy();
			ItemStack itemstack1 = putStackInInventoryAllSlots(destination, source, itemstack, (EnumFacing) null);

			if (itemstack1.isEmpty()) {
				flag = true;
				entity.setDead();
			} else {
				entity.setItem(itemstack1);
			}

			return flag;
		}
	}

	protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
		return new ManaHopperItemHandler(this);
	}

	/**
	 * Attempts to place the passed stack in the inventory, using as many slots as
	 * required. Returns leftover items
	 */
	public static ItemStack putStackInInventoryAllSlots(TileManaSimpleInventory destination, IInventory source,
			ItemStack stack, @Nullable EnumFacing direction) {

		if (destination instanceof TileManaSimpleInventory) {
			int i = destination.getItemHandler().getSlots();

			for (int j = 0; j < i && !stack.isEmpty(); ++j) {
				stack = insertStack(source, destination, stack, j, direction);
				destination.addItem(null, stack, null);

			}
		}
		destination.sendUpdates();
		destination.markDirty();
		return stack;
	}

	/**
	 * Can this hopper insert the specified item from the specified slot on the
	 * specified side?
	 */
	private static boolean canInsertItemInSlotChest(IInventory inventoryIn, ItemStack stack, int index,
			EnumFacing side) {
		if (!inventoryIn.isItemValidForSlot(index, stack)) {
			return false;
		} else {
			return !(inventoryIn instanceof ISidedInventory)
					|| ((ISidedInventory) inventoryIn).canInsertItem(index, stack, side);
		}
	}

//	/**
//	 * Can this hopper insert the specified item from the specified slot on the
//	 * specified side?
//	 */
	private static boolean canInsertItemInSlot(TileManaSimpleInventory inventoryIn, ItemStack stack, int index,
			EnumFacing side) {
		if (!inventoryIn.getItemHandler().isItemValid(index, stack)) {
			return false;
		} else {
			return !(inventoryIn instanceof TileManaSimpleInventory)
					|| ((TileManaSimpleInventory) inventoryIn).getItemHandler().isItemValid(index, stack);
		}
	}
	/*
		*//**
			 * Can this hopper extract the specified item from the specified slot on the
			 * specified side?
			 *//*
				 * private static boolean canExtractItemFromSlot(TileManaSimpleInventory
				 * inventoryIn, ItemStack stack, int index, EnumFacing side) { return
				 * !(inventoryIn instanceof TileManaSimpleInventory) || ((ISidedInventory)
				 * inventoryIn).canExtractItem(index, stack, side); }
				 */

	/**
	 * Insert the specified stack to the specified inventory and return any leftover
	 * items
	 */
	private static ItemStack insertStack(IInventory source, TileManaSimpleInventory destination, ItemStack stack,
			int index, EnumFacing direction) {
		ItemStack itemstack = destination.getItemHandler().getStackInSlot(index);

		// if (canInsertItemInSlot(destination, stack, index, direction)) {
		boolean flag = false;
		if (itemstack.isEmpty()) {
			destination.getItemHandler().setStackInSlot(index, itemstack);
			;
			destination.addItem(null, stack, null);

			stack = ItemStack.EMPTY;
			flag = true;
		} else if (canCombine(itemstack, stack)) {
			int i = stack.getMaxStackSize() - itemstack.getCount();
			int j = Math.min(stack.getCount(), i);
			stack.shrink(j);
			itemstack.grow(j);
			flag = j > 0;
		}
		// }

		return stack;
	}

	/**
	 * Returns the IInventory that this hopper is pointing into
	 */
	private TileManaSimpleInventory getInventoryForHopperTransfer() {
		EnumFacing enumfacing = BlockManaHopper.getFacing(this.getBlockMetadata());
		return getInventoryAtPosition(this.getWorld(), this.getXPos() + (double) enumfacing.getFrontOffsetX(),
				this.getYPos() + (double) enumfacing.getFrontOffsetY(),
				this.getZPos() + (double) enumfacing.getFrontOffsetZ());
	}

	/**
	 * Gets the inventory that the provided hopper will transfer items from.
	 */
	public static TileManaSimpleInventory getSourceInventoryMana(IHopper hopper) {
		return getInventoryAtPosition(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos());
	}

	public static TileManaSimpleInventory getInventoryAtPosition(World worldIn, double x, double y, double z) {
		TileManaSimpleInventory iinventory = null;
		int i = MathHelper.floor(x);
		int j = MathHelper.floor(y);
		int k = MathHelper.floor(z);
		BlockPos blockpos = new BlockPos(i, j, k);
		net.minecraft.block.state.IBlockState state = worldIn.getBlockState(blockpos);
		Block block = state.getBlock();

		if (block.hasTileEntity(state)) {
			TileEntity tileentity = worldIn.getTileEntity(blockpos);

			if (tileentity instanceof TileManaSimpleInventory) {
				iinventory = (TileManaSimpleInventory) tileentity;

			}
		}
//
//		if (iinventory == null) {
//			List<Entity> list = worldIn.getEntitiesInAABBexcluding((Entity) null,
//					new AxisAlignedBB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D),
//					EntitySelectors.HAS_INVENTORY);
//
//			if (!list.isEmpty()) {
//				iinventory = (TileManaSimpleInventory) list.get(worldIn.rand.nextInt(list.size()));
//			}
//		}

		return iinventory;
	}

	private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
		if (stack1.getItem() != stack2.getItem()) {
			return false;
		} else if (stack1.getMetadata() != stack2.getMetadata()) {
			return false;
		} else if (stack1.getCount() > stack1.getMaxStackSize()) {
			return false;
		} else {
			return ItemStack.areItemStackTagsEqual(stack1, stack2);
		}
	}

	/**
	 * Gets the world X position for this hopper entity.
	 */
	public double getXPos() {
		return (double) this.pos.getX() + 0.5D;
	}

	/**
	 * Gets the world Y position for this hopper entity.
	 */
	public double getYPos() {
		return (double) this.pos.getY() + 0.5D;
	}

	/**
	 * Gets the world Z position for this hopper entity.
	 */
	public double getZPos() {
		return (double) this.pos.getZ() + 0.5D;
	}

	public void setTransferCooldown(int ticks) {
		this.transferCooldown = ticks;
	}

	private boolean isOnTransferCooldown() {
		return this.transferCooldown > 0;
	}

	public boolean mayTransfer() {
		return this.transferCooldown > 8;
	}

	public String getGuiID() {
		return "minecraft:hopper";
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		this.fillWithLoot(playerIn);
		return new ContainerHopper(playerInventory, this, playerIn);
	}

	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	public long getLastUpdateTime() {
		return tickedGameTime;
	} // Forge
}