package com.pixar02.infoboard.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Board {

	private Scoreboard scoreboard;
	private Objective objective;

	/**
	 * Create a brand new objective and scoreboard
	 */
	public Board() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		setup();
	}

	/**
	 * Use the current objective and scoreboard that the player is shown
	 *
	 * @param player
	 */
	public Board(Player player) {
		scoreboard = player.getScoreboard();
		objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
	}

	/**
	 * Use the current objective and scoreboard
	 *
	 * @param board
	 */
	public Board(Scoreboard board) {
		scoreboard = board;
		objective = board.getObjective(DisplaySlot.SIDEBAR);
	}

	/**
	 * Add a line to the board
	 *
	 * @param line
	 * @param row
	 */
	public void add(String line, int row) {

		row = 15 - row;

		if (line.length() > 16) {
			addCreatingTeam(line, row);
		} else {
			Score score = objective.getScore(line);
			score.setScore(1);
			score.setScore(row);
		}
	}

	/**
	 * Add a line well creating a team to go with it(Allows for up to 48 characters
	 * in a line(Prefix and Suffix))
	 *
	 * @param line
	 * @param row
	 */
	private void addCreatingTeam(String line, int row) {
		String prefix = null, name = null, suffix = null;

		if (line.length() > 48) {
			line = line.substring(0, 47);
		}
		if (line.length() <= 16) {
			name = line;

		} else if (line.length() <= 32) {
			name = line.substring(0, 16);
			suffix = line.substring(16, line.length());
		} else {
			prefix = line.substring(0, 16);
			name = line.substring(16, 32);
			suffix = line.substring(32, line.length());
		}

		FastOfflinePlayer op = new FastOfflinePlayer(name);

		if ((prefix != null) || (suffix != null)) {
			Team team = scoreboard.getPlayerTeam(op);

			if (team == null) {
				team = scoreboard.registerNewTeam(name);
			}
			team.addPlayer(op);
			if (prefix != null) {
				team.setPrefix(prefix);
			}
			team.setSuffix(suffix);
		}
		Score score = objective.getScore(name);
		score.setScore(1);
		score.setScore(row);
	}

	/**
	 * Get the score for the line
	 *
	 * @param line
	 * @return score
	 */
	public Score getScore(String line) {
		return objective.getScore(line);
	}

	/**
	 * Get the scoreboard
	 *
	 * @return the scoreboard
	 */
	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	/**
	 * Get the title
	 *
	 * @return the title
	 */
	public String getTitle() {
		return objective.getDisplayName();
	}

	/**
	 * Set the title
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		objective.setDisplayName(title);
	}

	/**
	 * Remove the line from the board
	 *
	 * @param line
	 */
	public void remove(String line) {
		scoreboard.resetScores(line);
		if (scoreboard.getTeam(line) != null) {
			scoreboard.getTeam(line).unregister();
		}
	}

	/**
	 * Set up the new objective
	 */
	private void setup() {
		objective = scoreboard.registerNewObjective("InfoBoard", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	/**
	 * Update a line (Remove the current line, add the new line)
	 *
	 * @param line
	 * @param row
	 */
	public void update(String line, int row) {
		String name = null;

		if (line.length() <= 16) {
			name = line;

		} else if (line.length() <= 32) {
			name = line.substring(0, 16);

		} else {
			name = line.substring(16, 32);
		}

		if (!scoreboard.getEntries().contains(name)) {

			for (String op : scoreboard.getEntries()) {

				if (objective.getScore(op).getScore() == 15 - row) {
					remove(op);
					break;
				}
			}
			add(line, row);
		}
	}
}