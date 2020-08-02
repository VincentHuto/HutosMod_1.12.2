package com.huto.hutosmod.biomes;

import java.util.Random;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.entities.EntityDreamWalker;
import com.huto.hutosmod.entities.EntityElemental;
import com.huto.hutosmod.entities.EntityMemoryFlicker;
import com.huto.hutosmod.worldgen.WorldGenHugeMorelMushroom;
import com.huto.hutosmod.worldgen.WorldGenMysticTree;
import com.huto.hutosmod.worldgen.WorldGenSmallMysticTree;

import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeNightmare extends Biome {
	public static NoiseGeneratorSimplex SimplexNoise = new NoiseGeneratorSimplex();

	public BiomeNightmare() {
		super(new BiomeProperties("nightmare").setBaseHeight(0.1f).setTemperature(1.5f).setHeightVariation(0.1f)
				.setWaterColor(7274618));
		topBlock = BlockRegistry.nightmare_earth.getDefaultState();
		fillerBlock = BlockRegistry.nightmare_media.getDefaultState();

		getModdedBiomeGrassColor(7274618);
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityMooshroom.class, 1000, 3, 15));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 20000, 3, 5));

	}

	
	
	
	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		System.out.println("Hello");
		generateNightmareTerrain(worldIn, rand, chunkPrimerIn, x, z, 1);
		
	}

	
	public void generateNightmareTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z,
			double noiseVal) {
		int sealevel = worldIn.getSeaLevel();
		IBlockState iblockstate = this.topBlock;
		IBlockState iblockstate1 = this.fillerBlock;
		int count = -1;
		int noiseNormalizer = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int bitWiseX = x & 15;
		int bitWiseZ = z & 15;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int height = 255; height >= 0; --height) {
			if (height <= rand.nextInt(5)) {
				chunkPrimerIn.setBlockState(bitWiseZ, height, bitWiseX, BEDROCK);
			} else {
				IBlockState iblockstate2 = chunkPrimerIn.getBlockState(bitWiseZ, height, bitWiseX);

				if (iblockstate2.getMaterial() == Material.AIR) {
					height = -1;
				} else if (iblockstate2.getBlock() == Blocks.STONE) {
					if (height == -1) {
						if (noiseNormalizer <= 0) {
							iblockstate = AIR;
							iblockstate1 = STONE;
						} else if (height >= sealevel - 4 && height <= sealevel + 1) {
							iblockstate = this.topBlock;
							iblockstate1 = this.fillerBlock;
						}

						if (height < sealevel && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
							if (this.getTemperature(blockpos$mutableblockpos.setPos(x, height, z)) < 0.15F) {
								iblockstate = ICE;
							} else {
								iblockstate = WATER;
							}
						}

						height = noiseNormalizer;

						if (height >= sealevel - 1) {
							chunkPrimerIn.setBlockState(bitWiseZ, height, bitWiseX, iblockstate);
						} else if (height < sealevel - 7 - noiseNormalizer) {
							iblockstate = AIR;
							iblockstate1 = STONE;
							chunkPrimerIn.setBlockState(bitWiseZ, height, bitWiseX, GRAVEL);
						} else {
							chunkPrimerIn.setBlockState(bitWiseZ, height, bitWiseX, iblockstate1);
						}
					} else if (height > 0) {
						--height;
						chunkPrimerIn.setBlockState(bitWiseZ, height, bitWiseX, iblockstate1);

						if (height == 0 && iblockstate1.getBlock() == Blocks.SAND && noiseNormalizer > 1) {
							height = rand.nextInt(4) + Math.max(0, height - 63);
							iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND
									? RED_SANDSTONE
									: SANDSTONE;
						}
					}
				}
			}
		}
	}

}
