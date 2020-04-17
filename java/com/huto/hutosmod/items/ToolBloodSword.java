package com.huto.hutosmod.items;

import java.util.List;

import com.huto.hutosmod.MainClass;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ToolBloodSword extends ItemSword {

	public ToolBloodSword(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

	@Override

	public boolean hitEntity(ItemStack itemstack, EntityLivingBase attackedEntity, EntityLivingBase attacker)

	{
		attackedEntity.attackEntityFrom(ItemRegistry.BloodSwordDamageSource, 10F);
		System.out.println("HIT WITH BLOOD SWORD");
		// attackedEntity.dropItem(ModRegistry.Ruby, 5);

		return super.hitEntity(itemstack, attackedEntity, attacker);

	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("§4 +5 Blood Damage §r");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}