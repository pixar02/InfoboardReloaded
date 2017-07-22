package com.pixar02.infoboard;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.pixar02.infoboard.APIS.Vault;
import com.pixar02.infoboard.Utils.FileManager;
import com.pixar02.infoboard.events.PlayerJoin;
import com.pixar02.infoboard.events.ChangeWorld;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class InfoBoard extends JavaPlugin {

	public Timers timers;
	public FileManager fm;
	public boolean update = false;
	public static ArrayList<String> hidefrom = new ArrayList<String>();

	public static Economy economy;
	public static Permission permission;
	public static boolean economyB;
	public static boolean permissionB;

	@Override
	public void onEnable() {

		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			throw new RuntimeException("Could not find PlaceholderAPI!! Plugin can not work without it!");
		}
		if (!Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			throw new RuntimeException("Could not find WorldGuard!! Plugin can not work without it!");
		}
		if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			throw new RuntimeException("Could not find Vault!! Plugin can not work without it!");
		}

		loadFileManager();

		timers = new Timers(this);
		timers.start();

		// events
		 registerEvents();

		// commands
		getCommand("InfoBoard").setExecutor(new Commands(this));
		
		Vault.load();
		
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

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new ChangeWorld(), this);
		pm.registerEvents(new PlayerJoin(this), this);
	}

	public void registerConfig() {
		reloadConfig();
	}

}
