package com.pixar02.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.pixar02.infoboard.Changeable.ChangeableText;
import com.pixar02.infoboard.Scoreboard.Create;
import com.pixar02.infoboard.Scoreboard.Update;
import com.pixar02.infoboard.Scroll.ScrollText;
import com.pixar02.infoboard.Utils.Settings;

public class Timers {
	private int showtime;
	private int time;
	private int rotation;
//	private String changeable;

	public InfoBoardReloaded plugin;

	public Timers(InfoBoardReloaded pl) {
		plugin = pl;
	}

	public Timers() {
		time = 0;
		rotation = 1;
		showtime = plugin.fm.getFile("board").getInt("InfoBoard." + rotation + ".ShowTime");

	}

	public int getPage() {
		return rotation;
	}

	public void setPage(int page) {
		rotation = page;
		time = -1;
		showtime = plugin.fm.getFile("board").getInt("InfoBoard." + rotation + ".ShowTime");
	}

	public void start() {
		/*
		 * =========================================================================
		 * PAGE ROTATION
		 * =========================================================================
		 */
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (time >= showtime) {
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
		 * =========================================================================
		 * UPDATES BOARD'S VALUE
		 * =========================================================================
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

		}, 0, (long) plugin.fm.getFile("config").getDouble("Update Time.Lines") * 20);
		/*
		 * =========================================================================
		 * UPDATES BOARD TITLE VALUE
		 * =========================================================================
		 */
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				// TODO
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("ibr.View")) {
						Update.updateTitle(p, "test");
					}
				}

			}

		}, 0, (long) plugin.fm.getFile("config").getDouble("Update Time.Title") * 20);

		/*
		 * =========================================================================
		 * SCROLLING TEXT
		 * =========================================================================
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

			}, 0, (long) (plugin.fm.getFile("config").getDouble("Scrolling Text.Shift Time") * 20));
		}
		/*
		 * =========================================================================
		 * CHANGEABLE TEXT
		 * =========================================================================
		 */
		if (Settings.changeableTextEnabled()) {

			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				@Override
				public void run() {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.hasPermission("ibr.view")) {
							ChangeableText.change(p);
						}
					}
				}

			}, 0, (long) (plugin.fm.getFile("board").getDouble("Changeable Text.Changeables" + "healthloc" + ".interval")
					* 20));
		}
		/*
		 * =========================================================================
		 * UPDATE TIMER
		 * =========================================================================
		 */
		if (Settings.updater()) {
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				public void run() {
					plugin.logger.info("Checking for updates...");
					try {
						plugin.uc.checkUpdate(plugin.pdfFile.getVersion());
					} catch (Exception e) {
						plugin.logger.warning("Failed to check for updates, because: " + e);

					}
				}
			}, 0, 3600 * 20);
		}

	}

	public void stop() {
		time = 0;
		rotation = 1;
		showtime = plugin.fm.getFile("board").getInt("InfoBoard." + String.valueOf(rotation) + ".ShowTime");

		Bukkit.getScheduler().cancelTasks(plugin);
	}

	public void reset() {
		time = 0;
		rotation = 1;
		showtime = plugin.fm.getFile("board").getInt("InfoBoard." + String.valueOf(rotation) + ".ShowTime");

		Bukkit.getScheduler().cancelTasks(plugin);
		start();
	}
}
