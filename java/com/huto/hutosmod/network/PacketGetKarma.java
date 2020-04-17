package com.huto.hutosmod.network;

import com.huto.hutosmod.karma.IKarma;
import com.huto.hutosmod.karma.KarmaProvider;
import com.huto.hutosmod.reference.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGetKarma implements IMessage {

	float karma;
	private String className;
	private String karmaFieldName;

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(karma);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.karmaFieldName);

		return;
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

	public PacketGetKarma() {
	}

	public PacketGetKarma(float karma, String className, String karmaFieldName) {
		this.karma = karma;
		this.className = className;
		this.karmaFieldName = karmaFieldName;

	}

	public static class Handler implements IMessageHandler<PacketGetKarma, IMessage> {

		@Override
		public IMessage onMessage(PacketGetKarma message, MessageContext ctx) {
			if (ctx.side != Side.SERVER)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));

			return null;
		}

		void processMessage(PacketGetKarma message, MessageContext ctx) {
			EntityPlayer player = ctx.getServerHandler().player;
			if (player == null)
				return;
			if (!player.hasCapability(KarmaProvider.KARMA_CAPABILITY, null))
				return;
			IKarma karma = player.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
			PacketHandler.INSTANCE.sendTo(
					new PacketReturnKarma(karma.getKarma(), message.className, message.karmaFieldName),
					ctx.getServerHandler().player);
		}

	}

}
