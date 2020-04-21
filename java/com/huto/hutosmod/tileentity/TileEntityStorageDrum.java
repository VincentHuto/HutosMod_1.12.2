package com.huto.hutosmod.tileentity;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketGetManaLimit;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.particles.ManaParticle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	@Override
	public void onLoad() {
		super.onLoad();
		this.getTankSize();

	}

	@Override
	public void update() {
		this.getSizeInventory();
		
		
		if (world.isRemote) {
			Random rand = new Random();
			double xpos = pos.getX() + 0.5;
			double ypos = pos.getY() + 1.0;
			double zpos = pos.getZ() + 0.5;
			double velocityX = 0,velocityY = 0, velocityZ = 0; 

			// starting position = top of the pole
			xpos = pos.getX() + 0.5;
			ypos = pos.getY() + 2.0;
			zpos = pos.getZ() + 0.5;

			EntityPlayer mobTarget = getNearestTargetableMob(world, xpos, ypos, zpos);
			Vec3d fireballDirection;
			if (mobTarget == null) { // no target: fire straight upwards
				fireballDirection = new Vec3d(0.0, 1.0, 0.0);
			} else { 

				fireballDirection = mobTarget.getPositionEyes(1.0F).subtract(xpos, ypos, zpos);
				fireballDirection = fireballDirection.normalize();
			}

			final double SPEED_IN_BLOCKS_PER_SECOND = 2.0;
			final double TICKS_PER_SECOND = 20;
			final double SPEED_IN_BLOCKS_PER_TICK = SPEED_IN_BLOCKS_PER_SECOND / TICKS_PER_SECOND;
			double newy = ypos+Math.sin(rand.nextInt(360))-1.5;
			double newVX=(velocityX%(Math.random())*1.8);
			double newVZ=(velocityZ%(Math.random())*1.8);
			velocityX = SPEED_IN_BLOCKS_PER_TICK * fireballDirection.x; // how much to increase the x position every
			velocityY = SPEED_IN_BLOCKS_PER_TICK * fireballDirection.y; // how much to increase the y position every
			velocityZ = SPEED_IN_BLOCKS_PER_TICK * fireballDirection.z; // how much to increase the z position every
			
			ManaParticle newEffect = new ManaParticle(world, xpos, ypos, zpos, velocityX, velocityY,velocityZ);
		   // FlameParticle newEffect = new FlameParticle(world, xpos, newy, zpos, (velocityX), velocityY, (velocityZ));
		      Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
		    }
		
		// To check for a tile entity use this instead
			// if(world.getTileEntity(pos) != null){
			// if(world.getTileEntity(pos) instanceof TileEntityRadiusBlock){

		// for a block use this instead
			// if (world.getBlockState(pos).getBlock() != null) {
			// if (world.getBlockState(pos).getBlock() == Blocks.COAL_BLOCK){
		

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
		if (!playerList.isEmpty()) {
			for (EntityPlayer entityplayer : playerList) {
				PacketHandler.INSTANCE.sendToServer(new PacketGetMana(playerMana,
						"com.huto.hutosmod.tileentites.TileEntityStorageDrum", "playerMana"));
				PacketHandler.INSTANCE.sendToServer(new PacketGetManaLimit(playerLimit,
						"com.huto.hutosmod.tileentites.TileEntityStorageDrum", "playerLimit"));
				IMana pMana = entityplayer.getCapability(ManaProvider.MANA_CAP, null);
				if (this.manaValue >= 50 && this.manaValue >= playerMana && playerMana <= playerLimit) {
					this.setManaValue(manaValue - 0.1f);
					pMana.fill(0.1F);
				}
			}
		}
		// Checks block positions in a 3x3x3
		for (BlockPos pos : radiusPositions) {
			// Makes sure it doesnt run uselessly
			if (world.getTileEntity(pos) != null) {
				if (world.getTileEntity(pos) instanceof TileModMana) {
					TileModMana manaStor = (TileModMana) world.getTileEntity(pos);
					if (this.manaValue >= 50 && this.manaValue >= manaStor.getManaValue()
							&& manaStor.getManaValue() <= manaStor.getMaxMana()) {
						this.setManaValue(manaValue - 0.1f);
						manaStor.addManaValue(0.1f);
					}
				}
				// This is seperate due to WandMaker having different properties because it has
				// an inventory that i may want to interact with later
				// May change this to instanceof TileSimpleInventory
				if (world.getTileEntity(pos) instanceof TileManaSimpleInventory) {

					TileManaSimpleInventory wandMaker = (TileManaSimpleInventory) world.getTileEntity(pos);
					if (this.manaValue >= 50 && this.manaValue > wandMaker.getManaValue()) {
						this.setManaValue(manaValue - 0.1f);
						wandMaker.addManaValue(0.1f);
					}
				}
			}
		}

	}

	// THIS IS ALL DEFUNCT AND WAY LESS EFFICENT THANT THE ABOVE
	// , But they do have the place this checks only in north south east or west one
	// block, so adjecent use full for piping?
	/*
	 * for (EnumFacing direction : EnumFacing.VALUES) {
	 * 
	 * BlockPos neighbourPos = this.pos.offset(direction);
	 * 
	 * IBlockState neighbourState = world.getBlockState(neighbourPos);
	 * 
	 * Block neighbourBlock = neighbourState.getBlock();
	 * 
	 * if (neighbourBlock == Blocks.COAL_BLOCK) { System.out.println(pos);
	 * 
	 * }
	 * 
	 * }
	 */

	@SideOnly(Side.CLIENT)
	public boolean shouldRenderFace(EnumFacing face) {
		return true;
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

	@Override
	public int getSizeInventory() {
		// SO THIS IS NOT GONNA WORK BECAUSE HOW ITEMSTACKHANDLER WORKS IS IT CREATS A
		// NONENULLLIST OF A FIXED SIZE,no matter what, so if i wanna change this imma
		// have to write my own itemstackhandler using a list inteasd of an array
		// annnnnnnnnnnnnnd i dont want to right now
/*
		if (tankLevel <= 1) {
			return 1;
		}
		if (tankLevel >= 2 && tankLevel <= 3) {
			return 2;
		}
		if (tankLevel >= 4 && tankLevel <= 5) {
			return 3;
		}
		if (tankLevel >= 6 && tankLevel <= 7) {
			return 4;
		}
		if (tankLevel >= 8 && tankLevel <= 9) {
			return 5;
		}
		if (tankLevel >= 10 && tankLevel <= 11) {
			return 6;
		}
		if (tankLevel >= 12 && tankLevel <= 13) {
			return 7;
		} else*/
			return 4;
	}
	
	private EntityPlayer getNearestTargetableMob(World world, double xpos, double ypos, double zpos) {
		final double TARGETING_DISTANCE = 3;
		AxisAlignedBB targetRange = new AxisAlignedBB(xpos - TARGETING_DISTANCE, ypos-2, zpos - TARGETING_DISTANCE,
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
