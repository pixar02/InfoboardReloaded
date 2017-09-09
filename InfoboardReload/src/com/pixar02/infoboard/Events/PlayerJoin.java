package com.pixar02.infoboard.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.sendMessage("Its Working!");

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			int i;

			public void run() {
				// for (i = 0; i <= 6; i++) {
				if (i <= 6) {
					if (i == 0) {
						player.sendMessage("Its Working! %number%".replaceAll("%number%", Integer.toString(i)));
					} else if (i == 1) {
						player.sendMessage("Its Working! %number%".replaceAll("%number%", Integer.toString(i)));
					} else if (i == 2) {
						player.sendMessage("Its Working! %number%".replaceAll("%number%", Integer.toString(i)));
					} else if (i == 3) {
						player.sendMessage("Its Working! %number%".replaceAll("%number%", Integer.toString(i)));
					} else if (i == 4) {
						player.sendMessage("Its Working! %number%".replaceAll("%number%", Integer.toString(i)));
					} else if (i == 5) {
						player.sendMessage("Its Working! %number%".replaceAll("%number%", Integer.toString(i)));
					} else if (i == 6) {
						player.sendMessage("Its Working! %number%".replaceAll("%number%", Integer.toString(i)));
					}
				} else {
					Bukkit.getScheduler().cancelTasks(plugin);
				}

			}
		}, 0, 100);

	}

}
