package com.huto.hutosmod.items;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemPortaBench extends Item {

	public ItemPortaBench(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		maxStackSize = 1;
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

		if (worldIn.isRemote) {
			playerIn.openGui(Reference.MODID, Reference.GUI_PORTA_BENCH, worldIn, playerIn.getPosition().getX(),
					playerIn.getPosition().getY(), playerIn.getPosition().getZ());
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
