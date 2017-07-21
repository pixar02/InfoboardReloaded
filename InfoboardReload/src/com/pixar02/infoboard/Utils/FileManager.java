package com.pixar02.infoboard.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.pixar02.infoboard.InfoBoard;

public class FileManager {
	private InfoBoard plugin = InfoBoard.getPlugin(InfoBoard.class);

	// Files & configs
	private FileConfiguration board;
	private FileConfiguration variable;
	private File boardFile;
	private File variableFile;

	public void setup() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		boardFile = new File(plugin.getDataFolder(), "board.yml");
		variableFile = new File(plugin.getDataFolder(), "varaibles.yml");
		/*
		 * Checking if board.yml exists creating it if not
		 */
		if (!boardFile.exists()) {
			try {
				boardFile.createNewFile();
				// look for default file in .jar
				getBoard().options().copyDefaults(true);
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The board.yml file has been created");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Could not create the board.yml file" + e);
			}
		}
		board = YamlConfiguration.loadConfiguration(boardFile);
		/*
		 * Checking if variables.yml exists creating it if not
		 */
		if (!variableFile.exists()) {
			try {
				variableFile.createNewFile();
				// look for default file in .jar
				getVariable().options().copyDefaults(true);
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + "The variables.yml file has been created");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Could not create the variables.yml file" + e);
			}
		}
		variable = YamlConfiguration.loadConfiguration(variableFile);

		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}

	public FileConfiguration getBoard() {
		return board;
	}

	public FileConfiguration getVariable() {
		return variable;
	}

	public FileConfiguration getConfig() {
		return plugin.getConfig();
	}

	public void saveBoard() {
		try {
			board.save(boardFile);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The board.yml file has been saved");
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the board.yml file" + e);
		}
	}

	public void saveVariable() {
		try {
			variable.save(variableFile);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The variables.yml file has been saved");
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "Could not save the variables.yml file" + e);
		}
	}

	public void saveConfig() {
		plugin.saveConfig();
	}

	public void reloadBoard() {
		board = YamlConfiguration.loadConfiguration(boardFile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The board.yml file has been reload");
	}

	public void reloadVariable() {
		variable = YamlConfiguration.loadConfiguration(variableFile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The variables.yml file has been reload");
	}

	public void reloadConfig() {
		plugin.reloadConfig();
	}
}
