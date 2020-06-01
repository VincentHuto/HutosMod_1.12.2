package com.huto.hutosmod.entities;

import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.monster.EntityZombie;

public class EntityColinAttackAI extends EntityAIAttackMelee {
	private final EntityColin colin;
	private int raiseArmTicks;

	public EntityColinAttackAI(EntityColin colinIn, double speedIn, boolean longMemoryIn) {
		super(colinIn, speedIn, longMemoryIn);
		this.colin = colinIn;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		super.startExecuting();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void resetTask() {
		super.resetTask();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask() {
		super.updateTask();
	}
}