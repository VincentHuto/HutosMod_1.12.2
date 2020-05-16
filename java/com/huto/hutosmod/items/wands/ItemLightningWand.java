package com.huto.hutosmod.items.wands;

import java.util.List;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		if (worldIn.isRemote) {
			Vec3d aim = playerIn.getLookVec();

			sync++;
			sync %= 10;
			if (sync == 0)
				PacketHandler.INSTANCE.sendToServer(
						new PacketGetMana(mana, "com.huto.hutosmod.items.wands.ItemLightningWand", "mana"));

			IMana mana = playerIn.getCapability(ManaProvider.MANA_CAP, null);
			if (mana.getMana() >= 10F) {
				Vec3d traced = rayTrace(worldIn, playerIn, false).hitVec;
				Vector3 test = new Vector3(traced.x, traced.y, traced.z);
				EntityLiving mobTarget = getNearestTargetableMob(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ);
				Vector3 vec = Vector3.fromEntityCenter(playerIn);
				Vector3 trackingendVec = vec.fromEntity(mobTarget).add(0, 1, 0);
				MainClass.proxy.lightningFX(vec, test, 1F, System.nanoTime(), Reference.oxblood, Reference.black);

				// mana.consume(5);

				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);

			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
	}
}
