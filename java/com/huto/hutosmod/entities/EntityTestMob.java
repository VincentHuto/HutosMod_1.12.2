package com.huto.hutosmod.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTestMob extends EntityCow {

	public EntityTestMob(World world) {
		super(world);

	}

	@Override
	public EntityCow createChild(EntityAgeable ageable) {
		return new EntityTestMob(world);
	}

	protected SoundEvent getAmbientSound() {
		return super.getAmbientSound();
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return super.getHurtSound(source);
	}

	protected SoundEvent getDeathSound() {
		return super.getDeathSound();
	}
	
	
	

}