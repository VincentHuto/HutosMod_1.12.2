package com.huto.hutosmod.mindrunes;

import com.huto.hutosmod.mindrunes.cap.RuneCapabilities;
import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;

public class RuneApi
{
	/**
	 * Retrieves the baubles inventory capability handler for the supplied player
	 */
	public static IRunesItemHandler getRuneHandler(EntityPlayer player)
	{
		IRunesItemHandler handler = player.getCapability(RuneCapabilities.CAPABILITY_RUNES, null);
		handler.setPlayer(player);
		return handler;
	}

	/**
	 * Retrieves the baubles capability handler wrapped as a IInventory for the supplied player
	 */
	@Deprecated
	public static IInventory getRunes(EntityPlayer player)
	{
		IRunesItemHandler handler = player.getCapability(RuneCapabilities.CAPABILITY_RUNES, null);
		handler.setPlayer(player);
		return new RuneInventoryWrapper(handler, player);
	}
	
	/**
	 * Returns if the passed in item is equipped in a bauble slot. Will return the first slot found
	 * @return -1 if not found and slot number if it is found 
	 */
	public static int isRuneEquipped(EntityPlayer player, Item bauble) {
		IRunesItemHandler handler = getRuneHandler(player);
		for (int a=0;a<handler.getSlots();a++) {
			if (!handler.getStackInSlot(a).isEmpty() && handler.getStackInSlot(a).getItem()==bauble) return a;
		}
		return -1;
	}
}
