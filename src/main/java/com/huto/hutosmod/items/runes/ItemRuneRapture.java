package com.huto.hutosmod.items.runes;

import java.util.List;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mindrunes.RuneApi;
import com.huto.hutosmod.mindrunes.RuneType;
import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRuneRapture extends ItemRune implements IRune {

	public ItemRuneRapture(String name) {
		super(name);
	//	setLevel(2);
	
	}

	@Override
	public RuneType getRuneType(ItemStack itemstack) {
		return RuneType.RUNE;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(!world.isRemote) { 
			IRunesItemHandler runes = RuneApi.getRuneHandler(player);
			for(int i = 0; i < runes.getSlots(); i++) 
				if((runes.getStackInSlot(i) == null || runes.getStackInSlot(i).isEmpty()) && runes.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
					runes.setStackInSlot(i, player.getHeldItem(hand).copy());
					if(!player.capabilities.isCreativeMode){
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
					}
					onEquipped(player.getHeldItem(hand), player);
					break;
				}
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}


	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 1 ==0) {
			if (!player.isPotionActive(MobEffects.HEALTH_BOOST))

				player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 250, this.getLevel(), false, false));

			else {

			    PotionEffect potionEffect = player.getActivePotionEffect(MobEffects.HEALTH_BOOST);

			    if (potionEffect.getDuration() < 250)

			        potionEffect.combine(new PotionEffect(MobEffects.HEALTH_BOOST, 250, this.getLevel(), false,false));

			    }		}
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.RARE;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, .75F, 1.9f);
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, .75F, 2f);
	}
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(TextFormatting.GRAY + "Effect:Health Boost");

	}
}
