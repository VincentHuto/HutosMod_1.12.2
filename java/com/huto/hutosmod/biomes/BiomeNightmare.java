package com.huto.hutosmod.biomes;

import java.util.Random;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.entities.EntityDreamWalker;
import com.huto.hutosmod.entities.EntityElemental;
import com.huto.hutosmod.entities.EntityMemoryFlicker;
import com.huto.hutosmod.worldgen.WorldGenHugeMorelMushroom;
import com.huto.hutosmod.worldgen.WorldGenMysticTree;
import com.huto.hutosmod.worldgen.WorldGenSmallMysticTree;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeNightmare extends Biome {

	public static final WorldGenMysticTree MYSTIC_TREE = new WorldGenMysticTree();
	public static final WorldGenSmallMysticTree MYSTIC_TREE_Small = new WorldGenSmallMysticTree();

	public BiomeNightmare() {
		super(new BiomeProperties("nightmare").setBaseHeight(0.1F).setTemperature(1.5f).setHeightVariation(.2f)
				.setWaterColor(16711680));
		topBlock = BlockRegistry.anti_earth.getDefaultState();
		fillerBlock = BlockRegistry.anti_media.getDefaultState();
		
		getModdedBiomeGrassColor(16711680);
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

}
