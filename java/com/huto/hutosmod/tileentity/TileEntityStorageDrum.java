package com.huto.hutosmod.tileentity;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketGetManaLimit;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.particles.ManaParticle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileEntityStorageDrum extends TileManaSimpleInventory implements ITickable {

	public static final String TAG_MANA = "mana";
	public static final String TAG_LEVEL = "tankLevel";
	public static final String TAG_SIZE = "tankSize";
	public static float playerLimit;
	public static float playerMana;

	public IMana mana = this.getCapability(ManaProvider.MANA_CAP, null);
	public int tankLevel = 0;

	public void addTankLevel(float tankValue) {
		this.tankLevel += tankValue;
	}

	public int getTankLevel() {
		return tankLevel;
	}

	public void setTankLevel(int tankLevel) {
		this.tankLevel = tankLevel;
	}

	public float getTankSize() {

		if (tankLevel == 0) {
			return this.maxMana = 100;
		}
		if (tankLevel == 1) {
			return this.maxMana = 200;
		}
		if (tankLevel == 2) {
			return this.maxMana = 300;
		}
		if (tankLevel == 3) {
			return this.maxMana = 400;
		}
		if (tankLevel == 4) {
			return this.maxMana = 500;
		}
		if (tankLevel == 5) {
			return this.maxMana = 600;
		}
		if (tankLevel == 6) {
			return this.maxMana = 700;
		}
		if (tankLevel == 7) {
			return this.maxMana = 800;
		}
		if (tankLevel == 8) {
			return this.maxMana = 900;
		}
		if (tankLevel == 9) {
			return this.maxMana = 1000;
		}
		return maxMana;
	}

	@Override
	public void readPacketNBT(NBTTagCompound tag) {
		itemHandler = createItemHandler();
		itemHandler.deserializeNBT(tag);
		manaValue = tag.getFloat(TAG_MANA);
		tankLevel = tag.getInteger(TAG_LEVEL);
		maxMana = tag.getFloat(TAG_SIZE);

	}

	@Override
	public void writePacketNBT(NBTTagCompound tag) {
		tag.merge(itemHandler.serializeNBT());
		tag.setFloat(TAG_MANA, manaValue);
		tag.setInteger(TAG_LEVEL, tankLevel);
		tag.setFloat(TAG_SIZE, maxMana);

	}

	public boolean isNotFull() {
		if (this.getManaValue() <= this.getMaxMana()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onLoad() {
		super.onLoad();
		this.getTankSize();

	}

	
	public boolean checkImportFromAbsorber() {
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (this.itemHandler.getStackInSlot(i).getItem() == ItemRegistry.upgrade_import) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkExportToAbsorber() {
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (this.itemHandler.getStackInSlot(i).getItem() == ItemRegistry.upgrade_absorber) {
				return true;
			}
		}
		return false;
	}

	public boolean checkAllowBlock() {
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (this.itemHandler.getStackInSlot(i).getItem() == ItemRegistry.upgrade_block) {
				return true;
			}
		}
		return false;
	}

	public boolean checkAllowPlayer() {
		for (int i = 0; i < 4; i++) {
			if (this.itemHandler.getStackInSlot(i).getItem() == ItemRegistry.upgrade_player) {
				return true;
			}
		}
		return false;
	}

	public boolean checkInv() {
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (this.getItemHandler().getStackInSlot(i) != null) {
				return true;
			}

		}
		return false;
	}

	@Override
	public void update() {
		if (checkInv() == true) {
			sendUpdates();

		}
		if (playerMana != playerLimit && manaValue > playerMana) {
			Random rand = new Random();
			if (checkAllowPlayer()) {
				// Centering Variables
				double xpos = pos.getX() + 0.5, ypos = pos.getY() + 2.0, zpos = pos.getZ() + 0.5;
				double velocityX = 0, velocityY = 0, velocityZ = 0;
				EntityPlayer playerTarget = getNearestTargetableMob(world, xpos, ypos, zpos);
				Vec3d manaDirection;
				if (playerTarget == null) { // no target: fire straight upwards
					manaDirection = new Vec3d(0.0, 0.0, 0.0);
				} else {
					manaDirection = playerTarget.getPositionEyes(1.0F).subtract(xpos, ypos, zpos);
					manaDirection = manaDirection.normalize();
				}
				final double SPEED_IN_BLOCKS_PER_SECOND = 2.0;
				final double SPEED_IN_BLOCKS_PER_TICK = SPEED_IN_BLOCKS_PER_SECOND / 20;
				double newy = ypos + Math.sin(rand.nextInt(360)) - 1.5;
				double newVX = (velocityX % (Math.random()) * 1.8);
				double newVZ = (velocityZ % (Math.random()) * 1.8);
				velocityX = SPEED_IN_BLOCKS_PER_TICK * manaDirection.x; // how much to increase the x position every
				velocityY = SPEED_IN_BLOCKS_PER_TICK * manaDirection.y; // how much to increase the y position every
				velocityZ = SPEED_IN_BLOCKS_PER_TICK * manaDirection.z; // how much to increase the z position every
				float r;
				float g;
				float b;
				float scaleF = (float) (1 * rand.nextDouble());
				// Calculating the rgb values for the different tank levels
				if (this.getManaValue() <= 300) {
					r = 0.2F;
					g = 0.0F;
					b = 1.0F;
					ManaParticle newEffect = new ManaParticle(world, xpos, ypos, zpos, velocityX, velocityY, velocityZ,
							r, g, b, 70, scaleF);
					Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
				} else if (this.getManaValue() > 300 && this.getManaValue() <= 600) {
					r = 1.0F;
					g = 0.0F;
					b = 1.0F;
					ManaParticle newEffect = new ManaParticle(world, xpos, ypos, zpos, velocityX, velocityY, velocityZ,
							r, g, b, 70, scaleF);
					Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
				} else if (this.getManaValue() > 600 && this.getManaValue() <= 900) {
					r = 1.0F;
					g = 0.0F;
					b = 0.0F;
					ManaParticle newEffect = new ManaParticle(world, xpos, ypos, zpos, velocityX, velocityY, velocityZ,
							r, g, b, 70, scaleF);
					Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
				} else {
					r = 0.0F;
					g = 0.0F;
					b = 0.0F;
					ManaParticle newEffect = new ManaParticle(world, xpos, ypos, zpos, velocityX, velocityY, velocityZ,
							r, g, b, 70, scaleF);
					Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);

				}
			}
		}
		// Ease of use variables
		int x = this.pos.getX();
		int y = this.pos.getY();
		int z = this.pos.getZ();
		// Search radius for blocks and tiles
		Iterable<BlockPos> radiusPositions = BlockPos.getAllInBox(this.pos.add(3.0f, 3.0f, 3.0f),
				this.pos.add(-3.0f, -3.0f, -3.0f)); // 3x3x3 range
		// Search radius for entities
		AxisAlignedBB playerbb = (new AxisAlignedBB((double) x, (double) y, (double) z, (double) (x + 1),
				(double) (y + 1), (double) (z + 1))).grow(3);
		// extracts a list of only players from the bounding box
		List<EntityPlayer> playerList = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, playerbb);
		// Checks the player list to make sure it isnt running uselessly
		if (!playerList.isEmpty() && this.checkAllowPlayer()) {
			for (EntityPlayer entityplayer : playerList) {
				PacketHandler.INSTANCE.sendToServer(new PacketGetMana(playerMana,
						"com.huto.hutosmod.tileentity.TileEntityStorageDrum", "playerMana"));
				PacketHandler.INSTANCE.sendToServer(new PacketGetManaLimit(playerLimit,
						"com.huto.hutosmod.tileentity.TileEntityStorageDrum", "playerLimit"));
				IMana pMana = entityplayer.getCapability(ManaProvider.MANA_CAP, null);
				if (this.manaValue >= 50 && this.manaValue >= playerMana && playerMana != playerLimit) {
					this.setManaValue(manaValue - 0.1f);
					pMana.fill(0.1F);
				}
			}
		}
		// Checks block positions in a 3x3x3
		for (BlockPos pos : radiusPositions) {
			TileEntity tile = world.getTileEntity(pos);
			// Makes sure it doesnt run uselessly
			if (tile != null && this.getPos() != pos) {
				if (this.checkAllowBlock()) {
					if (tile instanceof TileModMana && !(tile instanceof TileEntityStorageDrum)) {
						TileModMana manaStor = (TileModMana) tile;
						if (this.manaValue >= 50 && this.manaValue > manaStor.getManaValue()
								&& this.maxMana > manaStor.maxMana) {
							double commonMax = Math.min(this.maxMana, manaStor.maxMana);
							if (commonMax <= this.maxMana) {
								if (manaStor.getManaValue() <= manaStor.getMaxMana()) {
									this.setManaValue(manaValue - 0.1f);
									manaStor.addManaValue(0.1f);
								}
							}
						}

					}
					if (tile instanceof TileManaSimpleInventory && !(tile instanceof TileEntityStorageDrum)) {
						TileManaSimpleInventory wandMaker = (TileManaSimpleInventory) tile;
						if (this.manaValue >= 50 && this.manaValue > wandMaker.getManaValue()
								&& wandMaker.getManaValue() <= wandMaker.maxMana) {
							double commonMax = Math.min(this.maxMana, wandMaker.maxMana);
							if (commonMax <= this.maxMana) {
								if (wandMaker.getManaValue() <= wandMaker.getMaxMana()) {
									this.setManaValue(manaValue - 0.1f);
									wandMaker.addManaValue(0.1f);
								}
							}
						}
					}
					if (tile instanceof TileEntityVibratorySelector) {
						TileEntityVibratorySelector wandMaker = (TileEntityVibratorySelector) tile;
						if (this.manaValue >= 40 && this.manaValue > wandMaker.getManaValue()
								&& wandMaker.getManaValue() < wandMaker.maxMana) {
							this.setManaValue(manaValue - 0.1f);
							wandMaker.addManaValue(0.1f);
						}
					}
				}
			}
		}

		for (EnumFacing face : EnumFacing.values()) {
			BlockPos adj = getPos().offset(face);
			TileEntity tile = world.getTileEntity(adj);
			if (tile instanceof TileEntityManaGatherer) {
				TileEntityManaGatherer manaG = (TileEntityManaGatherer) tile;
				if (manaG.getManaValue() > 0.1F && this.isNotFull()) {
					this.addManaValue(0.1F);
					manaG.setManaValue(manaG.getManaValue() - 0.1f);
				}

			}

		}

	}

	@SideOnly(Side.CLIENT)
	public boolean shouldRenderFace(EnumFacing face) {
		return true;
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
	public int getSizeInventory() {
		return 4;
	}

	private EntityPlayer getNearestTargetableMob(World world, double xpos, double ypos, double zpos) {
		final double TARGETING_DISTANCE = 3.5;
		AxisAlignedBB targetRange = new AxisAlignedBB(xpos - TARGETING_DISTANCE, ypos - 2, zpos - TARGETING_DISTANCE,
				xpos + TARGETING_DISTANCE, ypos + TARGETING_DISTANCE, zpos + TARGETING_DISTANCE);
		List<EntityPlayer> allNearbyMobs = world.getEntitiesWithinAABB(EntityPlayer.class, targetRange);
		EntityPlayer nearestMob = null;
		double closestDistance = Double.MAX_VALUE;
		for (EntityPlayer nextMob : allNearbyMobs) {
			double nextClosestDistance = nextMob.getDistanceSq(xpos, ypos, zpos);
			if (nextClosestDistance < closestDistance) {
				closestDistance = nextClosestDistance;
				nearestMob = nextMob;
			}
		}
		return nearestMob;
	}

}
