package com.huto.hutosmod.mindrunes.cap;

import com.huto.hutosmod.mindrunes.container.RunesContainer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class RunesContainerProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider {

	private final RunesContainer container;

	public RunesContainerProvider(RunesContainer container) {
		this.container = container;
	}

	@Override
	public boolean hasCapability (Capability<?> capability, EnumFacing facing) {
		return capability == RuneCapabilities.CAPABILITY_RUNES;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability (Capability<T> capability, EnumFacing facing) {
		if (capability == RuneCapabilities.CAPABILITY_RUNES) return (T) this.container;
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT () {
		return this.container.serializeNBT();
	}

	@Override
	public void deserializeNBT (NBTTagCompound nbt) {
		this.container.deserializeNBT(nbt);
	}
}
