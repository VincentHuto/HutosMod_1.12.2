package com.huto.hutosmod.blocks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidPrimalOoze extends Fluid
{
	public FluidPrimalOoze(String name, ResourceLocation still, ResourceLocation flow) 
	{
		super(name, still, flow);
		this.setUnlocalizedName(name);
		viscosity= 1;
	}
}
