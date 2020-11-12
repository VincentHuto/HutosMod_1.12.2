package com.huto.hutosmod.commands;

import java.util.List;

import com.google.common.collect.Lists;
import com.huto.hutosmod.karma.IKarma;
import com.huto.hutosmod.karma.KarmaProvider;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandClearKarma extends CommandBase {

	private final List<String> aliases = Lists.newArrayList(Reference.MODID, "karmaclear");

	@Override
	public String getName() {

		return "clearkarma";
	}

	@Override
	public String getUsage(ICommandSender sender) {

		return "sets players karma to 0";
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
	
		if (sender instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
			IKarma karma = player.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
			karma.set(0);
		}

	}

}
