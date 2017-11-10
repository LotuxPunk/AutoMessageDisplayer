package com.vandendaelen.simpleautomessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandSamTime implements CommandExecutor {
	
	Plugin plugin;
	
	public CommandSamTime(Plugin pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		plugin.getConfig().set("Time", Integer.parseInt(args[0]));
		plugin.saveConfig();
		plugin.reloadConfig();
		return true;
	}

}
