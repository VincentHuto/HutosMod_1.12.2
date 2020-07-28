package com.huto.hutosmod.dimension.dreamscape;

import java.util.List;
import java.util.Random;

import com.huto.hutosmod.blocks.BlockRegistry;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
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
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class DreamScapeGen implements IChunkGenerator
{
	protected static final IBlockState STONE = BlockRegistry.Mystic_Media.getDefaultState();
	protected static final IBlockState EARTH = BlockRegistry.Mystic_Earth.getDefaultState();
	protected static final IBlockState MINDFOG = BlockRegistry.mindfog.getDefaultState();
	protected static final IBlockState OOZE = BlockRegistry.primal_ooze_fluid.getDefaultState();
	protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
	protected static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
	private final Random rand;
	private NoiseGeneratorOctaves noiseGenOct_j;
	private NoiseGeneratorOctaves noiseGenOct_k;
	private NoiseGeneratorOctaves noiseGenOct_l;
	private NoiseGeneratorPerlin noiseGenPerl_m;
	public NoiseGeneratorOctaves noiseGenOct_b;
	public NoiseGeneratorOctaves noiseGenOct_c;
	public NoiseGeneratorOctaves noiseGenOct_d;
	private final World world;
	private final WorldType terrainType;
	private final double[] heightMap;
	private final float[] field_185999_r;
	private ChunkGeneratorSettings settings;
	private double[] field_186002_u = new double[256];
	private Biome[] biomesForGeneration;
	double[] field_185986_e;
	double[] field_185987_f;
	double[] field_185988_g;
	double[] field_185989_h;
	private final NoiseGeneratorOctaves noiseGen4;
	private double stoneNoise[];
	private MapGenBase caveGenerator;
    private double[] buffer;

	public DreamScapeGen(World worldIn, long seed) {
		
		this.world = worldIn;
		this.terrainType = worldIn.getWorldInfo().getTerrainType();
		this.rand = new Random(seed);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
		this.stoneNoise = new double[256];
		this.noiseGenOct_j = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGenOct_k = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGenOct_l = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGenPerl_m = new NoiseGeneratorPerlin(this.rand, 4);
		this.noiseGenOct_b = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGenOct_c = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGenOct_d = new NoiseGeneratorOctaves(this.rand, 8);
		this.heightMap = new double[825];
		this.field_185999_r = new float[25];
		this.world.setSeaLevel(10);
		caveGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(new MapGenCaves(),
				net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE);

		for (int i = -2; i <= 2; ++i) {
			for (int j = -2; j <= 2; ++j) {
				float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
				this.field_185999_r[i + 2 + (j + 2) * 5] = f;
			}
		}
		this.settings = new ChunkGeneratorSettings.Factory().build();
		net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld ctx = new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextOverworld(
				noiseGenOct_j, noiseGenOct_k, noiseGenOct_l, noiseGenPerl_m, noiseGenOct_b, noiseGenOct_c,
				noiseGenOct_d);

		ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(worldIn, this.rand, ctx);
		this.noiseGenOct_j = ctx.getLPerlin1();
		this.noiseGenOct_k = ctx.getLPerlin2();
		this.noiseGenOct_l = ctx.getPerlin();
		this.noiseGenPerl_m = ctx.getHeight();
		this.noiseGenOct_b = ctx.getScale();
		this.noiseGenOct_c = ctx.getDepth();
		this.noiseGenOct_d = ctx.getForest();
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		this.world.setSeaLevel(10);

		this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
		ChunkPrimer chunkprimer = new ChunkPrimer();
        this.prepareHeights(x, z, chunkprimer);
		this.setBlocksInChunk(x, z, chunkprimer);
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16,
				16);
		this.replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);

		this.caveGenerator.generate(this.world, x, z, chunkprimer);

		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);

		byte[] abyte = chunk.getBiomeArray();

		for (int i = 0; i < abyte.length; ++i) {
			abyte[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
		}
        chunk.resetRelightChecks();

		chunk.generateSkylightMap();
		return chunk;
	}
	private double[] getHeights(double[] p_185938_1_, int p_185938_2_, int p_185938_3_, int p_185938_4_, int p_185938_5_, int p_185938_6_, int p_185938_7_)
    {
        if (p_185938_1_ == null)
        {
            p_185938_1_ = new double[p_185938_5_ * p_185938_6_ * p_185938_7_];
        }

        net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField event = new net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField(this, p_185938_1_, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return event.getNoisefield();

        double d0 = 684.412D;
        double d1 = 2053.236D;
        double[] adouble = new double[p_185938_6_];

        for (int j = 0; j < p_185938_6_; ++j)
        {
            adouble[j] = Math.cos((double)j * Math.PI * 6.0D / (double)p_185938_6_) * 2.0D;
            double d2 = (double)j;

            if (j > p_185938_6_ / 2)
            {
                d2 = (double)(p_185938_6_ - 1 - j);
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble[j] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (int l = 0; l < p_185938_5_; ++l)
        {
            for (int i1 = 0; i1 < p_185938_7_; ++i1)
            {
                double d3 = 0.0D;

                for (int k = 0; k < p_185938_6_; ++k)
                {
                    double d4 = adouble[k];
                  


                    if ((double)k < 0.0D)
                    {
                        double d10 = (0.0D - (double)k) / 4.0D;
                        d10 = MathHelper.clamp(d10, 0.0D, 1.0D);
                    }

               
                }
            }
        }

        return p_185938_1_;
    }

	  public void prepareHeights(int p_185936_1_, int p_185936_2_, ChunkPrimer primer)
	    {
	        int i = 4;
	        int j = 20;
	        int k = 5;
	        int l = 17;
	        int i1 = 5;
	        this.buffer = this.getHeights(this.buffer, p_185936_1_ * 4, 0, p_185936_2_ * 4, 5, 17, 5);

	        for (int j1 = 0; j1 < 4; ++j1)
	        {
	            for (int k1 = 0; k1 < 4; ++k1)
	            {
	                for (int l1 = 0; l1 < 16; ++l1)
	                {
	                    double d0 = 0.125D;
	                    double d1 = this.buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 0];
	                    double d2 = this.buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 0];
	                    double d3 = this.buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 0];
	                    double d4 = this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 0];
	                    double d5 = (this.buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 1] - d1) * 0.125D;
	                    double d6 = (this.buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 1] - d2) * 0.125D;
	                    double d7 = (this.buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 1] - d3) * 0.125D;
	                    double d8 = (this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 1] - d4) * 0.125D;

	                    for (int i2 = 0; i2 < 8; ++i2)
	                    {
	                        double d9 = 0.25D;
	                        double d10 = d1;
	                        double d11 = d2;
	                        double d12 = (d3 - d1) * 0.25D;
	                        double d13 = (d4 - d2) * 0.25D;

	                        for (int j2 = 0; j2 < 4; ++j2)
	                        {
	                            double d14 = 0.25D;
	                            double d15 = d10;
	                            double d16 = (d11 - d10) * 0.25D;

	                            for (int k2 = 0; k2 < 4; ++k2)
	                            {
	                                IBlockState iblockstate = null;

	                                if (l1 * 8 + i2 < j)
	                                {
	                                    iblockstate = OOZE;
	                                }

	                       

	                                int l2 = j2 + j1 * 4;
	                                int i3 = i2 + l1 * 8;
	                                int j3 = k2 + k1 * 4;
	                                primer.setBlockState(l2, i3, j3, iblockstate);
	                                d15 += d16;
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
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
		if (biome != Biomes.DESERT && biome != Biomes.DESERT_HILLS && this.settings.useWaterLakes
				&& this.rand.nextInt(this.settings.waterLakeChance) == 0)
			if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, false,
					net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE)) {

			}
		biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
		if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, false,
				net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS))
			WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
		blockpos = blockpos.add(8, 0, 8);
		if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, false,
				net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ICE)) {
			for (int k2 = 0; k2 < 16; ++k2) {
				for (int j3 = 0; j3 < 16; ++j3) {
					BlockPos blockpos1 = this.world.getPrecipitationHeight(blockpos.add(k2, 0, j3));
					BlockPos blockpos2 = blockpos1.down();
					if (this.world.canBlockFreezeWater(blockpos2)) {
						this.world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
					}
					if (this.world.canSnowAt(blockpos1, true)) {
						this.world.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
					}
				}
			}
		}
		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
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
						double d5 = (this.heightMap[i1 + i2 + 1] - d1) * 0.125D;
						double d6 = (this.heightMap[j1 + i2 + 1] - d2) * 0.125D;
						double d7 = (this.heightMap[k1 + i2 + 1] - d3) * 0.125D;
						double d8 = (this.heightMap[l1 + i2 + 1] - d4) * 0.125D;

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
								int heightVar=(-5+rand.nextInt(5));

								for (int l2 = 0; l2 < 4; ++l2) {
									if ((lvt_45_1_ += d16) > 0.0D) {
										primer.setBlockState(i * 4 + k2, i2 * 4 + j2, l * 4 + l2, STONE);
									} else if ((i2 * 8 + j2 +heightVar) < this.settings.seaLevel+10) {
										//Change the x int to influnce spacing between cubes
										// change the y to influence height  
									//		primer.setBlockState(i * 4 + l2, i * 8 + l2, i* 4 + l2, WATER);

									primer.setBlockState(i * 4 + k2, i2 *7 + j2+(80), l * 4 + l2, MINDFOG);
								//		primer.setBlockState(i * 4 + k2, i2 *7 + j2+ 180, l * 4 + l2,CLAY);

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
		this.field_186002_u = this.noiseGenPerl_m.getRegion(this.field_186002_u, (double) (x * 16), (double) (z * 16),
				16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);
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
							iblockstate1 = MINDFOG;
						}
						j = k;
						if (j1 >= i - 1) {
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
						} else if (j1 < i - 7 - k) {
							iblockstate = AIR;
							iblockstate1 = STONE;
							chunkPrimerIn.setBlockState(i1, j1, l, EARTH);
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

	private void generateHeightmap(int x, int y, int z) {
		this.field_185989_h = this.noiseGenOct_c.generateNoiseOctaves(this.field_185989_h, x, z, 5,
				5, (double) this.settings.depthNoiseScaleX, (double) this.settings.depthNoiseScaleZ,
				(double) this.settings.depthNoiseScaleExponent);
		float f = this.settings.coordinateScale;
		float f1 = this.settings.heightScale;
		this.field_185986_e = this.noiseGenOct_l.generateNoiseOctaves(this.field_185986_e, x, y,
				z, 5, 33, 5, (double) (f / this.settings.mainNoiseScaleX),
				(double) (f1 / this.settings.mainNoiseScaleY), (double) (f / this.settings.mainNoiseScaleZ));
		this.field_185987_f = this.noiseGenOct_j.generateNoiseOctaves(this.field_185987_f, x, y,
				z, 5, 33, 5, (double) f, (double) f1, (double) f);
		this.field_185988_g = this.noiseGenOct_k.generateNoiseOctaves(this.field_185988_g, x, y,
				z, 5, 33, 5, (double) f, (double) f1, (double) f);
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
						float f5 = this.settings.biomeDepthOffSet
								+ biome1.getBaseHeight() * this.settings.biomeDepthWeight;
						float f6 = this.settings.biomeScaleOffset
								+ biome1.getHeightVariation() * this.settings.biomeScaleWeight;
						if (this.terrainType == WorldType.AMPLIFIED && f5 > 0.0F) {
							f5 = 1.0F + f5 * 2.0F;
							f6 = 1.0F + f6 * 4.0F;
						}
						float f7 = this.field_185999_r[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
						if (biome1.getBaseHeight() > biome.getBaseHeight()) {
							f7 /= 2.0F;
						}
						f2 += f6 * f7;
						f3 += f5 * f7;
						f4 += f7;
					}
				}
				f2 = f2 / f4;
				f3 = f3 / f4;
				f2 = f2 * 0.9F + 0.1F;
				f3 = (f3 * 4.0F - 1.0F) / 8.0F;
				double d7 = this.field_185989_h[j] / 8000.0D;
				if (d7 < 0.0D) {
					d7 = -d7 * 0.3D;
				}
				d7 = d7 * 3.0D - 2.0D;
				if (d7 < 0.0D) {
					d7 = d7 / 2.0D;
					if (d7 < -1.0D) {
						d7 = -1.0D;
					}
					d7 = d7 / 1.4D;
					d7 = d7 / 2.0D;
				} else {
					if (d7 > 1.0D) {
						d7 = 1.0D;
					}
					d7 = d7 / 8.0D;
				}
				++j;
				double d8 = (double) f3;
				double d9 = (double) f2;
				d8 = d8 + d7 * 0.2D;
				d8 = d8 * (double) this.settings.baseSize / 8.0D;
				double d0 = (double) this.settings.baseSize + d8 * 4.0D;
				for (int l1 = 0; l1 < 33; ++l1) {
					double d1 = ((double) l1 - d0) * this.settings.stretchY * 128.0D / 256.0D / d9;
					if (d1 < 0.0D) {
						d1 *= 4.0D;
					}
					double d2 = this.field_185987_f[i] / (double) this.settings.lowerLimitScale;
					double d3 = this.field_185988_g[i] / (double) this.settings.upperLimitScale;
					double d4 = (this.field_185986_e[i] / 10.0D + 1.0D) / 2.0D;
					double d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;
					if (l1 > 29) {
						double d6 = (double) ((float) (l1 - 29) / 3.0F);
						d5 = d5 * (1.0D - d6) + -10.0D * d6;
					}
					this.heightMap[i] = d5;
					++i;
				}
			}
		}
	}
}
