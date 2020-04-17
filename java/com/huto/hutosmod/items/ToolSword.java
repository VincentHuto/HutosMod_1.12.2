package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;

import net.minecraft.item.ItemSword;

public class ToolSword extends ItemSword{

	public ToolSword(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);

		ItemRegistry.ITEMS.add(this);
	}

}