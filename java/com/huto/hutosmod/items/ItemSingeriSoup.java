package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSingeriSoup extends ItemSoup {

	public ItemSingeriSoup(String name, int healAmount) {
		super(healAmount);
		this.setMaxStackSize(1);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		super.onItemUseFinish(stack, worldIn, entityLiving);
		return new ItemStack(Items.BOWL);
	}
}
