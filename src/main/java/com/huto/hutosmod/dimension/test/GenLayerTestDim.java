package com.huto.hutosmod.dimension.test;

import com.huto.hutosmod.biomes.BiomeRegistry;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerTestDim extends GenLayer 
{
	private Biome[] allowedBiomes = {BiomeRegistry.NIGHTMARE};
	public GenLayerTestDim(long seed) 
	{
		super(seed);
	}
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) 
	{
		int[] dest = IntCache.getIntCache(areaWidth*areaHeight);
		for(int dz = 0; dz < areaHeight; dz++)
		{
			for(int dx = 0; dx < areaWidth; dx++)
			{
				this.initChunkSeed(dx + areaX, dz + areaY);
				dest[(dx+dz*areaWidth)] = Biome.getIdForBiome(this.allowedBiomes[nextInt(this.allowedBiomes.length)]);
			}
		}
		return dest;
	}
}
