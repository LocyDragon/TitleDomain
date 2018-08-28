package com.locydragon.td.command;

import com.locydragon.td.TitleDomain;
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

			} else {
				sender.sendMessage("§9[TitleDomain]§c请使用/td world [区域名字] ——选取世界区域为你所在的区域.");
			}
		}
		return false;
	}
}
