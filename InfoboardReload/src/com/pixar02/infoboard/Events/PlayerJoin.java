package com.pixar02.infoboard.Events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.pixar02.infoboard.InfoBoardReloaded;

public class PlayerJoin implements Listener {
	public InfoBoardReloaded plugin;

	public PlayerJoin(InfoBoardReloaded pl) {
		plugin = pl;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		if (event.getPlayer().isOp() && plugin.update == true) {
			event.getPlayer().sendMessage(ChatColor.DARK_AQUA + plugin.fm.getFile("messages").getString("update"));
		}
	}

}
