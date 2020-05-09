package com.huto.hutosmod.events;

import java.util.Random;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.items.tools.ToolBloodSword;
import com.huto.hutosmod.keybinds.KeyBindRegistry;
import com.huto.hutosmod.potions.PotionInit;
import com.huto.hutosmod.renders.layers.LayerPlayerAura;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ModEventHandler {

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.player.world.isRemote)

		{
			System.out.println("Logged");
		}
	}

	@SubscribeEvent
	public void onPlayerPickupXP(PlayerPickupXpEvent e) {
	}

	@SubscribeEvent
	public void onKillWithBloodySword(LivingDeathEvent e) {
		if (e.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e.getSource().getTrueSource();
			if (p.getHeldItemMainhand() != null && p.getHeldItemMainhand().getItem() instanceof ToolBloodSword) {
				System.out.println("Killed with Bloodsword");
				Random rand = new Random();
				e.getEntity().dropItem(ItemRegistry.essence_drop, /* rand.nextInt(3) **/ 1);
			}
		}

	}
	public static LayerPlayerAura aura;

	public void renderPlayer(RenderPlayerEvent.Pre e) {
		 Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default").addLayer(aura);;
		 Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim").addLayer(aura);;

	}
	
}
