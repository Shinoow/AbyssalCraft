/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.ritual;

/**
 * Enum used to determine in what way particles are displayed on Ritual Pedestals during a ritual
 *
 * @author shinoow
 *
 * @since 1.24.0
 */
public enum EnumRitualParticle {

	NONE, ITEM, SMOKE, ITEM_SMOKE_COMBO, SMOKE_PILLARS, SPRINKLER, PE_STREAM, GLYPHS;

	public static EnumRitualParticle fromId(int id) {
		if (id < 0 || id >= values().length)
			id = 0;

		return values()[id];
	}
}
