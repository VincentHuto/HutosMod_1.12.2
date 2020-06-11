package com.huto.hutosmod.biomes;

import java.util.Random;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.entities.EntityElemental;
import com.huto.hutosmod.worldgen.WorldGenMysticTree;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;

public class BiomeTest extends Biome {

	public static final WorldGenMysticTree MYSTIC_TREE = new WorldGenMysticTree();

	public BiomeTest() {
		super(new BiomeProperties("Test").setBaseHeight(0.1F).setTemperature(0.5f).setHeightVariation(2.9f)
				.setWaterColor(77777));

		this.decorator.treesPerChunk = 0;
		//topBlock = Blocks.GRASS.getDefaultState();
		topBlock = BlockRegistry.Mystic_Earth.getDefaultState();

		fillerBlock = BlockRegistry.enchanted_stone.getDefaultState();
		getModdedBiomeGrassColor(8777934);

		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCreatureList.add(new SpawnListEntry(EntityElemental.class, 100, 2, 10));
	}

/*	public void decorate(World worldIn, Random rand, BlockPos pos) {

		this.addMushrooms(worldIn, rand, pos);

		if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand,
				new net.minecraft.util.math.ChunkPos(pos),
				net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS)) { // no tab for
																									// patch
		}
		super.decorate(worldIn, rand, pos);
	}
	
	

	public void addMushrooms(World p_185379_1_, Random p_185379_2_, BlockPos p_185379_3_) {

		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				int k = i * 4 + 1 + 8 + p_185379_2_.nextInt(3);
				int l = j * 4 + 1 + 8 + p_185379_2_.nextInt(3);
				BlockPos blockpos = p_185379_1_.getHeight(p_185379_3_.add(k, 0, l));

				if (p_185379_2_.nextInt(20) == 0 && net.minecraftforge.event.terraingen.TerrainGen.decorate(p_185379_1_,
						p_185379_2_, new net.minecraft.util.math.ChunkPos(p_185379_3_), blockpos,
						net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM)) {
					WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
					worldgenbigmushroom.generate(p_185379_1_, p_185379_2_, blockpos);
				} else if (net.minecraftforge.event.terraingen.TerrainGen.decorate(p_185379_1_, p_185379_2_,
						new net.minecraft.util.math.ChunkPos(p_185379_3_), blockpos,
						net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE)) {
					WorldGenAbstractTree worldgenabstracttree = this.getRandomTreeFeature(p_185379_2_);
					worldgenabstracttree.setDecorationDefaults();

					if (worldgenabstracttree.generate(p_185379_1_, p_185379_2_, blockpos)) {
						worldgenabstracttree.generateSaplings(p_185379_1_, p_185379_2_, blockpos);
					}
				}
			}
		}
	}

	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return MYSTIC_TREE;

	}
*/
}
