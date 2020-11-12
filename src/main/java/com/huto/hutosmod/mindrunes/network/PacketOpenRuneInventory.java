package com.huto.hutosmod.mindrunes.network;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.reference.Reference;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenRuneInventory implements IMessage, IMessageHandler<PacketOpenRuneInventory, IMessage> {

	public PacketOpenRuneInventory() {}

	@Override
	public void toBytes(ByteBuf buffer) {}

	@Override
	public void fromBytes(ByteBuf buffer) {}

	@Override
	public IMessage onMessage(PacketOpenRuneInventory message, MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
		mainThread.addScheduledTask(new Runnable(){ public void run() {
			ctx.getServerHandler().player.openContainer.onContainerClosed(ctx.getServerHandler().player);
			ctx.getServerHandler().player.openGui(MainClass.instance, Reference.GUI_MIND_RUNES, ctx.getServerHandler().player.world, 0, 0, 0);
		}});
		return null;
	}
}
