package com.pixar02.infoboard;

import org.bukkit.entity.Player;

import com.pixar02.infoboard.Utils.Messages;
import com.pixar02.infoboard.Utils.Settings;

import me.clip.placeholderapi.PlaceholderAPI;

public class GetVariables {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	public static String replaceVariables(String string, Player player) {
		String newString = string;

		// setting all placeholders that are hooked in placeholder API
		newString = PlaceholderAPI.setPlaceholders(player, string);

		// if (Settings.changeableTextEnabled() == true) {
		// if (newString.contains("%changeable_")) {
		// newString = null;
		// }
		// }

		// Bukkit.broadcastMessage(newString);

		// Custom Variables
		for (String custom : plugin.fm.getFile("config").getConfigurationSection("Custom Variables").getKeys(true)) {
			if (newString.contains(custom)) {
				newString = newString.replaceAll(custom,
						Messages.getColored(plugin.fm.getFile("config").getString("Custom Variables." + custom)));
			}
		}

		return newString;
	}
}