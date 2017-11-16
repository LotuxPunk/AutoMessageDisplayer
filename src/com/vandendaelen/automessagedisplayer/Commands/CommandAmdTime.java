package com.vandendaelen.automessagedisplayer.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandAmdTime implements CommandExecutor {

	private Plugin plugin;
	private String TIME_CONFIG;

	public CommandAmdTime(Plugin pl, String r) {
		plugin = pl;
		TIME_CONFIG = r;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		if(p.hasPermission("simpleautomessage.settime")||p.isOp()) {
			plugin.getConfig().set(TIME_CONFIG, Integer.parseInt(args[0]));
			p.sendMessage("§9Time set to : §f"+args[0]);
			plugin.saveConfig();
			plugin.reloadConfig();
			return true;
		}
		return false;
	}

}
