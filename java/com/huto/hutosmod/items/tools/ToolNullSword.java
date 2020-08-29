package com.huto.hutosmod.items.tools;

import java.util.List;

import javax.annotation.Nullable;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemAttractionCharm;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ToolNullSword extends ItemSword {

	public ToolNullSword(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		addPropertyOverride(new ResourceLocation(Reference.MODID, "FREQ"), new IItemPropertyGetter() {
			@Override
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return ToolNullSword.getPropertyOff(stack, entityIn);
			}
		});
	}
	
	
	public static float getPropertyOff(ItemStack stack, @Nullable EntityLivingBase entityIn) {
		if (entityIn == null)
			return 0.f;
		if (!stack.isEmpty() && stack.getItem() instanceof ToolNullSword)
			return !ToolNullSword.isActivated(stack) ? 0.f : 1.f; // 0 - not empty
		return 0.f;
	}

	public static boolean isActivated(ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			return nbt.hasKey("frequency") && nbt.getBoolean("frequency");
		}
		return false;
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