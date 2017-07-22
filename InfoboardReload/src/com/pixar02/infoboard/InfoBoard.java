package com.pixar02.infoboard;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.pixar02.infoboard.APIS.Vault;
import com.pixar02.infoboard.Utils.FileManager;
import com.pixar02.infoboard.PlayerListener;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class InfoBoard extends JavaPlugin {

	public Timers timers;
	public FileManager fm;
	public boolean update = false;
	public static ArrayList<String> hidefrom = new ArrayList<String>();

	public Permission permission;
	public Economy economy;
	public static boolean economyB;
	public static boolean permissionB;
    @Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Check 1");
		PluginDescriptionFile pdfFile = getDescription();
		Bukkit.getConsoleSender().sendMessage("Check 2");
		Logger logger = getLogger();
		if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			throw new RuntimeException("Could not find PlaceholderAPI!! Plugin can not work without it!");
		}else{
			Bukkit.getConsoleSender().sendMessage("Check 3");
		}
		if (!Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			throw new RuntimeException("Could not find WorldGuard!! Plugin can not work without it!");
		}else{
			Bukkit.getConsoleSender().sendMessage("Check 4");
		}
		if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			throw new RuntimeException("Could not find Vault!! Plugin can not work without it!");
		}else{
			Bukkit.getConsoleSender().sendMessage("Check 5");
		}

		loadFileManager();
		Bukkit.getConsoleSender().sendMessage("Check 6");
		
		Vault.load();
		Bukkit.getConsoleSender().sendMessage("Check 7");
		
		timers = new Timers();
		Bukkit.getConsoleSender().sendMessage("Check 8.1");
		
		timers.start();
		Bukkit.getConsoleSender().sendMessage("Check 8.2");
		
		
		// events
		PluginManager pm = getServer().getPluginManager();
		Bukkit.getConsoleSender().sendMessage("Check 9");
		
		pm.registerEvents(new PlayerListener(this), this);
		Bukkit.getConsoleSender().sendMessage("Check 10");
		
		// commands
		getCommand("InfoBoard").setExecutor(new Commands(this));

		logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");

	}
    @Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled (V." + pdfFile.getVersion() + ")");

	}

	private void loadFileManager() {
		fm = new FileManager();
		fm.setup();
	}

}
