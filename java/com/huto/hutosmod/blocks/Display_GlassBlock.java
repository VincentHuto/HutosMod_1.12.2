package com.huto.hutosmod.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Display_GlassBlock extends BlockBase {

	public Display_GlassBlock(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.GLASS);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(1.0F);
		setLightOpacity(15);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	@Override 
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	@Override 
	protected boolean canSilkHarvest() {
		return true;
	}
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}
