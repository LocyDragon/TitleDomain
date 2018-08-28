package com.locydragon.td;

import com.locydragon.td.api.Domain;
import com.locydragon.td.command.TitleCommand;
import com.locydragon.td.listeners.DomainTitleListener;
import com.locydragon.td.listeners.ThreadLoadListener;
import com.locydragon.td.listeners.select.DomainSelectMain;
import com.locydragon.td.listeners.thread.AsyncDomainReader;
import com.locydragon.td.util.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator(雾
 */
public class TitleDomain extends JavaPlugin {
	public static TitleDomain instance;
	public static FileConfiguration config;
	public static FileConfiguration titleSave;
	public static FileConfiguration settings;
	public static Vector<Domain> domainList = new Vector<>();
	public static File titleFile = new File(".//plugins//TitleDomain//Data//DataTitle.yml");
	public static ConcurrentHashMap<String, Domain> domainNameMap = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<String, AsyncDomainReader> readerHashMap = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<String, Title> titleForDomain = new ConcurrentHashMap<>();

	@Override
	public void onEnable() {
		getLogger().info("TitleDomain插件已经成功加载了~");
		Bukkit.getPluginManager().registerEvents(new ThreadLoadListener(), this);
		Bukkit.getPluginManager().registerEvents(new DomainSelectMain(), this);
		Bukkit.getPluginManager().registerEvents(new DomainTitleListener(), this);
		Bukkit.getPluginCommand("td").setExecutor(new TitleCommand());
		loadTitle();
		loadSettings();
		loadData();
		instance = this;
		for (String domain : config.getKeys(false)) {
			Domain domainObject = Domain.getByName(domain);
			domainList.add(domainObject);
			domainNameMap.put(domain, domainObject);
		}
		for (String title : titleSave.getKeys(false)) {
			String json = titleSave.getString(title);
			titleForDomain.put(title, Title.fromJSON(json));
		}
		getLogger().info("已经载入" + domainList.size() + "个区域.");
		if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
			getLogger().info("错误: 找不到前置 ProtocolLib,插件已经自动关闭了.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		new Metrics(this);
	}

	public static void saveConfiguration() {
		try {
			config.save(new File(".//plugins//TitleDomain//Data//data.yml"));
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	public static void reloadConfiguration() {
		instance.reloadConfig();
		domainList.clear();
		domainNameMap.clear();
		for (String domain : config.getKeys(false)) {
			Domain domainObject = Domain.getByName(domain);
			domainList.add(domainObject);
			domainNameMap.put(domain, domainObject);
		}
		instance.getLogger().info("已经载入" + domainList.size() + "个区域.");
	}

	public void loadTitle() {
		File titleFile = new File(".//plugins//TitleDomain//Data//DataTitle.yml");
		titleFile.getParentFile().mkdirs();
		if (!(titleFile.exists())) {
			try {
				titleFile.createNewFile();
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		}
		titleSave = YamlConfiguration.loadConfiguration(titleFile);
	}

	public void loadData() {
		File titleFile = new File(".//plugins//TitleDomain//Data//data.yml");
		titleFile.getParentFile().mkdirs();
		if (!(titleFile.exists())) {
			try {
				titleFile.createNewFile();
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(titleFile);
	}

	public void loadSettings() {
		File titleFile = new File(".//plugins//TitleDomain//config.yml");
		titleFile.getParentFile().mkdirs();
		if (!(titleFile.exists())) {
			try {
				titleFile.createNewFile();
			} catch (IOException exc) {
				exc.printStackTrace();
			}
			settings = YamlConfiguration.loadConfiguration(titleFile);
			settings.set("tool", Material.GOLD_HOE.toString());
			try {
				settings.save(new File(".//plugins//TitleDomain//config.yml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		settings = YamlConfiguration.loadConfiguration(titleFile);
	}
}
