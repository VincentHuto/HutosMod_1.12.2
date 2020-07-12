package com.huto.hutosmod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	/**
	 * The instance of packet handler, for use to be able to send messages
	 */
	public static SimpleNetworkWrapper INSTANCE;

	/**
	 * The unique ID tracker for our packets
	 */
	private static int ID = 0;

	/**
	 * Get the next id
	 * 
	 * @return The next id
	 */
	private static int nextID() {
		return ID++;
	}

	/**
	 * Register all of our network messages on their appropriate side
	 * 
	 * @param channelName The name of the network channel
	 */
	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);

		// Client packets
		INSTANCE.registerMessage(PacketReturnMana.Handler.class, PacketReturnMana.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketReturnKarma.Handler.class, PacketReturnKarma.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketReturnManaLimit.Handler.class, PacketReturnManaLimit.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketUpdateChiselRunes.Handler.class, PacketUpdateChiselRunes.class, nextID(), Side.CLIENT);

		// Server packets
		INSTANCE.registerMessage(PacketGetMana.Handler.class, PacketGetMana.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketGetKarma.Handler.class, PacketGetKarma.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketGetManaLimit.Handler.class, PacketGetManaLimit.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketUpdateChiselRunes.Handler.class, PacketUpdateChiselRunes.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketChiselCraftingEvent.Handler.class, PacketChiselCraftingEvent.class, nextID(), Side.SERVER);

	}

}