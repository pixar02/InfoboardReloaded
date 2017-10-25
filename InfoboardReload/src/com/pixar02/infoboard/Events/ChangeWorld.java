package com.pixar02.infoboard.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scoreboard.DisplaySlot;

import com.pixar02.infoboard.Scoreboard.Create;

public class ChangeWorld implements Listener {
	/*
	 * private InfoBoardReloaded plugin;
	 * 
	 * public ChangeWorld(InfoBoardReloaded plugin) { this.plugin = plugin; }
	 */
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		Create.createScoreBoard(player);
	}
}
