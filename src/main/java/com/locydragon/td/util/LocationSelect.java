package com.locydragon.td.util;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSelect {
	public static String serialize(Location loc) {
		return new StringBuilder().append(loc.getWorld().getName()).append(",").append(String.valueOf(loc.getBlockX()))
				.append(",")
				.append(String.valueOf(loc.getBlockY()))
				.append(",")
				.append(String.valueOf(loc.getBlockZ()))
				.toString();
	}
	public static Location fromString(String serialize) {
		if (serialize == null) {
			return null;
		}
		String[] strings = serialize.split(",");
		return new Location(Bukkit.getWorld(strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2]),
				Integer.valueOf(strings[3]));
	}
}
