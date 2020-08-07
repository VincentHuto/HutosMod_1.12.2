package com.huto.hutosmod.blocks;

import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockPrimalOozeFluid extends BlockFluidClassic
{
	public BlockPrimalOozeFluid(String name, Fluid fluid, Material material) 
	{
		super(fluid, material);
		setUnlocalizedName(name);
		setRegistryName(name);
	
		BlockRegistry.BLOCKS.add(this);
		ItemRegistry.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) 
	{
		return EnumBlockRenderType.MODEL;
	}
}