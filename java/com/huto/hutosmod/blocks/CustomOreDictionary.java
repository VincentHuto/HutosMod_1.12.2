package com.huto.hutosmod.blocks;

import net.minecraftforge.oredict.OreDictionary;

public class CustomOreDictionary {

	
	public static void init() {
		OreDictionary.registerOre("oreEnchanted", BlockRegistry.enchanted_ore);
		OreDictionary.registerOre("oreEnchanted", BlockRegistry.enchanted_ore_mystic);

	}
	
}
