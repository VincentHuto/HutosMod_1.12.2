package com.huto.hutosmod.items.runes;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.mindrunes.RuneApi;
import com.huto.hutosmod.mindrunes.RuneType;
import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketGetManaLimit;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemContractRuneRadiance extends ItemRune implements IRune {

	public static float sync = 0;
	public static float mana = 0;
	public static float manaLimit = 0;

	public ItemContractRuneRadiance(String name) {
		super(name);
		// setLevel(2);
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
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 1 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 250, 0, true, true));
			sync++;
			sync %= 10;
			if (sync == 0)
				PacketHandler.INSTANCE.sendToServer(
						new PacketGetMana(mana, "com.huto.hutosmod.items.runes.ItemContractRuneRadiance", "mana"));
			PacketHandler.INSTANCE.sendToServer(new PacketGetManaLimit(manaLimit,
					"com.huto.hutosmod.items.runes.ItemContractRuneRadiance", "manaLimit"));
			IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
			if (mana.manaLimit() <= 900) {
				mana.setLimit(900);

			}
			mana.fill(1);
		}
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

	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, .75F, 2f);
	}
}
