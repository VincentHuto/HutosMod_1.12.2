package com.huto.hutosmod.reference;

import com.huto.hutosmod.karma.KarmaProvider;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.tileentites.TileMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Capability handler
 *
 * This class is responsible for attaching our capabilities
 */
public class CapabilityHandler {
	public static final ResourceLocation MANA_CAP = new ResourceLocation(Reference.MODID, "mana");
	public static final ResourceLocation KARMA_CAP = new ResourceLocation(Reference.MODID, "karma");
	public static final ResourceLocation MINDRUNE_CAP = new ResourceLocation(Reference.MODID, "mindrunes");

	@SubscribeEvent
	public void attachCapabilityPlayer(AttachCapabilitiesEvent event) {
		if (!(event.getObject() instanceof EntityPlayer))
			return;

		event.addCapability(MANA_CAP, new ManaProvider());
		event.addCapability(KARMA_CAP, new KarmaProvider());
		
		//event.addCapability(MINDRUNE_CAP, new MindRuneProvider());

	}
	@SubscribeEvent
	public void attachCapabilityTile(AttachCapabilitiesEvent event) {
		if (!(event.getObject() instanceof TileMod))
			return;
		event.addCapability(MANA_CAP, new ManaProvider());
	}
}