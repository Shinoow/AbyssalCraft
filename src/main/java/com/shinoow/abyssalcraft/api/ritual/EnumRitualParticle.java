package com.shinoow.abyssalcraft.api.ritual;

/**
 * Enum used to determine in what way particles are displayed on Ritual Pedestals during a ritual
 * 
 * @author shinoow
 * 
 * @since 1.24.0
 */
public enum EnumRitualParticle {

	ITEM, SMOKE, ITEM_SMOKE_COMBO, SMOKE_PILLARS, SPRINKLER, NONE;

	public static EnumRitualParticle fromId(int id) {
		if (id < 0 || id >= values().length)
			id = 0;

		return values()[id];
	}
}
