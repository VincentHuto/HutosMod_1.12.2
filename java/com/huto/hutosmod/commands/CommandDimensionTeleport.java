package com.huto.hutosmod.commands;

import java.util.List;

import com.google.common.collect.Lists;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandDimensionTeleport extends CommandBase {

	private final List<String> aliases = Lists.newArrayList(Reference.MODID, "tpd", "tpdim", "tpdimension");

	@Override
	public String getName() {

		return "tpdimension";
	}

	@Override
	public String getUsage(ICommandSender sender) {

		return "tpdimension <id>";
	}

	@Override
	public List<String> getAliases() {

		return aliases;
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1)
			return;
		int dimensionID;
		String s = args[0];
		try {
			dimensionID = Integer.parseInt(s);
		} catch (NumberFormatException e) {

			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Dimension ID Invalid"));
			return;
		}
		if (sender instanceof EntityPlayer) {
			
			if(dimensionID==1 ||dimensionID==-402) {
				Teleport.teleportToDimention((EntityPlayer) sender, dimensionID,0,65,0);
			}
			
			if(dimensionID==-403) {
				Teleport.teleportToDimention((EntityPlayer) sender, dimensionID,0,65,0);
			}


			Teleport.teleportToDimention((EntityPlayer) sender, dimensionID, sender.getPosition().getX(),
					sender.getPosition().getY(), sender.getPosition().getZ());

		}

	}

}
