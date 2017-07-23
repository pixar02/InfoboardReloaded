package com.pixar02.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.pixar02.infoboard.Utils.Messages;
import com.pixar02.infoboard.variables.GriefPreventionVariables;

import me.clip.placeholderapi.PlaceholderAPI;

public class GetVariables {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	public static String replaceVariables(String string, Player player) {
		String newString = string;

		// setting all placeholders that are hooked in placeholder API
		newString = PlaceholderAPI.setPlaceholders(player, string);
		
		
		// checking if GriefPrevention is installed and there are variables used
		if (Bukkit.getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
			Bukkit.broadcastMessage("CHECK");
			if (newString.contains("%griefprevention_")) {
				Bukkit.broadcastMessage("CHECK");
				newString = GriefPreventionVariables.replaceVariables(string, player);
			}
		}

		// Bukkit.broadcastMessage(newString);

		// Custom Variables
		for (String custom : plugin.fm.getConfig().getConfigurationSection("Custom Variables").getKeys(true)) {
			if (newString.contains(custom)) {
				newString = newString.replaceAll(custom,
						Messages.getColored(plugin.fm.getConfig().getString("Custom Variables." + custom)));
			}
		}

		return newString;
	}
}