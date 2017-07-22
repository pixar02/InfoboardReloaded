package com.pixar02.infoboard.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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

	// TODO Setting default values of Board

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
		reloadBoard();
		reloadVariable();
	}

	public FileConfiguration getBoard() {
		if (board == null) {
			reloadBoard();
		}
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

		// look for defaults in the jar
		InputStream defConfigStream = plugin.getResource("board.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
			board.setDefaults(defConfig);
		}
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The board.yml file has been reload");
	}

	public void reloadVariable() {
		variable = YamlConfiguration.loadConfiguration(variableFile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The variables.yml file has been reload");
	}

	public void reloadConfig() {
		plugin.reloadConfig();
	}

	private void copy(InputStream in, File file) {
		try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
