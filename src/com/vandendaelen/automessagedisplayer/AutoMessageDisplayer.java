package com.vandendaelen.automessagedisplayer;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.vandendaelen.automessagedisplayer.Commands.CommandAmdList;
import com.vandendaelen.automessagedisplayer.Commands.CommandAmdRandom;
import com.vandendaelen.automessagedisplayer.Commands.CommandAmdTime;

public class AutoMessageDisplayer extends JavaPlugin {
	public static final String RANDOM_CONFIG = "Random enabled";
	public static final String TIME_CONFIG = "Time";
	public static final String MIN_PLAYER_CONFIG = "Min player to enable";
	private int iMessages = 0;

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	public void onEnable() {
		System.out.println("Waw, an amazing plugin powered by LotuxPunk ! :-)");
		registerCommands();
		createConfig();

		this.getConfig().addDefault(RANDOM_CONFIG, false);
		this.getConfig().addDefault(MIN_PLAYER_CONFIG, 1);
		this.getConfig().options().copyDefaults(true);
		saveConfig();

		//Enable display of messages
		messageManager();
	}
	
	private void registerCommands() {
		this.getCommand("amdtime").setExecutor(new CommandAmdTime(this, TIME_CONFIG));
		this.getCommand("amdrandom").setExecutor(new CommandAmdRandom(this, RANDOM_CONFIG));
		this.getCommand("amdlist").setExecutor(new CommandAmdList(this, this.getListMessages()));
	}

	private void createConfig() {
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			if (!file.exists()) {
				getLogger().info("Config.yml not found, creating!");
				saveDefaultConfig();
			} else {
				getLogger().info("Config.yml found, loading!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void messageManager() {
		Random randomGenerator = new Random();
		List<String> listMessages = getListMessages();
		System.out.println(listMessages.size()+" messages loaded");
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				if(getConfig().getBoolean("Enable")) {
					if(Bukkit.getOnlinePlayers().size() >= getConfig().getInt(MIN_PLAYER_CONFIG)) {
						if(!getConfig().getBoolean(RANDOM_CONFIG)) {
							messageDisplayer(listMessages.get(iMessages));
							iMessages++;
							if (iMessages >= listMessages.size())
								iMessages = 0;
						} else {
							messageDisplayer(listMessages.get(randomGenerator.nextInt(listMessages.size())));
						}
					}

				}

			}

		}, 0, this.getConfig().getInt("Time")*60*20);
	}
	
	public List<String> getListMessages() {
		return getConfig().getStringList("Messages");
	}

	public void messageDisplayer(String message) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}		
	}
}
