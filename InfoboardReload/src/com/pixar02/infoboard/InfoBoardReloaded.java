package com.pixar02.infoboard;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.pixar02.infoboard.APIS.Vault;
import com.pixar02.infoboard.Utils.FileManager;
import com.pixar02.infoboard.Utils.Metrics;
import com.pixar02.infoboard.Utils.UpdateChecker;
import com.pixar02.infoboard.events.PlayerJoin;
import com.pixar02.infoboard.events.ChangeWorld;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class InfoBoardReloaded extends JavaPlugin {

	public Timers timers;
	public FileManager fm;
	public UpdateChecker uc = new UpdateChecker(this);;
	public boolean update = false;
	public boolean debug = false;

	public ArrayList<String> hidefrom = new ArrayList<String>();

	public static Economy economy;
	public static Permission permission;
	public static boolean economyB;
	public static boolean permissionB;

	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();

	public void onEnable() {

		dependencies();
		loadFileManager();
		loadMetrics();

		timers = new Timers(this);
		timers.start();

		// events
		registerEvents();

		// commands
		getCommand("InfoBoardReloaded").setExecutor(new Commands(this));

		Vault.load();
		logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");

	}

	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
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

	@SuppressWarnings("unused")
	public void loadMetrics() {
		Metrics metrics = new Metrics(this);
	}

	public void dependencies() {
		if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			throw new RuntimeException("Could not find PlaceholderAPI!! Plugin can not work without it!");
		}
		if (!Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			throw new RuntimeException("Could not find WorldGuard!! Plugin can not work without it!");
		}
		if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			throw new RuntimeException("Could not find Vault!! Plugin can not work without it!");
		}
	}

}
