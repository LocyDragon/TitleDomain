package com.locydragon.td.util;

import org.bukkit.Location;

public class CylinderHelper {
	public static boolean isInCylinder(Location whichLoc, Location middle, int height, int redius) {
		if (distanceIgnoreY(whichLoc, middle) <= redius) {
			return true;
		}
		return false;
	}
	public static double distanceIgnoreY(Location a, Location b) {
		return Math.sqrt(Math.pow((a.getBlockX() - b.getBlockX()), 2)
				+ Math.pow((a.getBlockZ() - b.getBlockZ()), 2));
	}
}
