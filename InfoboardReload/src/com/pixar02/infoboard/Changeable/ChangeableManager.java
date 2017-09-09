package com.pixar02.infoboard.Changeable;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.pixar02.infoboard.Scoreboard.Board;
import com.pixar02.infoboard.Utils.Settings;

public class ChangeableManager {
	private static HashMap<Player, ArrayList<Changeable>> changeables = new HashMap<Player, ArrayList<Changeable>>();
	private static HashMap<Player, Changeable> title = new HashMap<Player, Changeable>();
	public static HashMap<String, ArrayList<String>> variable = Settings.getChangeables();

	/**
	 * Create a Changeable
	 *
	 * @param p
	 * @param message?
	 * @param row
	 * @return
	 */
	public static Changeable createChangeables(Player p, String changeable, int row) {
		ArrayList<String> lines = variable.get(changeable);
		Bukkit.getServer().getConsoleSender().sendMessage(changeable);
		Changeable ch = new Changeable(row, lines);
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
	 * @param changeable
	 * @return
	 */
	public static Changeable createChangeableTitle(Player p, String changeable) {
		ArrayList<String> lines = variable.get(changeable);
		Changeable ch = new Changeable(0, lines);
		ChangeableManager.title.put(p, ch);
		return ch;
	}

	/**
	 * Get the players changeable title
	 * 
	 * @param p
	 * @return
	 */
	public static Changeable getChangeableTitle(Player p) {
		return ChangeableManager.title.get(p);
	}

	/**
	 * Get the players changeables
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
