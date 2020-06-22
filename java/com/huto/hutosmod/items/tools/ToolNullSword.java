package com.huto.hutosmod.items.tools;

import java.util.List;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ToolNullSword extends ItemSword {

	public ToolNullSword(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

	@Override

	public boolean hitEntity(ItemStack itemstack, EntityLivingBase attackedEntity, EntityLivingBase attacker)

	{
		attackedEntity.attackEntityFrom(ItemRegistry.NullSwordDamageSource, 10F);
	//	System.out.println("HIT WITH Null SWORD");
		// attackedEntity.dropItem(ModRegistry.Ruby, 5);

		return super.hitEntity(itemstack, attackedEntity, attacker);

	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("§4 +5 Soul Damage §r");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}