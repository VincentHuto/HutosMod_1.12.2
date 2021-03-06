package com.huto.hutosmod.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.particles.ManaParticle;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.recipies.ModFuserRecipes;
import com.huto.hutosmod.recipies.RecipeManaFuser;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileEntityManaFuser extends TileManaSimpleInventory implements ITickable {
	int cooldown = 0;
	List<ItemStack> lastRecipe = null;
	RecipeManaFuser currentRecipe;
	int recipeKeepTicks = 0;
	private static final int SET_KEEP_TICKS_EVENT = 0;
	private static final int SET_COOLDOWN_EVENT = 1;
	private static final int CRAFT_EFFECT_EVENT = 2;
	public int count = 0;

	@Override
	public void onLoad() {
		super.onLoad();
		this.setMaxMana(300);
	}

	public RecipeManaFuser getCurrentRecipe() {
		for (RecipeManaFuser recipe_ : ModFuserRecipes.manaFuserRecipies) {
			if (recipe_.matches(itemHandler)) {
				currentRecipe = recipe_;
			}
		}

		return currentRecipe;
	}

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
		if (this.hasValidRecipe()) {
			for (RecipeManaFuser recipe : ModFuserRecipes.manaFuserRecipies) {
				if (recipe.matches(itemHandler) && this.getManaValue() > recipe.getMana()) {
					Random rand = new Random();
					count++;
					int mod = 3 + rand.nextInt(10);
					if (count % mod == 0) {
						double ypos = pos.getY() + 1.2;
						Vector3 vec = Vector3.fromTileEntityCenter(this).add(0, -0.2, 0);
						Vector3 endVec = vec.add(0, 0.5, 0);
						Vector3 redVec = Vector3.fromTileEntityCenter(this).add(0, -0.2, 0);
						Vector3 blueVec = Vector3.fromTileEntityCenter(this).add(0, -0.2, 0);
						double velocityX = 0, velocityY = 0.05, velocityZ = 0;
						double redValue = 0;
						double blueValue = 0;

						if (world.isRemote) {
							ManaParticle newEffect = new ManaParticle(world, pos.getX() + 0.1, ypos, pos.getZ() + 0.9,
									velocityX, velocityY, velocityZ, 1.0F, 0.0F, 1.0F, 13, 4);
							ManaParticle newEffect1 = new ManaParticle(world, pos.getX() + 0.1, ypos, pos.getZ() + 0.1,
									velocityX, velocityY, velocityZ, 1.0F, 0.0F, 1.0F, 13, 4);
							ManaParticle newEffect2 = new ManaParticle(world, pos.getX() + 0.9, ypos, pos.getZ() + 0.1,
									velocityX, velocityY, velocityZ, 1.0F, 0.0F, 1.0F, 13, 4);
							ManaParticle newEffect3 = new ManaParticle(world, pos.getX() + 0.9, ypos, pos.getZ() + 0.9,
									velocityX, velocityY, velocityZ, 1.0F, 0.0F, 1.0F, 13, 4);
							Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
							Minecraft.getMinecraft().effectRenderer.addEffect(newEffect1);
							Minecraft.getMinecraft().effectRenderer.addEffect(newEffect2);
							Minecraft.getMinecraft().effectRenderer.addEffect(newEffect3);
						}
						count = 0;

						if (world.isRemote) {
							if (count % 3 == 0) {
								MainClass.proxy.lightningFX(redVec, endVec, 15F, System.nanoTime(), Reference.red,
										Reference.red);
								MainClass.proxy.lightningFX(endVec, blueVec, 15F, System.nanoTime(), Reference.blue,
										Reference.blue);
							}
						}
					}
				}
			}
		}

	}

	public void updateRecipe() {
		for (RecipeManaFuser recipe : ModFuserRecipes.manaFuserRecipies)
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
		world.addBlockEvent(getPos(), BlockRegistry.mana_fuser, SET_KEEP_TICKS_EVENT, 400);
	}

	public boolean hasValidRecipe() {
		for (RecipeManaFuser recipe : ModFuserRecipes.manaFuserRecipies)
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

		RecipeManaFuser recipe = null;
		if (currentRecipe != null)
			recipe = currentRecipe;
		else
			for (RecipeManaFuser recipe_ : ModFuserRecipes.manaFuserRecipies) {
				if (recipe_.matches(itemHandler)) {
					recipe = recipe_;
					break;
				}
			}
		if (recipe != null && manaValue >= recipe.getMana()) {
			ItemStack output = recipe.getOutput().copy();
			EntityItem outputItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, output);
			if (world.isRemote) {
				world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX(), pos.getY(), pos.getZ(), 0.0D, 0.0D, 0.0D);
			}
			world.spawnEntity(outputItem);
			manaValue -= recipe.getMana();
			currentRecipe = null;
			world.addBlockEvent(getPos(), BlockRegistry.mana_fuser, SET_COOLDOWN_EVENT, 60);
			world.addBlockEvent(getPos(), BlockRegistry.mana_fuser, CRAFT_EFFECT_EVENT, 0);

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
