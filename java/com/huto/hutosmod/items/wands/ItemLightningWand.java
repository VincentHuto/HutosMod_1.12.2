package com.huto.hutosmod.items.wands;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLightningWand extends Item {
	public static float sync = 0;
	public static float mana = 0;

	public ItemLightningWand(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;

	}

	private EntityLiving getNearestTargetableMob(World world, double xpos, double ypos, double zpos) {
		final double TARGETING_DISTANCE = 16;
		AxisAlignedBB targetRange = new AxisAlignedBB(xpos - TARGETING_DISTANCE, ypos, zpos - TARGETING_DISTANCE,
				xpos + TARGETING_DISTANCE, ypos + TARGETING_DISTANCE, zpos + TARGETING_DISTANCE);
		List<EntityLiving> allNearbyMobs = world.getEntitiesWithinAABB(EntityLiving.class, targetRange);
		EntityLiving nearestMob = null;
		double closestDistance = Double.MAX_VALUE;
		for (EntityLiving nextMob : allNearbyMobs) {
			double nextClosestDistance = nextMob.getDistanceSq(xpos, ypos, zpos);
			if (nextClosestDistance < closestDistance) {
				closestDistance = nextClosestDistance;
				nearestMob = nextMob;
			}
		}
		return nearestMob;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		if (playerIn.world.isRemote) {
			if (target instanceof EntityLiving) {
				Random rand = new Random();
				for (int countparticles = 0; countparticles <= 30; ++countparticles) {
					target.world.spawnParticle(EnumParticleTypes.REDSTONE,
							target.posX + (rand.nextDouble() - 0.5D) * (double) target.width,
							target.posY + rand.nextDouble() * (double) target.height - (double) target.getYOffset()
									- 0.5,
							target.posZ + (rand.nextDouble() - 0.5D) * (double) target.width, 0.0D, 0.0D, 0.0D);

					playerIn.world.spawnParticle(EnumParticleTypes.WATER_SPLASH,
							playerIn.posX + (rand.nextDouble() - 0.5D) * (double) playerIn.width,
							playerIn.posY + rand.nextDouble() * (double) playerIn.height
									- (double) playerIn.getYOffset() - 0.5,
							playerIn.posZ + (rand.nextDouble() - 0.5D) * (double) playerIn.width, 0.0D, 0.0D, 0.0D);
				}

				EntityLiving mobTarget = getNearestTargetableMob(playerIn.world, playerIn.posX, playerIn.posY,
						playerIn.posZ);
				Vector3 vec = Vector3.fromEntityCenter(playerIn);
				Vector3 trackingendVec = vec.fromEntity(mobTarget).add(0, 1, 0);
				MainClass.proxy.lightningFX(vec, trackingendVec, 1F, System.nanoTime(), Reference.yellow,
						Reference.black);
				target.setHealth(target.getHealth() - 0.5F);
				target.performHurtAnimation();

			}
		}
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		RayTraceResult resu = playerIn.rayTrace(100, 10);
		Vector3 hitVec = new Vector3(resu.getBlockPos().getX(), resu.getBlockPos().getY(), resu.getBlockPos().getZ());
		Vector3 vec = Vector3.fromEntityCenter(playerIn);
		//MainClass.proxy.lightningFX(vec, hitVec, 5F, System.nanoTime(), Reference.black, Reference.white);

		//System.out.println(resu.toString());

		if (!playerIn.world.isRemote) {
			WorldServer ws = (WorldServer) playerIn.world;
		/*	ws.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, playerIn.posX + playerIn.width / 2,
					playerIn.posY + playerIn.height / 2, playerIn.posZ + playerIn.width / 2, 100, playerIn.width,
					playerIn.height, playerIn.width, 0.05);*/
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);

	}
	
	
}
