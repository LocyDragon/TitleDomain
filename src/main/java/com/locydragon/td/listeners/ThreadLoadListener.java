package com.locydragon.td.listeners;

import com.locydragon.td.TitleDomain;
import com.locydragon.td.listeners.thread.AsyncDomainReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Administrator
 */
public class ThreadLoadListener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		AsyncDomainReader reader = new AsyncDomainReader(e.getPlayer());
		reader.start();
		TitleDomain.readerHashMap.put(e.getPlayer().getName(), reader);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		TitleDomain.readerHashMap.get(e.getPlayer().getName()).stopThread();
		TitleDomain.readerHashMap.remove(e.getPlayer().getName());
	}

	@EventHandler
	public void onBeingKicked(PlayerKickEvent e) {
		TitleDomain.readerHashMap.get(e.getPlayer().getName()).stopThread();
		TitleDomain.readerHashMap.remove(e.getPlayer().getName());
	}
}
