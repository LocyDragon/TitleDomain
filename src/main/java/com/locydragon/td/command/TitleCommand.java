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
				Domain newDomain = new Domain(p.getWorld(), DomainSelectTypeEnum.NORMAL_DOMAIN.toString(), selectFirst,
						selectSecond, domainName);
				newDomain.save();
				TitleDomain.domainList.add(newDomain);
				TitleDomain.domainNameMap.put(domainName, newDomain);
				sender.sendMessage("§9[TitleDomain]§e区域 "+newDomain.getDomainName()+" 已经保存了.");
			} else {
				sender.sendMessage("§9[TitleDomain]§c请使用/td cube [区域名字] ——创建一个区域为你选取的区域.");
			}
		}
		return false;
	}
}
