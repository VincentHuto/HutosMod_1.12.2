package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemResonantFuel extends Item {

	public ItemResonantFuel(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		maxStackSize = 3;
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

		@Override
		public int getItemBurnTime(ItemStack itemStack) {
			return 6400;
		}
}
