package com.huto.hutosmod.items;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMysticTome extends Item {

	public ItemMysticTome(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
		maxStackSize = 1;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (worldIn.isRemote) {
			playerIn.openGui(MainClass.instance, Reference.Gui_Tome, worldIn, 0,0,0);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);

	}

	@Nonnull
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.UNCOMMON;
	}

}
