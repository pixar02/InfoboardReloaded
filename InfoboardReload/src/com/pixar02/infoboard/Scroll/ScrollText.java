package com.pixar02.infoboard.Scroll;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import com.pixar02.infoboard.InfoBoardReloaded;
import com.pixar02.infoboard.Scoreboard.Board;
import com.pixar02.infoboard.Utils.Settings;

public class ScrollText {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	public static void scroll(Player player) {
		// Make sure the user can see the board
		if (!plugin.getSettings().isWorldDisabled(player.getWorld().getName())
				&& !plugin.hidefrom.contains(player.getName())
				&& ((player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) || player.getScoreboard()
						.getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard"))) {
			if (ScrollManager.getScrollers(player) != null) {
				for (Scroll sc : ScrollManager.getScrollers(player)) {
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

			if (ScrollManager.getTitleScroller(player) != null) {
				try {
					Scroll sc = ScrollManager.getTitleScroller(player);

					// Change the boards title to the next scroller
					sc.next();
					String newLine = sc.getMessage();

					Board board = new Board(player);

					board.setTitle(newLine);
				} catch (Exception ignored) {
				}
			}

		} else {
			ScrollManager.reset(player);
		}

	}
}
