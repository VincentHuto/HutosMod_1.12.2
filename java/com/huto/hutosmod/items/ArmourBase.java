package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmourBase extends ItemArmor {

	public ArmourBase(String name,ArmorMaterial materialIn, int renderIndexIn,EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn,renderIndexIn,equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

}
