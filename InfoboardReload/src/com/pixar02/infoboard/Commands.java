package com.pixar02.infoboard;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import com.pixar02.infoboard.Changeable.ChangeableManager;
import com.pixar02.infoboard.Scoreboard.Create;
import com.pixar02.infoboard.Scroll.ScrollManager;
import com.pixar02.infoboard.Utils.Settings;

public class Commands implements CommandExecutor {

	InfoBoardReloaded plugin;

	public Commands(InfoBoardReloaded plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("InfoBoardReloaded")) {
			if (args.length > 0) {
				// HIDE
				if (args[0].equalsIgnoreCase("Hide")) {
					hideCmd(sender);
				}
				// SHOW
				else if (args[0].equalsIgnoreCase("Show")) {
					showCmd(sender);
				}
				// SET <PAGE>
				else if (args[0].equalsIgnoreCase("Set")) {
					setCmd(sender, args);
				}
				// RELOAD [FILE]
				else if (args[0].equalsIgnoreCase("Reload")) {
					if (args.length > 1) {
						if (args[1].equalsIgnoreCase("board")) {
							reloadCmd(sender, "board");
						} else if (args[1].equalsIgnoreCase("config")) {
							reloadCmd(sender, "config");
						} else if (args[1].equalsIgnoreCase("messages")) {
							reloadCmd(sender, "messages");
						} else if (args[1].equalsIgnoreCase("All")) {
							reloadCmd(sender, "all");
						} else {
							reloadCmd(sender, "error");
						}
					} else {
						reloadCmd(sender, "all");
					}
				}
				// CREATE <PAGE> <SHOWTIME>
				else if (args[0].equalsIgnoreCase("Create")) {
					createCmd(sender, args);

				}
				// ADD <PAGE> <WORLD> <RANK> <LINE/TITLE> <WORDS>
				else if (args[0].equalsIgnoreCase("Add")) {
					addCmd(sender, args);
				}
			}

			/*
			 * =============================================================================
			 * HELP
			 * =============================================================================
			 */
			else {
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "========[" + ChatColor.DARK_AQUA
						+ ChatColor.BOLD + " InfoBoardReloaded " + ChatColor.ITALIC + " v"
						+ plugin.getDescription().getVersion() + ChatColor.GOLD + " " + ChatColor.STRIKETHROUGH
						+ "]========");
				sender.sendMessage("/IBR Hide");
				sender.sendMessage(ChatColor.YELLOW + "- Hide the board");
				sender.sendMessage("/IBR Show");
				sender.sendMessage(ChatColor.YELLOW + "- Show the board");
				sender.sendMessage("/IBR Reload [FILE]");
				sender.sendMessage(ChatColor.YELLOW + "- Reloads the given file");
				sender.sendMessage("/IBR Set <Pg>");
				sender.sendMessage(ChatColor.YELLOW + "- Set the page to view");
				sender.sendMessage("/IBR Create <Pg> <ShowTime>");
				sender.sendMessage(ChatColor.YELLOW + "- Creates a page with showtime");
				sender.sendMessage("/IBR Add <Line/Title> <Pg> <World> <Rank> <Line>");
				sender.sendMessage(ChatColor.YELLOW + "- Adds a line to given page, rank and world");

				sender.sendMessage(
						"" + ChatColor.GOLD + ChatColor.STRIKETHROUGH + "--------------------------------------------");
				sender.sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "Authors: " + ChatColor.WHITE
						+ ChatColor.BOLD + "pixar02 and Ktar5");
				sender.sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "Spigot: " + ChatColor.WHITE
						+ ChatColor.BOLD + "http://bit.ly/InfoBoardReloaded");

				sender.sendMessage(
						"" + ChatColor.GOLD + ChatColor.STRIKETHROUGH + "--------------------------------------------");
				if (plugin.update) {
					sender.sendMessage(ChatColor.DARK_GREEN + plugin.getFm().getFile("messages").getString("update"));
					sender.sendMessage("" + ChatColor.GOLD + ChatColor.STRIKETHROUGH
							+ "--------------------------------------------");
				}

			}

		}
		return true;
	}

	/*
	 * =============================================================================
	 * ADD <LINE/TITLE> <PAGE> <WORLD> <RANK> <LINE>
	 * =============================================================================
	 *
	 */
	/**
	 * @param sender
	 * @param args
	 */
	public void addCmd(CommandSender sender, String[] args) {
		if (!(sender.hasPermission("ibr.Create"))) {
			sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("no-permission"));

		} else {
			if (args.length >= 6) {
				String what = args[1];
				String rotation = args[2];
				String world = args[3];
				String rank = args[4];
				String line = "";
				for (int i = 5; i < args.length; i++) {
					String temp = args[i] + " ";
					line = line + temp;
				}
				if (args[2] == "") {
					world = "global";
				}
				if (args[3] == "") {
					rank = "default";
				}
				if (what.equalsIgnoreCase("Title")) {
					// check if the Title isn't already set
					if (plugin.getFm().getFile("board")
							.getString("InfoBoard." + rotation + "." + world + "." + rank + ".Title") == null) {
						// Title doesn't exist
						plugin.getFm().getFile("board")
								.set("InfoBoard." + rotation + "." + world + "." + rank + ".Title", line);
						sender.sendMessage(plugin.getFm().getFile("messages").getString("add-success") + rotation);
						sender.sendMessage("Title: " + line);
						plugin.getFm().saveFile("board");
					} else {
						// Title exists
						sender.sendMessage(
								ChatColor.RED + plugin.getFm().getFile("messages").getString("title-exists"));
					}
				} else if (what.equalsIgnoreCase("Line")) {
					// check if the maximum of lines are exceeded
					if (plugin.getFm().getFile("board")
							.getStringList("InfoBoard." + rotation + "." + world + "." + rank + ".Rows").size() <= 14) {
						List<String> lines = plugin.getFm().getFile("board")
								.getStringList("InfoBoard." + rotation + "." + world + "." + rank + ".Rows");
						lines.add(line);
						plugin.getFm().getFile("board")
								.set("InfoBoard." + rotation + "." + world + "." + rank + ".Rows", lines);
						plugin.getFm().getFile("board");
						sender.sendMessage(plugin.getFm().getFile("messages").getString("add-success") + rotation);
						sender.sendMessage("Line: " + line);
						plugin.getFm().saveFile("board");

					} else {
						sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("max-lines"));
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + plugin.getFm().getFile("messages").getString("wrong-usage"));
					sender.sendMessage("/ibr add <line/title> <page> [world] [rank] <line>");
				}
			} else {
				sender.sendMessage(ChatColor.YELLOW + plugin.getFm().getFile("messages").getString("wrong-usage"));
				sender.sendMessage("/ibr add <line/title> <page> <world> <rank> <line>");
			}
		}
	}

	/*
	 * =============================================================================
	 * CREATE <PAGE> <SHOWTIME>
	 * =============================================================================
	 */
	/**
	 * @param sender
	 * @param args
	 */
	public void createCmd(CommandSender sender, String[] args) {
		if (!(sender.hasPermission("ibr.Create"))) {
			sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("no-permission"));

		} else {
			if (args.length == 3) {
				String rotation = args[1];
				int ShowTime = Integer.valueOf(args[2]);
				// check if the new created page doesn't already exist
				if (plugin.getFm().getFile("board").getString("InfoBoard." + rotation) != null) {
					sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("page-exists"));
				} else {
					// create the new page with the given showtime
					plugin.getFm().getFile("board").set("InfoBoard." + rotation + ".ShowTime", ShowTime);
					plugin.getFm().saveFile("board");
					sender.sendMessage(plugin.getFm().getFile("messages").getString("create-success"));
				}
			} else {
				sender.sendMessage(ChatColor.YELLOW + plugin.getFm().getFile("messages").getString("wrong-usage"));
				sender.sendMessage("/ibr create <Page> <ShowTime>");
			}
		}
	}

	/*
	 * =============================================================================
	 * Set <PAGE>
	 * =============================================================================
	 */
	/**
	 * @param sender
	 * @param args
	 */
	public void setCmd(CommandSender sender, String[] args) {
		if (!sender.hasPermission("ibr.Set")) {
			sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("no-permission"));
		} else if (args.length == 2) {
			String rotate = args[1];

			if (plugin.getFm().getFile("board").getInt("InfoBoard." + rotate + ".ShowTime") != 0) {

				plugin.timers.setPage(Integer.valueOf(rotate));
				sender.sendMessage(plugin.getFm().getFile("messages").getString("set-page") + args[1]);
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("ibr.View")) {
						Create.createScoreBoard(p);
					}
				}
			} else {
				sender.sendMessage(plugin.getFm().getFile("messages").getString("invalid-page") + args[1]);
			}
		} else {
			sender.sendMessage(ChatColor.YELLOW + plugin.getFm().getFile("messages").getString("wrong-usage"));
			sender.sendMessage("/ibr set <page>");
		}

	}

	/*
	 * =============================================================================
	 * SHOW
	 * =============================================================================
	 */
	/**
	 * @param sender
	 */
	public void showCmd(CommandSender sender) {
		if (!sender.hasPermission("ibr.Toggle")) {
			sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("no-permission"));
		} else if (!(sender instanceof Player)) {
			sender.sendMessage(plugin.getFm().getFile("messages").getString("not-player"));
		} else if (!plugin.hidefrom.contains(sender.getName())) {
			sender.sendMessage(plugin.getFm().getFile("messages").getString("already-shown"));

		} else {
			plugin.hidefrom.remove(sender.getName());
			sender.sendMessage(plugin.getFm().getFile("messages").getString("showing"));
		}
	}

	/*
	 * =============================================================================
	 * HIDE
	 * =============================================================================
	 */
	/**
	 * @param sender
	 */
	public void hideCmd(CommandSender sender) {
		if (!sender.hasPermission("ibr.Toggle")) {
			sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("no-permission"));
		} else if (!(sender instanceof Player)) {
			sender.sendMessage(plugin.getFm().getFile("messages").getString("not-player"));
		} else if (plugin.hidefrom.contains(sender.getName())) {
			sender.sendMessage(plugin.getFm().getFile("messages").getString("already-hidden"));

		} else {
			plugin.hidefrom.add(sender.getName());
			sender.sendMessage(plugin.getFm().getFile("messages").getString("hiding"));
			((Player) sender).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
	}

	/*
	 * =============================================================================
	 * RELOAD [file]
	 * =============================================================================
	 */
	/**
	 * @param sender
	 * @param file
	 */
	public void reloadCmd(CommandSender sender, String file) {
		if (!sender.hasPermission("ibr.Reload")) {
			sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("no-permission"));
		} else {
			/*
			 * =============================================================================
			 * RELOAD board
			 * =============================================================================
			 */
			if (file == "board") {

				Bukkit.getScheduler().cancelTasks(plugin);

				for (Player player : Bukkit.getOnlinePlayers()) {
					ScrollManager.reset(player);
					plugin.getCM().reset(player);
				}
				plugin.getFm().reloadFile("board");
				plugin.timers.reset();

				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.hasPermission("InfoBoard.View")) {
						Create.createScoreBoard(player);
					}
				}
				sender.sendMessage(ChatColor.GREEN + plugin.getFm().getFile("messages").getString("board-reload"));

				/*
				 * =============================================================================
				 * RELOAD config
				 * =============================================================================
				 */
			} else if (file == "config") {

				Bukkit.getScheduler().cancelTasks(plugin);
				for (Player player : Bukkit.getOnlinePlayers()) {
					ScrollManager.reset(player);
					plugin.getCM().reset(player);
				}

				plugin.getFm().reloadFile("config");
				plugin.getSettings().changeable.clear();
				plugin.getSettings().loadChangeable();
				plugin.timers.reset();

				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.hasPermission("InfoBoard.View")) {
						Create.createScoreBoard(player);
					}
				}
				sender.sendMessage(ChatColor.GREEN + plugin.getFm().getFile("messages").getString("config-reload"));
			}
			/*
			 * =============================================================================
			 * RELOAD messages
			 * =============================================================================
			 */
			else if (file == "messages") {
				Bukkit.getScheduler().cancelTasks(plugin);

				plugin.getFm().reloadFile("messages");
				plugin.timers.reset();

				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.hasPermission("InfoBoard.View")) {
						Create.createScoreBoard(player);
					}
				}
				sender.sendMessage(ChatColor.GREEN + plugin.getFm().getFile("messages").getString("messages-reload"));

			}

			/*
			 * =============================================================================
			 * RELOAD
			 * =============================================================================
			 */
			else if (file == "all") {

				Bukkit.getScheduler().cancelTasks(plugin);
				for (Player player : Bukkit.getOnlinePlayers()) {
					ScrollManager.reset(player);
					plugin.getCM().reset(player);
				}
				plugin.getFm().reloadFile("board");
				plugin.getFm().reloadFile("config");
				plugin.getFm().reloadFile("messages");

				plugin.getSettings().changeable.clear();
				plugin.getSettings().loadChangeable();
				plugin.timers.reset();

				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.hasPermission("ibr.View")) {
						Create.createScoreBoard(player);
					}
				}
				sender.sendMessage(ChatColor.GREEN + plugin.getFm().getFile("messages").getString("all-reload"));
			} else if (file == "error") {
				sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("wrong-usage"));
				sender.sendMessage("/ibr reload [File]");
			} else {
				sender.sendMessage(ChatColor.RED + plugin.getFm().getFile("messages").getString("wrong-usage"));
				sender.sendMessage("/ibr reload [File]");
			}
		}
	}
}