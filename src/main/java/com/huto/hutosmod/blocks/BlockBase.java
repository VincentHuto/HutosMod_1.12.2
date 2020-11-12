package com.huto.hutosmod.blocks;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block {

	public BlockBase(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);

		BlockRegistry.BLOCKS.add(this);
		ItemRegistry.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}


}
