package com.huto.hutosmod.entities;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class RegisterEntities {

	// Register Entity
	public static void registerEntites() {
		registerEntity("Colin", EntityColin.class, Reference.Entity_Colin, 69, 7733503, 16746829);
		registerEntity("Elemental", EntityElemental.class, Reference.EntityElemental, 69, 3145708, 14417900);
		registerEntity("Dream Walker", EntityDreamWalker.class, Reference.EntityDreamWalker, 69, 16777215, 16777215);
		registerEntity("Memory Flicker", EntityMemoryFlicker.class, Reference.EntityMemoryFlicker, 69, 3093088, 16777215);

	}

	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1,
			int color2) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id,
				MainClass.instance, range, 1, true, color1, color2);

	}

}
