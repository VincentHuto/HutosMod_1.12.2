package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCookedMorelStick extends ItemFood {

	public ItemCookedMorelStick(String name, int amount, boolean isWolfFood) {
		super(amount, isWolfFood);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);

	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		super.onItemUseFinish(stack, worldIn, entityLiving);
		return new ItemStack(Items.STICK, 1);
	}
}
