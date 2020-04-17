package com.huto.hutosmod.worldgen;

import java.util.Random;

import com.huto.hutosmod.blocks.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;

public class WorldGenMysticTree extends WorldGenAbstractTree {
	public static final IBlockState LOG = BlockRegistry.Mystic_Log.getDefaultState();
	public static final IBlockState LEAF = BlockRegistry.Mystic_Leaves.getDefaultState();
	private Random rand;
	int heightLimit;
	int heightLimitLimit = 12;
	private BlockPos basePos = BlockPos.ORIGIN;
	int trunkSize = 2;
	private World world;
	int height;

	public WorldGenMysticTree() {
		super(false);
	}

	public boolean generate(World worldIn, Random rand, BlockPos position) {
		// i modifies trunk height
		int i = rand.nextInt(3) + rand.nextInt(3) + 5;
		boolean flag = true;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
				int k = 1;

				if (j == position.getY()) {
					k = 0;
				}

				if (j >= position.getY() + 1 + i - 2) {
					k = 2;
				}

				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < 256) {
							if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
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
				BlockPos down = position.down();
				IBlockState state = worldIn.getBlockState(down);
				boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down,
						net.minecraft.util.EnumFacing.UP, ((net.minecraft.block.BlockSapling) Blocks.SAPLING));

				if (isSoil && position.getY() < worldIn.getHeight() - i - 1) {
					state.getBlock().onPlantGrow(state, worldIn, down, position);
					EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
					int rand1 = i - rand.nextInt(4) - 1;
					int rand2 = 3 - rand.nextInt(3);
					int x = position.getX();
					int z = position.getZ();
					int yIterat2 = 0;

					for (int l1 = 0; l1 < i; ++l1) {
						int yIterat = position.getY() + l1;

						if (l1 >= rand1 && rand2 > 0) {
							x += enumfacing.getFrontOffsetX();
							z += enumfacing.getFrontOffsetZ();
							--rand2;
						}

						BlockPos blockpos = new BlockPos(x, yIterat, z);
						state = worldIn.getBlockState(blockpos);

						if (state.getBlock().isAir(state, worldIn, blockpos)
								|| state.getBlock().isLeaves(state, worldIn, blockpos)) {
							this.placeLogAt(worldIn, blockpos);
							this.placeLogAt(worldIn, blockpos.north());
							this.placeLogAt(worldIn, blockpos.south());
							this.placeLogAt(worldIn, blockpos.east());
							this.placeLogAt(worldIn, blockpos.west());

							yIterat2 = yIterat;
						}
					}

					BlockPos blockpos2 = new BlockPos(x, yIterat2, z);
					// this controls the first layer of leaves
					// By increasing j3 and i4 it makes the sqaure of leaves/logs bigger
					for (int j3 = -3; j3 <= 3; ++j3) {
						for (int i4 = -3; i4 <= 3; ++i4) {
							// This statement clips the corners of the layer of logs
							if (Math.abs(j3) % 3 != 0 || Math.abs(i4) % 3 != 0) {

								this.placeLogAt(worldIn, blockpos2.add(j3, 1, i4));
								this.placeLeafAt(worldIn, blockpos2.add(j3, 0, i4));
								this.placeLeafAt(worldIn, blockpos2.add(j3, 1, i4));
								this.placeLeafAt(worldIn, blockpos2.add(j3, 0, i4));

							}
						}
					}

					for (int j3 = 0; j3 <= 8; ++j3) {
						for (int i4 = 0; i4 <= 8; ++i4) {
							// This statement clips the corners of the layer of logs, also gens middle layer
							if (Math.abs(j3) % 2 != 0 || Math.abs(i4) % 2 != 0) {
								this.placeLeafAt(worldIn, blockpos2.add(j3 - 4, 1, i4 - 4));

							}
						}
					}

					blockpos2 = blockpos2.up();

					for (int k3 = -3; k3 <= 3; ++k3) {
						for (int j4 = -3; j4 <= 3; ++j4) {
							// This statement clips the corners of the layer of logs
							if (Math.abs(k3) % 3 != 0 || Math.abs(j4) % 3 != 0) {

								// This is the second layer of leaves
								this.placeLeafAt(worldIn, blockpos2.add(k3, 1, j4));

							}
						}
					}
					
					//GENS THE VINES
					for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; ++i2) {
						int k2 = i2 - (position.getY() + i);
						int i3 = 2 - k2 / 4;
						BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

						for (int l4 = position.getX() - i3; l4 <= position.getX() + i3; ++l4) {
							for (int j4 = position.getZ() - i3; j4 <= position.getZ() + i3; ++j4) {
								mutableBlockPos.setPos(l4, i2, j4);

								if (worldIn.getBlockState(mutableBlockPos).getMaterial() == Material.LEAVES
										|| worldIn.getBlockState(mutableBlockPos).getMaterial() == Material.WOOD) {
									BlockPos blockposW = mutableBlockPos.west();
									BlockPos blockposE = mutableBlockPos.east();
									BlockPos blockposN = mutableBlockPos.north();
									BlockPos blockposS = mutableBlockPos.south();

									if (worldIn.isAirBlock(blockposE)) {
										this.addVine(worldIn, blockposE, BlockVine.EAST);
									}

									if (worldIn.isAirBlock(blockposW)) {
										this.addVine(worldIn, blockposW, BlockVine.WEST);
									}

									if (worldIn.isAirBlock(blockposS)) {
										this.addVine(worldIn, blockposS, BlockVine.SOUTH);
									}

									if (worldIn.isAirBlock(blockposN)) {
										this.addVine(worldIn, blockposN, BlockVine.NORTH);
									}
								}
							}
						}
					}

