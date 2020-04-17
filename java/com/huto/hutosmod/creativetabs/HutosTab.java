package com.huto.hutosmod.creativetabs;

import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HutosTab extends CreativeTabs
{
	public HutosTab(String label) 
	{
		super("tabHutosMod");
		this.setBackgroundImageName("item_search.png");
	}
	
	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(ItemRegistry.blood_ingot);
	}
}
