package com.huto.hutosmod.worldgen;

import java.util.Random;

import com.huto.hutosmod.blocks.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStatePaletteRegistry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenHugeMorelMushroom extends WorldGenerator {
	/** The mushroom type. 0 for brown, 1 for red. */
	private final Block mushroomType;

	public WorldGenHugeMorelMushroom(Block p_i46449_1_) {
		super(true);
		this.mushroomType = p_i46449_1_;
	}

	public WorldGenHugeMorelMushroom() {
		super(false);
		this.mushroomType = null;
	}

	public boolean generate(World worldIn, Random rand, BlockPos position) {
		Block block = this.mushroomType;

		if (block == null) {
			block = rand.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK;
		}

		int i = rand.nextInt(3) + 2;

		/*
		 * if (rand.nextInt(12) == 0) { i *= 2; }
		 */

		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + i + 1 < 256) {
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
				int k = 3;

				if (j <= position.getY() + 3) {
					k = 0;
				}

				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < 256) {
							IBlockState state = worldIn.getBlockState(blockpos$mutableblockpos.setPos(l, j, i1));

							if (!state.getBlock().isAir(state, worldIn, blockpos$mutableblockpos)
									&& !state.getBlock().isLeaves(state, worldIn, blockpos$mutableblockpos)) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else {
				Block block1 = worldIn.getBlockState(position.down()).getBlock();

				if (block1 != Blocks.DIRT && block1 != Blocks.GRASS && block1 != Blocks.MYCELIUM
						&& block1 != Blocks.STONE && block1 != BlockRegistry.Mystic_Earth) {
					return false;
				} else {
					int k2 = position.getY() + i;

					for (int l2 = k2; l2 <= position.getY() + i; ++l2) {
						int j3 = 1;

						if (l2 < position.getY() + i) {
							++j3;
						}

						if (block == BlockRegistry.morel_cap) {
							j3 = 3;
						}

						int k3 = position.getX() - j3;
						int l3 = position.getX() + j3;
						int j1 = position.getZ() - j3;
						int k1 = position.getZ() + j3;

						for (int l1 = k3; l1 <= l3; ++l1) {
							for (int i2 = j1; i2 <= k1; ++i2) {
								int j2 = 5;

								if (l1 == k3) {
									--j2;
								} else if (l1 == l3) {
									++j2;
								}

								if (i2 == j1) {
									j2 -= 3;
								} else if (i2 == k1) {
									j2 += 3;
								}

								BlockHugeMushroom.EnumType blockhugemushroom$enumtype = BlockHugeMushroom.EnumType
										.byMetadata(j2);

								// Trims the corners off the cap
								if (block == BlockRegistry.morel_mushroom || l2 < position.getY() + i) {
									if ((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1)) {
										continue;
									}

								}

								if (position.getY() >= position.getY() + i - 1
										|| blockhugemushroom$enumtype != BlockHugeMushroom.EnumType.ALL_INSIDE) {
									BlockPos blockpos1 = new BlockPos(l1, l2, i2);
									BlockPos blockpos2 = new BlockPos(l1, l2 + 1, i2);
									BlockPos blockpos3 = new BlockPos(l1, l2 + 2, i2);
									BlockPos blockpos4 = new BlockPos(l1, l2 + 3, i2);
									BlockPos blockpos5 = new BlockPos(position.getX(), position.getY() + (i + 4),
											position.getZ());
									Random rand1 = new Random();
									int capSise = rand1.nextInt(3);
									BlockPos blockposRand;
									for (int c = 0; c < 4; c++) {
										blockposRand = new BlockPos(l1, l2 + c, i2);
										this.setBlockAndNotifyAdequately(worldIn, blockposRand,
												BlockRegistry.morel_cap.getDefaultState());
									}

									// Generates tip
									this.setBlockAndNotifyAdequately(worldIn, blockpos5,
											BlockRegistry.morel_cap.getDefaultState());
								}
							}
						}
					}

					for (int i3 = 0; i3 < i; ++i3) {
						IBlockState iblockstate = worldIn.getBlockState(position.up(i3));
						if (iblockstate.getBlock().canBeReplacedByLeaves(iblockstate, worldIn, position.up(i3))) {
							// Create initial stem
							this.setBlockAndNotifyAdequately(worldIn, position.up(i3),
									BlockRegistry.morel_stem.getDefaultState());
							// Create the stem inside of the cap
							this.setBlockAndNotifyAdequately(worldIn, position.up(i3).up(i),
									BlockRegistry.morel_stem.getDefaultState());
						}
					}

					return true;
				}
			}
		} else {
			return false;
		}
	}
}