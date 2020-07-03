package com.huto.hutosmod.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMindFog extends BlockBase {

	public BlockMindFog(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.SNOW);
//		setLightLevel(1.0F);
	}

/*	@SideOnly(Side.CLIENT)
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
	}*/

}
