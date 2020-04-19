package com.huto.hutosmod.items.wands;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemLightningWand extends Item {
	public static float sync = 0;
	public static float mana = 0;

	public ItemLightningWand(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		Vec3d aim = playerIn.getLookVec();

		sync++;
		sync %= 10;
		if (sync == 0)
			PacketHandler.INSTANCE
					.sendToServer(new PacketGetMana(mana, "com.huto.hutosmod.items.ItemLightningWand", "mana"));

		IMana mana = playerIn.getCapability(ManaProvider.MANA_CAP, null);
		if (mana.getMana() >= 10F) {

			EntityLightningBolt lightningBall = new EntityLightningBolt(worldIn, 1D, 1D, 1D, false);
			lightningBall.setPosition(playerIn.posX + aim.x * 1.5D, playerIn.posY + aim.y * 1.5D,
					playerIn.posZ + aim.z * 1.5D);
			worldIn.spawnEntity(lightningBall);

			mana.consume(5);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
		} else {
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);

		}
	}
}
