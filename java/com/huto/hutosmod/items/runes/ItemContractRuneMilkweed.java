package com.huto.hutosmod.items.runes;

import java.util.List;

import com.huto.hutosmod.mindrunes.RuneApi;
import com.huto.hutosmod.mindrunes.RuneType;
import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.network.UpdateFlyingPacket;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemContractRuneMilkweed extends ItemRune implements IRune {

	public ItemContractRuneMilkweed(String name) {
		super(name);

	}

	@Override
	public RuneType getRuneType(ItemStack itemstack) {
		return RuneType.CONTRACT;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			IRunesItemHandler runes = RuneApi.getRuneHandler(player);
			for (int i = 0; i < runes.getSlots(); i++)
				if ((runes.getStackInSlot(i) == null || runes.getStackInSlot(i).isEmpty())
						&& runes.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
					runes.setStackInSlot(i, player.getHeldItem(hand).copy());
					if (!player.capabilities.isCreativeMode) {
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
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.EPIC;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, .75F, 1.9f);
		if (player instanceof EntityPlayer) {
			EntityPlayer capaPlayer = (EntityPlayer) player;
			if (!player.getEntityWorld().isRemote) {
				updateClientServerFlight((EntityPlayerMP) player, true);
			}
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, .75F, 2f);
		if (player instanceof EntityPlayer) {
			EntityPlayer capaPlayer = (EntityPlayer) player;
			if (!((EntityPlayer) player).isCreative()) {
				if (!player.getEntityWorld().isRemote) {
					updateClientServerFlight((EntityPlayerMP) player, false, false);
				}
			}
		}

	}

	public static void updateClientServerFlight(EntityPlayerMP player, boolean allowFlying) {
		updateClientServerFlight(player, allowFlying, allowFlying && player.capabilities.isFlying);
	}

	public static void updateClientServerFlight(EntityPlayerMP player, boolean allowFlying, boolean isFlying) {
		PacketHandler.sendTo(new UpdateFlyingPacket(allowFlying, isFlying), player);
		player.capabilities.allowFlying = allowFlying;
		player.capabilities.isFlying = isFlying;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(TextFormatting.AQUA + "Effect:Flight");

	}
}
