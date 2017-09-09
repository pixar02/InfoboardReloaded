package com.pixar02.infoboard.Changeable;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.pixar02.infoboard.Scoreboard.Board;

public class ChangeableManager {
	private static HashMap<Player, ArrayList<Changeable>> changeables = new HashMap<Player, ArrayList<Changeable>>();
	private static HashMap<Player, Changeable> title = new HashMap<Player, Changeable>();

	/**
	 * Create a Changeable
	 *
	 * @param p
	 * @param message?
	 * @param row
	 * @return
	 */
	public static Changeable createChangeables(Player p, String message, int row) {
		Changeable ch = new Changeable(message, row);
		ArrayList<Changeable> chs;
		if (ChangeableManager.changeables.containsKey(p)) {
			chs = ChangeableManager.changeables.get(p);
		} else {
			chs = new ArrayList<Changeable>();
		}
		chs.add(ch);
		ChangeableManager.changeables.put(p, chs);
		return null;
	}

	/**
	 * Create a Changeable
	 *
	 * @param p
	 * @param message?
	 * @return
	 */
	public static Changeable createChangeableTitle(Player p, String message) {
		Changeable ch = new Changeable(message, 0);
		ChangeableManager.title.put(p, ch);
		return ch;
	}

	/**
	 * Get the players changeables
	 * 
	 * @param p
	 * @return
	 */
	public static Changeable getChangeableTitle(Player p) {
		return ChangeableManager.title.get(p);
	}

	/**
	 * Get the players title changeables
	 * 
	 * @param p
	 * @return
	 */
	public static ArrayList<Changeable> getChangeables(Player p) {
		return ChangeableManager.changeables.get(p);
	}

	public static void reset(Player p) {
		if (getChangeables(p) != null) {
			for (Changeable ch : getChangeables(p)) {
				String lastString = ch.getMessage();
				new Board(p).remove(lastString);
			}
		}
		ChangeableManager.changeables.remove(p);
		ChangeableManager.title.remove(p);
	}
}
