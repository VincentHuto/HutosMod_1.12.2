package com.huto.hutosmod.items.tools;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;

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