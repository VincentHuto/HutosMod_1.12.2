package com.huto.hutosmod.mindrunes.events;

import com.huto.hutosmod.mindrunes.RuneType;
import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.mindrunes.cap.RuneCapabilities;
import com.huto.hutosmod.mindrunes.network.PacketOpenRuneInventory;
import com.huto.hutosmod.mindrunes.network.RunesPacketHandler;
import com.huto.hutosmod.proxy.ClientProxy;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientEventHandler
{

	@SubscribeEvent
	public void tooltipEvent(ItemTooltipEvent event) {
		if (!event.getItemStack().isEmpty() && event.getItemStack().hasCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null)) {
			IRune rune = event.getItemStack().getCapability(RuneCapabilities.CAPABILITY_ITEM_RUNE, null);
			RuneType rt = rune.getRuneType(event.getItemStack());
			event.getToolTip().add(TextFormatting.GOLD + I18n.format("name."+rt));
		}
	}
}
