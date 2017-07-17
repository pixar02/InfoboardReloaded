package com.pixar02.infoboard;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class InfoBoard extends JavaPlugin{
	
	
	public void onEnable(){
		
	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();
	
	registerconfig();
	
	getCommand("InfoBoard").setExecutor(new Commands(this));
	logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");
	
	}
	
	public void onDisable(){
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();
		logger.info(pdfFile.getName() + " has been disabled (V." + pdfFile.getVersion() + ")");
		
	}
	public void registerconfig(){
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}
