package com.pixar02.infoboard.Utils;

import org.bukkit.entity.Player;

public class Direction {
	public static String getCardinalDirection(Player player) {
		int degrees = (Math.round(player.getLocation().getYaw()) + 180) % 360;
		if (degrees <= 22)
			return "North";
		if (degrees <= 67)
			return "Northeast";
		if (degrees <= 112)
			return "East";
		if (degrees <= 157)
			return "Southeast";
		if (degrees <= 202)
			return "South";
		if (degrees <= 247)
			return "Southwest";
		if (degrees <= 292)
			return "West";
		if (degrees <= 337)
			return "Northwest";
		if (degrees <= 359)
			return "North";
		return null;
	}
}
