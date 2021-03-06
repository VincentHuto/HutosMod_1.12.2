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

public class PacketDeresonateEvent implements IMessage {

	public PacketDeresonateEvent() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}

	public static class Handler implements IMessageHandler<PacketDeresonateEvent, IMessage> {
		@Override
		public IMessage onMessage(PacketDeresonateEvent message, MessageContext ctx) {
			Container container = ctx.getServerHandler().player.openContainer;
			if (container instanceof ContainerVibratorySelector) {
				TileEntityVibratorySelector station = ((ContainerVibratorySelector) container).getChestInventory();
				station.deresonateEvent();
				station.receiveClientEvent(4, 0);

			}
			return null;
		}

	}
}