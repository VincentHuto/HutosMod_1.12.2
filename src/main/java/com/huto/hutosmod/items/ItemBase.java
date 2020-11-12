package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;

import net.minecraft.item.Item;

public class ItemBase extends Item{

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		
		ItemRegistry.ITEMS.add(this);
	}
	

}
