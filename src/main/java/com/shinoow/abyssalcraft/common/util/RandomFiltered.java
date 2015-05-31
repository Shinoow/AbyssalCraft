/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.util;

import java.util.Random;

public class RandomFiltered extends Random {

	private static final long serialVersionUID = 1L;

	public RandomFiltered(long par2) {
		super(par2);
	}

	@Override
	public int nextInt() {
		return this.nextInt(1);
	}

	@Override
	public int nextInt(int n) {
		if (n > 0)
			return super.nextInt(n);
		return 0;
	}
}
