package com.locydragon.td.listeners;

import com.locydragon.td.TitleDomain;
import com.locydragon.td.api.Domain;
import com.locydragon.td.api.DomainChangeEvent;
import com.locydragon.td.util.Title;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DomainTitleListener implements Listener {
	@EventHandler
	public void onDomainChange(DomainChangeEvent e) {
		if (e.getLast() == null && e.getNow() != null) {
			Domain domain = e.getNow();
			if (TitleDomain.titleForDomain.get(domain.getDomainName()+"/enter") != null) {
				Title title = TitleDomain.titleForDomain.get(domain.getDomainName()+"/enter");
				title.sendTo(e.getPlayer());
			}
		} else if (e.getLast() != null && e.getNow() == null) {
			Domain domainLast = e.getLast();
			if (TitleDomain.titleForDomain.get(domainLast.getDomainName()+"/leave") != null) {
				Title title = TitleDomain.titleForDomain.get(domainLast.getDomainName()+"/leave");
				title.sendTo(e.getPlayer());
			}
		} else if (!e.getLast().getDomainName().equals(e.getNow().getDomainName())) {
			Domain domain = e.getNow();
			if (TitleDomain.titleForDomain.get(domain.getDomainName()+"/enter") != null) {
				Title title = TitleDomain.titleForDomain.get(domain.getDomainName()+"/enter");
				title.sendTo(e.getPlayer());
			}
		}
	}
}
