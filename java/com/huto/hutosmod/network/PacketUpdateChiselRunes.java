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

public class PacketUpdateChiselRunes implements IMessage {
	int x,y,z;
	private List<Integer> runes;

	public PacketUpdateChiselRunes() {
	}

	public PacketUpdateChiselRunes(List<Integer> runesIn) {
		/*this.x=xIn;
		this.y=yIn;
		this.z=zIn;*/
		this.runes = runesIn;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		/*buf.readInt();
		buf.readInt();
		buf.readInt();*/

		runes = new ArrayList<Integer>();
		int listSize = buf.readInt();
		for (int is = 0; is < listSize; is++) {
			runes.add(buf.readInt());
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
/*		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
*/
		buf.writeInt(runes.size());
		for (Integer i : runes) {
			buf.writeInt(i);
	    	System.out.println(i);

		}

	}

	public List<Integer> getRunes() {
		return runes;
	}

	public static class Handler implements IMessageHandler<PacketUpdateChiselRunes, IMessage> {
		@Override
        public IMessage onMessage(PacketUpdateChiselRunes message, MessageContext ctx) {
            Container container = ctx.getServerHandler().player.openContainer;
            if(container instanceof ContainerChiselStation){
                TileEntityChiselStation station = ((ContainerChiselStation)container).getChestInventory();
                System.out.println("OUTGOING MESSAGE: " + message.runes);
                System.out.println("station: " + station.getPos());
                station.setRuneList(message.getRunes());
            }
            return null;
        }
    
	}
}