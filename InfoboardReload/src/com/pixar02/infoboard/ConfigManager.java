package com.pixar02.infoboard;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	private InfoBoard plugin = InfoBoard.getPlugin(InfoBoard.class);

	// Files & file configs
	public FileConfiguration playerscfg;
	public File playersfile;
	// -------------------------

	public void setup() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		playersfile = new File(plugin.getDataFolder(), "players.yml");
		if (!playersfile.exists()) {
			try {
				playersfile.createNewFile();
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.AQUA + "players.yml has been created");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Could not create the players.yml file");
			}
		}
		playerscfg = YamlConfiguration.loadConfiguration(playersfile);
	}

	public FileConfiguration getPlayers() {
		return playerscfg;
	}

	public void SavePlayers() {
		try {
			playerscfg.save(playersfile);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "players.yml has been saved");
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save te players.yml file");
		}
	}

	public void reloadPlayers() {
		playerscfg = YamlConfiguration.loadConfiguration(playersfile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "players.yml has been reloaded  ");
	}
}
