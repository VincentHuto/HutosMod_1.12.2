package com.huto.hutosmod.blocks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidWhiteWater extends Fluid
{
	public FluidWhiteWater(String name, ResourceLocation still, ResourceLocation flow) 
	{
		super(name, still, flow);
		this.setUnlocalizedName(name);
	}
}
