package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemManaBottle extends ItemSoup {

	public ItemManaBottle(String name, int healAmount) {
		super(healAmount);
		this.setMaxStackSize(1);
		this.setAlwaysEdible();
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		super.onItemUseFinish(stack, worldIn, entityLiving);
		if(entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			IMana mana = entityLiving.getCapability(ManaProvider.MANA_CAP	, null);
			mana.fill(30);
		}
		
		
		return new ItemStack(Items.GLASS_BOTTLE);
	}
}
