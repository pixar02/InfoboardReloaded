package com.pixar02.infoboard;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor{
	
private InfoBoard plugin;

	public Commands(InfoBoard pl){
		plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("Infoboard")){
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("hide")){
					if(!sender.hasPermission("ibr.hide")){
						sender.sendMessage("Invalid Permissions.");;
					}
				}
			}else{
				sender.sendMessage(ChatColor.STRIKETHROUGH + "============["+ ChatColor.DARK_AQUA + ChatColor.BOLD + " InfoBoardReloaded"
                        + ChatColor.ITALIC + " V" + plugin.getDescription().getVersion()
                        + ChatColor.GOLD + " " + ChatColor.STRIKETHROUGH + "]============");
                sender.sendMessage("/IB Hide      "+ ChatColor.YELLOW + "- Hide the board");
                sender.sendMessage("/IB Show      "+ ChatColor.YELLOW + "- Show the board");
                sender.sendMessage("/IB Reload    "+ ChatColor.YELLOW + "- Reload the config");
                sender.sendMessage("/IB Set <Pg>  " + ChatColor.YELLOW + "- Set the page to view");
                sender.sendMessage(ChatColor.GOLD +"--------------------------------------------");
                sender.sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "Author: " + ChatColor.WHITE + ChatColor.BOLD + plugin.getDescription().getAuthors());
                sender.sendMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "BukkitDev: " + ChatColor.WHITE + ChatColor.BOLD + "http://bit.ly/Info-Board");
                sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
			}
		}
		
		return false;
	}

}
