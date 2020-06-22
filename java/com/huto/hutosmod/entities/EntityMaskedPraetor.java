package com.huto.hutosmod.entities;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.sound.SoundsHandler;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMaskedPraetor extends EntityCreature {
	public static final ResourceLocation PraetorTable = LootTableList
			.register(new ResourceLocation(Reference.MODID, "maskedPraetor"));

	public int deathTicks;
	public int livingTicks;

	public EntityMaskedPraetor(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.95F);

	}

	@Override
	protected void initEntityAI() {

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.25D, ItemRegistry.null_ingot, false));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.tasks.addTask(8, new EntityAILookIdle(this));

	}

	@Override
	protected ResourceLocation getLootTable() {
		return PraetorTable;
	}

	@Override
	protected void applyEntityAttributes() {
		// TODO Auto-generated method stub
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);

	}

	@Override
	public float getEyeHeight() {
		return 2.8F;
	}



	protected SoundEvent getAmbientSound() {

		return SoundsHandler.ENTITY_COLIN_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource source) {

		return SoundsHandler.ENTITY_COLIN_HURT;
	}

	protected SoundEvent getDeathSound() {

		return SoundsHandler.ENTITY_COLIN_DEATH;

	}

	private void dropExperience(int amount) {
		while (amount > 0) {
			int i = EntityXPOrb.getXPSplit(amount);
			amount -= i;
			this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, i));
		}
	}

	/*@Override
	public void onLivingUpdate() {
		if (this.livingTicks <= 180) {

			++this.livingTicks;

			super.onLivingUpdate();
		}

	}

	*//**
	 * handles entity death timer, experience orb and particle creation
	 *//*
//	EntityPlayer player = new EntityPlayer();

	protected void onDeathUpdate() {
		if (this.livingTicks <= 1) {

			++this.deathTicks;

		}
		this.setDead();

	}*/
}
