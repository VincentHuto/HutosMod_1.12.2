package com.huto.hutosmod.blocks;

import java.util.Random;

import com.huto.hutosmod.particles.EntityBaseFX;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ore_enchantedBlock extends BlockBase {

	public ore_enchantedBlock(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(1.0F);
		setLightOpacity(1);
		// setBlockUnbreakable();
	}
/*	  @Override
	  @SideOnly(Side.CLIENT)
	  public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	  {
	    if (worldIn.isRemote) {  // is this on the client side?

	    
	      EntityBaseFX newEffect = new EntityBaseFX(worldIn, pos.getX(), pos.getY(), pos.getZ(), 0, 0.5, 0, 100, 100, 1, 0, 1, 1, 1, 1);
	      Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
	    }
	  }*/


}
