package com.vandendaelen.automessagedisplayer.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerListener implements Listener {

	private String MESS_ON_PLAY_JOIN_CONFIG;
	private String MESS_ON_PLAY_JOIN_ENABLE_CONFIG;
	private Plugin plugin;



	public PlayerListener(String m, String me, Plugin pl) {
		MESS_ON_PLAY_JOIN_CONFIG = m;
		MESS_ON_PLAY_JOIN_ENABLE_CONFIG = me;
		plugin = pl;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(plugin.getConfig().getBoolean(MESS_ON_PLAY_JOIN_ENABLE_CONFIG)){
			Player p = event.getPlayer();
			p.sendMessage(plugin.getConfig().getString(MESS_ON_PLAY_JOIN_CONFIG));
		}
	}
}
