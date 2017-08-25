package com.pixar02.infoboard.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import com.pixar02.infoboard.InfoBoardReloaded;

public class UpdateChecker {
	public InfoBoardReloaded plugin;

	public UpdateChecker(InfoBoardReloaded pl) {
		plugin = pl;
	}

	private boolean checkHigher(String currentVersion, String newVersion) {
		String current = toReadable(currentVersion);
		String newVers = toReadable(newVersion);
		return current.compareTo(newVers) <= 0;
	}

	public void checkUpdate(String currentVersion) throws Exception {
		String version = getVersion("98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4", 44270);
		if (Settings.debug()) {
			plugin.getLogger().info(version);
			plugin.getLogger().info(currentVersion);
		}
		if (checkHigher(currentVersion, version) == false) {
			if (Settings.debug()) {
				plugin.getLogger().info("You are running the most recent version!");
			}
			plugin.update = true;
		} else {
			if (Settings.debug()) {
				plugin.getLogger().info("There is an update aviable");
			}
			plugin.update = false;
		}
	}

	private String getVersion(String key, int resourceId) {
		String version = null;
		try {
			HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php")
					.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.getOutputStream().write(("key=" + key + "&resource=" + resourceId).getBytes("UTF-8"));
			version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
		} catch (IOException ex) {
			if (Settings.debug()) {
				plugin.getLogger().warning("Could not connect to Spigot");
			}
			ex.printStackTrace();
		}

		return version;
	}

	public String toReadable(String version) {
		String[] split = Pattern.compile(".", Pattern.LITERAL).split(version.replace("v", ""));
		version = "";
		for (String s : split)
			version += String.format("%4s", s);
		return version;
	}
}
