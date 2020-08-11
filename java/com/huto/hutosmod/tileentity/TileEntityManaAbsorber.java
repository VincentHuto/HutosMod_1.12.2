package com.huto.hutosmod.tileentity;

import java.sql.Ref;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.biomes.BiomeRegistry;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketGetManaLimit;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.particles.ManaParticle;
import com.huto.hutosmod.particles.RingParticle;
import com.huto.hutosmod.particles.SphereParticle;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntitySimpleInventory.SimpleItemStackHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class TileEntityManaAbsorber extends TileManaSimpleInventory implements ITickable {
	public static int count = 0;
	public static final String TAG_MANA = "mana";
	public static final String TAG_RANK = "rank";
	public int rank;
	public float rate = 0.2f;
	public int maxMana = 1000;

	@Override
	public void onLoad() {
		super.onLoad();
		this.updateRate();

	}

	public void addRankUp(float rankUp) {
		this.rank += rankUp;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getRate() {
		return rate;
	}

	public float updateRate() {
		if (rank == 0) {
			return this.rate = .2f;
		}
		if (rank == 1) {
			return this.rate = .4f;
		}
		if (rank == 2) {
			return this.rate = .8f;
		}
		if (rank == 3) {
			return this.rate = 1.6f;
		}
		if (rank == 4) {
			return this.rate = 4f;
		}
		return rate;
	}

	@Override
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
	public int getSizeInventory() {
		return 1;
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
		updateRate();

		count++;

		if (count > 100) {
			count = 0;
		}

		Random rand = new Random();
		// Ease of use variables
		int x = this.pos.getX();
		int y = this.pos.getY();
		int z = this.pos.getZ();
		Vector3 loc = Vector3.fromTileEntityCenter(this);

		// Search radius for blocks and tiles
		Iterable<BlockPos> radiusPositions = BlockPos.getAllInBox(this.pos.add(3.0f, 3.0f, 3.0f),
				this.pos.add(-3.0f, -3.0f, -3.0f)); // 3x3x3 range

		// Checks block positions in a 3x3x3
		for (BlockPos pos : radiusPositions) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile != null && this.getPos() != pos) {
				// Self Actuation
				if ((tile instanceof TileEntityManaAbsorber)) {
					if (!(this.getPos().getX() == pos.getX() && this.getPos().getY() == pos.getY()
							&& this.getPos().getY() == pos.getY())) {
						TileEntityManaAbsorber otherAbsorber = (TileEntityManaAbsorber) tile;
						if (this.manaValue >= otherAbsorber.getManaValue() && otherAbsorber.getManaValue() < maxMana) {
							this.setManaValue(manaValue - this.rate);
							otherAbsorber.addManaValue(this.rate);
							// Particle Stuff
							double xpos = pos.getX() + 0.5, ypos = pos.getY() + 1, zpos = pos.getZ() + 0.5;
							double velocityX, velocityY, velocityZ;
							Vec3d manaDirection = new Vec3d(otherAbsorber.getPos().getX() - this.pos.getX(),
									otherAbsorber.getPos().getY() - this.pos.getY(),
									otherAbsorber.getPos().getZ() - this.pos.getZ());
							velocityX = -manaDirection.x * 0.1;
							velocityY = -manaDirection.y * 0.1;
							velocityZ = -manaDirection.z * 0.1;
							if (count % 2 == 0) {
								SphereParticle effect = new SphereParticle(getWorld(), xpos, ypos, zpos, velocityX,
										velocityY, velocityZ, 1, 1, 1, 10, .1f);

								MainClass.proxy.spawnEffect(effect);

							}
							if (count % 3 == 0) {
								SphereParticle effect1 = new SphereParticle(getWorld(), xpos, ypos, zpos, velocityX,
										velocityY, velocityZ, 0, 0, 0, 10, .1f);
								MainClass.proxy.spawnEffect(effect1);
							}
						}
					}
				}

				// Functional
				if (tile instanceof TileEntityWaveGatherer || tile instanceof TileEntityManaGatherer) {
					TileModMana manaStor = (TileModMana) tile;

					if (manaStor.getManaValue() > this.rate && this.getManaValue() < maxMana) {
						this.addManaValue(this.rate);
						manaStor.setManaValue(manaStor.manaValue - this.rate);
						Vector3 exportLoc = Vector3.fromTileEntityCenter(manaStor);
						// Particle Stuff
						double xpos = pos.getX() + 0.5, ypos = pos.getY() + 1, zpos = pos.getZ() + 0.5;
						double velocityX, velocityY, velocityZ;
						Vec3d manaDirection = new Vec3d(manaStor.getPos().getX() - this.pos.getX(),
								manaStor.getPos().getY() - this.pos.getY(), manaStor.getPos().getZ() - this.pos.getZ());
						velocityX = -manaDirection.x * 0.1;
						velocityY = -manaDirection.y * 0.1;
						velocityZ = -manaDirection.z * 0.1;
						if (count % 20 == 0) {
							SphereParticle effect = new SphereParticle(getWorld(), xpos, ypos, zpos, velocityX,
									velocityY, velocityZ, 11, 0, 1, 10, .2f);
							MainClass.proxy.spawnEffect(effect);
						}
					}
				}
				// Functional
				if (tile instanceof TileEntityKarmicAltar) {
					TileEntityKarmicAltar manaStor = (TileEntityKarmicAltar) tile;
					if (manaStor.getManaValue() > this.rate && this.getManaValue() < maxMana) {
						this.addManaValue(this.rate * 0.3f);
						manaStor.setManaValue(manaStor.getManaValue() - this.rate * 0.3f);
						// Particle Stuff
						double xpos = pos.getX() + 0.5, ypos = pos.getY() + 1, zpos = pos.getZ() + 0.5;
						double velocityX, velocityY, velocityZ;
						Vec3d manaDirection = new Vec3d(manaStor.getPos().getX() - this.pos.getX(),
								manaStor.getPos().getY() - this.pos.getY(), manaStor.getPos().getZ() - this.pos.getZ());
						velocityX = -manaDirection.x * 0.1;
						velocityY = -manaDirection.y * 0.1;
						velocityZ = -manaDirection.z * 0.1;
						if (count % 20 == 0) {
							SphereParticle effect = new SphereParticle(getWorld(), xpos, ypos, zpos, velocityX,
									velocityY, velocityZ, 11, 0, 1, 10, .2f);
							MainClass.proxy.spawnEffect(effect);
						}
					}
				}

				// Storage Drums
				if (tile instanceof TileEntityManaCapacitor) {
					TileEntityManaCapacitor manaStor = (TileEntityManaCapacitor) tile;
					if (manaStor.checkExportToAbsorber()) {
						if (manaStor.getManaValue() > this.rate && this.getManaValue() < maxMana) {
							this.addManaValue(this.rate);
							manaStor.setManaValue(manaStor.manaValue - this.rate);
							Vector3 exportLoc = Vector3.fromTileEntityCenter(manaStor).add(0, 1, 0);
							if (count % 50 == 0) {
								MainClass.proxy.lightningFX(exportLoc, loc, 10f, System.nanoTime(), Reference.red,
										Reference.white);
							}
						}
					}
					if (manaStor.checkImportFromAbsorber()) {
						if (this.getManaValue() > this.rate && manaStor.getManaValue() <= manaStor.getMaxMana()) {
							this.setManaValue(this.manaValue - this.rate);
							manaStor.addManaValue(this.rate);
							// Particle Stuff
							double xpos = this.pos.getX() + 0.5, ypos = this.pos.getY() + 1,
									zpos = this.pos.getZ() + 0.5;
							double velocityX, velocityY, velocityZ;
							Vec3d manaDirection = new Vec3d(this.getPos().getX() - manaStor.getPos().getX(),
									this.getPos().getY() - manaStor.getPos().getY(),
									this.getPos().getZ() - manaStor.getPos().getZ());
							velocityX = -manaDirection.x * 0.1;
							velocityY = -manaDirection.y * 0.1;
							velocityZ = -manaDirection.z * 0.1;
							if (count % 20 == 0) {
								SphereParticle effect = new SphereParticle(getWorld(), xpos, ypos, zpos, velocityX,
										velocityY, velocityZ, 11, 0, 1, 10, .2f);
								MainClass.proxy.spawnEffect(effect);
							}
						}
					}
				}
				if (tile instanceof TileEntityStorageDrum) {
					TileEntityStorageDrum manaStor = (TileEntityStorageDrum) tile;
					if (manaStor.checkExportToAbsorber()) {
						if (manaStor.getManaValue() > this.rate && this.getManaValue() < maxMana) {
							this.addManaValue(this.rate);
							manaStor.setManaValue(manaStor.getManaValue() - this.rate);
							Vector3 exportLoc = Vector3.fromTileEntityCenter(manaStor).add(0, 1, 0);
							if (count % 50 == 0) {
								MainClass.proxy.lightningFX(exportLoc, loc, 0f, System.nanoTime(), Reference.red,
										Reference.white);
							}
						}
					}
					if (manaStor.checkImportFromAbsorber()) {
						if (this.getManaValue() > this.rate && manaStor.getManaValue() <= manaStor.getMaxMana()) {
							this.setManaValue(this.manaValue - this.rate);
							manaStor.addManaValue(this.rate);
							// Particle Stuff
							double xpos = this.pos.getX() + 0.5, ypos = this.pos.getY() + 1,
									zpos = this.pos.getZ() + 0.5;
							double velocityX, velocityY, velocityZ;
							Vec3d manaDirection = new Vec3d(this.getPos().getX() - manaStor.getPos().getX(),
									this.getPos().getY() - manaStor.getPos().getY() - .3,
									this.getPos().getZ() - manaStor.getPos().getZ());
							velocityX = -manaDirection.x * 0.1;
							velocityY = -manaDirection.y * 0.1;
							velocityZ = -manaDirection.z * 0.1;
							if (count % 20 == 0) {
								SphereParticle effect = new SphereParticle(getWorld(), xpos, ypos, zpos, velocityX,
										velocityY, velocityZ, 11, 0, 1, 10, .2f);
								MainClass.proxy.spawnEffect(effect);
							}
						}
					}
				}
				// MOST mana based machines
				if (tile instanceof TileManaSimpleInventory && !(tile instanceof TileEntityStorageDrum)
						&& !(tile instanceof TileEntityManaCapacitor) && !(tile instanceof TileEntityManaAbsorber)
						&& !(tile instanceof TileEntityKarmicAltar)) {
					TileManaSimpleInventory manaStor = (TileManaSimpleInventory) tile;
					if (manaStor.getManaValue() < manaStor.getMaxMana() && this.getManaValue() > this.rate) {
						manaStor.addManaValue(this.rate);
						this.setManaValue(this.manaValue - this.rate);
						// Particle Stuff
						double xpos = this.pos.getX() + 0.5, ypos = this.pos.getY() + 1, zpos = this.pos.getZ() + 0.5;
						double velocityX, velocityY, velocityZ;
						Vec3d manaDirection = new Vec3d(this.getPos().getX() - manaStor.getPos().getX(),
								this.getPos().getY() - manaStor.getPos().getY(),
								this.getPos().getZ() - manaStor.getPos().getZ());
						velocityX = -manaDirection.x * 0.1;
						velocityY = -manaDirection.y * 0.1;
						velocityZ = -manaDirection.z * 0.1;
						if (count % 20 == 0) {
							SphereParticle effect = new SphereParticle(getWorld(), xpos, ypos, zpos, velocityX,
									velocityY, velocityZ, 11, 0, 1, 10, .2f);
							MainClass.proxy.spawnEffect(effect);
						}
					}

				}
				// MOST mana based machines
				if (tile instanceof TileEntityVibratorySelector) {
					TileEntityVibratorySelector manaStor = (TileEntityVibratorySelector) tile;
					if (manaStor.getManaValue() < manaStor.maxMana && this.getManaValue() > this.rate) {
						manaStor.addManaValue(this.rate);
						this.setManaValue(this.manaValue - this.rate);

						// Particle Stuff
						double xpos = this.pos.getX() + 0.5, ypos = this.pos.getY() + 1, zpos = this.pos.getZ() + 0.5;
						double velocityX, velocityY, velocityZ;
						Vec3d manaDirection = new Vec3d(this.getPos().getX() - manaStor.getPos().getX(),
								this.getPos().getY() - manaStor.getPos().getY(),
								this.getPos().getZ() - manaStor.getPos().getZ());
						velocityX = -manaDirection.x * 0.1;
						velocityY = -manaDirection.y * 0.1;
						velocityZ = -manaDirection.z * 0.1;
						if (count % 20 == 0) {
							SphereParticle effect = new SphereParticle(getWorld(), xpos, ypos, zpos, velocityX,
									velocityY, velocityZ, 11, 0, 1, 10, .2f);
							MainClass.proxy.spawnEffect(effect);

						}
					}
				}
			}
		}
	}

	@Override
	public void readPacketNBT(NBTTagCompound tag) {
		itemHandler = createItemHandler();
		itemHandler.deserializeNBT(tag);
		manaValue = tag.getFloat(TAG_MANA);
		rank = tag.getInteger(TAG_RANK);

	}

	@Override
	public void writePacketNBT(NBTTagCompound tag) {
		tag.merge(itemHandler.serializeNBT());
		tag.setFloat(TAG_MANA, manaValue);
		tag.setInteger(TAG_RANK, rank);

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
