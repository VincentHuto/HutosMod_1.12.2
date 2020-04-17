package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;

import net.minecraft.item.ItemPickaxe;

public class ToolPickaxe extends ItemPickaxe{

	public ToolPickaxe(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

}
