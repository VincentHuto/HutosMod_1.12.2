package com.huto.hutosmod.items;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemAbsorbWand extends Item {

	public static float sync = 0;
	public static float mana = 0;

	public ItemAbsorbWand(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);

		sync++;
		sync %= 10;
		if (sync == 0)
			PacketHandler.INSTANCE
					.sendToServer(new PacketGetMana(mana, "com.huto.hutosmod.items.ItemConsumeWand", "mana"));

		IMana mana = playerIn.getCapability(ManaProvider.MANA_CAP, null);
		Random rand = new Random();
		for (int countparticles = 0; countparticles <= 30; ++countparticles) {
			if (worldIn.isRemote) {
				worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH,
						playerIn.posX + (rand.nextDouble() - 0.5D) * (double) playerIn.width,
						playerIn.posY + rand.nextDouble() * (double) playerIn.height - (double) playerIn.getYOffset()
								- 0.5,
						playerIn.posZ + (rand.nextDouble() - 0.5D) * (double) playerIn.width, 0.0D, 0.0D, 0.0D);
			}

		}
		mana.fill(20);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);

	}

}
