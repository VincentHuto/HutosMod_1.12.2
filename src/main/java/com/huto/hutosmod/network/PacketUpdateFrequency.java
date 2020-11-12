package com.huto.hutosmod.network;

import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.container.ContainerChiselStation;
import com.huto.hutosmod.container.ContainerVibratorySelector;
import com.huto.hutosmod.tileentity.TileEntityChiselStation;
import com.huto.hutosmod.tileentity.TileEntityVibratorySelector;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateFrequency implements IMessage {
	private float frequency;

	public PacketUpdateFrequency() {
	}

	public PacketUpdateFrequency(float freqIn) {
		this.frequency = freqIn;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.frequency = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(frequency);

	}

	public float getFrequency() {
		return frequency;
	}

	public static class Handler implements IMessageHandler<PacketUpdateFrequency, IMessage> {
		@Override
		public IMessage onMessage(PacketUpdateFrequency message, MessageContext ctx) {
			Container container = ctx.getServerHandler().player.openContainer;
			if (container instanceof ContainerVibratorySelector) {
				System.out.println(message.getFrequency());
				TileEntityVibratorySelector station = ((ContainerVibratorySelector) container).getChestInventory();
				station.setSelectedFrequency(message.getFrequency());
			}
			return null;
		}

	}
}