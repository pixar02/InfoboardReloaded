package com.pixar02.infoboard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.pixar02.infoboard.APIS.Vault;
import com.pixar02.infoboard.Utils.FileManager;
import com.pixar02.infoboard.Utils.Metrics;
import com.pixar02.infoboard.Utils.Settings;
import com.pixar02.infoboard.Utils.UpdateChecker;
import com.pixar02.infoboard.events.PlayerJoin;
import com.pixar02.infoboard.events.ChangeWorld;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class InfoBoardReloaded extends JavaPlugin {

	public Timers timers;
	public FileManager fm;
	public boolean update = false;
	public boolean debug = false;

	public static ArrayList<String> hidefrom = new ArrayList<String>();

	public static Economy economy;
	public static Permission permission;
	public static boolean economyB;
	public static boolean permissionB;

	// public HashMap<String, List<String>> ChangeableText = new HashMap<>();
	// public HashMap<String, Integer> ChangeableInt = new HashMap<>();
	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();

	public void onEnable() {

		dependencies();
		loadFileManager();
		loadMetrics();
		UpdateCheck();

		timers = new Timers(this);
		timers.start();

		// loadChangeables();

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

	/*
	 * public void loadChangeables() { if
	 * (fm.getConfig().getConfigurationSection("Changeable Text.Changeables") !=
	 * null) { for (String s :
	 * fm.getBoard().getConfigurationSection("Changeable Text.Changeables").getKeys(
	 * true)) { if
	 * (fm.getBoard().getConfigurationSection("Changeable Text.Changeables." + s +
	 * ".text") == null) { break; } else { ChangeableText.put(s,
	 * fm.getBoard().getStringList("Changeable Text.Changeables" + s + ".text"));
	 * ChangeableInt.put(s, fm.getBoard().getInt("Changeable Text.Changeables" + s +
	 * ".interval")); } } } }
	 */

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

	public void UpdateCheck() {
		UpdateChecker updateChecker = new UpdateChecker(this);
		if (Settings.updater()) {
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				public void run() {
					logger.info("Checking for updates...");
					try {
						updateChecker.checkUpdate(pdfFile.getVersion());
					} catch (Exception e) {
						logger.warning("Failed to check for updates, because: " + e);

					}
				}
			}, 0, 3600 * 20);
		}
	}
}
