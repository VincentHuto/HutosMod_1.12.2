package com.huto.hutosmod.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityFusionFurnace.class, "fusion_furnace");
		GameRegistry.registerTileEntity(TileEntityWandMaker.class, "wand_maker");
		GameRegistry.registerTileEntity(TileEntityBellJar.class, "mana_belljar");
		GameRegistry.registerTileEntity(TileEntityStorageDrum.class, "mana_storagedrum");
		GameRegistry.registerTileEntity(TileEntityRuneStation.class, "rune_station");
		GameRegistry.registerTileEntity(TileEntityEssecenceEnhancer.class, "esscence_enhancer");
		GameRegistry.registerTileEntity(TileEntityManaGatherer.class, "mana_gatherer");

	}
}