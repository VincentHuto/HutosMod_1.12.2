package com.huto.hutosmod.karma;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class KarmaStorage implements IStorage<IKarma> {
	@Override
	public NBTBase writeNBT(Capability<IKarma> capability, IKarma instance, EnumFacing side) {
		return new NBTTagFloat(instance.getKarma());
	}

	@Override
	public void readNBT(Capability<IKarma> capability, IKarma instance, EnumFacing side, NBTBase nbt) {
		instance.set(((NBTPrimitive) nbt).getFloat());
	}
}
