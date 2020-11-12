package com.huto.hutosmod.network;

import java.lang.reflect.Field;

import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.reference.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * A packet which will update fields in a class based on the information
 * provided
 * 
 * @author CJMinecraft
 *
 */
public class PacketReturnManaLimit implements IMessage {

	/**
	 * States whether the message is valid or not
	 */

	private float manaLimit;
	private String className;
	private String manaLimitFieldName;

	/**
	 * Default constructor used for registration
	 */
	public PacketReturnManaLimit() {
	}

	public PacketReturnManaLimit(float mana, String className,String manaFieldName) {

	
		this.manaLimit = mana;
		this.className = className;
		this.manaLimitFieldName=manaFieldName;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.manaLimit = buf.readFloat();
			this.className = ByteBufUtils.readUTF8String(buf);
			this.manaLimitFieldName = ByteBufUtils.readUTF8String(buf);

		} catch (IndexOutOfBoundsException ioe) {
			Utils.getLogger().catching(ioe);
			return;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(manaLimit);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.manaLimitFieldName);

	}

	public static class Handler implements IMessageHandler<PacketReturnManaLimit, IMessage> {

		/**
		 * Schedules the task on the client which will process the message
		 */
		@Override
		public IMessage onMessage(PacketReturnManaLimit message, MessageContext ctx) {
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
		void processMessage(PacketReturnManaLimit message) {
			try {
				Class clazz = Class.forName(message.className);
				Field manaLimitField = clazz.getDeclaredField(message.manaLimitFieldName);
				manaLimitField.setFloat(clazz, message.manaLimit);
			} catch (Exception e) {
				Utils.getLogger().catching(e);
			}
		}

	}

}
