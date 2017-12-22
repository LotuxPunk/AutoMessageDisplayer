package com.vandendaelen.automessagedisplayer;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.vandendaelen.automessagedisplayer.Commands.CommandAmdAdd;
import com.vandendaelen.automessagedisplayer.Commands.CommandAmdDelete;
import com.vandendaelen.automessagedisplayer.Commands.CommandAmdList;
import com.vandendaelen.automessagedisplayer.Commands.CommandAmdRandom;
import com.vandendaelen.automessagedisplayer.Commands.CommandAmdShow;
import com.vandendaelen.automessagedisplayer.Commands.CommandAmdTime;
import com.vandendaelen.automessagedisplayer.Listeners.PlayerListener;

public class AutoMessageDisplayer extends JavaPlugin implements MessageManager {
	//Const
	public static final String RANDOM_CONFIG = "Random enabled";
	public static final String TIME_CONFIG = "Time";
	public static final String MIN_PLAYER_CONFIG = "Min player to enable";
	public static final String MESS_CONFIG = "Messages";
	public static final String MESS_ON_PLAY_JOIN_CONFIG = "Message on player join";
	public static final String MESS_ON_PLAY_JOIN_ENABLE_CONFIG = "Message on player join enabled";
	public static final String PREFIX_CONFIG = "Prefix";
	//Var
	private int iMessages = 0;

	@Override
	public void onEnable() {
		System.out.println("Waw, an amazing plugin powered by LotuxPunk ! :-)");
		//Commands
		registerCommands();
		
		//Events
		registerListener();
		
		//Configs
		createConfig();
		registerConfig();

		//Enable display of messages
		messageManager();
	}
	
	private void registerConfig() {
		this.getConfig().addDefault(RANDOM_CONFIG, false);
		this.getConfig().addDefault(MIN_PLAYER_CONFIG, 1);
		this.getConfig().addDefault(MESS_ON_PLAY_JOIN_ENABLE_CONFIG, true);
		this.getConfig().addDefault(MESS_ON_PLAY_JOIN_CONFIG, "&9Welcome !");
		this.getConfig().addDefault(PREFIX_CONFIG, "[&9Server&r]");
		this.getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	private void registerListener() {
		this.getServer().getPluginManager().registerEvents(new PlayerListener(MESS_ON_PLAY_JOIN_CONFIG, MESS_ON_PLAY_JOIN_ENABLE_CONFIG, this), this); 
	}
	
	private void registerCommands() {
		this.getCommand("amdtime").setExecutor(new CommandAmdTime(this, TIME_CONFIG));
		this.getCommand("amdrandom").setExecutor(new CommandAmdRandom(this, RANDOM_CONFIG));
		this.getCommand("amdlist").setExecutor(new CommandAmdList(getListMessages()));
		this.getCommand("amdshow").setExecutor(new CommandAmdShow(getListMessages()));
		this.getCommand("amdadd").setExecutor(new CommandAmdAdd(getListMessages(),this, MESS_CONFIG));
		this.getCommand("amddel").setExecutor(new CommandAmdDelete(getListMessages(),this, MESS_CONFIG));
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
							MessageManager.messageDisplayer(getPrefix() + " " + listMessages.get(iMessages));
							iMessages++;
							if (iMessages >= listMessages.size())
								iMessages = 0;
						} else {
							MessageManager.messageDisplayer(getPrefix() + " " + listMessages.get(randomGenerator.nextInt(listMessages.size())));
						}
					}

				}

			}

		}, 0, this.getConfig().getInt("Time")*60*20);
	}
	
	private String getPrefix() {
		return getConfig().getString(PREFIX_CONFIG);
	}
	
	private List<String> getListMessages() {
		return getConfig().getStringList(MESS_CONFIG);
	}
	
	
}
