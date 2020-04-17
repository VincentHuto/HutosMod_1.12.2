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
public class PacketReturnMana implements IMessage {

	/**
	 * States whether the message is valid or not
	 */

	private float mana;
	private String className;
	private String manaFieldName;

	/**
	 * Default constructor used for registration
	 */
	public PacketReturnMana() {
	}

	public PacketReturnMana(float mana, String className,String manaFieldName) {

	
		this.mana = mana;
		this.className = className;
		this.manaFieldName=manaFieldName;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.mana = buf.readFloat();
			this.className = ByteBufUtils.readUTF8String(buf);
			this.manaFieldName = ByteBufUtils.readUTF8String(buf);

		} catch (IndexOutOfBoundsException ioe) {
			Utils.getLogger().catching(ioe);
			return;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(mana);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.manaFieldName);

	}

	public static class Handler implements IMessageHandler<PacketReturnMana, IMessage> {

		/**
		 * Schedules the task on the client which will process the message
		 */
		@Override
		public IMessage onMessage(PacketReturnMana message, MessageContext ctx) {
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
		void processMessage(PacketReturnMana message) {
			try {
				Class clazz = Class.forName(message.className);
				Field manaField = clazz.getDeclaredField(message.manaFieldName);
				manaField.setFloat(clazz, message.mana);
			} catch (Exception e) {
				Utils.getLogger().catching(e);
			}
		}

	}

}
