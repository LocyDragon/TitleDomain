package com.locydragon.td.command;

import com.locydragon.td.TitleDomain;
import com.locydragon.td.api.Domain;
import com.locydragon.td.api.type.DomainSelectTypeEnum;
import com.locydragon.td.listeners.select.DomainSelectMain;
import com.locydragon.td.util.Title;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

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
		Player p = (Player) sender;
		if (args.length <= 0) {
			sender.sendMessage("§9[TitleDomain]§c指令用法不正确.");
			return false;
		}
		if (args[0].equalsIgnoreCase("world")) {
			if (args.length == 2) {
				String domainName = args[1];
				if (domainName.contains("/")) {
					sender.sendMessage("§9[TitleDomain]§c区域名不能包含字符/.");
					return false;
				}
				for (Domain domain : TitleDomain.domainList) {
					if (domain == null) {
						continue;
					}
					if (domain.getDomainName().equalsIgnoreCase(domainName)) {
						sender.sendMessage("§9[TitleDomain]§c区域与 " + domain.getDomainName() + " 区域名字相同.");
						return false;
					}
					if (domain.getInWorld().getName().equals(p.getWorld().getName())) {
						sender.sendMessage("§9[TitleDomain]§c区域与 " + domain.getDomainName() + " 区域重叠.");
						return false;
					}
				}
				Domain newDomain = new Domain(p.getWorld(), DomainSelectTypeEnum.WORLD_DOMAIN.toString(), domainName);
				newDomain.save();
				TitleDomain.domainList.add(newDomain);
				TitleDomain.domainNameMap.put(domainName, newDomain);
				sender.sendMessage("§9[TitleDomain]§e区域 " + newDomain.getDomainName() + " 已经保存了.");
			} else {
				sender.sendMessage("§9[TitleDomain]§c请使用/td world [区域名字] ——选取世界区域为你所在的区域.");
			}
		} else if (args[0].equalsIgnoreCase("cube")) {
			if (args.length == 2) {
				String domainName = args[1];
				if (domainName.contains("/")) {
					sender.sendMessage("§9[TitleDomain]§c区域名不能包含字符/.");
					return false;
				}
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
					if (domain == null) {
						continue;
					}
					if (domain.getDomainName().equalsIgnoreCase(domainName)) {
						sender.sendMessage("§9[TitleDomain]§c区域与 " + domain.getDomainName() + " 区域名字相同.");
						return false;
					}
					if (domain.isCoincideWith(selectFirst, selectSecond)) {
						sender.sendMessage("§9[TitleDomain]§c区域与 " + domain.getDomainName() + " 区域有重合部分.");
						return false;
					}
				}
				Domain newDomain = new Domain(selectFirst.getWorld(), DomainSelectTypeEnum.NORMAL_DOMAIN.toString(), selectFirst,
						selectSecond, domainName);
				newDomain.save();
				TitleDomain.domainList.add(newDomain);
				TitleDomain.domainNameMap.put(domainName, newDomain);
				sender.sendMessage("§9[TitleDomain]§e区域 " + newDomain.getDomainName() + " 已经保存了.");
			} else {
				sender.sendMessage("§9[TitleDomain]§c请使用/td cube [区域名字] ——创建一个区域为你选取的区域.");
			}
		} else if (args[0].equalsIgnoreCase("cylinder")) {
			if (args.length == 4) {
				String domainName = args[1];
				if (domainName.contains("/")) {
					sender.sendMessage("§9[TitleDomain]§c区域名不能包含字符/.");
					return false;
				}
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
					if (domain == null) {
						continue;
					}
					if (domain.getDomainName().equalsIgnoreCase(domainName)) {
						sender.sendMessage("§9[TitleDomain]§c区域与 " + domain.getDomainName() + " 区域名字相同.");
						return false;
					}
				}
				Domain newDomain = new Domain(p.getWorld(), DomainSelectTypeEnum.CIRCLE_DOMAIN.toString(), selectFirst
						, domainName, height, radius);
				newDomain.save();
				TitleDomain.domainList.add(newDomain);
				TitleDomain.domainNameMap.put(domainName, newDomain);
				sender.sendMessage("§9[TitleDomain]§e区域 " + newDomain.getDomainName() + " 已经保存了.");
			} else {
				sender.sendMessage("§9[TitleDomain]§c请使用/td cylinder [区域名字] [高度] [半径] ——创建一个区域为你定义的圆柱.");
			}
		} else if (args[0].equalsIgnoreCase("set")) {
			if (args.length == 8) {
				String domainName = args[1];
				String when = args[2];
				String title = args[3];
				String msg = args[4];
				String fadeIn = args[5];
				String stay = args[6];
				String fadeOut = args[7];
				if (!isRightInteger(fadeIn) || !isRightInteger(stay) || !isRightInteger(fadeOut)) {
					sender.sendMessage("§9[TitleDomain]§c请输入一个正确的数字.");
					return false;
				}
				if (TitleDomain.domainNameMap.get(domainName) == null) {
					sender.sendMessage("§9[TitleDomain]§c你所输入的区域不存在.");
					return false;
				}
				if (!when.equalsIgnoreCase("enter") && !when.equalsIgnoreCase("leave")) {
					sender.sendMessage("§9[TitleDomain]§c请输入 [enter(当进入)/leave(当离开)].");
					return false;
				}
				if (!title.equalsIgnoreCase("title") && !title.equalsIgnoreCase("subtitle")) {
					sender.sendMessage("§9[TitleDomain]§c请输入 [title(大标题)/subtitle(副标题)].");
					return false;
				}
				msg = ChatColor.translateAlternateColorCodes('&', msg);
				when = when.toLowerCase();
				title = title.toLowerCase();
				int numFadeIn = Integer.valueOf(fadeIn);
				int numStay = Integer.valueOf(stay);
				int numFadeOut = Integer.valueOf(fadeOut);
				String magicValue = domainName+"/"+when;
				if (TitleDomain.titleForDomain.get(magicValue) == null) {
					if (title.equals("title")) {
						Title titleObj = new Title("", msg, numFadeIn, numStay, numFadeOut);
						titleObj.saveForDomain(magicValue);
						if (TitleDomain.titleForDomain.keySet().contains(magicValue)) {
							TitleDomain.titleForDomain.remove(magicValue);
						}
						TitleDomain.titleForDomain.put(magicValue, titleObj);
					} else if (title.equals("subtitle")) {
						Title titleObj = new Title(msg, "", numFadeIn, numStay, numFadeOut);
						titleObj.saveForDomain(magicValue);
						if (TitleDomain.titleForDomain.keySet().contains(magicValue)) {
							TitleDomain.titleForDomain.remove(magicValue);
						}
						TitleDomain.titleForDomain.put(magicValue, titleObj);
					}
				} else {
					Title titleObj = TitleDomain.titleForDomain.get(magicValue);
					titleObj.fadeOut = numFadeOut;
					titleObj.fadeIn = numFadeIn;
					titleObj.display = numStay;
					if (title.equals("title")) {
						titleObj.title = msg;
					} else if (title.equals("subtitle")) {
						titleObj.sub = msg;
					}
					titleObj.saveForDomain(magicValue);
					if (TitleDomain.titleForDomain.keySet().contains(magicValue)) {
						TitleDomain.titleForDomain.remove(magicValue);
					}
					TitleDomain.titleForDomain.put(magicValue, titleObj);
				}
				sender.sendMessage("§9[TitleDomain]§e已经为区域 "+domainName+" 添加了一个Title记录~");
			} else {
				sender.sendMessage("§9[TitleDomain]§c请使用/td set [区域名字] [enter/leave] [title/subtitle] [信息] [淡入] [显示时间] [淡出] ——添加一个Title信息.");
			}
		} else if (args[0].equalsIgnoreCase("del")) {
			if (args.length == 2) {
				String domain = args[1];
				TitleDomain.domainNameMap.remove(domain);
				for (int i = 0;i < TitleDomain.domainList.size();i++) {
					if (TitleDomain.domainList.get(i).getDomainName().equals(domain)) {
						TitleDomain.domainList.remove(i);
					}
				}
				TitleDomain.titleForDomain.remove(domain);
				TitleDomain.config.set(domain, null);
				TitleDomain.titleSave.set(domain+"/enter", null);
				TitleDomain.titleSave.set(domain+"/leave", null);
				TitleDomain.saveConfiguration();
				try {
					TitleDomain.titleSave.save(TitleDomain.titleFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				sender.sendMessage("§9[TitleDomain]§c删除成功.");
			} else {
				sender.sendMessage("§9[TitleDomain]§c使用/td del [区域名字] ——删除有关该区域的所有记录.");
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
