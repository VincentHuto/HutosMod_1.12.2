package com.huto.hutosmod.dimension.test;

import java.util.List;
import java.util.Random;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.worldgen.MapGenLavalessCaves;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class TestDimGen implements IChunkGenerator {
	protected static final IBlockState STONE = BlockRegistry.nightmare_media.getDefaultState();
	protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
	protected static final IBlockState OOZE = BlockRegistry.primal_ooze_fluid.getDefaultState();
	protected static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
	private final Random rand;
	private NoiseGeneratorOctaves perlinCtx2;
	private NoiseGeneratorOctaves perlinCtx3;
	private NoiseGeneratorOctaves perlinCtx1;
	private NoiseGeneratorPerlin heightCtx;
	public NoiseGeneratorOctaves scaleCtx;
	public NoiseGeneratorOctaves depthCtx;
	public NoiseGeneratorOctaves forestCtx;
	private final World world;
	private final WorldType terrainType;
	private final double[] heightMap;
	private final float[] field_185999_r;
	private ChunkGeneratorSettings settings;
	private double[] field_186002_u = new double[256];
	private Biome[] biomesForGeneration;
	double[] perlinArray1;
	double[] perlinArray2;
	double[] perlinArray3;
	double[] depthCtxNoiseOctaves;
	private final NoiseGeneratorOctaves noiseGen4;
	private double stoneNoise[];
	private MapGenBase caveGenerator;

	public TestDimGen(World worldIn, long seed) {
		this.world = worldIn;
		this.world.setSeaLevel(10);
		this.terrainType = worldIn.getWorldInfo().getTerrainType();
		this.rand = new Random(seed);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
		this.stoneNoise = new double[256];
		this.perlinCtx2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.perlinCtx3 = new NoiseGeneratorOctaves(this.rand, 16);
		this.perlinCtx1 = new NoiseGeneratorOctaves(this.rand, 8);
		this.heightCtx = new NoiseGeneratorPerlin(this.rand, 4);
		this.scaleCtx = new NoiseGeneratorOctaves(this.rand, 10);
		this.depthCtx = new NoiseGeneratorOctaves(this.rand, 16);
		this.forestCtx = new NoiseGeneratorOctaves(this.rand, 8);
		this.heightMap = new double[825];
		this.field_185999_r = new float[25];
		caveGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(new MapGenLavalessCaves(),
				net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE);
		for (int i = -2; i <= 2; ++i) {
			for (int j = -2; j <= 2; ++j) {
				float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
				this.field_185999_r[i + 2 + (j + 2) * 5] = f;
			}
		}
		this.settings = new ChunkGeneratorSettings.Factory().build();
		net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld ctx = new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld(
				perlinCtx2, perlinCtx3, perlinCtx1, heightCtx, scaleCtx, depthCtx, forestCtx);

		ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(worldIn, this.rand, ctx);
		this.perlinCtx2 = ctx.getLPerlin1();
		this.perlinCtx3 = ctx.getLPerlin2();
		this.perlinCtx1 = ctx.getPerlin();
		this.heightCtx = ctx.getHeight();
		this.scaleCtx = ctx.getScale();
		this.depthCtx = ctx.getDepth();
		this.forestCtx = ctx.getForest();
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
		ChunkPrimer chunkprimer = new ChunkPrimer();
		this.setBlocksInChunk(x, z, chunkprimer);

		// addIceForestTop(x, z, chunkprimer);
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16,
				16);
		this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);

		this.caveGenerator.generate(this.world, x, z, chunkprimer);

		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);

		byte[] abyte = chunk.getBiomeArray();

		for (int i = 0; i < abyte.length; ++i) {
			abyte[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(int x, int z) {
		BlockFalling.fallInstantly = true;
		int i = x * 16;
		int j = z * 16;
		BlockPos blockpos = new BlockPos(i, 0, j);
		Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
		this.rand.setSeed(this.world.getSeed());
		long k = this.rand.nextLong() / 2L * 2L + 1L;
		long l = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long) x * k + (long) z * l ^ this.world.getSeed());
		//net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
	//	biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
		if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, false,
				net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS))
			WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
		//blockpos = blockpos.add(8, 0, 8);

	//	net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
		BlockFalling.fallInstantly = false;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		Biome biome = this.world.getBiome(pos);
		return biome.getSpawnableList(creatureType);
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		return null;
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@SuppressWarnings("unused")
	private void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
		{
			this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration,
					x * 4 - 2, z * 4 - 2, 10, 10);
			this.generateHeightmap(x * 4, 0, z * 4);

			for (int i = 0; i < 4; ++i) {
				int j = i * 5;
				int k = (i + 1) * 5;

				for (int l = 0; l < 4; ++l) {
					int i1 = (j + l) * 33;
					int j1 = (j + l + 1) * 33;
					int k1 = (k + l) * 33;
					int l1 = (k + l + 1) * 33;

					for (int i2 = 0; i2 < 32; ++i2) {
						double d0 = 0.125D;
						double d1 = this.heightMap[i1 + i2];
						double d2 = this.heightMap[j1 + i2];
						double d3 = this.heightMap[k1 + i2];
						double d4 = this.heightMap[l1 + i2];

						double terraceMod = 1 + MathHelper.sin(rand.nextInt(360));
//					 	System.out.println(terraceMod);

						double d5 = (this.heightMap[i1 + i2 + 1] - d1 % terraceMod) * 0.125D;
						double d6 = (this.heightMap[j1 + i2 + 1] - d2 % terraceMod) * 0.125D;
						double d7 = (this.heightMap[k1 + i2 + 1] - d3 % terraceMod) * 0.125D;
						double d8 = (this.heightMap[l1 + i2 + 1] - d4 % terraceMod) * 0.125D;
						for (int j2 = 0; j2 < 8; ++j2) {
							double d9 = 0.25D;
							double d10 = d1;
							double d11 = d2;
							double d12 = (d3 - d1) * 0.25D;
							double d13 = (d4 - d2) * 0.25D;

							for (int k2 = 0; k2 < 4; ++k2) {
								double d14 = 0.25D;
								double d16 = (d11 - d10) * 0.25D;
								double lvt_45_1_ = d10 - d16;

								for (int l2 = 0; l2 < 4; ++l2) {
									if ((lvt_45_1_ += d16) > 0.0D) {
										this.world.setSeaLevel(10);
										primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, STONE);
									} else if (i2 * 8 + j2 < this.settings.seaLevel - (47 + rand.nextInt(2))) {
										primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2,
												BlockRegistry.nightmare_media.getDefaultState());
									} else if (i2 * 8 + j2 < this.settings.seaLevel - 46) {
										primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, OOZE);
									}
								}

								d10 += d12;
								d11 += d13;
							}

							d1 += d5;
							d2 += d6;
							d3 += d7;
							d4 += d8;
						}
					}
				}
			}
		}
	}

	private void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
		if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world))
			return;
		double d0 = 0.03125D;
		this.field_186002_u = this.heightCtx.getRegion(this.field_186002_u, (double) (x * 16), (double) (z * 16), 16,
				16, d0 * 2.0D, d0 * 2.0D, 1.0D);
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				Biome Biome = biomesIn[j + i * 16];
				generateBiomeTerrain(this.world, this.rand, primer, x * 16 + i, z * 16 + j,
						this.field_186002_u[j + i * 16], Biome);
			}
		}
	}

	@SuppressWarnings("unused")
	private void generateBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z,
			double noiseVal, Biome biome) {
		this.world.setSeaLevel(10);
		int i = worldIn.getSeaLevel();
		IBlockState iblockstate = biome.topBlock;
		IBlockState iblockstate1 = biome.fillerBlock;
		int j = -1;
		int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int l = x & 15;
		int i1 = z & 15;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		for (int j1 = 255; j1 >= 0; --j1) {
			if (j1 == 0) {
				chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
			} else {
				IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);
				if (iblockstate2.getMaterial() == Material.AIR) {
					j = -1;
				} else if (iblockstate2.getBlock() == STONE.getBlock()) {
					if (j == -1) {
						if (k <= 0) {
							iblockstate = AIR;
							iblockstate1 = STONE;
						} else if (j1 >= i - 4 && j1 <= i + 1) {
							iblockstate = biome.topBlock;
							iblockstate1 = biome.fillerBlock;
						}
						if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
							iblockstate1 = OOZE;
						}
						j = k;
						if (j1 >= i - 1) {
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
						} else if (j1 < i - 7 - k) {
							iblockstate = AIR;
							iblockstate1 = STONE;
							// chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
						} else {
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
						}
					} else if (j > 0) {
						--j;
						chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
						if (j == 0 && iblockstate1.getBlock() == Blocks.SAND) {
							j = rand.nextInt(4);
							iblockstate1 = STONE;
						}
					}
				}
			}
		}
	}

	private void generateHeightmap(int xOffset, int yOffset, int zOffset) {
		
		this.depthCtxNoiseOctaves = this.depthCtx.generateNoiseOctaves(this.depthCtxNoiseOctaves, xOffset, zOffset, 5, 5,(double) this.settings.depthNoiseScaleX, (double) this.settings.depthNoiseScaleZ,(double) this.settings.depthNoiseScaleExponent);
		float coordinateScale = this.settings.coordinateScale;
		float heightScale = this.settings.heightScale;
		this.perlinArray1 = this.perlinCtx1.generateNoiseOctaves(this.perlinArray1, xOffset, yOffset,zOffset, 5, 33, 5, (double) (coordinateScale / this.settings.mainNoiseScaleX),(double) (heightScale / this.settings.mainNoiseScaleY), (double) (coordinateScale / this.settings.mainNoiseScaleZ));
		this.perlinArray2 = this.perlinCtx2.generateNoiseOctaves(this.perlinArray2, xOffset, yOffset,zOffset, 5, 33, 5, (double) coordinateScale, (double) heightScale, (double) coordinateScale);
		this.perlinArray3 = this.perlinCtx3.generateNoiseOctaves(this.perlinArray3, xOffset, yOffset,zOffset, 5, 33, 5, (double) coordinateScale, (double) heightScale, (double) coordinateScale);
		int i = 0;
		int j = 0;
		for (int k = 0; k < 5; ++k) {
			for (int l = 0; l < 5; ++l) {
				float f2 = 0.0F;
				float f3 = 0.0F;
				float f4 = 0.0F;
				int i1 = 2;
				Biome biome = this.biomesForGeneration[k + 2 + (l + 2) * 10];
				for (int j1 = -i1; j1 <= i1; ++j1) {
					for (int k1 = -i1; k1 <= i1; ++k1) {
						Biome biome1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
						float biomeDepthWeightMod = this.settings.biomeDepthOffSet+ biome1.getBaseHeight() * this.settings.biomeDepthWeight;
						float biomeHeightWeightMod = this.settings.biomeScaleOffset + biome1.getHeightVariation() * this.settings.biomeScaleWeight;
						if (this.terrainType == WorldType.AMPLIFIED && biomeDepthWeightMod > 0.0F) {
							biomeDepthWeightMod = 1.0F + biomeDepthWeightMod * 2.0F;
							biomeHeightWeightMod = 1.0F + biomeHeightWeightMod * 4.0F;
						}
						float f7 = this.field_185999_r[j1 + 2 + (k1 + 2) * 5] / (biomeDepthWeightMod + 2.0F);
						if (biome1.getBaseHeight() > biome.getBaseHeight()) {
							f7 /= 2.0F;
						}
						f2 += biomeHeightWeightMod * f7;
						f3 += biomeDepthWeightMod * f7;
						f4 += f7;
					}
				}
				
				//f2 is some form of height scale
				f2 = f2 / f4;
				f3 = f3 / f4;
				f2 = f2 * 0.9F + 0.1F;
				
				//f3 is just where the surface level is higher number = higher level
				f3 = (f3 * 9 - 1.0F) / 1.0F;
				double d7 = this.depthCtxNoiseOctaves[j] / 8000.0D;
				if (d7 < 0.0D) {
					d7 = -d7 * 0.3D;
				}
				//Makes more juting pillars in the rivers and increases level

				d7 = d7 * 3.0D - 2.0D;
				if (d7 < 0.0D) {
					d7 = d7 / 2.0D;
					//Something with the steepness of the river banks?
					if (d7 < -3.0D) {
						d7 = -1.0D;
					}
					d7 = d7 / 1.7D;
					d7 = d7 / 2.0D;
				} else {
					if (d7 > 1.0D) {
						d7 = 1.0D;
					}
					d7 = d7 / 8.0D;
				}
				++j;
				double d8 = (double) f3 ;
				double d9 = (double) f2 ;
				//Increasing d8 mkes large gaps between land masses with more extreme peaks, so i think it just makes highs high and lows lower
				//This value is the nice stable one the current one is jagged
					d8 = d8 + d7 *8;

				//d8 = d8 + d7 *MathHelper.nextDouble(rand, 6, 10);
				d8 = d8 * (double) this.settings.baseSize / 8.0D;
				double d0 = (double) this.settings.baseSize + d8 * 4.0D;
				for (int l1 = 0; l1 < 33; ++l1) {
					double d1 = ((double) l1 - d0) * this.settings.stretchY * 128.0D / 256.0D / d9;
					if (d1 < 0.0D) {
						d1 *= 4.0D;
					}
					double d2 = this.perlinArray2[i] / (double) this.settings.lowerLimitScale;
					double d3 = this.perlinArray3[i] / (double) this.settings.upperLimitScale;
					double d4 = (this.perlinArray1[i] / 1.0D + 1.0D) / 2.0D;
					double d5 = MathHelper.clamp(d2, d3, d4) - d1;
					if (l1 > 29) {
						double d6 = (double) ((float) (l1 - 29) / 15.0F);
						d5 = d5 * (1.0D - d6) + -1.0D * d6;
					}
					this.heightMap[i] = d5 * rand.nextInt(10);
					this.heightMap[j] = d5 % rand.nextInt(10);
					this.heightMap[j] *= rand.nextInt(10);
					++i;
				}
			}
		}
	}
}
