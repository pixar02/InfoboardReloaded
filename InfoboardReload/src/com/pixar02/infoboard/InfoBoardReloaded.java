package com.pixar02.infoboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import com.pixar02.infoboard.APIS.Vault;
import com.pixar02.infoboard.Changeable.ChangeableManager;
import com.pixar02.infoboard.Events.ChangeWorld;
import com.pixar02.infoboard.Events.PlayerJoin;
import com.pixar02.infoboard.Utils.FileManager;
import com.pixar02.infoboard.Utils.Messages;
import com.pixar02.infoboard.Utils.Metrics;
import com.pixar02.infoboard.Utils.Settings;
import com.pixar02.infoboard.Utils.UpdateChecker;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class InfoBoardReloaded extends JavaPlugin {

	public Timers timers;
	private FileManager fm;
	private Settings settings;
	private Messages msgs;
	private ChangeableManager CM;

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
		this.Instance();

		loadMetrics();

		// events
		registerEvents();

		// commands
		getCommand("InfoBoardReloaded").setExecutor(new Commands(this));

		Vault.load();
		if (settings.changeableTextEnabled()) {
			logger.info("Feature: Changeable Text is enbaled!");
			logger.info(settings.getChangeable().size() + " Changeable(s) loaded");
		}
		if (settings.scrollingEnabled()) {
			logger.info("Feature: Scrolling is enabled!");
		}
		logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");

	}

	public void onDisable() {
		timers.stop();
		Bukkit.getScheduler().cancelTasks(this);
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
				if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard")) {
					player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				}
			}
		}
		logger.info(pdfFile.getName() + " has been disabled (V." + pdfFile.getVersion() + ")");

	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new ChangeWorld(), this);
		pm.registerEvents(new PlayerJoin(this), this);
	}

	public void loadMetrics() {
		Metrics metrics = new Metrics(this);
		metrics.addCustomChart(new Metrics.SimpleBarChart("features", new Callable<Map<String, Integer>>() {
			@Override
			public Map<String, Integer> call() throws Exception {
				Map<String, Integer> map = new HashMap<String, Integer>();
				if (settings.changeableTextEnabled()) {
					map.put("Changeables", 1);
				}
				if (settings.scrollingEnabled()) {
					map.put("Scroll", 1);
				}
				return map;
			}
		}));
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

	private void Instance() {

		fm = new FileManager(this);
		settings = new Settings(this);
		msgs = new Messages(this);
		timers = new Timers(this);

		fm.setup();
		settings.loadChangeable();
		timers.start();
	}

	public FileManager getFm() {
		return this.fm;
	}

	public Settings getSettings() {
		return this.settings;
	}

	public Messages getMessages() {
		return this.msgs;
	}

	public ChangeableManager getCM() {
		return this.CM;
	}
}
