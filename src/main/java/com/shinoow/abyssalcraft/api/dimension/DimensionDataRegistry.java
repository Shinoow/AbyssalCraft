package com.shinoow.abyssalcraft.api.dimension;

import java.util.*;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private final Map<Integer, Integer> gateway_key_overrides = new HashMap<>();

	private final Logger logger = LogManager.getLogger("DimensionDataRegistry");

	private static final DimensionDataRegistry instance = new DimensionDataRegistry();

	private DimensionDataRegistry() {}

	public static DimensionDataRegistry instance() {
		return instance;
	}

	public void registerDimensionData(DimensionData data) {
		if(dimensions.stream().anyMatch(d -> d.getId() == data.getId())) {
			logger.log(Level.ERROR, "Dimension Data for dimension {} has already been registered", data.getId());
		} else {
			dimensions.add(data);
		}
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
	 * Registers a Gateway Key Override, allowing you to use a Gateway Key inside the specified dimension.
	 * @param dimId Dimension ID
	 * @param type Which Portal to place down
	 * <ul>
	 * <li>0 = The Abyssal Wasteland</li>
	 * <li>1 = The Dreadlands</li>
	 * <li>2 = Omothol</li>
	 * </ul>
	 */
	public void addGatewayKeyOverride(int dimId, int type){
		gateway_key_overrides.put(dimId, type);
	}

	/**
	 * Fetches a Gateway Key Override for the specified dimension (provided one is registered)
	 * @param dimId Dimension ID to fetch a override for
	 * @return A Integer in the range 0 - 2 if a override was found, otherwise -1
	 */
	public int getGatewayKeyOverride(int dimId){
		return !gateway_key_overrides.containsKey(dimId) ? -1 : gateway_key_overrides.get(dimId);
	}
}
