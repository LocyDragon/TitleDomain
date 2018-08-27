package com.locydragon.td.listeners.thread;

import com.locydragon.td.TitleDomain;
import com.locydragon.td.api.Domain;
import org.bukkit.entity.Player;

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
			for (Domain domain : TitleDomain.domainList) {
				switch (domain.getType()) {
					case WORLD_DOMAIN:

				}
			}
		}
	}

	public void stopThread() {
		this.stop = true;
	}
}
