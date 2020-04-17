package com.huto.hutosmod.blocks;

import java.util.List;
import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.particles.FlameParticle;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentites.TileEntityFusionFurnace;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FusionFurnaceBlock extends BlockBase {

	public static final PropertyDirection Facing = BlockHorizontal.FACING;
	public static final PropertyBool Active = PropertyBool.create("active");

	public FusionFurnaceBlock(String name) {
		super(name, Material.IRON);
		setSoundType(SoundType.METAL);
		this.setDefaultState(
				this.blockState.getBaseState().withProperty(Facing, EnumFacing.NORTH).withProperty(Active, false));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(BlockRegistry.Fusion_Furnace);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(BlockRegistry.Fusion_Furnace);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(MainClass.instance, Reference.Gui_Fusion_Furnace, worldIn, pos.getX(), pos.getY(),
					pos.getZ());
		}

		return true;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState west = worldIn.getBlockState(pos.west());
			IBlockState east = worldIn.getBlockState(pos.east());
			EnumFacing face = (EnumFacing) state.getValue(Facing);

			if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock())
				face = EnumFacing.SOUTH;
			else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock())
				face = EnumFacing.NORTH;
			else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock())
				face = EnumFacing.EAST;
			else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock())
				face = EnumFacing.WEST;
			worldIn.setBlockState(pos, state.withProperty(Facing, face), 2);
		}
	}

	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		// if(active) worldIn.setBlockState(pos,
		// BlockInit.SINTERING_FURNACE.getDefaultState().withProperty(FACING,
		// state.getValue(FACING)).withProperty(BURNING, true), 3);
		// else worldIn.setBlockState(pos,
		// BlockInit.SINTERING_FURNACE.getDefaultState().withProperty(FACING,
		// state.getValue(FACING)).withProperty(BURNING, false), 3);

		if (tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityFusionFurnace();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(Facing, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos,
				this.getDefaultState().withProperty(Facing, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(Facing, rot.rotate((EnumFacing) state.getValue(Facing)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(Facing)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { Active, Facing });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta);
		if (facing.getAxis() == EnumFacing.Axis.Y)
			facing = EnumFacing.NORTH;
		return this.getDefaultState().withProperty(Facing, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(Facing)).getIndex();
	}
	
	
	/*
	  // This method is called at random intervals - typically used by blocks which produce occasional effects, like
	  //  smoke from a torch or stars from a portal.
	  //  In this case, we use it to spawn two different types of Particle- vanilla, or custom.
	  // Don't forget     @SideOnly(Side.CLIENT) otherwise this will crash on a dedicated server.
	  @Override
	  @SideOnly(Side.CLIENT)
	  public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	  {
	    // Particle must be spawned on the client only.
	    // If you want the server to be able to spawn Particle, you need to send a network message to the client and get the
	    //   client to spawn the Particle in response to the message (see mbe60 MessageHandlerOnClient for an example).
	    if (worldIn.isRemote) {  // is this on the client side?
	      // first example:
	      // spawn a vanilla particle of LAVA type (smoke from lava)
	      //  The starting position is the [x,y,z] of the tip of the pole (i.e. at [0.5, 1.0, 0.5] relative to the block position)
	      //  Set the initial velocity to zero.
	      // When the particle is spawned, it will automatically add a random amount of velocity - see EntityLavaFX constructor and
	      //   Particle constructor.  This can be a nuisance if you don't want your Particle to have a random starting velocity!  See
	      //  second example below for more information.

	      double xpos = pos.getX() + 0.5;
	      double ypos = pos.getY() + 1.0;
	      double zpos = pos.getZ() + 0.5;
	      double velocityX = 0; // increase in x position every tick
	      double velocityY = 0; // increase in y position every tick;
	      double velocityZ = 0; // increase in z position every tick
	      int [] extraInfo = new int[0];  // extra information if needed by the particle - in this case unused

	      worldIn.spawnParticle(EnumParticleTypes.LAVA, xpos, ypos, zpos, velocityX, velocityY, velocityZ, extraInfo);

	      // second example:
	      // spawn a custom Particle ("FlameParticle") with a texture we have added ourselves.
	      // FlameParticle also has custom movement and collision logic - it moves in a straight line until it hits something.
	      // To make it more interesting, the stream of fireballs will target the nearest non-player entity within 16 blocks at
	      //   the height of the pole or above.

	      // starting position = top of the pole
	      xpos = pos.getX() + 0.5;
	      ypos = pos.getY() + 1.0;
	      zpos = pos.getZ() + 0.5;

	      EntityMob mobTarget = getNearestTargetableMob(worldIn, xpos, ypos, zpos);
	      Vec3d fireballDirection;
	      if (mobTarget == null) { // no target: fire straight upwards
	        fireballDirection = new Vec3d(0.0, 1.0, 0.0);
	      } else {  // otherwise: aim at the mob
	        // the direction that the fireball needs to travel is calculated from the starting point (the pole) and the
	        //   end point (the mob's eyes).  A bit of googling on vector maths will show you that you calculate this by
	        //  1) subtracting the start point from the end point
	        //  2) normalising the vector (if you don't do this, then the fireball will fire faster if the mob is further away

	        fireballDirection = mobTarget.getPositionEyes(1.0F).subtract(xpos, ypos, zpos);  // NB this method only works on client side
	        fireballDirection = fireballDirection.normalize();
	      }

	      // the velocity vector is now calculated as the fireball's speed multiplied by the direction vector.

	      final double SPEED_IN_BLOCKS_PER_SECOND = 2.0;
	      final double TICKS_PER_SECOND = 20;
	      final double SPEED_IN_BLOCKS_PER_TICK = SPEED_IN_BLOCKS_PER_SECOND / TICKS_PER_SECOND;

	      velocityX = SPEED_IN_BLOCKS_PER_TICK * fireballDirection.x; // how much to increase the x position every tick
	      velocityY = SPEED_IN_BLOCKS_PER_TICK * fireballDirection.y; // how much to increase the y position every tick
	      velocityZ = SPEED_IN_BLOCKS_PER_TICK * fireballDirection.z; // how much to increase the z position every tick

	      FlameParticle newEffect = new FlameParticle(worldIn, xpos, ypos, zpos, velocityX, velocityY, velocityZ);
	      Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
	    }
	  }

	  *//**
	   * Returns the nearest targetable mob to the indicated [xpos, ypos, zpos].
	   * @param world
	   * @param xpos [x,y,z] position to s
	   * @param ypos
	   * @param zpos
	   * @return the nearest mob, or null if none within range.
	   *//*
	  private EntityMob getNearestTargetableMob(World world, double xpos, double ypos, double zpos) {
	    final double TARGETING_DISTANCE = 16;
	    AxisAlignedBB targetRange = new AxisAlignedBB(xpos - TARGETING_DISTANCE,
	                                                  ypos,
	                                                  zpos - TARGETING_DISTANCE,
	                                                  xpos + TARGETING_DISTANCE,
	                                                  ypos + TARGETING_DISTANCE,
	                                                  zpos + TARGETING_DISTANCE);

	    List<EntityMob> allNearbyMobs = world.getEntitiesWithinAABB(EntityMob.class, targetRange);
	    EntityMob nearestMob = null;
	    double closestDistance = Double.MAX_VALUE;
	    for (EntityMob nextMob : allNearbyMobs) {
	      double nextClosestDistance = nextMob.getDistanceSq(xpos, ypos, zpos);
	      if (nextClosestDistance < closestDistance) {
	        closestDistance = nextClosestDistance;
	        nearestMob = nextMob;
	      }
	    }
	    return nearestMob;
	  }
*/
}