package com.pixar02.infoboard.Scroll;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.pixar02.infoboard.InfoBoardReloaded;
import com.pixar02.infoboard.Scoreboard.Board;

public class ScrollManager {
	private InfoBoardReloaded plugin;

	private HashMap<Player, ArrayList<Scroll>> scrollers = new HashMap<Player, ArrayList<Scroll>>();
	private HashMap<Player, Scroll> title = new HashMap<Player, Scroll>();

	public ScrollManager(InfoBoardReloaded plugin) {
		this.plugin = plugin;
	}

	/**
	 * Create a scroller
	 *
	 * @param p
	 * @param message
	 * @param row
	 * @param width
	 * @return
	 */
	public Scroll createScroller(Player p, String message, int row, int width) {
		Scroll sc = new Scroll(plugin, message, row, width);
		ArrayList<Scroll> scs;
		if (this.scrollers.containsKey(p)) {
			scs = this.scrollers.get(p);
		} else {
			scs = new ArrayList<Scroll>();
		}
		scs.add(sc);
		this.scrollers.put(p, scs);
		return sc;
	}

	/**
	 * Create a title scroller
	 *
	 * @param p
	 * @param message
	 * @return
	 */
	public Scroll createTitleScroller(Player p, String message) {

		Scroll sc = new Scroll(plugin, message, 0, 16);
		this.title.put(p, sc);

		return sc;
	}

	/**
	 * Get the players scrollers
	 *
	 * @param p
	 * @return
	 */
	public ArrayList<Scroll> getScrollers(Player p) {
		return this.scrollers.get(p);
	}

	/**
	 * Get the players title scroller
	 *
	 * @param p
	 * @return
	 */
	public Scroll getTitleScroller(Player p) {
		return this.title.get(p);
	}

	/**
	 * Reset all the players scrollers
	 *
	 * @param p
	 */
	public void reset(Player p) {
		if (getScrollers(p) != null) {
			for (Scroll sc : getScrollers(p)) {
				String lastString = sc.getMessage();
				new Board(p).remove(lastString);
			}
		}
		this.scrollers.remove(p);
		this.title.remove(p);
	}

}
