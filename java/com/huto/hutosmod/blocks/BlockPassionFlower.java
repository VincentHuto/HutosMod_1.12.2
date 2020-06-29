package com.huto.hutosmod.blocks;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.worldgen.WorldGenHugeMorelMushroom;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockPassionFlower extends BlockBush implements IGrowable {
	protected static final AxisAlignedBB MUSHROOM_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D,
			0.30000001192092896D, 0.699999988079071D, 0.4000000059604645D, 0.699999988079071D);

	public BlockPassionFlower(String name, Material material) {
		super(material);
		this.setTickRandomly(true);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.PLANT);
		setLightOpacity(1);
		setCreativeTab(MainClass.tabHutosMod);
		BlockRegistry.BLOCKS.add(this);
		ItemRegistry.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return MUSHROOM_AABB;
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (rand.nextInt(25) == 0) {
			int i = 5;
			int j = 4;

			for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
				if (worldIn.getBlockState(blockpos).getBlock() == this) {
					--i;

					if (i <= 0) {
						return;
					}
				}
			}

			BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

			for (int k = 0; k < 4; ++k) {
				if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1, this.getDefaultState())) {
					pos = blockpos1;
				}

				blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
			}

			if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1, this.getDefaultState())) {
				worldIn.setBlockState(blockpos1, this.getDefaultState(), 2);
			}
		}
	}

	/**
	 * Checks if this block can be placed exactly at the given position.
	 */
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, this.getDefaultState());
	}

	/**
	 * Return true if the block can sustain a Bush
	 */
	protected boolean canSustainBush(IBlockState state) {
		return state.isFullBlock();
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		if (pos.getY() >= 0 && pos.getY() < 256) {
			IBlockState iblockstate = worldIn.getBlockState(pos.down());

			if (iblockstate.getBlock() == Blocks.MYCELIUM || iblockstate.getBlock() == BlockRegistry.Mystic_Earth || iblockstate.getBlock() == Blocks.GRASS) {
				return true;
			} else if (iblockstate.getBlock() == Blocks.DIRT
					&& iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL) {
				return true;
			} else {
				return worldIn.getLight(pos) < 13 && iblockstate.getBlock().canSustainPlant(iblockstate, worldIn,
						pos.down(), net.minecraft.util.EnumFacing.UP, this);
			}
		} else {
			return false;
		}
	}


	/**
	 * Whether this IGrowable can grow
	 */
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double) rand.nextFloat() < 0.4D;
	}

	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
	}
}