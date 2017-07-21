package com.pixar02.infoboard.APIS;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WorlGuard {
	public static boolean boardsAllowedHere(Location loc) {
		boolean allowed = true;
		if (serverHasWorldGuard()) {

			return allowed;
		}
		return false;
	}

	
	
	/*
	 * Does the server have WorldGuard? returns true/false
	 * 
	 */
	private static boolean serverHasWorldGuard() {
		if (Bukkit.getPluginManager().getPlugin("Worlguard") != null) {
			return true;
		}
		return false;
	}
}
