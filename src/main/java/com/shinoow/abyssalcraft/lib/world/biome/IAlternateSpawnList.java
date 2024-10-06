package com.shinoow.abyssalcraft.lib.world.biome;

import java.util.List;

import net.minecraft.world.biome.Biome.SpawnListEntry;

/**
 * Utility interface for providing alternate spawn lists
 * @author shinoow
 *
 */
public interface IAlternateSpawnList {

	/**
	 * Returns the Abyssal Wasteland spawn list
	 */
	public List<SpawnListEntry> getAbyssalWastelandList();
	
	/**
	 * Returns the Dreadlands spawn list
	 */
	public List<SpawnListEntry> getDreadlandsList();
}
