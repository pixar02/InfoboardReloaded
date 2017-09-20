package com.pixar02.infoboard.Utils;

import java.util.ArrayList;
import java.util.List;

import com.pixar02.infoboard.InfoBoardReloaded;

public class Settings {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	public static List<String> changeable = new ArrayList<String>();

	/**
	 * Determines if the rank has valid scoreboard
	 *
	 * @param rotation
	 * @return true/false
	 */
	public static boolean doesGlobalHaveScoreBoard(int rotation) {
		boolean hasBoard = false;
		for (String s : plugin.fm.getFile("board").getConfigurationSection("InfoBoard." + String.valueOf(rotation))
				.getKeys(true)) {
			if (!s.contains(".")) {
				if (s.equals("global")) {
					hasBoard = true;
					break;
				}
			}
		}
		return hasBoard;
	}

	/**
	 * Determines if the rank has valid scoreboard
	 *
	 * @param rotation
	 * @param world
	 * @param rank
	 * @return true/false
	 */
	public static boolean doesRankHaveScoreBoard(int rotation, String world, String rank) {
		boolean hasBoard = false;
		for (String s : plugin.fm.getFile("board")
				.getConfigurationSection("InfoBoard." + String.valueOf(rotation) + "." + world).getKeys(true)) {
			if (!s.contains(".")) {
				if (s.equals(rank)) {
					hasBoard = true;
					break;
				}
			}
		}
		return hasBoard;
	}

	/**
	 * Determines if the world given has a valid scoreboard
	 *
	 * @param rotation
	 * @param world
	 * @return true/false
	 */
	public static boolean doesWorldHaveScoreBoard(int rotation, String world) {
		boolean hasBoard = false;
		for (String s : plugin.fm.getFile("board").getConfigurationSection("InfoBoard." + String.valueOf(rotation))
				.getKeys(true)) {
			if (!s.contains(".")) {
				if (s.equalsIgnoreCase(world)) {
					hasBoard = true;
					break;
				}
			}
		}
		return hasBoard;
	}

	/**
	 * Get the list of blocked regions for worldguard
	 *
	 * @return list
	 */
	public static List<String> getRegionsDisabled() {
		return plugin.fm.getFile("config").getStringList("WorldGuard.Prevent Showing In");
	}

	/**
	 * Determine if the world given is blocked
	 *
	 * @param world
	 * @return
	 */
	public static boolean isWorldDisabled(String world) {
		return plugin.fm.getFile("config").getStringList("Disabled Worlds").contains(world) || (world == null);
	}

	/**
	 * Get if scrolling is enabled
	 *
	 * @return true/false
	 */
	public static boolean scrollingEnabled() {
		return plugin.fm.getFile("config").getBoolean("Scrolling Text.Enable");
	}

	/**
	 * Get if changeable text is enabled
	 * 
	 * @return true/false
	 */
	public static boolean changeableTextEnabled() {
		return plugin.fm.getFile("config").getBoolean("Changeable Text.Enable");
	}

	/**
	 * Get if updater is enabled
	 * 
	 * @return true/false
	 */
	public static boolean updater() {
		return plugin.fm.getFile("config").getBoolean("Updater");
	}

	/**
	 * Get if Debugger is enabled
	 * 
	 * @return true/false
	 */
	public static boolean debug() {
		return plugin.fm.getFile("config").getBoolean("Debug");
	}

	/**
	 * Get the lines for give Changeable
	 * 
	 * @return ArrayList
	 * 
	 */
	public static ArrayList<String> getText(String changeable) {
		ArrayList<String> lines = new ArrayList<String>();
		for (String s : plugin.fm.getFile("config")
				.getStringList("Changeable Text.Changeables." + changeable + ".text")) {
			lines.add(s);
		}
		return lines;
	}

	/*
	 * Loads the changeable's in a list.
	 * 
	 */
	public static void loadChangeable() {
		changeable.clear();
		for (String s : plugin.fm.getFile("config").getConfigurationSection("Changeable Text.Changeables")
				.getKeys(false)) {
			changeable.add(s);
		}
	}

	/**
	 * Get's the changeable list
	 * 
	 * @return List
	 */
	public static List<String> getChangeable() {
		return changeable;
	}

	/**
	 * Get's the time for the given changeable
	 * 
	 * @return integer
	 */
	public static Integer getInterval(String changeable) {
		int time = plugin.fm.getFile("config").getInt("Changeable Text.Changeables." + changeable + ".interval");
		return time;
	}

}
