package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.tileentites.TileEntityStorageDrum;
import net.minecraft.block.state.IBlockState;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemDebugTool extends Item {

	public ItemDebugTool(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);

		if (worldIn.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);

		if (!player.isSneaking()) {
			String message = String.format("You have §9%d§r mana ", (int) mana.getMana());
			player.sendMessage(new TextComponentString(message));

		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
/*
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {

		TileEntityStorageDrum drum = (TileEntityStorageDrum) worldIn.getTileEntity(pos);
		ItemStack stack = player.getHeldItem(hand);
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);

		if (!player.isSneaking()) {
			String message = String.format("Drum contains §9%d§r mana ", (int) drum.getManaValue());
			player.sendMessage(new TextComponentString(message));

		}

		return EnumActionResult.SUCCESS;
	}
*/
}
