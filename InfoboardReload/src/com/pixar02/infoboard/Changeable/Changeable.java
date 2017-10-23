package com.pixar02.infoboard.Changeable;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.pixar02.infoboard.InfoBoardReloaded;

public class Changeable {
	private InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);
	
	private int counter = 0;
	private String message;
	private int row;
	private int interval;
	private ArrayList<String> lines;

	/**
	 * Create a new changealbe
	 * 
	 * @param p
	 * @param row
	 * @param lines
	 * @param interval
	 */
	public Changeable(Player p, int row, ArrayList<String> lines, int interval) {
		this.interval = interval;
		this.row = row;
		this.lines = new ArrayList<String>(lines);
		this.message = this.lines.get(0);
		/*
		 * =========================================================================
		 * CHANGEABLE TEXT UPDATES VALUE
		 * =========================================================================
		 */
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				ChangeableText.change(p);
			}

		}, 0, (long) this.interval * 20);

	}

	/**
	 * Gets the row
	 * 
	 * @return row (Integer)
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the Message
	 * 
	 * @return message (String)
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Moves to the next line
	 */
	public void next() {
		if (lines.size() == counter) {
			counter = 0;
		} else {
			message = lines.get(counter);
			counter++;
		}
	}

	/**
	 * Gets the changeable interval
	 * 
	 * @return interval (Integer)
	 */
	public int getInterval() {
		return interval;
	}

}
