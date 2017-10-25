package com.pixar02.infoboard.Scroll;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import com.pixar02.infoboard.InfoBoardReloaded;
import com.pixar02.infoboard.Scoreboard.Board;

public class ScrollText {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	public static void scroll(Player player) {
		// Make sure the user can see the board
		if (!plugin.getSettings().isWorldDisabled(player.getWorld().getName())
				&& !plugin.hidefrom.contains(player.getName())
				&& ((player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) || player.getScoreboard()
						.getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard"))) {
			if (plugin.getSM().getScrollers(player) != null) {
				for (Scroll sc : plugin.getSM().getScrollers(player)) {
					try {
						// Move scroller over one, and add the new line
						sc.next();
						String newLine = sc.getMessage();

						Board board = new Board(player);

						board.update(newLine, sc.getRow());
					} catch (Exception ignored) {
					}
				}
			}

			if (plugin.getSM().getTitleScroller(player) != null) {
				try {
					Scroll sc = plugin.getSM().getTitleScroller(player);

					// Change the boards title to the next scroller
					sc.next();
					String newLine = sc.getMessage();

					Board board = new Board(player);

					board.setTitle(newLine);
				} catch (Exception ignored) {
				}
			}

		} else {
			plugin.getSM().reset(player);
		}

	}
}
