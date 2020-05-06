package com.huto.hutosmod.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockRegistry {
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	// Blocks
	public static final Block enchanted_stone = new EnchantedStoneBlock("enchanted_stone", Material.IRON);
	public static final Block enchanted_ore = new ore_enchantedBlock("enchanted_ore", Material.ROCK);
	public static final Block Display_Glass = new Display_GlassBlock("display_glass", Material.GLASS);
	public static final Block Mystic_Earth = new BlockMysticEarth("mystic_earth",Material.GROUND);
	public static final Block Mystic_Media= new BlockBase("mystic_media",Material.ROCK).setHardness(5F).setResistance(15F);
	//public static final Block Connected_Cube= new BlockConnectedTexture("connected_cube",Material.ROCK);
	public static final Block Mystic_Sapling = new BlockSaplingBase("mystic_sapling");
	public static final Block Mystic_Log = new BlockLogBase("mystic_log");
	public static final Block Mystic_Leaves = new BlockLeafBase("mystic_leaves");
	public static final Block Mystic_Vine = new BlockMysticVine("mystic_vine");
	public static final Block runed_obsidian = new BlockRunedObsidian("runed_obsidian", Material.ROCK);
	public static final Block activated_obsidian = new BlockActivatedObsidian("activated_obsidian", Material.IRON);
	public static final Block nether_block= new BlockNetherBlock("nether_block",Material.ROCK).setHardness(5F).setResistance(15F);
	public static final Block Fusion_Furnace = new FusionFurnaceBlock("fusion_furnace");
	public static final Block wand_maker = new wand_makerBlock("wand_maker", Material.ROCK);
	public static final Block mana_belljar = new mana_belljarBlock("mana_belljar", Material.GLASS);
	public static final Block mana_capacitor = new mana_capacitorBlock("mana_capacitor", Material.GLASS);
	public static final Block mana_storagedrum = new mana_storagedrumBlock("mana_storagedrum", Material.GLASS);
	public static final Block Rune_Station = new runestationBlock("rune_station", Material.ROCK);
	public static final Block essecence_enhancer = new essecence_enhancerBlock("essecence_enhancer", Material.ROCK);
	public static final Block mana_gatherer = new mana_gathererBlock("mana_gatherer", Material.ROCK);

}
