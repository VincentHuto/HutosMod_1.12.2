package com.huto.hutosmod.items.tools;

import java.util.Set;

import com.google.common.collect.Sets;
import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemKnapper extends ItemTool {
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.OBSIDIAN, BlockRegistry.runed_obsidian,
			BlockRegistry.activated_obsidian, BlockRegistry.nether_block);
private float speed;
	public ItemKnapper(String nameIn,float speedIn, ToolMaterial materialIn) {
		super(1.0F, -2.8F, materialIn, EFFECTIVE_ON);
		setUnlocalizedName(nameIn);
		setRegistryName(nameIn);
		setCreativeTab(MainClass.tabHutosMod);
		this.speed = speedIn;
		ItemRegistry.ITEMS.add(this);
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		Block block = blockIn.getBlock();
		if (block == BlockRegistry.activated_obsidian) {
			return this.toolMaterial.getHarvestLevel() == 2;
		}
		return this.toolMaterial.getHarvestLevel() == 1;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		Material material = state.getMaterial();
		Block stateBlock = state.getBlock();
		if (stateBlock == Blocks.OBSIDIAN || stateBlock == BlockRegistry.runed_obsidian
				|| stateBlock == BlockRegistry.activated_obsidian || stateBlock == BlockRegistry.nether_block) {
			return speed;
		} else {
			return 0.5f;
		}
	}
}
