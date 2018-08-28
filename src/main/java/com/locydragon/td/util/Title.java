package com.locydragon.td.util;

import com.alibaba.fastjson.JSON;
import com.locydragon.td.TitleDomain;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Title {
	public String sub = "";
	public String title = "";
	public int fadeIn = 1;
	public int display = 2;
	public int fadeOut = 1;

	public Title(String sub, String title, int fadeIn, int display, int fadeOut) {
		this.sub = sub;
		this.title = title;
		this.fadeIn = fadeIn;
		this.display = display;
		this.fadeOut = fadeOut;
	}

	public void sendTo(Player target) {
		TitleUtils.sendTitle(target, this.fadeIn * 20, this.display * 20, this.fadeOut * 20, this.title, this.sub);
	}

	public String toJSON() {
		return JSON.toJSONString(this);
	}

	public static Title fromJSON(String json) {
		return JSON.parseObject(json, Title.class);
	}

	public void saveForDomain(String domainName) {
		TitleDomain.titleSave.set(domainName, toJSON());
		try {
			TitleDomain.titleSave.save(TitleDomain.titleFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
