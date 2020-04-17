package com.huto.hutosmod.blocks;

import java.util.List;
import java.util.Random;

import com.huto.hutosmod.particles.EntityBaseFX;
import com.huto.hutosmod.particles.FlameParticle;
import com.huto.hutosmod.particles.Helper;
import com.huto.hutosmod.particles.TestParticle;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnchantedStoneBlock extends BlockBase {

	public EnchantedStoneBlock(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.ANVIL);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
		//setLightLevel(1.0F);
		setLightOpacity(1);
		// setBlockUnbreakable();
	}

	
	
/*	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {

		//super.updateTick(worldIn, pos, state, rand);

		// the velocity vector is now calculated as the fireball's speed multiplied by
					// the direction vector.
		double velocityX = 0; // increase in x position every tick
		double velocityY = 0; // increase in y position every tick;
		double velocityZ = 0; // increase in z position every tick
					final double SPEED_IN_BLOCKS_PER_SECOND = 2.0;
					final double TICKS_PER_SECOND = 20;
					final double SPEED_IN_BLOCKS_PER_TICK = SPEED_IN_BLOCKS_PER_SECOND / TICKS_PER_SECOND;

					velocityX = SPEED_IN_BLOCKS_PER_TICK; // how much to increase the x position every
																				// tick
					velocityY = SPEED_IN_BLOCKS_PER_TICK; // how much to increase the y position every
																				// tick
					velocityZ = SPEED_IN_BLOCKS_PER_TICK ; // how much to increase the z position every
																				// tick
		
		TestParticle newEffect1 = new TestParticle(worldIn, pos.getX(), pos.getY()+2, pos.getZ(), velocityX, velocityY, velocityZ, 100, 1000, 110,
				1, 0, 1, 0.5, 1);

		Minecraft.getMinecraft().effectRenderer.addEffect(newEffect1);
	}


	  @SideOnly(Side.CLIENT)

	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {

		if (worldIn.isRemote) { // is this on the client side?

			double xpos = pos.getX() + 0.5;
			double ypos = pos.getY() + 1.0;
			double zpos = pos.getZ() + 0.5;
			double velocityX = 0; // increase in x position every tick
			double velocityY = 0; // increase in y position every tick;
			double velocityZ = 0; // increase in z position every tick
			int[] extraInfo = new int[0]; // extra information if needed by the particle - in this case unused

			// second example:
			// spawn a custom Particle ("FlameParticle") with a texture we have added
			// ourselves.
			// FlameParticle also has custom movement and collision logic - it moves in a
			// straight line until it hits something.
			// To make it more interesting, the stream of fireballs will target the nearest
			// non-player entity within 16 blocks at
			// the height of the pole or above.

			// starting position = top of the pole
			xpos = pos.getX() + 0.5;
			ypos = pos.getY() + 1.0;
			zpos = pos.getZ() + 0.5;

			EntityMob mobTarget = getNearestTargetableMob(worldIn, xpos, ypos, zpos);
			Vec3d fireballDirection;
			if (mobTarget == null) { // no target: fire straight upwards
				fireballDirection = new Vec3d(0.0, 1.0, 0.0);
			} else { // otherwise: aim at the mob
				// the direction that the fireball needs to travel is calculated from the
				// starting point (the pole) and the
				// end point (the mob's eyes). A bit of googling on vector maths will show you
				// that you calculate this by
				// 1) subtracting the start point from the end point
				// 2) normalising the vector (if you don't do this, then the fireball will fire
				// faster if the mob is further away

				fireballDirection = mobTarget.getPositionEyes(1.0F).subtract(xpos, ypos, zpos); // NB this method only
																								// works on client side
				fireballDirection = fireballDirection.normalize();
			}

			// the velocity vector is now calculated as the fireball's speed multiplied by
			// the direction vector.

			final double SPEED_IN_BLOCKS_PER_SECOND = 2.0;
			final double TICKS_PER_SECOND = 20;
			final double SPEED_IN_BLOCKS_PER_TICK = SPEED_IN_BLOCKS_PER_SECOND / TICKS_PER_SECOND;

			velocityX = SPEED_IN_BLOCKS_PER_TICK * fireballDirection.x; // how much to increase the x position every
																		// tick
			velocityY = SPEED_IN_BLOCKS_PER_TICK * fireballDirection.y; // how much to increase the y position every
																		// tick
			velocityZ = SPEED_IN_BLOCKS_PER_TICK * fireballDirection.z; // how much to increase the z position every
																		// tick

			// TestParticle newEffect = new TestParticle(worldIn, xpos, ypos, zpos,
			// velocityX, velocityY, velocityZ, 150, 3, 0.5, 1, 1, 1, 0.5, 0);
		TestParticle newEffect1 = new TestParticle(worldIn, xpos, ypos, zpos, velocityX, velocityY, velocityZ, 100,
					1000, 110, 1, 0, 1, 0.5, 1);

			// Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
			Minecraft.getMinecraft().effectRenderer.addEffect(newEffect1);

		}
	}

	*//**
	 * Returns the nearest targetable mob to the indicated [xpos, ypos, zpos].
	 * 
	 * @param world
	 * @param xpos  [x,y,z] position to s
	 * @param ypos
	 * @param zpos
	 * @return the nearest mob, or null if none within range.
	 *//*
	private EntityMob getNearestTargetableMob(World world, double xpos, double ypos, double zpos) {
		final double TARGETING_DISTANCE = 16;
		AxisAlignedBB targetRange = new AxisAlignedBB(xpos - TARGETING_DISTANCE, ypos, zpos - TARGETING_DISTANCE,
				xpos + TARGETING_DISTANCE, ypos + TARGETING_DISTANCE, zpos + TARGETING_DISTANCE);

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
