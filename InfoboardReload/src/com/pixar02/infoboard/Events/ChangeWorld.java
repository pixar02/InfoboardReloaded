package com.pixar02.infoboard.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scoreboard.DisplaySlot;

public class ChangeWorld implements Listener {

	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
		event.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	}
}
