package com.pixar02.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.pixar02.infoboard.Utils.Messages;

import me.clip.placeholderapi.PlaceholderAPI;

public class GetVariables {
	private static InfoBoard plugin = InfoBoard.getPlugin(InfoBoard.class);

	public static String replaceVariables(String string, Player player) {
		String newString = PlaceholderAPI.setPlaceholders(player, string);
		Bukkit.broadcastMessage(newString);

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