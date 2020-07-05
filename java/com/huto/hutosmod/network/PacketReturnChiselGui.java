package com.huto.hutosmod.network;

import java.lang.reflect.Field;
import java.util.List;

import com.huto.hutosmod.reference.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
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
public class PacketReturnChiselGui implements IMessage {

	/**
	 * States whether the message is valid or not
	 */

	private List<Integer> runesOut;
	private String className;
	private String runeFieldName;
	/**
	 * Default constructor used for registration
	 */
	public PacketReturnChiselGui() {
	}

	public PacketReturnChiselGui(List<Integer> runesIn, String classNameIn, String runeFieldNameIn) {
		this.runesOut = runesIn;
		this.className = classNameIn;
		this.runeFieldName = runeFieldNameIn;

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			buf.readInt();
			/*for (int i = 0; i < runesOut.size(); i++) {
				this.runesOut.set(i, buf.readInt());
			}*/
			this.className = ByteBufUtils.readUTF8String(buf);
			this.runeFieldName = ByteBufUtils.readUTF8String(buf);

		} catch (IndexOutOfBoundsException ioe) {
			Utils.getLogger().catching(ioe);
			return;
		}

	}

	@Override
	public void toBytes(ByteBuf buf) {
		System.out.println("MADE IT TO TOBYTES");
		System.out.println(runesOut.get(0));

		for (int i = 0; i < runesOut.size(); i++) {
			System.out.println("FILLING RUNE PACKET");
			System.out.println(runesOut.get(i));

			buf.writeInt(runesOut.get(i));
		}
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.runeFieldName);

		return;
	}

	public static class Handler implements IMessageHandler<PacketReturnChiselGui, IMessage> {

		/**
		 * Schedules the task on the client which will process the message
		 */
		@Override
		public IMessage onMessage(PacketReturnChiselGui message, MessageContext ctx) {
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
		void processMessage(PacketReturnChiselGui message) {
			try {
				Class clazz = Class.forName(message.className);
				Field runeField = clazz.getDeclaredField(message.runeFieldName);
				runeField.set(clazz, message.runesOut);
			} catch (Exception e) {
				Utils.getLogger().catching(e);
			}
		}

	}

}
