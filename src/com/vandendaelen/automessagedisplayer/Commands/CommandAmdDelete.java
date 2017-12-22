package com.vandendaelen.automessagedisplayer.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.vandendaelen.automessagedisplayer.MessageManager;

public class CommandAmdDelete implements CommandExecutor {

	private List<String> messages;
	private Plugin plugin;
	private String MESS_CONFIG;

	public CommandAmdDelete(List<String> messages, Plugin plugin, String mESS_CONFIG) {
		this.messages = messages;
		this.plugin = plugin;
		MESS_CONFIG = mESS_CONFIG;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		if(p.hasPermission("automessagedisplayer.delete")||p.isOp()) {
			if(args.length >= 1) {
				int iMessage = Integer.parseInt(args[0]) - 1;
				if(iMessage <= messages.size() && iMessage >= 0) {
					String messageDeleted = messages.get(iMessage);
					messages.remove(iMessage);
					p.sendMessage(MessageManager.switchChar("&9Removed : &r" + messageDeleted + " &9Do /reload."));
					plugin.getConfig().set(MESS_CONFIG, messages);
					plugin.saveConfig();
					plugin.reloadConfig();
					return true;
				}
			}
		}
		return false;
	}

}
