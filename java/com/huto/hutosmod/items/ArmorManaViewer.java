package com.huto.hutosmod.items;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.models.ModelManaViewer;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorManaViewer extends ItemArmor {

	public ArmorManaViewer(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, 1, equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		setMaxStackSize(1);
		ItemRegistry.ITEMS.add(this);
	}

	


	@SideOnly(Side.CLIENT)

	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		if (itemStack != ItemStack.EMPTY) {
			if (itemStack.getItem() instanceof ItemArmor) {

				ModelManaViewer model = new ModelManaViewer();

				model.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;

				model.isChild = _default.isChild;
				model.isSneak = _default.isSneak;
				model.isRiding = _default.isRiding;
				model.rightArmPose = _default.rightArmPose;
				model.leftArmPose = _default.leftArmPose;

				return model;
			}
		}

		return null;
	}

	@Override
	  @SideOnly(Side.CLIENT)

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {

		super.onArmorTick(world, player, itemStack);

		Random rand = new Random();
		for (int countparticles = 0; countparticles <= 10; ++countparticles) {

			float f3 = 0.25F;

			/*world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE,
					player.posX + (rand.nextDouble() - 0.5D) * (double) player.width,
					player.posY + rand.nextDouble() * (double) player.height - (double) player.getYOffset() - .5,
					player.posZ + (rand.nextDouble() - 0.5D) * (double) player.width, 0.0D, 0.0D, 0.0D);*/
	

		}

	}

}
