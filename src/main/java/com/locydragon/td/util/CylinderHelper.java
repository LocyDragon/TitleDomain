package com.locydragon.td.util;

import org.bukkit.Location;

public class CylinderHelper {
	public static boolean isInCylinder(Location whichLoc, Location middle, int height, int redius) {
		if (whichLoc.getBlockY() > middle.getBlockY() + height) {
			return false;
		}
		if (whichLoc.getBlockY() < middle.getBlockY()) {
			return false;
		}
		Location planeLoc = middle.clone();
		middle.setY(whichLoc.getY());
		if (planeLoc.distance(whichLoc) <= redius) {
			return true;
		}
		return false;
	}
}
