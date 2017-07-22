package com.pixar02.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.pixar02.infoboard.Utils.Messages;

import me.clip.placeholderapi.PlaceholderAPI;

public class GetVariables {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	public static String replaceVariables(String string, Player player) {
		//setting all placeholders that are hooked in placehodler API
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