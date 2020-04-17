package com.huto.hutosmod.sounds;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler {

	public static SoundEvent ENTITY_COLIN_AMBIENT, ENTITY_COLIN_HURT, ENTITY_COLIN_DEATH;

	public static void registerSounds() {

		ENTITY_COLIN_AMBIENT = registerSound("entity.colin.ambient");
		 ENTITY_COLIN_HURT = registerSound("entity.colin.hurt");
		 ENTITY_COLIN_DEATH = registerSound("entity.colin.death");

	}

	private static SoundEvent registerSound(String name) {
		ResourceLocation location = new ResourceLocation(Reference.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}

}
