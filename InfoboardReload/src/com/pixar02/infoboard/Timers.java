package com.pixar02.infoboard;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.pixar02.infoboard.Scroll.ScrollText;
import com.pixar02.infoboard.Utils.Settings;
import com.pixar02.infoboard.scoreboard.Create;
import com.pixar02.infoboard.scoreboard.Update;

public class Timers {
	private int showtime;
	private int time;
	private int rotation;
	private HashMap<String, String> chanText = new HashMap<>();
	private HashMap<String, Integer> chanTextInt = new HashMap<>();
	// private InfoBoard plugin = InfoBoard.getPlugin(InfoBoard.class);

	public InfoBoardReloaded plugin;

	public Timers(InfoBoardReloaded pl) {
		plugin = pl;
		// time = 0;
		// rotation = 1;
		// showtime = plugin.fm.getBoard().getInt("InfoBoard." + rotation +
		// ".ShowTime");
	}

	public Timers() {
		time = 0;
		rotation = 1;
		showtime = plugin.fm.getBoard().getInt("InfoBoard." + rotation + ".ShowTime");

	}

	public int getPage() {
		return rotation;
	}

	public void setPage(int page) {
		rotation = page;
		time = -1;
		showtime = plugin.fm.getBoard().getInt("InfoBoard." + rotation + ".ShowTime");
	}

	public void start() {
		/*
		 * ===================================================================
		 * PAGE ROTATION
		 * ===================================================================
		 */

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (time >= showtime) {
					// TODO checkif current page is last page
					// if so set to first page
					setPage(getPage() + 1);

					if (showtime == 0) {
						setPage(1);
					}
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.hasPermission("ibr.view")) {
							Create.createScoreBoard(p);
						}
					}

				}
				time++;
			}
		}, 0, 20);

		/*
		 * ===================================================================
		 * UPDATES BOARD'S VALUE
		 * ===================================================================
		 */
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("ibr.View")) {
						Update.updateScoreBoard(p);
					}
				}
			}

		}, 0, (long) plugin.fm.getConfig().getDouble("Update Time") * 20);
		/*
		 * ===================================================================
		 * SCROLLING TEXT
		 * ===================================================================
		 */
		if (Settings.scrollingEnabled()) {
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				@Override
				public void run() {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.hasPermission("ibr.view")) {
							ScrollText.scroll(p);
						}
					}

				}

			}, 0, (long) (plugin.fm.getConfig().getDouble("Scrolling Text.Shift Time") * 20));
		}
		/*
		 * ===================================================================
		 * CHANGEABLE TEXT UPDATE
		 * ===================================================================
		 */
		if (Settings.changeableTextEnabled()) {
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				@Override
				public void run() {

				}

			}, 0, (long) (plugin.fm.getBoard().getDouble("InfoBoard." + rotation + ".Changeable.interval") * 20));
		}
	}

	public void stop() {
		time = 0;
		rotation = 1;
		showtime = plugin.fm.getBoard().getInt("InfoBoard." + String.valueOf(rotation) + ".ShowTime");

		Bukkit.getScheduler().cancelTasks(plugin);
	}

	public void reset() {
		time = 0;
		rotation = 1;
		showtime = plugin.fm.getBoard().getInt("InfoBoard." + String.valueOf(rotation) + ".ShowTime");

		// Bukkit.getScheduler().cancelTasks(plugin);
		start();
	}
}
