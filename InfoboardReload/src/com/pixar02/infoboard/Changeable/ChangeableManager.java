package com.pixar02.infoboard.Changeable;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.pixar02.infoboard.InfoBoardReloaded;
import com.pixar02.infoboard.Scoreboard.Board;
import com.pixar02.infoboard.Utils.Settings;

public class ChangeableManager {
	private InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);
	
	private HashMap<Player, ArrayList<Changeable>> changeables = new HashMap<Player, ArrayList<Changeable>>();
	private HashMap<Player, Changeable> title = new HashMap<Player, Changeable>();

	/**
	 * Create a Changeable
	 *
	 * @param p
	 * @param changeable
	 * @param row
	 * @return
	 */
	public Changeable createChangeables(Player p, String changeable, int row) {
		ArrayList<String> lines = plugin.getSettings().getText(changeable);
		int time = plugin.getSettings().getInterval(changeable);
		Changeable ch = new Changeable(p, row, lines, time);
		ArrayList<Changeable> chs;
		if (this.changeables.containsKey(p)) {
			chs = this.changeables.get(p);
		} else {
			chs = new ArrayList<Changeable>();
		}
		chs.add(ch);
		this.changeables.put(p, chs);
		return ch;
	}

	/**
	 * Create a Changeable Title
	 *
	 * @param p
	 * @param changeable
	 * @return
	 */
	public Changeable createChangeableTitle(Player p, String changeable) {
		ArrayList<String> lines = plugin.getSettings().getText(changeable);
		int time = plugin.getSettings().getInterval(changeable);
		Changeable ch = new Changeable(p, 0, lines, time);
		this.title.put(p, ch);
		return ch;
	}

	/**
	 * Get the players changeable title
	 * 
	 * @param p
	 * @return
	 */
	public Changeable getChangeableTitle(Player p) {
		return this.title.get(p);
	}

	/**
	 * Get the players changeables
	 * 
	 * @param p
	 * @return players changeables
	 */
	public ArrayList<Changeable> getChangeables(Player p) {
		return this.changeables.get(p);
	}

	/**
	 * Resets the players changeable
	 * 
	 * @param p
	 */
	public void reset(Player p) {
		if (getChangeables(p) != null) {
			for (Changeable ch : getChangeables(p)) {
				String lastString = ch.getMessage();
				new Board(p).remove(lastString);
			}
		}
		this.changeables.remove(p);
		this.title.remove(p);
	}
}
