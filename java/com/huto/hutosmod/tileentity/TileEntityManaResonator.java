package com.huto.hutosmod.tileentity;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.particles.ManaParticle;
import com.huto.hutosmod.recipies.EnumEssecenceType;
import com.huto.hutosmod.recipies.ModEnhancerRecipies;
import com.huto.hutosmod.recipies.ModResonatorRecipies;
import com.huto.hutosmod.recipies.ModWandRecipies;
import com.huto.hutosmod.recipies.RecipeEnhancer;
import com.huto.hutosmod.recipies.RecipeResonator;
import com.huto.hutosmod.recipies.RecipeWandMaker;
import com.huto.hutosmod.tileentity.TileManaSimpleInventory.SimpleItemStackHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import scala.reflect.internal.Trees.This;

public class TileEntityManaResonator extends TileManaSimpleInventory implements ITickable {
	public static final String TAG_MANA = "mana";
	int cooldown = 0;
	RecipeResonator currentRecipe;
	int recipeKeepTicks = 0;
	private static final int SET_KEEP_TICKS_EVENT = 0;
	private static final int SET_COOLDOWN_EVENT = 1;
	private static final int CRAFT_EFFECT_EVENT = 2;
	public int count = 0;

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public void onLoad() {
		super.onLoad();
		this.setMaxMana(200);
	}

	public RecipeResonator getCurrentRecipe() {
		for (RecipeResonator recipe_ : ModResonatorRecipies.resonatorRecipies) {
			if (recipe_.matches(itemHandler)) {
				currentRecipe = recipe_;
			}
		}

		return currentRecipe;
	}

	public void setCurrentRecipe(RecipeResonator currentRecipe) {
		this.currentRecipe = currentRecipe;
	}

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
	public void readPacketNBT(NBTTagCompound tag) {
		itemHandler = createItemHandler();
		itemHandler.deserializeNBT(tag);
		manaValue = tag.getFloat(TAG_MANA);

	}

	@Override
	public void writePacketNBT(NBTTagCompound tag) {
		tag.merge(itemHandler.serializeNBT());
		tag.setFloat(TAG_MANA, manaValue);

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
		if (this.hasValidRecipe()) {
			for (RecipeResonator recipe :ModResonatorRecipies.resonatorRecipies) {
				if (recipe.matches(itemHandler) && this.getManaValue() > recipe.getMana()) {
					Random rand = new Random();
					count++;
					int mod = 3 + rand.nextInt(10);
		
				}
			}
		}

	}

	public boolean hasValidRecipe() {
		for (RecipeResonator recipe : ModResonatorRecipies.resonatorRecipies)
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

		RecipeResonator recipe = null;
		if (currentRecipe != null)
			recipe = currentRecipe;
		else
			for (RecipeResonator recipe_ : ModResonatorRecipies.resonatorRecipies) {
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
			world.addBlockEvent(getPos(), BlockRegistry.essecence_enhancer, SET_COOLDOWN_EVENT, 60);
			world.addBlockEvent(getPos(), BlockRegistry.essecence_enhancer, CRAFT_EFFECT_EVENT, 0);

			for (int i = 0; i < getSizeInventory(); i++) {
				ItemStack stack = itemHandler.getStackInSlot(i);
				if (!stack.isEmpty()) {
				}
				this.sendUpdates();
				itemHandler.setStackInSlot(i, ItemStack.EMPTY);
			}
		}
	}
}
