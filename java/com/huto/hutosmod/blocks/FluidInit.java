package com.huto.hutosmod.blocks;

import com.huto.hutosmod.reference.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidInit {
	public static final Fluid WHITE_WATER_FLUID = new FluidWhiteWater("white_water",
			new ResourceLocation(Reference.MODID + ":blocks/white_water_still"),
			new ResourceLocation(Reference.MODID + ":blocks/white_water_flow"));

	public static final Fluid primal_ooze_fluid = new FluidPrimalOoze("primal_ooze",
			new ResourceLocation(Reference.MODID + ":blocks/primal_ooze_still"),
			new ResourceLocation(Reference.MODID + ":blocks/primal_ooze_flow"));
	
	public static void registerFluids() {
		registerFluid(WHITE_WATER_FLUID);
		registerFluid(primal_ooze_fluid);
	}

	public static void registerFluid(Fluid fluid) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}
}