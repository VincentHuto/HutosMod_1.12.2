package com.huto.hutosmod.recipies;

import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModFurnaceRecipies {
	// Smelting
	public static void init() {

		GameRegistry.addSmelting(ItemRegistry.blood_ingot, new ItemStack(Items.REDSTONE, 3), 100.0F);

	}
}
