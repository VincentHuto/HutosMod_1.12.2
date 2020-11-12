package com.huto.hutosmod.mindrunes.container;

import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.mindrunes.cap.RuneCapabilities;
import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerPlayerExpanded extends Container {
	/**
	 * The crafting matrix inventory.
	 */
	public final InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
	public final InventoryCraftResult craftResult = new InventoryCraftResult();
	public IRunesItemHandler runes;
	/**
	 * Determines if inventory manipulation should be handled.
	 */
	public boolean isLocalWorld;
	private final EntityPlayer thePlayer;
	private static final EntityEquipmentSlot[] equipmentSlots = new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD,
			EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };

	public ContainerPlayerExpanded(InventoryPlayer playerInv, boolean par2, EntityPlayer player) {
		this.isLocalWorld = par2;
		this.thePlayer = player;
		runes = player.getCapability(RuneCapabilities.CAPABILITY_RUNES, null);

		this.addSlotToContainer(new SlotCrafting(playerInv.player, this.craftMatrix, this.craftResult, 0, 154, 28+26));

		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 2; ++j) {
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 116 + j * 18-18, 18 + i * 18+26));
			}
		}

		for (int k = 0; k < 4; k++) {
			final EntityEquipmentSlot slot = equipmentSlots[k];
			this.addSlotToContainer(new Slot(playerInv, 36 + (3 - k), 8, 8 + k * 18) {
				@Override
				public int getSlotStackLimit() {
					return 1;
				}

				@Override
				public boolean isItemValid(ItemStack stack) {
					return stack.getItem().isValidArmor(stack, slot, player);
				}

				@Override
				public boolean canTakeStack(EntityPlayer playerIn) {
					ItemStack itemstack = this.getStack();
					return !itemstack.isEmpty() && !playerIn.isCreative()
							&& EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
				}

				@Override
				public String getSlotTexture() {
					return ItemArmor.EMPTY_SLOT_NAMES[slot.getIndex()];
				}
			});
		}

		this.addSlotToContainer(new SlotRune(player, runes, 0, 78, 8));
		this.addSlotToContainer(new SlotRune(player, runes, 1, 78 + 1 * 18, 8));
		this.addSlotToContainer(new SlotRune(player, runes, 2, 78 + 2 * 18, 8));
		this.addSlotToContainer(new SlotRune(player, runes, 3, 78 + 3 * 18, 8));
		/*
		 * this.addSlotToContainer(new SlotBauble(player,runes,4,96,8 ));
		 * this.addSlotToContainer(new SlotBauble(player,runes,5,96,8 + 1 * 18));
		 * this.addSlotToContainer(new SlotBauble(player,runes,6,96,8 + 2 * 18));
		 */

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInv, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
		}

		this.addSlotToContainer(new Slot(playerInv, 40, 96-19, 62) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return super.isItemValid(stack);
			}

			@Override
			public String getSlotTexture() {
				return "minecraft:items/empty_armor_slot_shield";
			}
		});

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory) {
		this.slotChangedCraftingGrid(this.thePlayer.getEntityWorld(), this.thePlayer, this.craftMatrix,
				this.craftResult);
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.craftResult.clear();

		if (!player.world.isRemote) {
			this.clearContainer(player, player.world, this.craftMatrix);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return true;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you
	 * will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);

			int slotShift = runes.getSlots();

			if (index == 0) {
				if (!this.mergeItemStack(itemstack1, 9 + slotShift, 45 + slotShift, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index >= 1 && index < 5) {
				if (!this.mergeItemStack(itemstack1, 9 + slotShift, 45 + slotShift, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 5 && index < 9) {
				if (!this.mergeItemStack(itemstack1, 9 + slotShift, 45 + slotShift, false)) {
					return ItemStack.EMPTY;
				}
			}

			// runes -> inv
			else if (index >= 9 && index < 9 + slotShift) {
				if (!this.mergeItemStack(itemstack1, 9 + slotShift, 45 + slotShift, false)) {
					return ItemStack.EMPTY;
				}
			}

			// inv -> armor
			else if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR
					&& !((Slot) this.inventorySlots.get(8 - entityequipmentslot.getIndex())).getHasStack()) {
				int i = 8 - entityequipmentslot.getIndex();

				if (!this.mergeItemStack(itemstack1, i, i + 1, false)) {
					return ItemStack.EMPTY;
				}
			}

			// inv -> offhand
			else if (entityequipmentslot == EntityEquipmentSlot.OFFHAND
					&& !((Slot) this.inventorySlots.get(45 + slotShift)).getHasStack()) {
				if (!this.mergeItemStack(itemstack1, 45 + slotShift, 46 + slotShift, false)) {
					return ItemStack.EMPTY;
				}
			}
			// inv -> bauble
			else if (itemstack.hasCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null)) {
				IRune rune = itemstack.getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null);
				for (int runeSlot : rune.getRuneType(itemstack).getValidSlots()) {
					if (rune.canEquip(itemstack1, thePlayer)
							&& !((Slot) this.inventorySlots.get(runeSlot + 9)).getHasStack()
							&& !this.mergeItemStack(itemstack1, runeSlot + 9, runeSlot + 10, false)) {
						return ItemStack.EMPTY;
					}
					if (itemstack1.getCount() == 0)
						break;
				}
			} else if (index >= 9 + slotShift && index < 36 + slotShift) {
				if (!this.mergeItemStack(itemstack1, 36 + slotShift, 45 + slotShift, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 36 + slotShift && index < 45 + slotShift) {
				if (!this.mergeItemStack(itemstack1, 9 + slotShift, 36 + slotShift, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 9 + slotShift, 45 + slotShift, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty() && !runes.isEventBlocked() && slot instanceof SlotRune
					&& itemstack.hasCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null)) {
				itemstack.getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null).onUnequipped(itemstack,
						playerIn);
			}

			ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

			if (index == 0) {
				playerIn.dropItem(itemstack2, false);
			}
		}

		return itemstack;
	}

	// private void unequipBauble(ItemStack stack) { }

	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.craftResult && super.canMergeSlot(stack, slot);
	}
}
