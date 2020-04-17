package com.huto.hutosmod.mindrunes.cap;

import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class RuneCapabilities {
	/**
	 * Access to the baubles capability.
	 */
	@CapabilityInject(IRunesItemHandler.class)
	public static final Capability<IRunesItemHandler> CAPABILITY_RUNES = null;

	@CapabilityInject(IRune.class)
	public static final Capability<IRune> CAPABILITY_ITEM_RUNE = null;

	public static class CapabilityRunes<T extends IRunesItemHandler> implements IStorage<IRunesItemHandler> {

		@Override
		public NBTBase writeNBT(Capability<IRunesItemHandler> capability, IRunesItemHandler instance, EnumFacing side) {
			return null;
		}

		@Override
		public void readNBT(Capability<IRunesItemHandler> capability, IRunesItemHandler instance, EnumFacing side,
				NBTBase nbt) {
		}
	}

	public static class CapabilityItemRuneStorage implements IStorage<IRune> {

		@Override
		public NBTBase writeNBT(Capability<IRune> capability, IRune instance, EnumFacing side) {
			return null;
		}

		@Override
		public void readNBT(Capability<IRune> capability, IRune instance, EnumFacing side, NBTBase nbt) {

		}
	}
}
