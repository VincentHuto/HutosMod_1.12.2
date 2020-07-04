package com.huto.hutosmod.dimension;

import com.huto.hutosmod.dimension.dreamscape.DimensionDreamScape;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
/*	public static final DimensionType OVERWORLD_TEMPLATE = DimensionType.register("overworld_temp_dim",
			"_overworld_temp_dim", -400, DimensionOverworldTemp.class, false);
	public static final DimensionType NETHER_TEMPLATE = DimensionType.register("nether_temp_dim", "_nether_temp_dim",
			-401, DimensionNetherTemp.class, false);
	public static final DimensionType END_TEMPLATE = DimensionType.register("end_temp_dim", "_end_temp_dim", -402,
			DimensionEndTemp.class, false);*/
	public static final DimensionType ALAGADA_DIM = DimensionType.register("alagada_dim", "_alagada_dim", -403,
			DimensionDreamScape.class, false);
	
	public static void registerDimension() {
//		DimensionManager.registerDimension(-400, OVERWORLD_TEMPLATE);
//		DimensionManager.registerDimension(-401, NETHER_TEMPLATE);
//		DimensionManager.registerDimension(-402, END_TEMPLATE);
		DimensionManager.registerDimension(-403, ALAGADA_DIM);

	}

}
