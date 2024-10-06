package com.shinoow.abyssalcraft.lib.world.biome;

/**
 * Utility interface for controlled biome spawn list
 * @author shinoow
 *
 */
public interface IControlledSpawnList {

	/**
	 * Assigns mob spawns (can also wipe unwanted mobs, check impl)
	 */
	public void setMobSpawns();
}
