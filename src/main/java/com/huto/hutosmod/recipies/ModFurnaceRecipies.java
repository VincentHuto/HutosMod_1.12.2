package com.huto.hutosmod.recipies;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModFurnaceRecipies {
	// Smelting
	public static void init() {

		GameRegistry.addSmelting(ItemRegistry.null_ingot, new ItemStack(Items.REDSTONE, 3), 100.0F);
		GameRegistry.addSmelting(BlockRegistry.enchanted_stone, new ItemStack(BlockRegistry.enchanted_stone_smooth, 1), 100.0F);
		GameRegistry.addSmelting(ItemRegistry.raw_morel_on_a_stick, new ItemStack(ItemRegistry.cooked_morel_on_a_stick, 1), 100.0F);

	}
}
