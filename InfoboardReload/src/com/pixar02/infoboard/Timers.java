package com.pixar02.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.pixar02.infoboard.scoreboard.Create;
import com.pixar02.infoboard.scoreboard.Update;

public class Timers {
	private int showtime;
	private int time;
	private int rotation;
	private InfoBoard plugin = InfoBoard.getPlugin(InfoBoard.class);

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
		 * ==============================================
		 *  Page Rotation
		 * ==============================================
		 */
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
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
		}, 0, 20L);
		/*
		 * ============================================== 
		 * updates board's value
		 * ==============================================
		 */
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("InfoBoard.View")) {

						Update.updateScoreBoard(p);
					}
				}
			}

		}, 0, (long) plugin.fm.getConfig().getDouble("Update Time") * 20);

	}

	public void stop() {
		time = 0;
		rotation = 1;
		showtime = plugin.fm.getBoard().getInt("InfoBoard." + String.valueOf(rotation) + ".Show Time");

		Bukkit.getScheduler().cancelTasks(plugin);
	}

	public void reset() {
		time = 0;
		rotation = 1;
		showtime = plugin.fm.getBoard().getInt("InfoBoard." + String.valueOf(rotation) + ".Show Time");

		Bukkit.getScheduler().cancelTasks(plugin);
		start();
	}
}
