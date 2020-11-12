package com.huto.hutosmod.tileentity;

import java.util.List;

import com.huto.hutosmod.particles.RingParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;

public class TileEntitySlimeRepelent extends TileEntity implements ITickable {

	public static int count = 0;

	@Override
	public void update() {
		count++;
		repelSlimeInAABBFromPoint(world, new AxisAlignedBB(pos.add(-4, -4, -4), pos.add(4, 4, 4)), pos.getX() + 0.5,
				pos.getY() + 0.5, pos.getZ() + 0.5);
		if (world.isRemote && count % (35 + world.rand.nextInt(10)) == 0) {
			count = 0;
			double xpos = pos.getX() + 0.5 + ((world.rand.nextDouble() - world.rand.nextDouble()) * .3);
			double ypos = pos.getY() + .8;
			double zpos = pos.getZ() + 0.5 + ((world.rand.nextDouble() - world.rand.nextDouble()) * .3);
			RingParticle particle = new RingParticle(world, xpos, ypos, zpos, 0, 0.01, 0, 1, 0, 0, 60, 0.3f);
			Minecraft.getMinecraft().effectRenderer.addEffect(particle);
		}
	}

	public static void repelSlimeInAABBFromPoint(World world, AxisAlignedBB effectBounds, double x, double y,
			double z) {
		List<Entity> list = world.getEntitiesWithinAABB(Entity.class, effectBounds);
		for (Entity ent : list) {
			if ((ent instanceof EntitySlime)) {

				Vec3d p = new Vec3d(x, y, z);
				Vec3d t = new Vec3d(ent.posX, ent.posY, ent.posZ);
				double distance = p.distanceTo(t) + 0.1D;
				Vec3d r = new Vec3d(t.x - p.x, t.y - p.y, t.z - p.z);

				ent.motionX += r.x / 1.2D / distance;
				ent.motionY += r.y / 1.2D / distance;
				ent.motionZ += r.z / 1.2D / distance;

				if (world.isRemote) {
					for (int countparticles = 0; countparticles <= 30; ++countparticles) {
						ent.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
								ent.posX + (world.rand.nextDouble() - 0.5D) * (double) ent.width,
								ent.posY + world.rand.nextDouble() * (double) ent.height - (double) ent.getYOffset()
										- 0.5,
								ent.posZ + (world.rand.nextDouble() - 0.5D) * (double) ent.width, 0.0D, 0.0D, 0.0D);
					}
				}

			}
		}
	}
}
