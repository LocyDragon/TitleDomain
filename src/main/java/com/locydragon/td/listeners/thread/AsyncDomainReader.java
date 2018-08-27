package com.locydragon.td.listeners.thread;

import com.locydragon.td.TitleDomain;
import com.locydragon.td.api.Domain;
import com.locydragon.td.api.DomainChangeEvent;
import com.locydragon.td.util.CylinderHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static com.locydragon.td.api.type.DomainSelectTypeEnum.NORMAL_DOMAIN;

public class AsyncDomainReader extends Thread {
	public static final Long CHECK_PER_SECOND = 500L;
	private Player target;
	public boolean stop = false;
	public Domain last = null;
	public Domain now = null;

	public AsyncDomainReader(Player target) {
		this.target = target;
	}

	@Override
	public void run() {
		while (target != null && target.isOnline() && !stop) {
			try {
				Thread.sleep(CHECK_PER_SECOND);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
			this.last = this.now;
			this.now = null;
			Loop:
			for (Domain domain : TitleDomain.domainList) {
				switch (domain.getType()) {
					case WORLD_DOMAIN:
						if (target.getWorld().getName().equals(domain.getInWorld())) {
							if (last != null && !last.getDomainName().equals(domain.getDomainName())) {
								this.now = domain;
								Bukkit.getScheduler().runTask(TitleDomain.instance, new Runnable() {
									@Override
									public void run() {
										Bukkit.getPluginManager()
												.callEvent(new DomainChangeEvent(target, last, now));
									}
								});
								break Loop;
							} else if (last == null) {
								this.now = domain;
								Bukkit.getScheduler().runTask(TitleDomain.instance, new Runnable() {
									@Override
									public void run() {
										Bukkit.getPluginManager()
												.callEvent(new DomainChangeEvent(target, last, now));
									}
								});
								break Loop;
							}
						}
						break;
					case NORMAL_DOMAIN:
						if (target.getLocation().toVector()
								.isInAABB(domain.getSelectFist().toVector(), domain.getSelectSecond().toVector())) {
							if (last != null && !last.getDomainName().equals(domain.getDomainName())) {
								this.now = domain;
								Bukkit.getScheduler().runTask(TitleDomain.instance, new Runnable() {
									@Override
									public void run() {
										Bukkit.getPluginManager()
												.callEvent(new DomainChangeEvent(target, last, now));
									}
								});
								break Loop;
							} else if (last == null) {
								this.now = domain;
								Bukkit.getScheduler().runTask(TitleDomain.instance, new Runnable() {
									@Override
									public void run() {
										Bukkit.getPluginManager()
												.callEvent(new DomainChangeEvent(target, last, now));
									}
								});
								break Loop;
							}
						}
					case CIRCLE_DOMAIN:
						if (CylinderHelper.isInCylinder(target.getLocation(),
								domain.getSelectFist(), domain.getHeight(), domain.getRadius())) {
							if (last != null && !last.getDomainName().equals(domain.getDomainName())) {
								this.now = domain;
								Bukkit.getScheduler().runTask(TitleDomain.instance, new Runnable() {
									@Override
									public void run() {
										Bukkit.getPluginManager()
												.callEvent(new DomainChangeEvent(target, last, now));
									}
								});
								break Loop;
							} else if (last == null) {
								this.now = domain;
								Bukkit.getScheduler().runTask(TitleDomain.instance, new Runnable() {
									@Override
									public void run() {
										Bukkit.getPluginManager()
												.callEvent(new DomainChangeEvent(target, last, now));
									}
								});
								break Loop;
							}
						}
					default:
						break;
				}
			}
		}
	}

	public void stopThread() {
		this.stop = true;
	}
}
