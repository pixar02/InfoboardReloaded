package com.pixar02.infoboard;

import org.bukkit.Bukkit;

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
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			
		}

	public void stop() {

	}

	public void reset() {

	}
}
