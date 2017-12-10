package com.vandendaelen.automessagedisplayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public interface MessageManager {
	
	static void messageDisplayer(String message) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(switchChar(message));
		}		
	}
	
	static String switchChar(String message) {
		return message.replace('&', '§');
	}

}
