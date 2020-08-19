package com.huto.hutosmod.items.runes;

import java.util.List;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRune extends Item {

	public int level = 1;
	public String TAG_LEVEL = "level";

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
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound compound = stack.getTagCompound();
		compound.setInteger(TAG_LEVEL, this.getLevel());

		if (compound.hasKey(TAG_LEVEL)) {
			int lev = compound.getInteger(TAG_LEVEL);
			compound.setInteger(TAG_LEVEL, lev++);
		}
		stack.setTagCompound(compound);
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_LEVEL)) {
			tooltip.add(
					TextFormatting.GOLD + "Form: " + Integer.toString(stack.getTagCompound().getInteger(TAG_LEVEL)));
		}

	}

}
