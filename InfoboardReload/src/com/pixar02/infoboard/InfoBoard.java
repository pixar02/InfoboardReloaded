package com.pixar02.infoboard;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.pixar02.infoboard.Utils.FileManager;

public class InfoBoard extends JavaPlugin {
	public FileManager fm;
	public boolean update = false;

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

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		getCommand("InfoBoard").setExecutor(new Commands(this));
		
		logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");

	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled (V." + pdfFile.getVersion() + ")");

	}

	private void loadFileManager() {
		fm = new FileManager();
		fm.setup();
	}

}
