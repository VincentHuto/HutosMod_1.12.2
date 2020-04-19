package com.huto.hutosmod.items.runes;

import java.util.List;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.font.TextFormating;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRune extends Item {

	public int level =0;

	public ItemRune(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		setMaxStackSize(1);
		ItemRegistry.ITEMS.add(this);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		FontRenderer LC = TextFormating.getAkloFont();
		tooltip.add("§5Form: " + level + "§r");
		super.addInformation(stack, worldIn, tooltip, flagIn);

	}

}
