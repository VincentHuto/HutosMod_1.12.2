package com.huto.hutosmod.mindrunes;

import com.huto.hutosmod.mindrunes.cap.IRune;

import net.minecraft.item.ItemStack;

public class RuneItem implements IRune
{
	private RuneType runetype;

	public RuneItem(RuneType type) {
		runetype = type;
	}

	@Override
	public RuneType getRuneType(ItemStack itemstack) {
		return runetype;
	}
}
