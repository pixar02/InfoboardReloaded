package com.pixar02.infoboard;

import java.io.BufferedReader;
import java.io.IOException;
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
import com.pixar02.infoboard.events.PlayerJoin;
import com.pixar02.infoboard.events.ChangeWorld;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class InfoBoardReloaded extends JavaPlugin {

	public Timers timers;
	public FileManager fm;
	public boolean update = false;
	public static ArrayList<String> hidefrom = new ArrayList<String>();

	public static Economy economy;
	public static Permission permission;
	public static boolean economyB;
	public static boolean permissionB;

	public HashMap<String, List<String>> ChangeableText = new HashMap<>();
	public HashMap<String, Integer> ChangeableInt = new HashMap<>();
	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();
	public void onEnable() {



		if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			throw new RuntimeException("Could not find PlaceholderAPI!! Plugin can not work without it!");
		}
		if (!Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			throw new RuntimeException("Could not find WorldGuard!! Plugin can not work without it!");
		}
		if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			throw new RuntimeException("Could not find Vault!! Plugin can not work without it!");
		}
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}

		loadFileManager();
		loadMetrics();
		check();

		timers = new Timers(this);
		timers.start();

		loadChangeables();

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

	public void loadMetrics() {
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}
	}

	public void loadChangeables() {
		if (fm.getConfig().getConfigurationSection("Changeable Text.Changeables") != null) {
			for (String s : fm.getBoard().getConfigurationSection("Changeable Text.Changeables").getKeys(true)) {
				if (fm.getBoard().getConfigurationSection("Changeable Text.Changeables." + s + ".text") == null) {
					break;
				} else {
					ChangeableText.put(s, fm.getBoard().getStringList("Changeable Text.Changeables" + s + ".text"));
					ChangeableInt.put(s, fm.getBoard().getInt("Changeable Text.Changeables" + s + ".interval"));
				}
			}
		}
	}

	public void check() {

		if (fm.getConfig().getBoolean("Updater") == true) {
			try {
				HttpURLConnection c = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php")
						.openConnection();
				c.setDoOutput(true);
				c.setRequestMethod("POST");
				c.getOutputStream()
						.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=44270")
								.getBytes("UTF-8"));
				String current = getDescription().getVersion();
				String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine()
						.replaceAll("[a-zA-Z ]", "");

				int cur = Integer.valueOf(current.replaceAll(".", ""));
				int newv = Integer.valueOf(newVersion.replaceAll(".", ""));

				if ((!(cur >= newv)) && cur < newv) {
					// there is a new version
					setEnabled(update);
					logger.warning("There is an update for InFoboardReloaded!");
					logger.warning("Pleas Download it at bit.ly/InfoBoardReloaded");
				}
			} catch (Exception e) {
				// update failed, most likely to spigot being down or the server not having
				// internet connection

			}
		}
	}
}
