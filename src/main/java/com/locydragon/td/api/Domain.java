package com.locydragon.td.api;

import com.locydragon.td.TitleDomain;
import com.locydragon.td.api.type.DomainSelectTypeEnum;
import com.locydragon.td.util.LocationSelect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nullable;

/**
 * @author Administrator(雾
 */
public class Domain {
	private DomainSelectTypeEnum type;
	private World inWhich;
	private Location loc1;
	private Location loc2;
	private String domainName;
	private int height = -1;
	private int radius = -1;

	public Domain(World inWhich, String type, Location loc1, Location loc2, String domainName) {
		this.inWhich = inWhich;
		this.type = DomainSelectTypeEnum.valueOf(type.toUpperCase());
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.domainName = domainName;
	}

	public Domain(World inWhich, String type, Location circle, String domainName, int height, int radius) {
		this.inWhich = inWhich;
		this.type = DomainSelectTypeEnum.valueOf(type.toUpperCase());
		this.loc1 = circle;
		this.domainName = domainName;
		this.height = height;
		this.radius = radius;
	}

	public Domain(World inWhich, String type, String domainName) {
		this.inWhich = inWhich;
		this.type = DomainSelectTypeEnum.valueOf(type.toUpperCase());
		this.domainName = domainName;
	}

	public String getDomainName() {
		return this.domainName;
	}

	@Nullable
	public Location getSelectFist() {
		return this.loc1;
	}

	@Nullable
	public Location getSelectSecond() {
		return this.loc2;
	}

	public World getInWorld() {
		return this.inWhich;
	}

	public DomainSelectTypeEnum getType() {
		return this.type;
	}

	public int getHeight() {
		return this.height;
	}

	public int getRadius() {
		return this.radius;
	}

	public void save() {
		TitleDomain.config.set(this.domainName + ".inWhich", this.inWhich.getName());
		if (loc1 != null) {
			TitleDomain.config.set(this.domainName + ".loc1", LocationSelect.serialize(this.loc1));
		}
		if (loc2 != null) {
			TitleDomain.config.set(this.domainName + ".loc2", LocationSelect.serialize(this.loc2));
		}
		TitleDomain.config.set(this.domainName + ".type", this.type.toString());
		TitleDomain.config.set(this.domainName + ".height", this.height);
		TitleDomain.config.set(this.domainName + ".radius", this.radius);
		TitleDomain.saveConfiguration();
	}

	public static Domain getByName(String domainName) {
		if (TitleDomain.config.getString(domainName + ".inWhich", "Unknown..").equals("Unknown..")) {
			return null;
		}
		World inWhich
				= Bukkit.getWorld(TitleDomain.config.getString(domainName + ".inWhich"));
		Location loc1
				= LocationSelect.fromString(TitleDomain.config.getString(domainName + ".loc1", null));
		Location loc2
				= LocationSelect.fromString(TitleDomain.config.getString(domainName + ".loc2", null));
		DomainSelectTypeEnum type
				= DomainSelectTypeEnum.valueOf(TitleDomain.config.getString(domainName + ".type").toUpperCase());
		int height = TitleDomain.config.getInt(domainName + ".height");
		int radius = TitleDomain.config.getInt(domainName + ".radius");
		if (loc1 == null && loc2 == null && height == -1 && radius == -1) {
			return new Domain(inWhich, DomainSelectTypeEnum.WORLD_DOMAIN.toString(), domainName);
		}
		if (loc1 == null && loc2 != null && height != -1 && radius != -1) {
			return new Domain(inWhich, DomainSelectTypeEnum.CIRCLE_DOMAIN.toString()
					, loc1, domainName, height, radius);
		}
		if (loc1 != null && loc2 != null && height == -1 && radius == -1) {
			return new Domain(inWhich, DomainSelectTypeEnum.NORMAL_DOMAIN.toString(), loc1, loc2, domainName);
		}
		return null;
	}
}
