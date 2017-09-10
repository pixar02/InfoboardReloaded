package com.pixar02.infoboard.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.pixar02.infoboard.GetVariables;
import com.pixar02.infoboard.InfoBoardReloaded;
import com.pixar02.infoboard.Changeable.ChangeableManager;
import com.pixar02.infoboard.Scroll.ScrollManager;

public class Messages {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	/**
	 * Get the message in color
	 *
	 * @param line
	 * @return new line
	 */
	public static String getColored(String line) {
		line = ChatColor.translateAlternateColorCodes('&', line);
		line = line.replaceAll("&x", RandomChatColor.getColor().toString());
		line = line.replaceAll("&y", RandomChatColor.getFormat().toString());
		return line;
	}

	/**
	 * Get the new line by doing everything
	 *
	 * @param line
	 * @param player
	 * @return new line
	 */
	public static String getLine(String line, Player player) {

		if (line.contains("%")) {
			line = getReplacements(line, player);
		}
		if (line.contains("<") && line.contains(">")) {
			line = getReplacements(line, player);
		}

		line = getColored(line);

		return line;
	}

	/**
	 * Get the <strong>Replaced</strong> version of the line
	 *
	 * @param line
	 * @param player
	 * @return new line
	 */
	public static String getReplacements(String line, Player player) {
		return GetVariables.replaceVariables(line, player);
	}

	/**
	 * Get the title from the config and set it
	 *
	 * @param player
	 * @param worldName
	 * @param rankName
	 * @return
	 */
	public static String getTitle(Player player, String worldName, String rankName) {

		String title = plugin.fm.getFile("board").getString(
				"InfoBoard." + String.valueOf(plugin.timers.getPage()) + "." + worldName + "." + rankName + ".Title");

		if (title.startsWith("<scroll>") && Settings.scrollingEnabled()) {
			title = title.replaceAll("<scroll>", "");
			// and create a Title scroller
			title = ScrollManager.createTitleScroller(player, getLine(title, player)).getMessage();

		} else if (title.startsWith("<changeable_")) {
			title.replaceAll("<changeable_", "").replaceAll(">", "");
			title = ChangeableManager.createChangeableTitle(player, title).getMessage();

		} else {
			title = getLine(title.substring(0, Math.min(title.length(), 32)), player);
		}
		return title;

	}
}