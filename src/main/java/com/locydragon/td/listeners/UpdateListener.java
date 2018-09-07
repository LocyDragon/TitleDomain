package com.locydragon.td.listeners;

import com.locydragon.td.util.web.Version;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().isOp()) {
			Version.genVersionMsg().forEach(line -> e.getPlayer().sendMessage(line));
		}
	}
}
