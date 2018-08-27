package com.locydragon.td.api;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;


public class DomainChangeEvent extends PlayerEvent {
	private static final HandlerList handlers = new HandlerList();
	private Player who;
	private Domain last;
	private Domain now;

	public DomainChangeEvent(Player who, Domain last, Domain now) {
		super(who);
		this.who = who;
		this.last = last;
		this.now = now;
	}

	public Domain getLast() {
		return this.last;
	}

	public Domain getNow() {
		return this.now;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}