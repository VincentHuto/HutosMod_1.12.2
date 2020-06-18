package com.huto.hutosmod.entities;

import java.util.Random;

import javax.annotation.Nullable;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.proxy.Vector3;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.sound.SoundsHandler;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityColin extends EntityMob implements IRangedAttackMob {
	public static final ResourceLocation ColinTable = LootTableList
			.register(new ResourceLocation(Reference.MODID, "colin"));
	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(
			this.getDisplayName().appendText(" The Homewrecker"), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS))
					.setDarkenSky(true);

	public int deathTicks;
	public int livingTicks;

	public EntityColin(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.95F);
		this.isImmuneToFire = true;
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(3, new EntityColin.AIFireballAttack(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0, false));
//		this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 5.0D));
		// this.tasks.addTask(2, new EntityAISwimming(this));
		// this.tasks.addTask(4, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		// this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

	}

	@Override
	protected ResourceLocation getLootTable() {
		return ColinTable;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.7D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
	}

	/**
	 * Called only once on an entity when first time spawned, via egg, mob spawner,
	 * natural spawning etc, but not called when entity is reloaded from nbt. Mainly
	 * used for initializing attributes and inventory
	 */
	@Nullable
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		float f = difficulty.getClampedAdditionalDifficulty();

		this.setEquipmentBasedOnDifficulty(difficulty);

		return livingdata;
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);

		if (this.rand.nextFloat() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
			int i = this.rand.nextInt(1);

			if (i == 0) {
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			} else {
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
			}
		}
	}

	@Override
	public float getEyeHeight() {
		return 2.8F;
	}

	public boolean isArmored() {
		return this.getHealth() <= this.getMaxHealth() / 2.0F;
	}

	public boolean isVulnerable() {
		return this.getHealth() <= this.getMaxHealth() / 4.0F;
	}

	/*
	 * @Override public EntityCow createChild(EntityAgeable ageable) { return new
	 * EntityColin(world); }
	 */
	protected SoundEvent getAmbientSound() {

		return SoundsHandler.ENTITY_COLIN_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource source) {

		return SoundsHandler.ENTITY_COLIN_HURT;
	}

	protected SoundEvent getDeathSound() {

		return SoundsHandler.ENTITY_COLIN_DEATH;

	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		if (this.isArmored()) {
			// AutoRegen
			this.heal(0.05F);
		}
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

		if (this.isDead) {
			this.bossInfo.setPercent(0);

		}
	}

	private void dropExperience(int amount) {
		while (amount > 0) {
			int i = EntityXPOrb.getXPSplit(amount);
			amount -= i;
			this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, i));
		}
	}

	@Override
	public void onLivingUpdate() {

		if (this.livingTicks <= 180) {

			++this.livingTicks;
			// System.out.println("Incremeting living");
			// System.out.println(livingTicks);

		} else {
			this.livingTicks = 0;
			// System.out.println("Living = 0");
		}

		if (this.world.isRemote) {
			if (this.rand.nextInt(24) == 0 && !this.isSilent()) {

				// this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D,
				// SoundEvents.BLOCK_ANVIL_PLACE,this.getSoundCategory(), 1.0F +
				// this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F,false);

			}

			// i>n n is how frequent itl be n spawn per tick
			for (int i = 0; i < 0.0005; ++i) {

				this.world.spawnParticle(EnumParticleTypes.TOTEM,
						this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width,
						this.posY + this.rand.nextDouble() * (double) this.height,
						this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
				Vector3 vec = Vector3.fromEntityCenter(this).add(0, -2, 0);
				Vector3 endVec = vec.add(0, 3.5, 0);
			//	MainClass.proxy.lightningFX(endVec, vec, 5F, System.nanoTime(), Reference.white, Reference.oxblood);
				// this.world.createExplosion(this, posX, posY, posZ, 2, true);

			}

		}
		super.onLivingUpdate();

	}

	/**
	 * handles entity death timer, experience orb and particle creation
	 */
//	EntityPlayer player = new EntityPlayer();

	protected void onDeathUpdate() {

		++this.deathTicks;

		if (this.deathTicks >= 180 && this.deathTicks <= 200) {
			float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
			float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
			float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
			this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (double) f, this.posY + 2.0D + (double) f1,
					this.posZ + (double) f2, 0.0D, 0.0D, 0.0D);
		}

		boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
		int i = 500;

		Random rand = new Random();
		if (!this.world.isRemote) {
			if (this.deathTicks > 150 && flag) {
				// this.dropExperience(MathHelper.floor((float) i * 0.08F));
				this.dropItem(Items.BAKED_POTATO, 1);
				// this.world.playSound(this.posX, this.posY, this.posZ,
				// SoundEvents.BLOCK_ANVIL_PLACE,this.getSoundCategory(), 1.0F +
				// this.rand.nextFloat(), this.rand.nextFloat() * 110.7F + 0.3F,false);

			}

			if (this.deathTicks == 1) {
				this.world.playBroadcastSound(2048, new BlockPos(this), 0);
				this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_ANVIL_PLACE,
						this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 110.7F + 0.3F,
						false);

			}
		}

		this.move(MoverType.SELF, 0.0D, 1.10000000149011612D, 0.0D);
		this.rotationYaw += 200000.0F;
		this.renderYawOffset = this.rotationYaw;

		this.rotationYaw += 200000.0F;
		this.renderYawOffset = this.rotationYaw;

		if (this.deathTicks == 200 && !this.world.isRemote) {
			if (flag) {

				// this.world.createExplosion(this, posX, posY, posZ, 500, true);
			}

			this.setDead();
		}
	}

	/**
	 * Add the given player to the list of players tracking this entity. For
	 * instance, a player may track a boss in order to view its associated boss bar.
	 */
	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	/**
	 * Removes the given player from the list of players tracking this entity. See
	 * {@link Entity#addTrackingPlayer} for more information on tracking.
	 */
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	protected boolean canBeRidden(Entity entityIn) {
		return true;
	}

	public boolean isNonBoss() {
		return false;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
	}

	static class AIFireballAttack extends EntityAIBase {
		private final EntityColin blaze;
		private int attackStep;
		private int attackTime;

		public AIFireballAttack(EntityColin blazeIn) {
			this.blaze = blazeIn;
			this.setMutexBits(3);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
			return entitylivingbase != null && entitylivingbase.isEntityAlive();
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			this.attackStep = 0;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask() {
			--this.attackTime;
			EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
			double d0 = this.blaze.getDistanceSq(entitylivingbase);

			if (d0 < 4.0D) {
				if (this.attackTime <= 0) {
					this.attackTime = 20;
					this.blaze.attackEntityAsMob(entitylivingbase);
				}

				this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY,
						entitylivingbase.posZ, 1.0D);
			} else if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
				double d1 = entitylivingbase.posX - this.blaze.posX;
				double d2 = entitylivingbase.getEntityBoundingBox().minY + (double) (entitylivingbase.height / 2.0F)
						- (this.blaze.posY + (double) (this.blaze.height / 2.0F));
				double d3 = entitylivingbase.posZ - this.blaze.posZ;

				if (this.attackTime <= 0) {
					++this.attackStep;

					if (this.attackStep == 1) {
						this.attackTime = 10;
					} else if (this.attackStep <= 4) {
						this.attackTime = 6;
					} else {
						this.attackTime = 30;
						this.attackStep = 0;
					}

					if (this.attackStep > 1) {
						float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
						this.blaze.world.playEvent((EntityPlayer) null, 1018,
								new BlockPos((int) this.blaze.posX, (int) this.blaze.posY, (int) this.blaze.posZ), 0);

						for (int i = 0; i < 1; ++i) {
							EntityLightningBolt bolt = new EntityLightningBolt(this.blaze.world,
									d1 + this.blaze.getRNG().nextGaussian() * (double) f, d2,
									d3 + this.blaze.getRNG().nextGaussian() * (double) f, false);
							EntitySmallFireball entitySmallFireball = new EntitySmallFireball(this.blaze.world,
									this.blaze, d1 + this.blaze.getRNG().nextGaussian() * (double) f, d2,
									d3 + this.blaze.getRNG().nextGaussian() * (double) f);
							entitySmallFireball.posY = this.blaze.posY + (double) (this.blaze.height / 2.0F) + 0.5D;
							bolt.posY = this.blaze.posY + (double) (this.blaze.height / 2.0F) + 0.5D;

							this.blaze.world.spawnEntity(entitySmallFireball);

						}
					}
				}

				this.blaze.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
			} else {
				this.blaze.getNavigator().clearPath();
				this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY,
						entitylivingbase.posZ, 1.0D);
			}

			super.updateTask();
		}

		private double getFollowDistance() {
			IAttributeInstance iattributeinstance = this.blaze.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
			return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
		}
	}
}
