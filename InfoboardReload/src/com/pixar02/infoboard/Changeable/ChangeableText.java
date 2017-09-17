package com.pixar02.infoboard.Changeable;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import com.pixar02.infoboard.InfoBoardReloaded;
import com.pixar02.infoboard.Scoreboard.Board;
import com.pixar02.infoboard.Utils.Messages;
import com.pixar02.infoboard.Utils.Settings;

public class ChangeableText {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	public static void change(Player player) {
		// Make sure the user can see the board
		if (!Settings.isWorldDisabled(player.getWorld().getName()) && !plugin.hidefrom.contains(player.getName())
				&& ((player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) || player.getScoreboard()
						.getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard"))) {

			if (ChangeableManager.getChangeables(player) != null) {
				for (Changeable ch : ChangeableManager.getChangeables(player)) {
					try {
						// Move changeable over one, and add the new line
						ch.next();
						String newLine = ch.getMessage();

						Board board = new Board(player);
						newLine = Messages.getLine(newLine, player);
						board.update(newLine, ch.getRow());
					} catch (Exception ex) {

					}
				}
			}
			if (ChangeableManager.getChangeableTitle(player) != null) {

				try {
					Changeable ch = ChangeableManager.getChangeableTitle(player);
					// Move changeable over one, and add the new line
					ch.next();
					String newLine = ch.getMessage();

					Board board = new Board(player);
					newLine = Messages.getLine(newLine, player);
					board.setTitle(newLine);
				} catch (Exception ex) {

				}

			}
		} else {
			ChangeableManager.reset(player);
		}

	}

}
