package com.huto.hutosmod.network;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.Sys;

import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.reference.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGetChiselData implements IMessage {

	 List<Integer> runesOut ;
	private String className;
	private String runeFieldName;

	

	public PacketGetChiselData() {
	}

	public PacketGetChiselData(List<Integer> runesIn, String classNameIn, String runeFieldNameIn) {
		this.runesOut = runesIn;
		this.className = classNameIn;
		this.runeFieldName = runeFieldNameIn;

	}

	
	@Override
	public void toBytes(ByteBuf buf) {
		System.out.println("MADE IT TO TOBYTES");
		for (int i = 0; i < runesOut.size(); i++) {
			System.out.println("FILLING RUNE PACKET");
			System.out.println(runesOut.get(i));

			buf.writeInt(runesOut.get(i));
		}
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.runeFieldName);

		return;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			System.out.println("MADE IT TO FROMBYTES");
			System.out.println(buf.readInt());

			buf.readInt();
			/*
			for (int i = 0; i < runesOut.size(); i++) {
				this.runesOut.set(i, buf.readInt());
				System.out.println("OPENING RUNE PACKET");

			}*/
			System.out.println("READ RUNE PACKET");

			this.className = ByteBufUtils.readUTF8String(buf);
			this.runeFieldName = ByteBufUtils.readUTF8String(buf);

		} catch (IndexOutOfBoundsException ioe) {
			Utils.getLogger().catching(ioe);
			return;
		}

	}

	public static class Handler implements IMessageHandler<PacketGetChiselData, IMessage> {

		@Override
		public IMessage onMessage(PacketGetChiselData messageIn, MessageContext ctx) {
			if (ctx.side != Side.SERVER)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(messageIn, ctx));
			return null;

		}

		void processMessage(PacketGetChiselData message, MessageContext ctx) {
			System.out.println("SENDING RUNESTOSEND TO RETURN" + message.runesOut);
			PacketHandler.INSTANCE.sendTo(new PacketReturnChiselGui(message.runesOut, message.className, message.runeFieldName),ctx.getServerHandler().player);
		}

	}

}
