package com.huto.hutosmod.blocks;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class BlockMysticEarth extends BlockBase implements IGrowable {

	public BlockMysticEarth(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.GROUND);
		//setHardness(0.3f);
		setHardness(1.0F);
		setResistance(1.0F);
		setHarvestLevel("shovel", 1);

	}

	/*@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (!worldIn.isAreaLoaded(pos, 2))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and
						// spreading
			if (worldIn.getLightFromNeighbors(pos.up()) < 4
					&& worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
				worldIn.setBlockState(pos,
						Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
			} else {
				if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
					for (int i = 0; i < 4; ++i) {
						BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
						IBlockState iblockstate = worldIn.getBlockState(blockpos);
						IBlockState iblockstate1 = worldIn.getBlockState(blockpos.up());

						if (iblockstate.getBlock() == Blocks.DIRT
								&& iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT
								&& worldIn.getLightFromNeighbors(blockpos.up()) >= 4
								&& iblockstate1.getLightOpacity(worldIn, blockpos.up()) <= 2) {
							worldIn.setBlockState(blockpos, this.getDefaultState());
						}
					}
				}
			}
		}
	}*/

	/**
	 * Whether this IGrowable can grow
	 */
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override

	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		BlockPos blockpos = pos.up();

		for (int i = 0; i < 128; ++i) {
			BlockPos blockpos1 = blockpos;
			int j = 0;

			while (true) {
				if (j >= i / 16) {
					if (worldIn.isAirBlock(blockpos1)) {
						if (rand.nextInt(8) == 0) {
							worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1);
						} else {
							IBlockState iblockstate1 = Blocks.TALLGRASS.getDefaultState()
									.withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);

							if (Blocks.TALLGRASS.canBlockStay(worldIn, blockpos1, iblockstate1)) {
								worldIn.setBlockState(blockpos1, iblockstate1, 3);
							}
						}
					}

					break;
				}

				blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2,
						rand.nextInt(3) - 1);

				if (worldIn.getBlockState(blockpos1.down()).getBlock() != BlockRegistry.Mystic_Earth
						|| worldIn.getBlockState(blockpos1).isNormalCube()) {
					break;
				}

				++j;
			}
		}
	}
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
			IPlantable plantable) {
		// TODO Auto-generated method stub
			return true;
	}

}
