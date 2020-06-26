package com.huto.hutosmod.items.armor;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.models.ModelMysteriousMask2;
import com.huto.hutosmod.particles.TestParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorMysteriousMask extends ItemArmor {

	public ArmorMysteriousMask(String name, ArmorMaterial materialIn, int renderIndexIn,
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
				ModelMysteriousMask2 model = new ModelMysteriousMask2();
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

}
