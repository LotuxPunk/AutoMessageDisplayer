package com.vandendaelen.automessagedisplayer;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.vandendaelen.automessagedisplayer.Commands.CommandAmdRandom;
import com.vandendaelen.automessagedisplayer.Commands.CommandAmdTime;

public class AutoMessageDisplayer extends JavaPlugin {
	public static final String RANDOM_CONFIG = "Random enabled";
	public static final String TIME_CONFIG = "Time";
	private int iMessages = 0;
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	public void onEnable() {
		System.out.println("Waw, an amazing plugin powered by LotuxPunk ! :-)");
		this.getCommand("amdtime").setExecutor(new CommandAmdTime(this, TIME_CONFIG));
		this.getCommand("amdrandom").setExecutor(new CommandAmdRandom(this, RANDOM_CONFIG));
		createConfig();
		
		this.getConfig().addDefault(RANDOM_CONFIG, false);
		this.getConfig().options().copyDefaults(true);
		saveConfig();
		
		//Enable display of messages
		if(getConfig().getBoolean("Enable")) {
			if(getConfig().getBoolean(RANDOM_CONFIG)) {
				messageRandomDisplayer();
			} else {
				messageDisplayer();
			}
		}
			
		
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
	
	private void messageDisplayer() {
		List<String> listMessages = getConfig().getStringList("Messages");
		System.out.println(listMessages.size()+" messages loaded");
		BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.sendMessage(listMessages.get(iMessages));
				}
				iMessages++;
				if (iMessages >= listMessages.size())
					iMessages = 0;				
			}
        	
        }, 0, this.getConfig().getInt("Time")*60*20);
	}
	
	private void messageRandomDisplayer() {
		Random randomGenerator = new Random();
		List<String> listMessages = getConfig().getStringList("Messages");
		System.out.println(listMessages.size()+" messages loaded");
		BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.sendMessage(listMessages.get(randomGenerator.nextInt(listMessages.size())));
				}			
			}
        	
        }, 0, this.getConfig().getInt(TIME_CONFIG)*60*20);
	}
	
}
