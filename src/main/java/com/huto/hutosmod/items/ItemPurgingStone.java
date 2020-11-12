package com.huto.hutosmod.items;

import java.util.Random;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.karma.IKarma;
import com.huto.hutosmod.karma.KarmaProvider;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetKarma;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemPurgingStone extends Item {
	public static float sync = 0;
	public static float karma = 0;

	public ItemPurgingStone(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		maxStackSize = 3;
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);

		sync++;
		sync %= 10;
		if (sync == 0)
			PacketHandler.INSTANCE
					.sendToServer(new PacketGetKarma(karma, "com.huto.hutosmod.items.ItemPurgingStone", "karma"));

		IKarma karma = playerIn.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
		if (karma.getKarma() <0 && karma.getKarma() > -20) {
			karma.set(0);
			itemStack.shrink(1);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);

		} else
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);

	
	}
}
