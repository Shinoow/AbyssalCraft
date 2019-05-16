package com.shinoow.abyssalcraft.common.util;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

	private static List<ScheduledProcess> queue = new ArrayList<>();
	
	public static void schedule(ScheduledProcess process) {
		if(process.getTime() == 0) {
			process.execute();
		} else {
			queue.add(process);
		}
	}
	
	public static void tick() {
		int queueSize = queue.size();

		for(int i = queueSize - 1; i >= 0; i--) {
			ScheduledProcess process = queue.get(i);
			if(process.tick()) {
				process.execute();
				queue.remove(i);
			}
		}
	}
}
