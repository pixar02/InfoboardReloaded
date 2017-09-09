package com.pixar02.infoboard.Scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import com.pixar02.infoboard.Scroll.Scroll;
import com.pixar02.infoboard.Scroll.ScrollManager;
import com.pixar02.infoboard.Utils.Messages;
import com.pixar02.infoboard.Utils.Settings;
import com.pixar02.infoboard.InfoBoardReloaded;
import com.pixar02.infoboard.APIS.WorldGuard;
import com.pixar02.infoboard.Changeable.Changeable;
import com.pixar02.infoboard.Changeable.ChangeableManager;
import com.pixar02.infoboard.APIS.Vault;
import java.util.List;

public class Create {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	public static boolean createScoreBoard(Player player) {
		// Set the default variable values
		String worldName = "global";
		String rankName = "default";
		int row, spaces = 0;

		// Make sure the player is allowed to see the scoreboard
		if (WorldGuard.boardsAllowedHere(player.getLocation()) && !Settings.isWorldDisabled(player.getWorld().getName())
				&& player.hasPermission("ibr.View") && !plugin.hidefrom.contains(player.getName())
				&& ((player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) || player.getScoreboard()
						.getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard"))) {
			// Get the board's world name
			if (Settings.doesWorldHaveScoreBoard(plugin.timers.getPage(), player.getWorld().getName())) {
				worldName = player.getWorld().getName();
			} else if (Settings.doesGlobalHaveScoreBoard(plugin.timers.getPage())) {
				worldName = "global";
			} else {
				return false;
			}
			// Get the players rank name
			String rank = Vault.getRank(player);

			// Make sure the rank is on the board, if it is set that to the
			// player's rankName
			if (Settings.doesRankHaveScoreBoard(plugin.timers.getPage(), worldName, rank)) {
				rankName = rank;
			}
			// Make sure there is a default for the board
			if (!Settings.doesRankHaveScoreBoard(plugin.timers.getPage(), worldName, rankName)) {
				return false;
			}
			// Remove any old objective from the sidebar
			if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
				player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			}
			// Create a new scoreboard and set it to the sidebar display
			Board board = new Board();

			// Remove and scrolling texts that the player may have had
			ScrollManager.reset(player);

			// Remove and changeable texts that the player may have had
			ChangeableManager.reset(player);
			List<String> changeables = plugin.fm.getFile("config").getStringList("Changeable Text.Changeables");

			// Now we go to the title setting method thats down below
			board.setTitle(Messages.getTitle(player, worldName, rankName));

			// Loop through the lines
			List<String> lines = plugin.fm.getFile("board").getStringList("InfoBoard."
					+ String.valueOf(plugin.timers.getPage()) + "." + worldName + "." + rankName + ".Rows");

			for (row = 0; row != lines.size(); row++) {
				String line = lines.get(row);

				// ShouldSet is used for the boolean variables (~! an ~@)
				ShouldSet set = new ShouldSet(player, line);
				line = set.getLine();

				if (set.getBoolean())
					// If the line is empty just assume it's an empty line
					if (line.equals(" ") || line.equals("")) {
						String space = "§" + spaces;
						spaces++;
						board.add(Messages.getColored(space), row);
					} else // Manage all scrolling lines
					if (line.startsWith("<scroll>")) {

						if (Settings.scrollingEnabled()) {
							line = line.replaceAll("<scroll>", "");
							int longestLine = getLongestLine(lines, player);
							String string = Messages.getLine(line, player);
							Scroll sc = ScrollManager.createScroller(player, string, row, longestLine);
							line = sc.getMessage();
							board.add(line, row);
						} else {
							line = "Enable Scroll";
							board.add(line, row);
						}
					} else// Manage all changeable lines
					if (line.startsWith("<Changeable_")) {
						if (Settings.changeableTextEnabled()) {
							if (changeables.contains(line)) {
								//leaves the changeable.
								line = line.replaceAll("<Changeable_", "").replaceAll(">", "");
								// TODO NOT FINISHED
								String string = Messages.getLine(line, player);
								Changeable ch = ChangeableManager.createChangeables(player, string, row);
								line = ch.getMessage();
								board.add(line, row);
							} else {
								line = "Unknow Changeable";
								board.add(line, row);
							}
						} else {
							line = "Enable Changeable Text";
							board.add(line, row);
						}
					}
					// If the line has a split in it
					else if (line.contains(";")) {
						String a = line.split(";")[0];
						String b = line.split(";")[1];

						try {
							board.add(Messages.getLine(a, player), Integer.valueOf(Messages.getLine(b, player)));
						} catch (NumberFormatException ne) {
							board.add(Messages.getLine(a, player), 0);
						}
					}
					// Just a regular line
					else
						board.add(Messages.getLine(line, player), row);
			}
			// then we just set the scoreboard for the player
			player.setScoreboard(board.getScoreboard());

		}
		return true;
	}

	public static int getLongestLine(List<String> lines, Player player) {
		int longest = 0;
		for (String line : lines) {
			if (!line.contains("<scroll>")) {
				String string = Messages.getReplacements(line, player);
				if (string.length() > longest)
					longest = string.length();

			}
		}
		return longest;
	}
}