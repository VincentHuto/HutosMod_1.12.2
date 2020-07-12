package com.huto.hutosmod.network;

import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.container.ContainerChiselStation;
import com.huto.hutosmod.tileentity.TileEntityChiselStation;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketChiselCraftingEvent implements IMessage {

	public PacketChiselCraftingEvent() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}

	public static class Handler implements IMessageHandler<PacketChiselCraftingEvent, IMessage> {
		@Override
		public IMessage onMessage(PacketChiselCraftingEvent message, MessageContext ctx) {
			Container container = ctx.getServerHandler().player.openContainer;
			if (container instanceof ContainerChiselStation) {
				TileEntityChiselStation station = ((ContainerChiselStation) container).getChestInventory();
				station.craftEvent();
			}
			return null;
		}

	}
}