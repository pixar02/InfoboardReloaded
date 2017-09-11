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

	public Changeable(Player p, int row, ArrayList<String> lines, int interval) {
		this.interval = interval;
		this.row = row;
		this.lines = new ArrayList<String>(lines);
		this.message = lines.get(0);
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

	public int getRow() {
		return row;
	}

	public String getMessage() {
		return message;
	}

	public void next() {
		if (lines.size() == counter) {
			counter = 0;
		} else {
			message = lines.get(counter);
			counter++;
		}
	}

	public int getInterval() {
		return interval;
	}

}
