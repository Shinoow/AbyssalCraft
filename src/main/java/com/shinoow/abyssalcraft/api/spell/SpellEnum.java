/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.spell;

import java.util.Arrays;

/**
 * Collection of Enums used by various parts of the Spell system.
 *
 * @author shinoow
 *
 * @since 1.16.0
 */
public class SpellEnum {

	/**
	 * Scroll Types
	 *
	 * @since 1.16.0
	 */
	public enum ScrollType {

		NONE(-1),BASIC(0), LESSER(1), MODERATE(2), GREATER(3), UNIQUE(4);
		private int quality;

		private ScrollType(int quality){
			this.quality = quality;
		}

		public int getQuality() {
			return quality;
		}

		public static ScrollType byQuality(int id) {
			return Arrays.stream(values())
					.filter(p -> p.quality == id)
					.findFirst().orElse(NONE);
		}
	}
}