					this.placeLeafAt(worldIn, blockpos2.east(3));
					this.placeLeafAt(worldIn, blockpos2.west(3));
					this.placeLeafAt(worldIn, blockpos2.south(3));
					this.placeLeafAt(worldIn, blockpos2.north(3));

					//THIS PLAYCES THE MIDDLE BLOCK FLOATING
					this.placeLogAt(worldIn, blockpos2.add(0,1,0));
					this.placeGlowstoneAt(worldIn, blockpos2.add(0,2,0));				
					this.placeLeafAt(worldIn, blockpos2.add(1,2,0));
					this.placeLeafAt(worldIn, blockpos2.add(0,2,1));
					this.placeLeafAt(worldIn, blockpos2.add(0,2,-1));
					this.placeLeafAt(worldIn, blockpos2.add(-1,2,0));

					
					
					x = position.getX();
					z = position.getZ();
					EnumFacing enumfacing1 = EnumFacing.Plane.HORIZONTAL.random(rand);

					if (enumfacing1 != enumfacing) {
						int l3 = rand1 - rand.nextInt(2) - 1;
						// k4 is somthing with maybe

						int k4 = 1 + rand.nextInt(6);
						yIterat2 = 0;

						for (int l4 = l3; l4 < i && k4 > 0; --k4) {
							if (l4 >= 1) {
								int j2 = position.getY() + l4;
								x += enumfacing1.getFrontOffsetX();
								z += enumfacing1.getFrontOffsetZ();
								BlockPos blockpos1 = new BlockPos(x, j2, z);
								state = worldIn.getBlockState(blockpos1);

								if (state.getBlock().isAir(state, worldIn, blockpos1)
										|| state.getBlock().isLeaves(state, worldIn, blockpos1)) {

									yIterat2 = j2;

									this.world = worldIn;
									this.basePos = position;
									this.rand = new Random(rand.nextLong());
									this.placeLogAt(worldIn, blockpos1);

									if (this.heightLimit == 0) {
										this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
									}
									// this.generateTrunk();

								}
							}

							--l4;
						}

						if (yIterat2 > 0) {
							BlockPos blockpos3 = new BlockPos(x, yIterat2, z);

							for (int i5 = -2; i5 <= 2; ++i5) {
								for (int k5 = -2; k5 <= 2; ++k5) {
									if (Math.abs(i5) != 2 || Math.abs(k5) != 2) {
										this.placeLeafAt(worldIn, blockpos3.add(i5, 0, k5));
										// THIS IS WHERE THE LIMBS LEAVES ARE GENRATED
									}
								}
							}
						}
					}

					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	private void placeLogAt(World worldIn, BlockPos pos) {
		this.setBlockAndNotifyAdequately(worldIn, pos, LOG);
	}

