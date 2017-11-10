package com.vandendaelen.simpleautomessage;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class SimpleAutoMessage extends JavaPlugin {
	int iMessages = 0;
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	public void onEnable() {
		System.out.println("Waw, an amazing plugin powered by LotuxPunk ! :-)");
		this.getCommand("samtime").setExecutor(new CommandSamTime(this));
		createConfig();
		if(getConfig().getBoolean("Enable"))
			messageDisplayer();
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
	
}
