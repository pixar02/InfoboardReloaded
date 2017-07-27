package com.pixar02.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import com.pixar02.infoboard.Scroll.ScrollManager;
import com.pixar02.infoboard.scoreboard.Create;

public class Commands implements CommandExecutor {

	InfoBoardReloaded plugin;

	public Commands(InfoBoardReloaded plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("InfoBoardReloaded"))
			if (args.length > 0) {
				sender.sendMessage("");
				/*
				 * =============================================================================
				 * HIDE
				 * =============================================================================
				 */
				if (args[0].equalsIgnoreCase("Hide")) {
					if (!sender.hasPermission("ibr.Toggle")) {
						sender.sendMessage("Invalid Permissions.");
					} else if (!(sender instanceof Player)) {
						sender.sendMessage("Expected a player");
					} else if (InfoBoardReloaded.hidefrom.contains(sender.getName())) {
						sender.sendMessage("Already hidden");

					} else {
						InfoBoardReloaded.hidefrom.add(sender.getName());
						sender.sendMessage("Hiding InfoBoard.");
						((Player) sender).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					}
				}
				/*
				 * =============================================================================
				 * SHOW
				 * =============================================================================
				 */
				else if (args[0].equalsIgnoreCase("Show")) {
					if (!sender.hasPermission("ibr.Toggle")) {
						sender.sendMessage("Invalid Permissions.");
					} else if (!(sender instanceof Player)) {
						sender.sendMessage("Expected a player");
					} else if (!InfoBoardReloaded.hidefrom.contains(sender.getName())) {
						sender.sendMessage("Not hidden");

					} else {
						InfoBoardReloaded.hidefrom.remove(sender.getName());
						sender.sendMessage("Showing Info Board.");
					}
				}
				/*
				 * =============================================================================
				 * Set <page>
				 * =============================================================================
				 */
				else if (args[0].equalsIgnoreCase("Set")) {
					if (!sender.hasPermission("ibr.Set"))
						sender.sendMessage("Invalid Permissions.");

					else if (args.length == 2) {
						String rotate = args[1];

						if (plugin.fm.getBoard().getInt("InfoBoard." + rotate + ".Show Time") != 0) {

							plugin.timers.setPage(Integer.valueOf(args[1]));
							sender.sendMessage("");
							sender.sendMessage("Rotation set to: " + args[1]);
							for (Player p : Bukkit.getOnlinePlayers())
								if (p.hasPermission("ibr.View"))
									Create.createScoreBoard(p);
						} else {
							sender.sendMessage("Page not found: " + args[1]);
						}
					}
				}
				/*
				 * =============================================================================
				 * RELOAD [file]
				 * =============================================================================
				 */
				else if (args[0].equalsIgnoreCase("Reload"))
					if (!sender.hasPermission("ibr.Reload"))
						sender.sendMessage("Invalid Permissions.");
				if (args.length == 2) {
					if (args[1].equalsIgnoreCase("Board")) {

						Bukkit.getScheduler().cancelTasks(plugin);

						for (Player player : Bukkit.getOnlinePlayers()) {
							ScrollManager.reset(player);
						}
						plugin.fm.reloadBoard();
						plugin.timers.reset();

						for (Player player : Bukkit.getOnlinePlayers())
							if (player.hasPermission("InfoBoard.View"))
								Create.createScoreBoard(player);
						sender.sendMessage(ChatColor.GREEN + "board.yml has been reloaded");

					} else if (args[1].equalsIgnoreCase("config")) {

						Bukkit.getScheduler().cancelTasks(plugin);
						for (Player player : Bukkit.getOnlinePlayers())
							ScrollManager.reset(player);

						plugin.fm.reloadConfig();
						plugin.timers.reset();

						for (Player player : Bukkit.getOnlinePlayers())
							if (player.hasPermission("InfoBoard.View")) {
								Create.createScoreBoard(player);
							}
						sender.sendMessage(ChatColor.GREEN + "Config.yml has been reloaded");
					}

				}

				else if (args.length == 1 && args.length != 2 && args[0].equalsIgnoreCase("Reload")) {

					sender.sendMessage(ChatColor.GREEN + "All configs have been reloaded");
					Bukkit.getScheduler().cancelTasks(plugin);
					for (Player player : Bukkit.getOnlinePlayers())
						ScrollManager.reset(player);

					plugin.fm.reloadBoard();
					plugin.fm.reloadConfig();

					plugin.timers.reset();
					for (Player player : Bukkit.getOnlinePlayers())
						if (player.hasPermission("ibr.View"))
							Create.createScoreBoard(player);

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
				sender.sendMessage("/IBR Hide         " + ChatColor.YELLOW + "- Hide the board");
				sender.sendMessage("/IBR Show         " + ChatColor.YELLOW + "- Show the board");
				sender.sendMessage("/IBR Reload [FILE]" + ChatColor.YELLOW + "- Reload the config");
				sender.sendMessage("/IBR Set <Pg>     " + ChatColor.YELLOW + "- Set the page to view");
				sender.sendMessage(
						"" + ChatColor.GOLD + ChatColor.STRIKETHROUGH + "--------------------------------------------");
				sender.sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "Authors: " + ChatColor.WHITE
						+ ChatColor.BOLD + "pixar02 and Ktar5");
				sender.sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "Spiggot: " + ChatColor.WHITE
						+ ChatColor.BOLD + "http://bit.ly/InfoBoardReloaded");

				sender.sendMessage(
						"" + ChatColor.GOLD + ChatColor.STRIKETHROUGH + "--------------------------------------------");
				if (plugin.update) {
					sender.sendMessage(ChatColor.DARK_GREEN + "Theres a new update for InfoBoardReloaded");
					sender.sendMessage("" + ChatColor.GOLD + ChatColor.STRIKETHROUGH
							+ "--------------------------------------------");
				}

			}

		return true;
	}
}