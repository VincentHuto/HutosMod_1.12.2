package com.huto.hutosmod.items.armor;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.models.ModelRobes;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ElderHelemt extends ItemArmor {

	public ElderHelemt(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setRegistryName("elder_helmet");
		this.setUnlocalizedName("elder_helmet");
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

/*	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (stack.getItem() == ItemRegistry.elder_helmet) {
			return Reference.MODID + ":textures/models/armor/modelelder.png";

		} else {
			return null;
		}
	}
*/
	@Override
	public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack,
			EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default) {
		if (itemStack != null) {
			if (itemStack.getItem() instanceof ElderHelemt) {
				EntityEquipmentSlot type = ((ItemArmor) itemStack.getItem()).getEquipmentSlot();

				ModelRobes armorModel = (ModelRobes) MainClass.proxy.getArmorModel(4);
				if (armorModel != null) {
					armorModel.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
					armorModel.bipedHeadwear.showModel = armorSlot == EntityEquipmentSlot.HEAD;
					armorModel.bipedBody.showModel = armorSlot == EntityEquipmentSlot.HEAD
							|| armorSlot == EntityEquipmentSlot.CHEST;
					armorModel.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
					armorModel.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
					armorModel.bipedRightLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS
							|| armorSlot == EntityEquipmentSlot.LEGS;
					armorModel.bipedLeftLeg.showModel = armorSlot == EntityEquipmentSlot.LEGS
							|| armorSlot == EntityEquipmentSlot.LEGS;

					armorModel.isSneak = entityLiving.isSneaking();
					armorModel.isRiding = entityLiving.isRiding();
					armorModel.isChild = entityLiving.isChild();

					return armorModel;
				}
			}

		}

		return null;

	}

}