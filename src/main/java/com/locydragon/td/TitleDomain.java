package com.locydragon.td;

import com.locydragon.td.api.Domain;
import com.locydragon.td.command.TitleCommand;
import com.locydragon.td.listeners.ThreadLoadListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator(雾
 */
public class TitleDomain extends JavaPlugin {
	public static TitleDomain instance;
	public static FileConfiguration config;
	public static List<Domain> domainList = new ArrayList<>();
	public static HashMap<String, Domain> domainNameMap = new HashMap<>();

	@Override
	public void onEnable() {
		getLogger().info("TitleDomain插件已经成功加载了~");
		Bukkit.getPluginManager().registerEvents(new ThreadLoadListener(), this);
		Bukkit.getPluginCommand("td").setExecutor(new TitleCommand());
		saveDefaultConfig();
		instance = this;
		config = getConfig();
		for (String domain : config.getKeys(false)) {
			Domain domainObject = Domain.getByName(domain);
			domainList.add(domainObject);
			domainNameMap.put(domain, domainObject);
		}
		getLogger().info("已经载入" + domainList.size() + "个区域.");
	}

	public static void saveConfiguration() {
		instance.saveConfig();
		instance.reloadConfig();
	}
}
