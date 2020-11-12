package com.huto.hutosmod.biomes;

import java.util.Random;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.entities.EntityDreamWalker;
import com.huto.hutosmod.entities.EntityElemental;
import com.huto.hutosmod.entities.EntityMemoryFlicker;
import com.huto.hutosmod.worldgen.WorldGenHugeMorelMushroom;
import com.huto.hutosmod.worldgen.WorldGenMysticTree;
import com.huto.hutosmod.worldgen.WorldGenSmallMysticTree;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeDreamScape extends Biome {

	public static final WorldGenMysticTree MYSTIC_TREE = new WorldGenMysticTree();
	public static final WorldGenSmallMysticTree MYSTIC_TREE_Small = new WorldGenSmallMysticTree();

	public BiomeDreamScape() {
		super(new BiomeProperties("dreamscape").setBaseHeight(0.2F).setTemperature(0.5f).setHeightVariation(2.1f)
				.setWaterColor(77777));
		this.decorator.treesPerChunk = 10;
		topBlock = BlockRegistry.Mystic_Earth.getDefaultState();
		fillerBlock = BlockRegistry.enchanted_stone.getDefaultState();
		getModdedBiomeGrassColor(8777934);
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityElemental.class, 1000, 3, 15));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityDreamWalker.class, 10000, 1, 1));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityMemoryFlicker.class, 20000, 3, 5));


	}
	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		this.addMushrooms(worldIn, rand, pos);
	}

	public void addMushrooms(World worldIn, Random randIn, BlockPos blockPosIn) {
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				// NEVER FORGET THE +8 OFFSET
				int k = i * 4 + 1 + 8 + randIn.nextInt(3);
				int l = j * 4 + 1 + 8 + randIn.nextInt(3);
				BlockPos blockpos = worldIn.getHeight(blockPosIn.add(k, 0, l));
				if (randIn.nextInt(20) == 0 && net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, randIn,
						new net.minecraft.util.math.ChunkPos(blockPosIn), blockpos,
						net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM)) {
					WorldGenHugeMorelMushroom worldgenMorelmushroom = new WorldGenHugeMorelMushroom();
					worldgenMorelmushroom.generate(worldIn, randIn, blockpos);
				} else if (net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, randIn,
						new net.minecraft.util.math.ChunkPos(blockPosIn), blockpos,
						net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE)) {
					WorldGenAbstractTree worldgenabstracttree = this.getRandomTreeFeature(randIn);
					worldgenabstracttree.setDecorationDefaults();
					if (worldgenabstracttree.generate(worldIn, randIn, blockpos)) {
						worldgenabstracttree.generateSaplings(worldIn, randIn, blockpos);

					}
				}
			}
		}
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return MYSTIC_TREE;

	}

}
