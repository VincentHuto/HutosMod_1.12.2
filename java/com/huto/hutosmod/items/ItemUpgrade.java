package com.huto.hutosmod.items;

import java.util.List;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.font.ModTextFormatting;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUpgrade extends Item {


	public ItemUpgrade(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		setMaxStackSize(1);
		ItemRegistry.ITEMS.add(this);
	}


	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		FontRenderer LC = ModTextFormatting.getAkloFont();
		tooltip.add("§5Form: " + this.getUnlocalizedName() + "§r");
		super.addInformation(stack, worldIn, tooltip, flagIn);

	}

}
