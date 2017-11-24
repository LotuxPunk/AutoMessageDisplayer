package com.vandendaelen.automessagedisplayer.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandAmdList implements CommandExecutor {

	Plugin plugin;
	List<String> messages;


	public CommandAmdList(Plugin pl,List<String> m) {
		plugin = pl;
		messages = m;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		if(p.hasPermission("automessagedisplayer.list")||p.isOp()) {
			messagesDisplayer(p);
			return true;
		}
		return false;
	}

	public void messagesDisplayer(Player p) {
		if(!messages.isEmpty()) {
			for(int i = 0; i < messages.size(); i++){
				p.sendMessage(i+". "+messages.get(i));
			}
		} else {
			p.sendMessage("§9List is empty");
		}

	}

}
