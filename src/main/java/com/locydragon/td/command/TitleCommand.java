package com.locydragon.td.command;

import com.locydragon.td.TitleDomain;
import com.locydragon.td.api.Domain;
import com.locydragon.td.api.type.DomainSelectTypeEnum;
import com.locydragon.td.listeners.select.DomainSelectMain;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Administrator
 */
public class TitleCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender.hasPermission("TitleDomain.admin")) || !(sender instanceof Player)) {
			sender.sendMessage("§9[TitleDomain]§c没有权限.");
			return false;
		}
		Player p = (Player)sender;
		if (args.length <= 0) {
			sender.sendMessage("§9[TitleDomain]§c指令用法不正确.");
		}
		if (args[0].equalsIgnoreCase("reload")) {
			sender.sendMessage("§9[TitleDomain]§c重载配置文件中...");
			TitleDomain.reloadConfiguration();
			sender.sendMessage("§9[TitleDomain]§c载入完成.");
		} else if (args[0].equalsIgnoreCase("world")) {
			if (args.length == 2) {
				String domainName = args[1];
				for (Domain domain : TitleDomain.domainList) {
					if (domain.getDomainName().equalsIgnoreCase(domainName)) {
						sender.sendMessage("§9[TitleDomain]§c区域与 "+domain.getDomainName()+" 区域名字相同.");
						return false;
					}
					if (domain.getInWorld().getName().equals(p.getWorld().getName())) {
						sender.sendMessage("§9[TitleDomain]§c区域与 "+domain.getDomainName()+" 区域重叠.");
						return false;
					}
				}
				Domain newDomain = new Domain(p.getWorld(), DomainSelectTypeEnum.WORLD_DOMAIN.toString(), domainName);
				newDomain.save();
				TitleDomain.domainList.add(newDomain);
				TitleDomain.domainNameMap.put(domainName, newDomain);
				sender.sendMessage("§9[TitleDomain]§e区域 "+newDomain.getDomainName()+" 已经保存了.");
			} else {
				sender.sendMessage("§9[TitleDomain]§c请使用/td world [区域名字] ——选取世界区域为你所在的区域.");
			}
		} else if (args[0].equalsIgnoreCase("cube")) {
			if (args.length == 2) {
				String domainName = args[1];
				Location selectFirst = DomainSelectMain.selectFist.get(p.getName());
				Location selectSecond = DomainSelectMain.selectSecond.get(p.getName());
				if (!selectFirst.getWorld().getName().equals(selectSecond.getWorld().getName())) {
					sender.sendMessage("§9[TitleDomain]§c你选取的两点不在同一世界.");
					return false;
				}
				boolean willReturn = false;
				if (selectFirst == null) {
					sender.sendMessage("§9[TitleDomain]§c你没有选取点A.");
					willReturn = true;
				}
				if (selectSecond == null) {
					sender.sendMessage("§9[TitleDomain]§c你没有选取点B.");
					willReturn = true;
				}
				if (willReturn) {
					return false;
				}
				for (Domain domain : TitleDomain.domainList) {
					if (domain.getDomainName().equalsIgnoreCase(domainName)) {
						sender.sendMessage("§9[TitleDomain]§c区域与 "+domain.getDomainName()+" 区域名字相同.");
						return false;
					}
					if (domain.isCoincideWith(selectFirst, selectSecond)) {
						sender.sendMessage("§9[TitleDomain]§c区域与 "+domain.getDomainName()+" 区域有重合部分.");
						return false;
					}
				}
				Domain newDomain = new Domain(selectFirst.getWorld(), DomainSelectTypeEnum.NORMAL_DOMAIN.toString(), selectFirst,
						selectSecond, domainName);
				newDomain.save();
				TitleDomain.domainList.add(newDomain);
				TitleDomain.domainNameMap.put(domainName, newDomain);
				sender.sendMessage("§9[TitleDomain]§e区域 "+newDomain.getDomainName()+" 已经保存了.");
			} else {
				sender.sendMessage("§9[TitleDomain]§c请使用/td cube [区域名字] ——创建一个区域为你选取的区域.");
			}
		} else if (args[0].equalsIgnoreCase("cylinder")) {
			if (args.length == 4) {
				String domainName = args[1];
				Location selectFirst = DomainSelectMain.selectFist.get(p.getName());
				if (selectFirst == null) {
					sender.sendMessage("§9[TitleDomain]§c你没有选取点A.");
					return false;
				}
				if (!isRightInteger(args[2]) || !isRightInteger(args[3])) {
					sender.sendMessage("§9[TitleDomain]§c请输入一个正确的数字.");
					return false;
				}
				int height = Integer.valueOf(args[2]);
				int radius = Integer.valueOf(args[3]);
				for (Domain domain : TitleDomain.domainList) {
					if (domain.getDomainName().equalsIgnoreCase(domainName)) {
						sender.sendMessage("§9[TitleDomain]§c区域与 "+domain.getDomainName()+" 区域名字相同.");
						return false;
					}
				}
				Domain newDomain = new Domain(p.getWorld(), DomainSelectTypeEnum.CIRCLE_DOMAIN.toString(), selectFirst
						, domainName, height, radius);
				newDomain.save();
				TitleDomain.domainList.add(newDomain);
				TitleDomain.domainNameMap.put(domainName, newDomain);
				sender.sendMessage("§9[TitleDomain]§e区域 "+newDomain.getDomainName()+" 已经保存了.");
			} else {
				sender.sendMessage("§9[TitleDomain]§c请使用/td cylinder [区域名字] [高度] [半径] ——创建一个区域为你定义的圆柱.");
			}
		}
		return false;
	}
	public static boolean isRightInteger(String num) {
		try {
			int number = Integer.valueOf(num);
			if (number <= 0) {
				return false;
			}
			return true;
		} catch (Exception exc) {
			return false;
		}
	}
}
