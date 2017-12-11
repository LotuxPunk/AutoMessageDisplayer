package com.vandendaelen.automessagedisplayer.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandAmdRandom implements CommandExecutor {

	private String RANDOM_CONFIG;
	private Plugin plugin;

	public CommandAmdRandom(Plugin pl, String r) {
		plugin = pl;
		RANDOM_CONFIG = r;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		if(args.length >= 1) {
			Boolean randomEnabled = Boolean.parseBoolean(args[0]);
			if(p.hasPermission("automessagedisplayer.setrandom")||p.isOp()) {
				plugin.getConfig().set(RANDOM_CONFIG, randomEnabled);
				
				if(randomEnabled) {
					p.sendMessage("§2Random enabled. Do /reload");
				} else {
					p.sendMessage("§4Random disabled. Do /reload");
				}
				plugin.saveConfig();
				plugin.reloadConfig();
				return true;
			}
		}

		return false;
	}

}
