package com.vandendaelen.automessagedisplayer.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.vandendaelen.automessagedisplayer.MessageManager;

public class CommandAmdAdd implements CommandExecutor, MessageManager {

	private List<String> messages;
	private Plugin plugin;
	private String MESS_CONFIG;

	public CommandAmdAdd(List<String> messages, Plugin plugin, String mESS_CONFIG) {
		this.messages = messages;
		this.plugin = plugin;
		MESS_CONFIG = mESS_CONFIG;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		if(p.hasPermission("automessagedisplayer.show")||p.isOp()) {
			if(args.length >= 1) {
				String messageAdded = String.join(" ", args);
				messages.add(messageAdded);
				plugin.getConfig().set(MESS_CONFIG, messages);
				plugin.saveConfig();
				plugin.reloadConfig();
				p.sendMessage(MessageManager.switchChar("&9Message added. Do /reload to refresh messages list"));
				return true;
			}
		}
		return false;
	}

}
