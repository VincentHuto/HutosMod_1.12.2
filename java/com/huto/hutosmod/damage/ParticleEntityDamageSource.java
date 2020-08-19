package com.huto.hutosmod.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSourceIndirect;

public class ParticleEntityDamageSource extends EntityDamageSourceIndirect {

	public ParticleEntityDamageSource(String name, Entity transmitter, Entity indirectSource) {
	    super(name, transmitter, indirectSource);
	    this.setDamageBypassesArmor();
	}

	public static ParticleEntityDamageSource causeElectricDamage(Entity source, Entity transmitter) {
		return new ParticleEntityDamageSource("electric.entity", transmitter, source);
	}
}
