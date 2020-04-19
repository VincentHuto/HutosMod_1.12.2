package com.huto.hutosmod.items.wands;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemGreatFireballWand extends Item {

	public static float sync = 0;
	public static float mana = 0;

	public ItemGreatFireballWand(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;

	}



	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);

		Vec3d aim = playerIn.getLookVec();
		EntityLargeFireball fireball = new EntityLargeFireball(worldIn, playerIn, 1, 1, 1);

		sync++;
		sync %= 10;
		if (sync == 0)
			PacketHandler.INSTANCE
					.sendToServer(new PacketGetMana(mana, "com.huto.hutosmod.items.ItemGreatFireballWand", "mana"));

		IMana mana = playerIn.getCapability(ManaProvider.MANA_CAP, null);
		if (mana.getMana() >=  10F) {

			Random rand = new Random();
			for (int countparticles = 0; countparticles <= 30; ++countparticles) {
				worldIn.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY,
						playerIn.posX + (rand.nextDouble() - 0.5D) * (double) playerIn.width,
						playerIn.posY + rand.nextDouble() * (double) playerIn.height - (double) playerIn.getYOffset()
								- 1,
						playerIn.posZ + (rand.nextDouble() - 0.5D) * (double) playerIn.width, 0.0D, 0.0D, 0.0D);

			}
			mana.consume(50);
			fireball.setPosition(playerIn.posX + aim.x * 1.5D, playerIn.posY + aim.y +1* 1.5D,
					playerIn.posZ + aim.z * 1.5D);
			fireball.accelerationX = aim.x * 0.1;
			fireball.accelerationY = aim.y * 0.1;
			fireball.accelerationZ = aim.z * 0.1;
			worldIn.spawnEntity(fireball);
			itemStack.damageItem(1, playerIn);

			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);

		} else
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);

	}
}
