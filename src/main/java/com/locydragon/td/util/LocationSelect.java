package com.locydragon.td.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.Vector;

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

	//mcbbs上的代码 很乱 无视即可 懒得改了
	public static List<Location> fillWith(Location loc1, Location loc2) {
		List<Location> locList = new ArrayList<>();
		int x1 = 0;
		int y1 = 0;
		int z1 = 0;
		int x2 = 2;
		int y2 = 2;
		int z2 = 2;
		int xx;
		int yy;
		int zz;
		if (x1 > x2) {
			xx = x1 - x2;
		} else {
			xx = x2 - x1;
		}
		if (y1 > y2) {
			yy = y1 - y2;
		} else {
			yy = y2 - y1;
		}
		if (z1 > z2) {
			zz = z1 - z2;
		} else {
			zz = z2 - z1;
		}
		int xc = xx;
		int yc = yy;
		int zc = zz;
		while (xc >= 0) {
			yc = yy;
			while (yc >= 0) {
				zc = zz;
				while (zc >= 0) {
					locList.add(new Location(loc1.getWorld(), xc, yc, zc));
					zc--;
				}
				yc--;
			}
			xc--;
		}
		return locList;
	}

	public static boolean isInAABB(Location needGo, Location AA, Location BB) {
		int xMax = AA.getBlockX() > BB.getBlockX() ? AA.getBlockX() : BB.getBlockX();
		int xMin = AA.getBlockX() > BB.getBlockX() ? BB.getBlockX() : AA.getBlockX();
		int yMax = AA.getBlockY() > BB.getBlockY() ? AA.getBlockY() : BB.getBlockY();
		int yMin = AA.getBlockY() > BB.getBlockY() ? BB.getBlockY() : AA.getBlockY();
		int zMax = AA.getBlockZ() > BB.getBlockZ() ? AA.getBlockZ() : BB.getBlockZ();
		int zMin = AA.getBlockZ() > BB.getBlockZ() ? BB.getBlockZ() : AA.getBlockZ();
		return (needGo.getBlockX() >= xMin) && (needGo.getBlockX() <= xMax)
				&& (needGo.getBlockY() >= yMin) && (needGo.getBlockY() <= yMax)
				&& (needGo.getBlockZ() >= zMin) && (needGo.getBlockZ() <= zMax);
	}
}
