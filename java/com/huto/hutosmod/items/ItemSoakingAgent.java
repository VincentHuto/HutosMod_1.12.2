package com.huto.hutosmod.items;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.particles.RingParticle;
import com.huto.hutosmod.particles.SphereParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSoakingAgent extends Item {

	public ItemSoakingAgent(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 64;

	}

	public static int count = 0;
	public static int spiralCount = 0;

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {

		count++;

		// spiralCount++;
		if (spiralCount == 360) {
			while (spiralCount > 0) {
				spiralCount--;
			}

		} else {
			spiralCount++;

		}
		Random rand = new Random();
		BlockPos pos = entityItem.getPosition();
		World world = entityItem.getEntityWorld();
		double ypos = pos.getY() + .5;
		double velocityX = 0, velocityBlueY = -0.005, velocityRedY = +0.005, velocityZ = 0;
		double redValue = 0;
		double blueValue = 0;
		double xMod = Math.sin(spiralCount);
		double zMod = Math.cos(spiralCount);
		int mod = 3 + rand.nextInt(10);
		int age = 200;

		if (!entityItem.getEntityWorld().isRaining() || !entityItem.getEntityWorld().isThundering()) {
			if (!entityItem.isAirBorne)
				if (count > 0) {
					if (world.isRemote) {
						SphereParticle newEffect = new SphereParticle(world, pos.getX() + 0.5 + xMod * 0.5, ypos,
								pos.getZ() + 0.5 + zMod * 0.5, (xMod * 0.1) * 0.01, velocityBlueY, (zMod * 0.1) * 0.01,
								0.0f, 0.0f, 0.84F, age, 0.1f);
						Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
					}
					count = 0;
				}
			if (entityItem.ticksExisted > 150) {
				world.getWorldInfo().setRainTime(0);
				world.getWorldInfo().setThunderTime(0);
				world.getWorldInfo().setRaining(true);
				world.getWorldInfo().setThundering(false);
				entityItem.setDead();
			}
		}

		return super.onEntityItemUpdate(entityItem);

	}

}
