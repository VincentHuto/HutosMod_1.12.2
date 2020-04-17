package com.huto.hutosmod.network;

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

public class PacketGetMana implements IMessage {

	float mana;
	private String className;
	private String manaFieldName;

	@Override
	public void toBytes(ByteBuf buf) {
			buf.writeFloat(mana);
			ByteBufUtils.writeUTF8String(buf, this.className);
			ByteBufUtils.writeUTF8String(buf, this.manaFieldName);

		return;
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

	public PacketGetMana() {
	}

	public PacketGetMana(float mana, String className,String manaFieldName) {
		this.mana = mana;
		this.className = className;
		this.manaFieldName = manaFieldName;

	}

	public static class Handler implements IMessageHandler<PacketGetMana, IMessage> {

		@Override
		public IMessage onMessage(PacketGetMana message, MessageContext ctx) {
			if ( ctx.side != Side.SERVER)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));

			return null;
		}

		void processMessage(PacketGetMana message, MessageContext ctx) {
			EntityPlayer player = ctx.getServerHandler().player;
			if (player == null)
				return;
			if (!player.hasCapability(ManaProvider.MANA_CAP, null))
				return;
			IMana mana = player.getCapability(ManaProvider.MANA_CAP,null);
			PacketHandler.INSTANCE.sendTo(new PacketReturnMana(mana.getMana(),message.className,message.manaFieldName), ctx.getServerHandler().player);
		}

	}

}
