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

	InfoBoard plugin;

	public Commands(InfoBoard plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("InfoBoard"))
			if (args.length > 0) {
				sender.sendMessage("");
				// ====================================================================================
				// HIDE =====================================
				if (args[0].equalsIgnoreCase("Hide")) {
					if (!sender.hasPermission("InfoBoard.Toggle"))
						sender.sendMessage("Invalid Permissions.");
					else if (!(sender instanceof Player))
						sender.sendMessage("Expected a player");
					else if (InfoBoard.hidefrom.contains(sender.getName()))
						sender.sendMessage("Already hidden");

					else {
						InfoBoard.hidefrom.add(sender.getName());
						sender.sendMessage("Hiding Info Board.");
						((Player) sender).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
					}
				}
				// ====================================================================================
				// SHOW =====================================
				else if (args[0].equalsIgnoreCase("Show")) {
					if (!sender.hasPermission("InfoBoard.Toggle"))
						sender.sendMessage("Invalid Permissions.");
					else if (!(sender instanceof Player))
						sender.sendMessage("Expected a player");
					else if (!InfoBoard.hidefrom.contains(sender.getName()))
						sender.sendMessage("Not hidden");

					else {
						InfoBoard.hidefrom.remove(sender.getName());
						sender.sendMessage("Showing Info Board.");
					}
				}
				// ====================================================================================
				// SET =====================================
				else if (args[0].equalsIgnoreCase("Set")) {
					if (!sender.hasPermission("InfoBoard.Set"))
						sender.sendMessage("Invalid Permissions.");

					else if (args.length == 2) {
						String rotate = args[1];

						if (plugin.fm.getBoard().getInt("Info Board." + rotate + ".Show Time") != 0) {

							plugin.timers.setPage(Integer.valueOf(args[1]));
							sender.sendMessage("");
							sender.sendMessage("Rotation set to: " + args[1]);
							for (Player p : Bukkit.getOnlinePlayers())
								if (p.hasPermission("InfoBoard.View"))
									Create.createScoreBoard(p);
						} else
							sender.sendMessage("Page not found: " + args[1]);

					}
				}
				// ====================================================================================
				// RELOAD =====================================
				else if (args[0].equalsIgnoreCase("Reload"))
					if (!sender.hasPermission("InfoBoard.Reload"))
						sender.sendMessage("Invalid Permissions.");

					else {
						sender.sendMessage("" + ChatColor.GREEN + "Configs been reloaded");
						Bukkit.getScheduler().cancelTasks(plugin);
						for (Player player : Bukkit.getOnlinePlayers())
							ScrollManager.reset(player);

						plugin.fm.reloadBoard();
						plugin.fm.reloadConfig();

						plugin.timers.reset();
						for (Player player : Bukkit.getOnlinePlayers())
							if (player.hasPermission("InfoBoard.View"))
								Create.createScoreBoard(player);

					}

			}
			// ====================================================================================
			// HELP =====================================
			else {
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "========[" + ChatColor.DARK_AQUA
						+ ChatColor.BOLD + " InfoBoardReloaded " + ChatColor.ITALIC + " v"
						+ plugin.getDescription().getVersion() + ChatColor.GOLD + " " + ChatColor.STRIKETHROUGH
						+ "]========");
				sender.sendMessage("/IB Hide      " + ChatColor.YELLOW + "- Hide the board");
				sender.sendMessage("/IB Show     " + ChatColor.YELLOW + "- Show the board");
				sender.sendMessage("/IB Reload   " + ChatColor.YELLOW + "- Reload the config");
				sender.sendMessage("/IB Set <Pg> " + ChatColor.YELLOW + "- Set the page to view");
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