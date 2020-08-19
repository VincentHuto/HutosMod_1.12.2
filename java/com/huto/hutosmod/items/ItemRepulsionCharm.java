package com.huto.hutosmod.items;

import java.util.List;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.entities.EntityColin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemRepulsionCharm extends Item {

	public ItemRepulsionCharm(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;

	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		repelSlimeInAABBFromPoint(worldIn,
				new AxisAlignedBB(entityIn.getPosition().add(-4, -4, -4), entityIn.getPosition().add(4, 4, 4)),
				entityIn.getPosition().getX() + 0.5, entityIn.getPosition().getY() + 0.5,
				entityIn.getPosition().getZ() + 0.5);

	}

	public static void repelSlimeInAABBFromPoint(World world, AxisAlignedBB effectBounds, double x, double y,
			double z) {
		List<Entity> list = world.getEntitiesWithinAABB(Entity.class, effectBounds);
		for (Entity ent : list) {
			if ((ent instanceof EntityMob || ent instanceof EntitySlime || ent instanceof EntityFireball
					|| ent instanceof IProjectile && !(ent instanceof EntityDragon)
							&& !(ent instanceof EntityWither))) {

				Vec3d p = new Vec3d(x, y, z);
				Vec3d t = new Vec3d(ent.posX, ent.posY, ent.posZ);
				double distance = p.distanceTo(t) + 0.1D;
				Vec3d r = new Vec3d(t.x - p.x, t.y - p.y, t.z - p.z);

				// if += is changed to -= this become attraction
				ent.motionX += r.x / 1.2D / distance;
				ent.motionY += r.y / 1.2D / distance;
				ent.motionZ += r.z / 1.2D / distance;

				if (world.isRemote) {
					for (int countparticles = 0; countparticles <= 10; ++countparticles) {
						ent.world.spawnParticle(EnumParticleTypes.REDSTONE,
								ent.posX + (world.rand.nextDouble() - 0.5D) * (double) ent.width,
								ent.posY + world.rand.nextDouble() * (double) ent.height - (double) ent.getYOffset()
										- 0.5,
								ent.posZ + (world.rand.nextDouble() - 0.5D) * (double) ent.width, 0.0D, 0.0D, 0.0D);
					}
				}
			//	ent.attackEntityFrom(DamageSource.GENERIC, 1f);

			}
		}
	}
}
