package com.huto.hutosmod.network;

import java.lang.reflect.Field;
import com.huto.hutosmod.reference.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketReturnKarma implements IMessage {

	private float karma;
	private String className;
	private String karmaFieldName;

	public PacketReturnKarma() {
	}

	public PacketReturnKarma(float karma, String className,String karmaFieldName) {

	
		this.karma = karma;
		this.className = className;
		this.karmaFieldName=karmaFieldName;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.karma = buf.readFloat();
			this.className = ByteBufUtils.readUTF8String(buf);
			this.karmaFieldName = ByteBufUtils.readUTF8String(buf);

		} catch (IndexOutOfBoundsException ioe) {
			Utils.getLogger().catching(ioe);
			return;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(karma);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.karmaFieldName);

	}

	public static class Handler implements IMessageHandler<PacketReturnKarma, IMessage> {

		/**
		 * Schedules the task on the client which will process the message
		 */
		@Override
		public IMessage onMessage(PacketReturnKarma message, MessageContext ctx) {
			if (ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
			return null;
		}

		/**
		 * Update the fields in the class provided using the data from the message
		 * 
		 * @param message The message which holds the data
		 */
		void processMessage(PacketReturnKarma message) {
			try {
				Class clazz = Class.forName(message.className);
				Field karmaField = clazz.getDeclaredField(message.karmaFieldName);
				karmaField.setFloat(clazz, message.karma);
			} catch (Exception e) {
				Utils.getLogger().catching(e);
			}
		}

	}

}
