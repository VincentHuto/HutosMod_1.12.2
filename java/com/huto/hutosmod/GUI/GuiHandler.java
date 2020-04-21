package com.huto.hutosmod.gui;

import com.huto.hutosmod.container.ContainerFusionFurnace;
import com.huto.hutosmod.container.ContainerRuneStation;
import com.huto.hutosmod.mindrunes.container.ContainerPlayerExpanded;
import com.huto.hutosmod.mindrunes.gui.GuiMindRunes;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntityFusionFurnace;
import com.huto.hutosmod.tileentity.TileEntityRuneStation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.Gui_Fusion_Furnace) {
			return new ContainerFusionFurnace(player.inventory,
					(TileEntityFusionFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		}

		if (ID == Reference.GUI_Rune_Station) {
			return new ContainerRuneStation(player.inventory,
					(TileEntityRuneStation) world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		if (ID == Reference.GUI_MIND_RUNES) {
			return new ContainerPlayerExpanded(player.inventory, !world.isRemote, player);
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.Gui_Fusion_Furnace) {
			return new GuiFusionFurnace(player.inventory,
					(TileEntityFusionFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		}

		if (ID == Reference.GUI_Rune_Station) {
			return new GuiRuneStation(player.inventory,
					(TileEntityRuneStation) world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		if (ID == Reference.GUI_MIND_RUNES) {
			return new GuiMindRunes(player);
		}
		return null;
	}
}