/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.util;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

	private static List<ScheduledProcess> queue = new ArrayList<>();

	public static void schedule(ScheduledProcess process) {
		if(process.getTime() == 0)
			process.execute();
		else
			queue.add(process);
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
