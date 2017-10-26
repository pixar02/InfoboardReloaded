package com.pixar02.infoboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.pixar02.infoboard.Changeable.ChangeableManager;
import com.pixar02.infoboard.Scroll.ScrollManager;
import com.pixar02.infoboard.Utils.Metrics;

public class InfoBoardReloaded extends JavaPlugin {

	private ChangeableManager CM;
	private ScrollManager SM;

	public boolean update = false;
	public boolean debug = false;

	public ArrayList<String> hidefrom = new ArrayList<String>();

	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();

	public void onEnable() {
		dependencies();
		this.Instance();

		loadMetrics();

		// events
		registerEvents();

		// commands

		logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");

	}

	public void onDisable() {

		logger.info(pdfFile.getName() + " has been disabled (V." + pdfFile.getVersion() + ")");

	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();

	}

	public void loadMetrics() {
		Metrics metrics = new Metrics(this);
		metrics.addCustomChart(new Metrics.SimpleBarChart("features", new Callable<Map<String, Integer>>() {
			@Override
			public Map<String, Integer> call() throws Exception {
				Map<String, Integer> map = new HashMap<String, Integer>();
				if (getConfig().getBoolean("")) {
					map.put("Changeables", 1);
				}
				if (getConfig().getBoolean("")) {
					map.put("Scroll", 1);
				}
				return map;
			}
		}));
	}

	private void Instance() {

		this.CM = new ChangeableManager(this);
		this.SM = new ScrollManager(this);

	}

	private void dependencies() {
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

	public ChangeableManager getCM() {
		return this.CM;
	}

	public ScrollManager getSM() {
		return this.SM;
	}

}
