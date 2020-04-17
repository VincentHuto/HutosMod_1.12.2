package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;

import net.minecraft.item.ItemAxe;

public class ToolAxe extends ItemAxe{

	public ToolAxe(String name, ToolMaterial material) {
		super(material,6.0F,-3.2F);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		
		ItemRegistry.ITEMS.add(this);
	}
	
}
