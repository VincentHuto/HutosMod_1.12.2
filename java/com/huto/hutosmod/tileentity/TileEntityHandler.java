package com.huto.hutosmod.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityWandMaker.class, "wand_maker");
		GameRegistry.registerTileEntity(TileEntityBellJar.class, "mana_belljar");
		GameRegistry.registerTileEntity(TileEntityStorageDrum.class, "mana_storagedrum");
		GameRegistry.registerTileEntity(TileEntityRuneStation.class, "rune_station");
	//	GameRegistry.registerTileEntity(TileEntityEssecenceEnhancer.class, "esscence_enhancer");
		GameRegistry.registerTileEntity(TileEntityManaGatherer.class, "mana_gatherer");
		GameRegistry.registerTileEntity(TileEntityManaCapacitor.class, "mana_capacitor");
		GameRegistry.registerTileEntity(TileEntityManaHopper.class, "mana_hopper");
		GameRegistry.registerTileEntity(TileEntityKarmicAltar.class, "karmic_altar");
		GameRegistry.registerTileEntity(TileEntityManaFuser.class, "mana_fuser");
		GameRegistry.registerTileEntity(TileEntityKarmicExtractor.class, "karmic_extractor");
		GameRegistry.registerTileEntity(TileEntityCelestialActuator.class, "celestial_actuator");
		GameRegistry.registerTileEntity(TileEntityDisplayPedestal.class, "display_pedestal");
		GameRegistry.registerTileEntity(TileEntityChiselStation.class, "runic_chiselstation");
		GameRegistry.registerTileEntity(TileEntityManaResonator.class, "mana_resonator");
		GameRegistry.registerTileEntity(TileEntityDisapperingBlock.class, "disappering_block");
		GameRegistry.registerTileEntity(TileEntityVibratorySelector.class, "vibratory_selector");
		GameRegistry.registerTileEntity(TileEntityWaveGatherer.class, "wave_gatherer");

	}
}
