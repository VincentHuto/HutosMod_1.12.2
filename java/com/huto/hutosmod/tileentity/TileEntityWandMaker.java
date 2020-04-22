package com.huto.hutosmod.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModWandRecipies;
import com.huto.hutosmod.recipies.RecipeWandMaker;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileEntityWandMaker extends TileManaSimpleInventory implements ITickable {
	int cooldown = 0;
	List<ItemStack> lastRecipe = null;
	RecipeWandMaker currentRecipe;
	int recipeKeepTicks = 0;
	private static final int SET_KEEP_TICKS_EVENT = 0;
	private static final int SET_COOLDOWN_EVENT = 1;
	private static final int CRAFT_EFFECT_EVENT = 2;

	@Override
	public boolean addItem(@Nullable EntityPlayer player, ItemStack stack, @Nullable EnumHand hand) {
		if (cooldown > 0 || stack.getItem() == ItemRegistry.maker_activator)
			return false;

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
		return 4;
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

	@Override
	public void update() {
		if (!world.isRemote) {
			if (cooldown > 0) {
				cooldown--;
			}

		}

	}

	public static void tryToSetLastRecipe(EntityPlayer player, IItemHandlerModifiable inv, List<ItemStack> lastRecipe) {
		if (lastRecipe == null || lastRecipe.isEmpty() || player.world.isRemote)
			return;

		int index = 0;
		boolean didAny = false;
		for (ItemStack stack : lastRecipe) {
			if (stack.isEmpty())
				continue;

			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack pstack = player.inventory.getStackInSlot(i);
				if (!pstack.isEmpty() && pstack.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(stack, pstack)) {
					inv.setStackInSlot(index, pstack.splitStack(1));
					didAny = true;
					index++;
					break;
				}
			}
		}

		if (didAny) {
			player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_SPLASH,
					SoundCategory.BLOCKS, 0.1F, 10F);
			EntityPlayerMP mp = (EntityPlayerMP) player;
			mp.inventoryContainer.detectAndSendChanges();
		}
	}

	public void trySetLastRecipe(EntityPlayer player) {
		tryToSetLastRecipe(player, itemHandler, lastRecipe);
		if (!isEmpty())
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);
	}

	public void updateRecipe() {
		for (RecipeWandMaker recipe : ModWandRecipies.wandMakerRecipies)
			if (recipe.matches(itemHandler)) {
				ItemStack output = recipe.getOutput().copy();
				EntityItem outputItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5,
						output);
				world.spawnEntity(outputItem);
			}
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, pos);
	}

	public void saveLastRecipe() {
		lastRecipe = new ArrayList<>();
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = itemHandler.getStackInSlot(i);
			if (stack.isEmpty())
				break;
			lastRecipe.add(stack.copy());
		}
		recipeKeepTicks = 400;
		world.addBlockEvent(getPos(), BlockRegistry.wand_maker, SET_KEEP_TICKS_EVENT, 400);
	}

	public boolean hasValidRecipe() {
		for (RecipeWandMaker recipe : ModWandRecipies.wandMakerRecipies)
			if (recipe.matches(itemHandler))
				return true;

		return false;
	}

	@Override
	public boolean receiveClientEvent(int id, int param) {
		switch (id) {
		case SET_KEEP_TICKS_EVENT:
			recipeKeepTicks = param;
			return true;
		case SET_COOLDOWN_EVENT:
			cooldown = param;
			return true;
		case CRAFT_EFFECT_EVENT: {

			world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.AMBIENT_CAVE, SoundCategory.BLOCKS, 1, 1,
					false);
			return true;

		}
		default:
			return super.receiveClientEvent(id, param);

		}
	}

	public void onWanded(EntityPlayer player, ItemStack wand) {
		if (world.isRemote)
			return;

		RecipeWandMaker recipe = null;
		if (currentRecipe != null)
			recipe = currentRecipe;
		else
			for (RecipeWandMaker recipe_ : ModWandRecipies.wandMakerRecipies) {
				if (recipe_.matches(itemHandler)) {
					recipe = recipe_;
					break;
				}
			}
		if (recipe != null && manaValue >= recipe.getMana()) {
			ItemStack output = recipe.getOutput().copy();
			EntityItem outputItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, output);
			world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX(), pos.getY(), pos.getZ(), 0.0D, 0.0D, 0.0D);
			world.spawnEntity(outputItem);
			manaValue -= recipe.getMana();
			currentRecipe = null;
			world.addBlockEvent(getPos(), BlockRegistry.wand_maker, SET_COOLDOWN_EVENT, 60);
			world.addBlockEvent(getPos(), BlockRegistry.wand_maker, CRAFT_EFFECT_EVENT, 0);

			saveLastRecipe();
			for (int i = 0; i < getSizeInventory(); i++) {
				ItemStack stack = itemHandler.getStackInSlot(i);
				if (!stack.isEmpty()) {
				}
				this.sendUpdates();
				itemHandler.setStackInSlot(i, ItemStack.EMPTY);
			}
		}
	}

	private IBlockState getState() {
		return world.getBlockState(pos);
	}

	private void setBlockToUpdate() {
		sendUpdates();
	}

	private void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		markDirty();
	}

}
