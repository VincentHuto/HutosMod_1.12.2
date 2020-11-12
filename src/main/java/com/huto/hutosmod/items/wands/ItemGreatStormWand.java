package com.huto.hutosmod.items.wands;

import java.util.List;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemGreatStormWand extends Item {
	public static float sync = 0;
	public static float mana = 0;

	public ItemGreatStormWand(String name) {
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		Vector3 vec = Vector3.fromEntityCenter(player);

		RayTraceResult result = player.rayTrace(10, 0);
		Vector3 endVec = new Vector3(result.getBlockPos().getX(), result.getBlockPos().getY(),
				result.getBlockPos().getZ());

		int x1 = (int) vec.x, y1 = (int) vec.y, z1 = (int) vec.z;
		int x2 = result.getBlockPos().getX(), y2 = result.getBlockPos().getY(), z2 = result.getBlockPos().getZ();

		MainClass.proxy.lightningFX(vec, endVec, 1F, System.nanoTime(), Reference.yellow, Reference.black);
		MainClass.proxy.lightningFX(vec, endVec, 1F, System.nanoTime(), Reference.white, Reference.black);
		MainClass.proxy.lightningFX(vec, endVec, 1F, System.nanoTime(), Reference.orange, Reference.black);
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}


	/*
	 * @Override public boolean itemInteractionForEntity(ItemStack stack,
	 * EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
	 * 
	 * if (target instanceof EntityLiving && target != null) { Random rand = new
	 * Random(); for (int countparticles = 0; countparticles <= 30;
	 * ++countparticles) { target.world.spawnParticle(EnumParticleTypes.REDSTONE,
	 * target.posX + (rand.nextDouble() - 0.5D) * (double) target.width, target.posY
	 * + rand.nextDouble() * (double) target.height - (double) target.getYOffset() -
	 * 0.5, target.posZ + (rand.nextDouble() - 0.5D) * (double) target.width, 0.0D,
	 * 0.0D, 0.0D);
	 * 
	 * playerIn.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, playerIn.posX +
	 * (rand.nextDouble() - 0.5D) * (double) playerIn.width, playerIn.posY +
	 * rand.nextDouble() * (double) playerIn.height - (double) playerIn.getYOffset()
	 * - 0.5, playerIn.posZ + (rand.nextDouble() - 0.5D) * (double) playerIn.width,
	 * 0.0D, 0.0D, 0.0D); }
	 * 
	 * EntityLiving mobTarget = getNearestTargetableMob(playerIn.world,
	 * playerIn.posX, playerIn.posY, playerIn.posZ); if (mobTarget != null) {
	 * Vector3 vec = Vector3.fromEntityCenter(playerIn); Vector3 trackingendVec =
	 * vec.fromEntityCenter(mobTarget); if (trackingendVec != null && mobTarget !=
	 * null && vec != null) { if (playerIn.world.isRemote) {
	 * MainClass.proxy.lightningFX(vec, trackingendVec, 1F, System.nanoTime(),
	 * Reference.yellow, Reference.black); MainClass.proxy.lightningFX(vec,
	 * trackingendVec, 1F, System.nanoTime(), Reference.white, Reference.black);
	 * MainClass.proxy.lightningFX(vec, trackingendVec, 1F, System.nanoTime(),
	 * Reference.orange, Reference.black); }
	 * 
	 * ParticleEntityDamageSource damage = new
	 * ParticleEntityDamageSource("lightning", null, playerIn);
	 * target.attackEntityFrom(damage, 5F); if (playerIn.getEntityWorld().isRemote)
	 * { target.performHurtAnimation(); } } } } return true; }
	 */

}
