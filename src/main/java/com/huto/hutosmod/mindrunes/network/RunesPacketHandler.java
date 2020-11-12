package com.huto.hutosmod.mindrunes.network;

import com.huto.hutosmod.reference.Reference;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class RunesPacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID.toLowerCase()+"Baubles");

	public static void init()
	{
		INSTANCE.registerMessage(PacketOpenRuneInventory.class, PacketOpenRuneInventory.class, 0, Side.SERVER);
		INSTANCE.registerMessage(PacketOpenNormalInventory.class, PacketOpenNormalInventory.class, 1, Side.SERVER);
		INSTANCE.registerMessage(PacketSync.Handler.class, PacketSync.class, 2, Side.CLIENT);
	}
}
