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

public abstract class ScheduledProcess {

	private int time;

	public ScheduledProcess(int time) {
		this.time = time;
	}

	public boolean tick() {
		time--;

		return time <= 0;
	}

	public int getTime() {
		return time;
	}

	public abstract void execute();
}
