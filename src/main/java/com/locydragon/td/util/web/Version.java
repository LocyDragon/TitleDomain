package com.locydragon.td.util.web;

import com.locydragon.td.TitleDomain;

import java.util.ArrayList;
import java.util.List;

public class Version {
	public static String version = null;
	public static final String webVersionURL = "https://raw.githubusercontent.com/LocyDragon/TitleDomain/master/NowVersion.yml";
	public static final String webInfoURL = "https://raw.githubusercontent.com/LocyDragon/TitleDomain/master/LastestInfo.yml";
	public static List<String> genVersionMsg() {
		List<String> genedMsg = new ArrayList<>();
		genedMsg.add("§6===========[TitleDomain]============");
		if (TitleDomain.visitedVersion) {
			genedMsg.add("§c无法访问版本更新网络.找不到更新信息.");
			return genedMsg;
		}
		if (TitleDomain.webVersion.equals(version)) {
			genedMsg.add("§7欢迎您使用§bTitleDomain§7——最新版本!版本号-"+version+"!");
			return genedMsg;
		}
		genedMsg.add("§6TitleDomain不是最新版本!最新版本号: §b"+TitleDomain.webVersion+"§6!你的版本: §b"+version);
		genedMsg.addAll(TitleDomain.webInfo);
		genedMsg.add("§6=======================");
		return genedMsg;
	}
}
