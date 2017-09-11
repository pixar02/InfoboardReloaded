package com.pixar02.infoboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import com.pixar02.infoboard.InfoBoardReloaded;
import com.pixar02.infoboard.APIS.Vault;
import com.pixar02.infoboard.APIS.WorldGuard;
import com.pixar02.infoboard.Utils.Messages;
import com.pixar02.infoboard.Utils.Settings;

public class Update {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	public static HashMap<Integer, String> getLines(List<String> list) {
		HashMap<Integer, String> toAdd = new HashMap<Integer, String>();

		int i = 0;
		for (String line : list) {
			if (!line.equals(" ") && !line.equals("") && !line.contains("<scroll>") && !line.contains("<changeable_")) {
				toAdd.put(-i, line);
			}
			i++;
		}
		return toAdd;
	}

	public void getTitle(List<String> list) {
		ArrayList<String> titles = new ArrayList<String>();

		int i = 0;
		for (String title : list) {
			if (!title.equals(" ") && title.equals(" ") && !title.contains("<scroll>")
					&& !title.contains("<changeable_")) {
				titles.add(title);
			}
			i++;
		}
	}

	public static boolean updateScoreBoard(Player player) {

		String worldName = "global";
		String rankName = "default";

		// Make sure the player is allowed to see the board
		if (!Settings.isWorldDisabled(player.getWorld().getName()) && !plugin.hidefrom.contains(player.getName())
				&& ((player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) || player.getScoreboard()
						.getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard"))) {
			// If the player no longer has permissions to see the board, remove
			// it
			if (!player.hasPermission("ibr.View") || !WorldGuard.boardsAllowedHere(player.getLocation())) {
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			} else {// If the player doesn't have a scoreboard, then just create
					// one
				if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) {
					Create.createScoreBoard(player);
				} else {

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

					// Make sure the rank is on the board, if it is set that to
					// the
					// player's rankName
					if (Settings.doesRankHaveScoreBoard(plugin.timers.getPage(), worldName, rank)) {
						rankName = rank;
					}
					// Make sure there is a default for the board
					if (!Settings.doesRankHaveScoreBoard(plugin.timers.getPage(), worldName, rankName)) {
						player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						return false;
					}
					Board board = new Board(player);

					HashMap<Integer, String> toAdd = getLines(plugin.fm.getFile("board").getStringList("InfoBoard."
							+ String.valueOf(plugin.timers.getPage()) + "." + worldName + "." + rankName + ".Rows"));

					for (Entry<Integer, String> e : toAdd.entrySet()) {

						int row = e.getKey();
						String line = e.getValue();
						ShouldSet set = new ShouldSet(player, line);
						line = set.getLine();

						if (set.getBoolean())
							if (line.contains(";")) {
								String a = line.split(";")[0];
								String b = line.split(";")[1];

								try {
									board.remove(Messages.getLine(a, player));
									board.add(Messages.getLine(a, player),
											Integer.valueOf(Messages.getLine(b, player)));
								} catch (NumberFormatException ne) {
									board.remove(Messages.getLine(a, player));
									board.add(Messages.getLine(a, player),
											Integer.valueOf(Messages.getLine(b, player)));
								}
							}
							// Just a regular line
							else {

								board.update(Messages.getLine(line, player), -row);
							}
					}
				}
			}
		}
		return true;
	}

	public static boolean updateTitle(Player player, String s) {

		return true;
	}
}