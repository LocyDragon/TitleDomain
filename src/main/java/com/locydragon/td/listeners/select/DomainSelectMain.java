package com.locydragon.td.listeners.select;

import com.locydragon.td.util.LocationSelect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.ConcurrentHashMap;

public class DomainSelectMain implements Listener {
	public static ConcurrentHashMap<String, Location> selectFist = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<String, Location> selectSecond = new ConcurrentHashMap<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getItem() != null && e.getItem().getType() == Material.GOLD_HOE &&
				e.getPlayer().hasPermission("TitleDomain.admin")
				&& e.getClickedBlock() != null) {
			if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (selectFist.keySet().contains(e.getPlayer().getName())) {
					selectFist.remove(e.getPlayer().getName());
				}
				selectFist.put(e.getPlayer().getName(), e.getClickedBlock().getLocation());
				e.getPlayer().sendMessage("§9[TitleDomain]§6选取选择点A: "
						+ LocationSelect.serialize(e.getClickedBlock().getLocation()));
				e.setCancelled(true);
			} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (selectSecond.keySet().contains(e.getPlayer().getName())) {
					selectSecond.remove(e.getPlayer().getName());
				}
				selectSecond.put(e.getPlayer().getName(), e.getClickedBlock().getLocation());
				e.getPlayer().sendMessage("§9[TitleDomain]§6选取选择点B: "
						+ LocationSelect.serialize(e.getClickedBlock().getLocation()));
				e.setCancelled(true);
			}
		}
	}
}
