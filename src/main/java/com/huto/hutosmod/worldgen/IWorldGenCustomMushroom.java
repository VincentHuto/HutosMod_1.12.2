package com.huto.hutosmod.worldgen;

import java.util.Random;

import com.huto.hutosmod.biomes.BiomeRegistry;
import com.huto.hutosmod.blocks.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class IWorldGenCustomMushroom implements IWorldGenerator {
	
	private final WorldGenerator MYSTIC_SMALL = new WorldGenSmallMysticTree();

	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		// these are important!
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		// generate differently based on dimension
		switch (world.provider.getDimension()) {
		case 0:
			// generateOverworld(world, random, blockX, blockZ);
			break;
		case -403:
			generateMushs(world, random, blockX, blockZ, BlockRegistry.morel_mushroom);
			generateMushs(world, random, blockX, blockZ, BlockRegistry.singeri_mushroom);
			generateMushs(world, random, blockX, blockZ, BlockRegistry.passion_flower);
			break;
		}

	}

	private void generateMushs(World world, Random rand, int blockX, int blockZ, Block mushType) {
		int randX = blockX + 8 + rand.nextInt(16);
		int randZ = blockZ + 8 + rand.nextInt(16);
		// make a world generator to use
		WorldGenerator genMush = new WorldGenCustomMushrooms(mushType);

		// get the biome. I used 64 for Y, but you can use anything between 0 and 255
		Biome biome = world.getBiomeForCoordsBody(new BlockPos(blockX, 64, blockZ));
		// we could also use: if(biome instanceof BiomeGenPlains)
		if (biome == BiomeRegistry.DREAMSCAPE) {
			// how many we want to make per chunk
			// let's make it random between MIN and MAX
			int MIN = 9;
			int MAX = 16;
			int numBushes = MIN + rand.nextInt(MAX - MIN);
			// now let's generate the bushes
			for (int i = 0; i < numBushes; i++) {
				// get a random position in the chunk
				// ALWAYS REMEMEBER THE 8 BLOCK OFFSET OR YOU WILL GET RUNAWAY WORLD GEN
				// the y-value we pass here will be used as minimum spawn height (in our
				// generator, anyway)
				genMush.generate(world, rand, new BlockPos(randX, 10, randZ));

			}

		}
	}

	/** HELPER METHODS **/

	// find a grass or dirt block to place the bush on
	public static int getGroundFromAbove(World world, int x, int z) {
		int y = 255;
		boolean foundGround = false;
		while (!foundGround && y-- >= 0) {
			Block blockAt = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			// "ground" for our bush is grass or dirt
			foundGround = blockAt == Blocks.STONE || blockAt == Blocks.GRASS || blockAt == Blocks.MYCELIUM
					|| blockAt == BlockRegistry.Mystic_Earth;
		}

		return y;
	}

}