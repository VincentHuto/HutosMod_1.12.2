package com.huto.hutosmod.items.tools;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.item.ItemSpade;

public class ToolShovel extends ItemSpade {

	public ToolShovel(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		
		ItemRegistry.ITEMS.add(this);
	}
	


}
