package com.huto.hutosmod.items;

import java.util.List;

import javax.annotation.Nullable;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.entities.EntityColin;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRepulsionCharm extends Item {

	public static boolean state;
	public static String TAG_STATE = "state";

	public ItemRepulsionCharm(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;
		addPropertyOverride(new ResourceLocation(Reference.MODID, "ON"), new IItemPropertyGetter() {
			@Override
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return ItemRepulsionCharm.getPropertyOff(stack, entityIn);
			}
		});
	}
	
	public static float getPropertyOff(ItemStack stack, @Nullable EntityLivingBase entityIn) {
		if (entityIn == null)
			return 0.f;
		if (!stack.isEmpty() && stack.getItem() instanceof ItemRepulsionCharm)
			return !ItemRepulsionCharm.isActivated(stack) ? 0.f : 1.f; // 0 - not empty
		return 0.f;
	}

	public static boolean isActivated(ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			return nbt.hasKey(TAG_STATE) && nbt.getBoolean(TAG_STATE);
		}
		return false;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound compound = stack.getTagCompound();
			compound.setBoolean(TAG_STATE, false);
		}
		NBTTagCompound compound = stack.getTagCompound();

		if (compound.hasKey(TAG_STATE)) {
			boolean lev = compound.getBoolean(TAG_STATE);
			compound.setBoolean(TAG_STATE, !lev);
		}
		stack.setTagCompound(compound);
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_STATE)) {
			if (stack.getTagCompound().getBoolean(TAG_STATE)) {
				tooltip.add(TextFormatting.BLUE + "State: On");
			} else {
				tooltip.add(TextFormatting.RED + "State: Off");

			}

		}

	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound compound = stack.getTagCompound();
			compound.setBoolean(TAG_STATE, false);
		}
		NBTTagCompound compound = stack.getTagCompound();

		if (compound.hasKey(TAG_STATE)) {
			if (compound.getBoolean(TAG_STATE) == true) {
				repelSlimeInAABBFromPoint(worldIn,
						new AxisAlignedBB(entityIn.getPosition().add(-4, -4, -4), entityIn.getPosition().add(4, 4, 4)),
						entityIn.getPosition().getX() + 0.5, entityIn.getPosition().getY() + 0.5,
						entityIn.getPosition().getZ() + 0.5);
			}
		}

	}

	public static void repelSlimeInAABBFromPoint(World world, AxisAlignedBB effectBounds, double x, double y,
			double z) {
		List<Entity> list = world.getEntitiesWithinAABB(Entity.class, effectBounds);
		for (Entity ent : list) {
			if ((ent instanceof EntityMob || ent instanceof EntitySlime || ent instanceof EntityFireball
					|| ent instanceof IProjectile && !(ent instanceof EntityDragon)
							&& !(ent instanceof EntityWither))) {

				Vec3d p = new Vec3d(x, y, z);
				Vec3d t = new Vec3d(ent.posX, ent.posY, ent.posZ);
				double distance = p.distanceTo(t) + 0.1D;
				Vec3d r = new Vec3d(t.x - p.x, t.y - p.y, t.z - p.z);

				// if += is changed to -= this become attraction
				ent.motionX += r.x / 1.2D / distance;
				ent.motionY += r.y / 1.2D / distance;
				ent.motionZ += r.z / 1.2D / distance;

				if (world.isRemote) {
					for (int countparticles = 0; countparticles <= 10; ++countparticles) {
						ent.world.spawnParticle(EnumParticleTypes.REDSTONE,
								ent.posX + (world.rand.nextDouble() - 0.5D) * (double) ent.width,
								ent.posY + world.rand.nextDouble() * (double) ent.height - (double) ent.getYOffset()
										- 0.5,
								ent.posZ + (world.rand.nextDouble() - 0.5D) * (double) ent.width, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		}
	}
}
