package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEnchancedOverlay extends Item {

	public ItemEnchancedOverlay(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		maxStackSize = 1;
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

}
