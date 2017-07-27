package com.pixar02.infoboard.variables;

import org.bukkit.entity.Player;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;

public class GriefPreventionVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = string;
		String prefix = "griefprevention_";
		GriefPrevention inst = GriefPrevention.instance;
		PlayerData pd = inst.dataStore.getPlayerData(player.getUniqueId());

		if (newString.contains("%" + prefix + "claims%")) {
			newString = newString.replaceAll("%" + prefix + "claims%", String.valueOf(pd.getClaims().size()));
		}
		if (newString.contains("%" + prefix + "currentclaim_ownername%")) {
			newString = newString.replaceAll("%" + prefix + "currentclaim_ownername",
					String.valueOf(pd.lastClaim.getOwnerName()));
		}
		// if (newString.contains("%" + prefix + "spamcount%")) {
		// newString = newString.replaceAll("%" + prefix +
		// "spamcount%", String.valueOf());
		// }
		if (newString.contains("%" + prefix + "bonusclaims%")) {
			newString = newString.replaceAll("%" + prefix + "bonusclaims%", String.valueOf(pd.getBonusClaimBlocks()));
		}
		if (newString.contains("%" + prefix + "accruedclaims%")) {
			newString = newString.replaceAll("%" + prefix + "accruedclaims%",
					String.valueOf(pd.getAccruedClaimBlocks()));
		}
		if (newString.contains("%" + prefix + "remainingclaims%")) {
			newString = newString.replaceAll("%" + prefix + "remainingclaims%",
					String.valueOf(pd.getRemainingClaimBlocks()));
		}

		return newString;
	}
}