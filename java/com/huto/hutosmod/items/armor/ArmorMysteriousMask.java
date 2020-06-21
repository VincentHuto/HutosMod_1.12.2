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

public class ArmorMysteriousMask extends ItemArmor{

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
			TestParticle newEffect1 = new TestParticle(world, player.posX, player.posY + 2, player.posZ, 0, 0.01, 0, 50,
					50, 90, 0, 1, 1, 0.3, .50);
			TestParticle newEffect2 = new TestParticle(world, player.posX, player.posY + 2, player.posZ, 0, 0.01, 0, 50,
					30, 90, 1, 1, 1, 0.2, .50);
	//		Minecraft.getMinecraft().effectRenderer.addEffect(newEffect1);
	//		Minecraft.getMinecraft().effectRenderer.addEffect(newEffect2);

		}

	}

}
