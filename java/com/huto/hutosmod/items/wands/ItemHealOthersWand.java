package com.huto.hutosmod.items.wands;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemHealOthersWand extends Item {

	public static float sync = 0;
	public static float mana = 0;

	public ItemHealOthersWand(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;

	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		sync++;
		sync %= 10;
		if (sync == 0)
			PacketHandler.INSTANCE
					.sendToServer(new PacketGetMana(mana, "com.huto.hutosmod.items.ItemHealOthersWand", "mana"));
		IMana mana = playerIn.getCapability(ManaProvider.MANA_CAP, null);

		if (target instanceof EntityLiving && mana.getMana() > 20) {
			Random rand = new Random();

			target.world.spawnParticle(EnumParticleTypes.HEART,
						playerIn.posX + (rand.nextDouble() - 0.5D) * (double) playerIn.width,
						playerIn.posY + rand.nextDouble() * (double) playerIn.height - (double) playerIn.getYOffset()
								- 0.5,
						playerIn.posZ + (rand.nextDouble() - 0.5D) * (double) playerIn.width, 0.0D, 0.0D, 0.0D);
			}
				target.setHealth(target.getHealth() + 0.5F);
				mana.consume(20);

			
		
		return true;
	}
}