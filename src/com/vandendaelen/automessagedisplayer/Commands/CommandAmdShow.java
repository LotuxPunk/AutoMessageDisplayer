package com.vandendaelen.automessagedisplayer.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vandendaelen.automessagedisplayer.MessageManager;

public class CommandAmdShow implements CommandExecutor, MessageManager {

	private List<String> messages;

	public CommandAmdShow(List<String> m) {
		messages = m;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		if(args.length >= 1) {
			int iMessage = Integer.parseInt(args[0]) - 1;
			if(p.hasPermission("automessagedisplayer.show")||p.isOp()) {
				if(messages.size() >= iMessage+1 && iMessage+1 > 0) {
					MessageManager.messageDisplayer("[&d"+p.getName()+"&r] "+messages.get(iMessage));
				} else {
					p.sendMessage(MessageManager.switchChar("&4No message selected"));
				}
				return true;
			}
		}
		return false;
	}

}
