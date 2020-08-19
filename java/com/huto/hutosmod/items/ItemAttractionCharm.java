package com.huto.hutosmod.items;

import java.util.List;

import com.google.common.collect.Lists;
import com.huto.hutosmod.MainClass;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemAttractionCharm extends Item {

	public ItemAttractionCharm(String name) {
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
			if ((ent instanceof EntityItem)) {

				Vec3d p = new Vec3d(x, y, z);
				Vec3d t = new Vec3d(ent.posX, ent.posY, ent.posZ);
				double distance = p.distanceTo(t) + 0.1D;
				Vec3d r = new Vec3d(t.x - p.x, t.y - p.y, t.z - p.z);

				// if += is changed to -= this become attraction
				ent.motionX -= r.x / 10.2D / distance * 0.3;
				ent.motionY -= r.y / 10.2D / distance;
				ent.motionZ -= r.z / 10.2D / distance * 0.3;

				if (world.isRemote) {
					for (int countparticles = 0; countparticles <= 1; ++countparticles) {
						ent.world.spawnParticle(EnumParticleTypes.PORTAL,
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
