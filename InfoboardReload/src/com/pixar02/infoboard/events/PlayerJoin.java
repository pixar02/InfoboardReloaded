package com.pixar02.infoboard.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.pixar02.infoboard.InfoBoard;

public class PlayerJoin implements Listener {
	public InfoBoard plugin;

	public PlayerJoin(InfoBoard pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		if (event.getPlayer().isOp() && plugin.update)
			event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Theres a new update for InfoBoard");
	}
}