	private void placeLeafAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);

		if (state.getBlock().isAir(state, worldIn, pos) || state.getBlock().isLeaves(state, worldIn, pos)) {
			this.setBlockAndNotifyAdequately(worldIn, pos, LEAF);
		}
	}
	
	private void placeGlowstoneAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);

		if (state.getBlock().isAir(state, worldIn, pos) || state.getBlock().isLeaves(state, worldIn, pos)) {
			this.setBlockAndNotifyAdequately(worldIn, pos, Blocks.GLOWSTONE.getDefaultState());
		}
	}

	/**
	 * Places the trunk for the big tree that is being generated. Able to generate
	 * double-sized trunks by changing a field that is always 1 to 2.
	 */
	void generateTrunk() {
		BlockPos blockpos = this.basePos;
		BlockPos blockpos1 = this.basePos.up(this.height);
		Block block = Blocks.LOG;
		this.limb(blockpos, blockpos1, block);

		if (this.trunkSize == 2) {
			this.limb(blockpos.east(), blockpos1.east(), block);
			this.limb(blockpos.east().south(), blockpos1.east().south(), block);
			this.limb(blockpos.south(), blockpos1.south(), block);
		}
	}

	/**
	 * Returns the absolute greatest distance in the BlockPos object.
	 */
	private int getGreatestDistance(BlockPos posIn) {
		int i = MathHelper.abs(posIn.getX());
		int j = MathHelper.abs(posIn.getY());
		int k = MathHelper.abs(posIn.getZ());

		if (k > i && k > j) {
			return k;
		} else {
			return j > i ? j : i;
		}
	}

	void limb(BlockPos p_175937_1_, BlockPos p_175937_2_, Block p_175937_3_) {
		BlockPos blockpos = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
		int i = this.getGreatestDistance(blockpos);
		float f = (float) blockpos.getX() / (float) i;
		float f1 = (float) blockpos.getY() / (float) i;
		float f2 = (float) blockpos.getZ() / (float) i;

		for (int j = 0; j <= i; ++j) {
			BlockPos blockpos1 = p_175937_1_.add((double) (0.5F + (float) j * f), (double) (0.5F + (float) j * f1),
					(double) (0.5F + (float) j * f2));
			BlockLog.EnumAxis blocklog$enumaxis = this.getLogAxis(p_175937_1_, blockpos1);
			this.setBlockAndNotifyAdequately(this.world, blockpos1,
					p_175937_3_.getDefaultState().withProperty(BlockLog.LOG_AXIS, blocklog$enumaxis));
		}
	}

	private BlockLog.EnumAxis getLogAxis(BlockPos p1, BlockPos p2) {
		BlockLog.EnumAxis blocklog$enumaxis = BlockLog.EnumAxis.Y;
		int i = Math.abs(p2.getX() - p1.getX());
		int j = Math.abs(p2.getZ() - p1.getZ());
		int k = Math.max(i, j);

		if (k > 0) {
			if (i == k) {
				blocklog$enumaxis = BlockLog.EnumAxis.X;
			} else if (j == k) {
				blocklog$enumaxis = BlockLog.EnumAxis.Z;
			}
		}

		return blocklog$enumaxis;
	}

	private void addVine(World worldIn, BlockPos pos, PropertyBool prop) {
		IBlockState iblockstate = BlockRegistry.Mystic_Vine.getDefaultState().withProperty(prop, Boolean.valueOf(true));
		this.setBlockAndNotifyAdequately(worldIn, pos, iblockstate);
		// I changes how far the vines start in length, they will still grow however,
		// i=2 = 2+base vine
		int i = 1;

		for (BlockPos blockpos = pos.down(); worldIn.isAirBlock(blockpos) && i > 0; --i) {
			this.setBlockAndNotifyAdequately(worldIn, blockpos, iblockstate);
			blockpos = blockpos.down();
		}
	}

	@Override
	protected boolean canGrowInto(Block blockType) {
		Material material = blockType.getDefaultState().getMaterial();
		return material == Material.AIR || material == Material.LEAVES || material == Material.GROUND
				|| blockType == Blocks.GRASS||blockType == BlockRegistry.Mystic_Earth || blockType == Blocks.DIRT || blockType == Blocks.LOG
				|| blockType == Blocks.LOG2 || blockType == Blocks.SAPLING || blockType == Blocks.VINE;

	}
}