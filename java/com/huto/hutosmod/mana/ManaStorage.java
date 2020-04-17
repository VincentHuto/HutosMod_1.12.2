package com.huto.hutosmod.mana;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

/**
 * This class is responsible for saving and reading mana data from or to server
 */
public class ManaStorage implements IStorage<IMana> {
	String TAG_MANA = "TAG_MANA";
	String TAG_MANALIMIT = "TAG_MANALIMIT";

	@Override
	public NBTBase writeNBT(Capability<IMana> capability, IMana instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat(TAG_MANA, instance.getMana());
		tag.setFloat(TAG_MANALIMIT, instance.manaLimit());
		return tag;
	}

	@Override
	public void readNBT(Capability<IMana> capability, IMana instance, EnumFacing side, NBTBase nbt) {
		instance.set(((NBTTagCompound) nbt).getFloat(TAG_MANA));
		instance.setLimit(((NBTTagCompound) nbt).getFloat(TAG_MANALIMIT));
				
	}
}
