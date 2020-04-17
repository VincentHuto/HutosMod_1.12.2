package com.huto.hutosmod.karma;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class KarmaProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IKarma.class)
	public static final Capability<IKarma> KARMA_CAPABILITY = null;

	private IKarma instance = KARMA_CAPABILITY.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == KARMA_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == KARMA_CAPABILITY ? KARMA_CAPABILITY.<T>cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return KARMA_CAPABILITY.getStorage().writeNBT(KARMA_CAPABILITY, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		KARMA_CAPABILITY.getStorage().readNBT(KARMA_CAPABILITY, this.instance, null, nbt);
	}
}
