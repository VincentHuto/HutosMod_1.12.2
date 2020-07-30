package com.huto.hutosmod.biomes;


import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeRegistry 
{
	public static final Biome TEST_BIOME = new BiomeDreamScape();
	public static final Biome NIGHTMARE = new BiomeNightmare();
	public static final Biome APHANTASIA = new BiomeAphantasia();

	public static void registerBiomes()
	{
		initBiome(TEST_BIOME, "DreamScape", BiomeType.WARM, Type.MAGICAL, Type.LUSH, Type.WET);
		initBiome(NIGHTMARE, "Nightmare", BiomeType.WARM, Type.SPOOKY, Type.DEAD, Type.WASTELAND);
		initBiome(APHANTASIA, "Aphantasia", BiomeType.COOL, Type.SPOOKY, Type.VOID, Type.WASTELAND);

	}
	
	private static Biome initBiome(Biome biome, String name, BiomeType bType, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addSpawnBiome(biome);
		return biome;
	}
}