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
package com.shinoow.abyssalcraft.api.dimension;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Tuple;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Registry class for Dimension Data and other dimension-related stuff
 * @author shinoow
 *
 * @since 2.0.0
 */
public class DimensionDataRegistry {

	private final List<DimensionData> dimensions = new ArrayList<>();
	private final Map<Integer, String> dimToName = new HashMap<>();
	private final Map<Integer, Set<Tuple<Integer, Integer>>> gateway_key_overrides = new HashMap<>();

	private final Logger logger = LogManager.getLogger("DimensionDataRegistry");

	private static final DimensionDataRegistry instance = new DimensionDataRegistry();

	private DimensionDataRegistry() {}

	public static DimensionDataRegistry instance() {
		return instance;
	}

	/**
	 * Registers a DimensionData instance (or overrides if one already exists for the dimension)
	 * @param data DimensionData DTO
	 */
	public void registerDimensionData(DimensionData data) {
		if(dimensions.stream().anyMatch(d -> d.getId() == data.getId())) {
			dimensions.removeIf(d -> d.getId() == data.getId());
			logger.log(Level.WARN, "Dimension Data for dimension {} has already been registered, overriding", data.getId());
		}
		dimensions.add(data);
	}

	/**
	 * Returns a list of all registered Dimension Data
	 */
	public List<DimensionData> getDimensions(){
		return dimensions;
	}

	/**
	 * Maps a dimension to a name, in order to display it in the Necronomicon (and other places)
	 * @param dim The Dimension ID
	 * @param name A String representing the name
	 */
	public void addDimensionToName(int dim, String name){
		if(dim != OreDictionary.WILDCARD_VALUE)
			dimToName.put(dim, name);
		else logger.log(Level.ERROR, "You're not allowed to register that Dimension ID: {}", dim);
	}

	/**
	 * Attempts to fetch Dimension Data tied to a dimension ID, if present
	 * @param dim Dimension ID to check for
	 * @return Dimension Data if found, otherwise null
	 */
	@Nullable
	public DimensionData getDataForDim(int dim) {
		return dimensions.stream().filter(d -> d.getId() == dim).findFirst().orElse(null);
	}

	/**
	 * Used to fetch the dimension/name mappings
	 * @return A HashMap containing Dimension IDs and Strings associated with them
	 */
	public Map<Integer, String> getDimensionNameMappings(){
		return dimToName;
	}

	/**
	 * Returns the mapped name of a dimension
	 * @param dim ID of dimension
	 */
	public String getDimensionName(int dim) {
		if(!dimToName.containsKey(dim))
			dimToName.put(dim, "DIM"+dim);
		return dimToName.get(dim);
	}

	/**
	 * Registers a Gateway Key Override, allowing you to create portals going between the two dimensions
	 * @param key Minimum required Gateway Key
	 * <ul>
	 * <li>0 = Gateway Key</li>
	 * <li>1 = Dreadlands Infused Gateway Key</li>
	 * <li>2 = Omothol-forged Gateway Key</li>
	 * <li>3 = The Silver Key</li>
	 * </ul>
	 * @param dim1 First dimension to connect
	 * @param dim2 Second dimension to connect
	 */
	public void addGatewayKeyOverride(int key, int dim1, int dim2){

		int key1 = MathHelper.clamp(key, 0, 3);

		gateway_key_overrides.putIfAbsent(key1, new HashSet<>());

		gateway_key_overrides.get(key1).add(new Tuple<>(dim1, dim2));
	}

	/**
	 * Fetches all Gateway Key Overrides for the specified key type
	 * @param key The Gateway Key
	 * <ul>
	 * <li>0 = Gateway Key</li>
	 * <li>1 = Dreadlands Infused Gateway Key</li>
	 * <li>2 = Omothol-forged Gateway Key</li>
	 * <li>3 = The Silver Key</li>
	 * </ul>
	 */
	public Stream<Tuple<Integer, Integer>> getGatewayKeyOverrides(int key){

		int key1 = MathHelper.clamp(key, 0, 3);

		gateway_key_overrides.putIfAbsent(key1, new HashSet<>());

		return gateway_key_overrides.entrySet().stream()
				.filter(e -> e.getKey() <= key1)
				.flatMap(e -> e.getValue().stream());
	}

	/**
	 * Checks whether or not a portal can be created between the two dimension using the
	 * current Gateway Key
	 * @param dim1 Current dimension
	 * @param dim2 Target dimension
	 * @param key Gateway Key
	 * <ul>
	 * <li>0 = Gateway Key</li>
	 * <li>1 = Dreadlands Infused Gateway Key</li>
	 * <li>2 = Omothol-forged Gateway Key</li>
	 * <li>3 = The Silver Key</li>
	 * </ul>
	 */
	public boolean areDimensionsConnected(int dim1, int dim2, int key) {

		DimensionData data1 = getDataForDim(dim1);
		DimensionData data2 = getDataForDim(dim2);

		if(data1 == null && data2 == null)
			return false;

		boolean bool1 = true;
		boolean bool2 = true;

		if(data1 != null)
			bool1 = data1.getConnectedDimensions().isEmpty() || data1.getConnectedDimensions().contains(dim2);
		if(data2 != null)
			bool2 = data2.getConnectedDimensions().isEmpty() || data2.getConnectedDimensions().contains(dim1);

		if(bool1 && bool2)
			return true;

		return getGatewayKeyOverrides(key)
				.anyMatch(t -> t.getFirst() == dim1 && t.getSecond() == dim2 || t.getFirst() == dim2 && t.getSecond() == dim1);
	}

	/**
	 * Removes Dimension Data related to the ID presented
	 * @param id
	 */
	public void removeDimensionData(int id) {
		if(dimensions.removeIf(d -> d.getId() == id)) {
			dimensions.stream()
			.filter(d -> d.getConnectedDimensions().contains(id))
			.forEach(d -> d.getConnectedDimensions().remove(id));
			for(Entry<Integer, Set<Tuple<Integer, Integer>>> e : gateway_key_overrides.entrySet())
				e.getValue().removeIf(t -> t.getFirst() == id || t.getSecond() == id);
			logger.log(Level.INFO, "Dimension Data for dimension {} was removed", id);
		}

	}
}
