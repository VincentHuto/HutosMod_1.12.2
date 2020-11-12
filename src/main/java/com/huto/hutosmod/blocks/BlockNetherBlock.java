package com.huto.hutosmod.blocks;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockNetherBlock extends Block {
    public static final PropertyEnum<BlockLog.EnumAxis> LOG_AXIS = PropertyEnum.<BlockLog.EnumAxis>create("axis", BlockLog.EnumAxis.class);

	
	public BlockNetherBlock(String name,Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.WOOD);
		setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
		setCreativeTab(MainClass.tabHutosMod);
				
		BlockRegistry.BLOCKS.add(this);
		ItemRegistry.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		IBlockState state = this.getDefaultState();
		
		switch(meta & 6)
		{
		case 0:
			state = state.withProperty(LOG_AXIS, EnumAxis.Y);
			break;
			
		case 2:
			state = state.withProperty(LOG_AXIS, EnumAxis.X);
			break;
			
		case 4:
			state = state.withProperty(LOG_AXIS, EnumAxis.Z);
			break;
			
		default:
			state = state.withProperty(LOG_AXIS, EnumAxis.NONE);
		}
		
		return state;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int i = 0;
		
		switch((BlockLog.EnumAxis)state.getValue(LOG_AXIS))
		{
		case X:
			i |= 2;
			break;
			
		case Y:
			i |= 4;
			break;
			
		case Z:
			i |= 6;
		}
		
		return i;
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) 
	{
		return new ItemStack(this);
	}
}