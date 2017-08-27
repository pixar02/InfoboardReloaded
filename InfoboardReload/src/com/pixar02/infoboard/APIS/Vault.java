package com.pixar02.infoboard.APIS;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.pixar02.infoboard.InfoBoardReloaded;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Vault {
	private static InfoBoardReloaded plugin = InfoBoardReloaded.getPlugin(InfoBoardReloaded.class);

	/**
	 * Gets the players rank
	 *
	 * @param player
	 * @return the rank
	 */
	public static String getRank(Player player) {
		String rank = "default";
		if (hasVaultOnServer())
			try {
				rank = InfoBoardReloaded.permission.getPlayerGroups(player.getWorld().getName(), player.getPlayer())[0];
			} catch (UnsupportedOperationException ignored) {
			}
		return rank;
	}

	/**
	 * Does the server have vault and did everything for vault load okay
	 *
	 * @return true/false
	 */
	private static boolean hasVaultOnServer() {
		return (Bukkit.getPluginManager().getPlugin("Vault") != null) && (InfoBoardReloaded.permission != null)
				&& (InfoBoardReloaded.permissionB);
	}

	/**
	 * Load economy and permissions
	 */
	public static void load() {
		if (!setupEconomy() || !setupPermissions()) {
			System.out.println(
					"InfoBoard will be shut down because VAULT either does not have economy or permissions setup");
			System.out.println(
					"InfoBoard will be shut down because VAULT either does not have economy or permissions setup");
			System.out.println(
					"InfoBoard will be shut down because VAULT either does not have economy or permissions setup");
			Bukkit.getPluginManager().disablePlugin(plugin);
		}
	}

	/**
	 * Load Economy
	 *
	 * @return if it loaded
	 */
	private static boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null) {
			InfoBoardReloaded.economy = economyProvider.getProvider();
		}

		if (InfoBoardReloaded.economy != null) {
			InfoBoardReloaded.economyB = true;
		}
		return (InfoBoardReloaded.economy != null);
	}

	/**
	 * Load Permissions
	 *
	 * @return if it loaded
	 */
	private static boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null)
			InfoBoardReloaded.permission = permissionProvider.getProvider();
		if (InfoBoardReloaded.permission != null)
			InfoBoardReloaded.permissionB = true;

		return (InfoBoardReloaded.permission != null);
	}
}