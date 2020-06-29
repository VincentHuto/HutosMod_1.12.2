package com.huto.hutosmod.blocks;

import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.ResourceLocation;

public class BlockRegistry {
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	// Blocks
	public static final Block enchanted_stone = new BlockEnchantedStone("enchanted_stone", Material.IRON);
	public static final Block enchanted_stone_smooth = new BlockBase("enchanted_stone_smooth", Material.IRON);
	public static final Block enchanted_ore = new BlockEnchantedOre("enchanted_ore", Material.ROCK);
	public static final Block enchanted_ore_mystic = new BlockEnchantedOre("enchanted_ore_mystic", Material.ROCK);

	public static final Block Display_Glass = new BlockDisplayGlass("display_glass", Material.GLASS);
	public static final Block Mystic_Earth = new BlockMysticEarth("mystic_earth", Material.GROUND);
	public static final Block Mystic_Media = new BlockBase("mystic_media", Material.ROCK).setHardness(5F)
			.setResistance(15F);
	// public static final Block Connected_Cube = new
	// BlockConnectedTexture("connected_cube", Material.ROCK);
	public static final Block Mystic_Sapling = new BlockSaplingBase("mystic_sapling");
	public static final Block Mystic_Log = new BlockLogBase("mystic_log");
	public static final Block Mystic_Leaves = new BlockLeafBase("mystic_leaves");
	public static final Block Mystic_Vine = new BlockMysticVine("mystic_vine");
	public static final Block runed_obsidian = new BlockRunedObsidian("runed_obsidian", Material.ROCK);
	public static final Block activated_obsidian = new BlockActivatedObsidian("activated_obsidian", Material.IRON);
	public static final Block nether_block = new BlockNetherBlock("nether_block", Material.ROCK).setHardness(5F)
			.setResistance(15F);
	public static final Block morel_cap = new BlockBase("morel_cap", Material.CACTUS);
	public static final Block morel_stem = new BlockBase("morel_stem", Material.CACTUS);
	public static final Block morel_mushroom = new BlockMorelMushroom("morel_mushroom", Material.CACTUS);
	public static final Block singeri_mushroom = new BlockSingeriMushroom("singeri_mushroom", Material.CACTUS);
	public static final Block passion_flower = new BlockPassionFlower("passion_flower", Material.CACTUS);
	public static final Block display_pedestal = new BlockDisplayPedestal("display_pedestal", Material.ROCK);
	public static final Block wand_maker = new BlockWandMaker("wand_maker", Material.ROCK);
	public static final Block mana_belljar = new BlockManaBelljar("mana_belljar", Material.GLASS);
	public static final Block mana_capacitor = new BlockManaCapacitor("mana_capacitor", Material.GLASS);
	public static final Block mana_storagedrum = new BlockManaStorageDrum("mana_storagedrum", Material.GLASS);
	public static final Block Rune_Station = new BlockRuneStation("rune_station", Material.ROCK);
	public static final Block essecence_enhancer = new BlockEssenceEnhancer("essecence_enhancer", Material.ROCK);
	public static final Block mana_gatherer = new BlockManaGatherer("mana_gatherer", Material.ROCK);
	public static final Block mana_hopper = new BlockManaHopper("mana_hopper", Material.ROCK);
	public static final Block karmic_altar = new Blockkarmic_altar("karmic_altar", Material.ROCK);
	public static final Block mana_fuser = new BlockManaFuser("mana_fuser", Material.ROCK);
	public static final Block karmic_extractor = new Blockkarmic_extractor("karmic_extractor", Material.ROCK);
	public static final Block celestial_actuator = new BlockCelestialActuator("celestial_actuator", Material.ROCK);

	// Fluids
	public static final Block WHITE_WATER_FLUID = new BlockWhiteWaterFluid("white_water", FluidInit.WHITE_WATER_FLUID,
			Material.WATER);
	public static final Block primal_ooze_fluid = new BlockPrimalOozeFluid("primal_ooze", FluidInit.primal_ooze_fluid,
			Material.WATER);

}
