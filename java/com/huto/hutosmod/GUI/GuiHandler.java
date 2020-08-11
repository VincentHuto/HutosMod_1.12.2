package com.huto.hutosmod.gui;

import java.util.ArrayList;

import com.huto.hutosmod.container.ContainerChiselStation;
import com.huto.hutosmod.container.ContainerRuneStation;
import com.huto.hutosmod.container.ContainerVibratorySelector;
import com.huto.hutosmod.gui.pages.GuiTomeTitle;
import com.huto.hutosmod.mindrunes.container.ContainerPlayerExpanded;
import com.huto.hutosmod.mindrunes.gui.GuiMindRunes;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntityChiselStation;
import com.huto.hutosmod.tileentity.TileEntityRuneStation;
import com.huto.hutosmod.tileentity.TileEntityVibratorySelector;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case (Reference.GUI_Rune_Station):
			return new ContainerRuneStation(player.inventory,
					(TileEntityRuneStation) world.getTileEntity(new BlockPos(x, y, z)), player);
		case (Reference.GUI_Runic_ChiselStation):
			return new ContainerChiselStation(player.inventory,
					(TileEntityChiselStation) world.getTileEntity(new BlockPos(x, y, z)), player);
		case (Reference.GUI_MIND_RUNES):
			return new ContainerPlayerExpanded(player.inventory, !world.isRemote, player);
		case (Reference.GUI_VIBRATORY_SELECTOR):
			return new ContainerVibratorySelector(player.inventory,
					(TileEntityVibratorySelector) world.getTileEntity(new BlockPos(x, y, z)), player);
		case (Reference.GUI_PORTA_BENCH):
			return new ContainerWorkbench(player.inventory, world, new BlockPos(x, y, z));
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		switch (ID) {
		case (Reference.Gui_Tome):
			return new GuiTomeTitle(false);
		case (Reference.Gui_ElderTome):
			return new GuiTomeTitle(true);
		case (Reference.GUI_Rune_Station):
			return new GuiRuneStation(player.inventory,
					(TileEntityRuneStation) world.getTileEntity(new BlockPos(x, y, z)), player);
		case (Reference.GUI_Runic_ChiselStation):
			return new GuiChiselStation(player.inventory,
					(TileEntityChiselStation) world.getTileEntity(new BlockPos(x, y, z)), player);
		case (Reference.GUI_VIBRATORY_SELECTOR):
			return new GuiVibratorySelector(player.inventory,
					(TileEntityVibratorySelector) world.getTileEntity(new BlockPos(x, y, z)), player);
		case (Reference.GUI_MIND_RUNES):
			return new GuiMindRunes(player);
		case (Reference.GUI_PORTA_BENCH):
			return new GuiCrafting(player.inventory, world);
		default:
			return null;
		}
	}
}