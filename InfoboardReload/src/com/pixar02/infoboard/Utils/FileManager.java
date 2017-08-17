package com.pixar02.infoboard.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.pixar02.infoboard.InfoBoardReloaded;

public class FileManager {
	private InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	// Files & configs
	private FileConfiguration board;
	private FileConfiguration variable;
	private FileConfiguration messages;

	private File boardFile;
	private File variableFile;
	private File messagesFile;

	public void setup() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		boardFile = new File(plugin.getDataFolder(), "board.yml");
		variableFile = new File(plugin.getDataFolder(), "varaibles.yml");
		messagesFile = new File(plugin.getDataFolder(), "messages.yml");

		/*
		 * Checking if board.yml exists creating it if not
		 */
		if (!boardFile.exists()) {
			try {
				boardFile.createNewFile();
				copy(plugin.getResource("board.yml"), boardFile);
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + "The board.yml file has been created");
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
				copy(plugin.getResource("variables.yml"), variableFile);
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + "The variables.yml file has been created");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Could not create the variables.yml file" + e);
			}
		}
		variable = YamlConfiguration.loadConfiguration(variableFile);

		if (!messagesFile.exists()) {
			try {
				messagesFile.createNewFile();
				copy(plugin.getResource("messages.yml"), messagesFile);
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GREEN + "The messages.yml file has been created");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "Could not create the messages.yml file" + e);
			}
		}
		messages = YamlConfiguration.loadConfiguration(messagesFile);

		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}

	/*
	 * saveBoard -> saveFile(board,boardFile) _____________________________
	 * saveMessages -> saveFile(messages,messagesFile) ____________________
	 * saveVariable -> saveFile(variable, variableFile)____________________
	 * saveConfig -> saveFile(config)______________________________________
	 */
	public void saveFile(String s) {

		try {
			if (s == "board") {
				board.save(boardFile);
			} else if (s == "variable") {
				variable.save(variableFile);
			} else if (s == "messages") {
				messages.save(messagesFile);
			} else if (s == "config") {
				plugin.saveConfig();
			}

			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The" + s + ".yml file has been saved");
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "Could not save the " + s + ".yml file" + e);

		}
	}

	/*
	 * reloadBoard -> reloadFile(board) ___________________________________
	 * reloadMessages -> reloadFile(messages) _____________________________
	 * reloadVariable -> reloadFile(variable)______________________________
	 * reloadConfig -> reloadFile(config)__________________________________
	 */
	public void reloadFile(String s) {
		if (s == "board") {
			board = YamlConfiguration.loadConfiguration(boardFile);
		} else if (s == "config") {
			plugin.reloadConfig();
		} else if (s == "messages") {
			messages = YamlConfiguration.loadConfiguration(messagesFile);
		} else if (s == "variable") {
			variable = YamlConfiguration.loadConfiguration(variableFile);
		}
	}

	/*
	 * getBoard -> getFile(board) _________________________________________
	 * getMessages -> getFile(messages) ___________________________________
	 * getVariable -> getFile(variable)____________________________________
	 * getConfig -> getFile(config)________________________________________
	 */
	public FileConfiguration getFile(String s) {
		if (s == "board") {
			return board;
		} else if (s == "config") {
			return plugin.getConfig();
		} else if (s == "messages") {
			return messages;
		} else if (s == "variable") {
			return variable;
		} else {
			return null;
		}

	}

	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
