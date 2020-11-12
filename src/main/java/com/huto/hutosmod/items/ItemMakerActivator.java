package com.huto.hutosmod.items;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.blocks.IActivatable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ItemMakerActivator extends Item {


	public ItemMakerActivator(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;

	}

	@Nonnull
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float par8, float par9, float par10) {
		ItemStack stack = player.getHeldItem(hand);
		Block block = world.getBlockState(pos).getBlock();

		if(block instanceof IActivatable) {
			TileEntity tile = world.getTileEntity(pos);

			boolean wanded;
				if(world.isRemote) {
					player.swingArm(hand);
					player.playSound(SoundEvents.BLOCK_ANVIL_USE, 0.11F, 1F);
				}
				wanded = true;
			
				wanded = ((IActivatable) block).onUsedByActivator(player, stack, world, pos, side);
				if(wanded && world.isRemote)
					player.swingArm(hand);
			return wanded ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
		}
		return EnumActionResult.PASS;
	}


	@Nonnull
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.RARE;
	}

}
