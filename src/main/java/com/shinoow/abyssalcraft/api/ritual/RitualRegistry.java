/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2026 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.ritual;

import java.util.*;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Registry class for Necronomicon Rituals
 * @author shinoow
 *
 * @since 1.4
 */
public class RitualRegistry {

	private final Map<Integer, Integer> dimToBookType = new HashMap<>();
	private final List<NecronomiconRitual> rituals = new ArrayList<>();

	private final Map<Integer, Integer> configDimToBookType = new HashMap<>();

	private final Logger logger = LogManager.getLogger("RitualRegistry");

	// 4 is the highest book type, so nothing can be 5
	private final int NO_BOOKTYPE = 5;

	private static final RitualRegistry instance = new RitualRegistry();

	public static RitualRegistry instance(){
		return instance;
	}

	private RitualRegistry(){}

	/**
	 * Maps a dimension to a book type, in order to specify dimensions where a ritual of that book type can be performed
	 * @param dim The Dimension ID
	 * @param bookType The Necronomicon book type required
	 *
	 * @since 1.4
	 */
	public void addDimensionToBookType(int dim, int bookType){

		if(validBookType(bookType))
			if(dim != OreDictionary.WILDCARD_VALUE)
				dimToBookType.put(dim, bookType);
			else logger.log(Level.ERROR, "You're not allowed to register that Dimension ID: {}", dim);
		else logger.log(Level.ERROR, "Necronomicon book type does not exist: {}", bookType);
	}

	/**
	 * Maps a dimension to a book type, in order to specify dimensions where a ritual of that book type can be performed,<br>
	 * and maps it to a name, in order to display it in the Necronomicon if rituals can only be performed in said dimension
	 * @param dim The Dimension ID
	 * @param bookType The Necronomicon book type required
	 * @param name A String representing the name
	 *
	 * @since 1.4.5
	 */
	public void addDimensionToBookTypeAndName(int dim, int bookType, String name){
		addDimensionToBookType(dim, bookType);
		DimensionDataRegistry.instance().addDimensionToName(dim, name);
	}

	/**
	 * Temporary config version, gets wiped
	 */
	public void addDimensionToBookTypeConfig(int dim, int bookType) {

		if(validBookType(bookType))
			if(dim != OreDictionary.WILDCARD_VALUE)
				configDimToBookType.put(dim, bookType);
			else logger.log(Level.ERROR, "You're not allowed to register that Dimension ID: {}", dim);
		else logger.log(Level.ERROR, "Necronomicon book type does not exist: {}", bookType);
	}

	/**
	 * Temporary config version, gets wiped
	 */
	public void addDimensionToBookTypeConfig(int dim, int bookType, String name){
		addDimensionToBookTypeConfig(dim, bookType);
		DimensionDataRegistry.instance().addDimensionToNameConfig(dim, name);
	}

	/**
	 * Clears the temporary list
	 */
	public void wipeConfig() {

		configDimToBookType.clear();
	}

	/**
	 * Checks if any Ritual-related action can be performed in this dimension with the current Necronomicon
	 * @param dim The dimension ID
	 * @param bookType The Necronomicon book type
	 * @return True if the action can be performed, otherwise false
	 *
	 * @since 1.4
	 */
	public boolean canPerformAction(int dim, int bookType){

		if(!validBookType(bookType)) return false;
		int ret = dimToBookType.getOrDefault(dim, NO_BOOKTYPE);
		if(ret == NO_BOOKTYPE)
			ret = configDimToBookType.getOrDefault(dim, NO_BOOKTYPE);

		return bookType >= ret;
	}

	/**
	 * A more sensitive version of {@link #canPerformAction(int, int)}
	 * @param dim The dimension ID
	 * @param bookType The Necronomicon book type
	 * @return True if the book types match, otherwise false
	 *
	 * @since 1.4
	 */
	public boolean sameBookType(int dim, int bookType){

		if(!validBookType(bookType)) return false;
		int ret = dimToBookType.getOrDefault(dim, NO_BOOKTYPE);
		if(ret == NO_BOOKTYPE)
			ret = configDimToBookType.getOrDefault(dim, NO_BOOKTYPE);

		return bookType == ret;
	}

	/**
	 * Checks if a book type is within the valid range
	 *
	 * @since 2.0
	 */
	private boolean validBookType(int bookType) {

		return bookType <= 4 && bookType >= 0;
	}

	/**
	 * Registers a Necronomicon Ritual
	 * @param ritual The Ritual, contains all data used to perform it
	 *
	 * @since 1.4
	 */
	public void registerRitual(NecronomiconRitual ritual){

		if(!validBookType(ritual.getBookType())) {
			logger.log(Level.ERROR, "Necronomicon book type does not exist: {}", ritual.getBookType());
			return;
		}

		if(rituals.stream().map(NecronomiconRitual::getID).anyMatch(x -> x.equals(ritual.getID()))) {
			logger.log(Level.ERROR, "Necronomicon Ritual already registered: {}", ritual.getID());
			return;
		}

		rituals.add(ritual);
	}

	/**
	 * Used to fetch a list of rituals
	 * @return An ArrayList containing all registered Necronomicon Rituals
	 *
	 * @since 1.4
	 */
	public List<NecronomiconRitual> getRituals(){

		return rituals;
	}

	/**
	 * Used to fetch a ritual by ID, or null if none was found
	 *
	 * @since 2.0
	 */
	@Nullable
	public NecronomiconRitual getRitualById(String id) {

		return rituals.stream().filter(r -> r.getID().equals(id)).findFirst().orElse(null);
	}

	/**
	 * Attempts to fetch a ritual
	 * @param dimension The provided dimension
	 * @param bookType The provided book type
	 * @param offerings The provided offerings
	 * @param sacrifice The provided sacrifice (object placed on the altar)
	 * @return A Necronomicon Ritual, or null if none was found
	 *
	 * @since 1.4
	 */
	public NecronomiconRitual getRitual(int dimension, int bookType, ItemStack[] offerings, ItemStack sacrifice){

		return rituals.stream().filter(ritual -> areRitualsSame(ritual, dimension, bookType, offerings, sacrifice)).findFirst().orElse(null);
	}

	/**
	 * Used to check if a Necronomicon Ritual has the same values as the supplied values
	 * @param ritual The ritual in question
	 * @param dimension The supplied dimension ID
	 * @param bookType The supplied book type
	 * @param offerings The supplied offerings
	 * @param sacrifice The supplied sacrifice
	 * @return True if the rituals match, otherwise false
	 *
	 * @since 1.4
	 */
	private boolean areRitualsSame(NecronomiconRitual ritual, int dimension, int bookType, ItemStack[] offerings, ItemStack sacrifice){

		if(ritual.getDimension() != dimension && ritual.getDimension() != OreDictionary.WILDCARD_VALUE)
			return false;

		if(ritual.getBookType() > bookType)
			return false;

		if(ritual.getOfferings() == null || offerings == null)
			return false;

		if(!APIUtils.areItemStackArraysEqual(ritual.getOfferings(), offerings, ritual.isNBTSensitive()))
			return false;

		if(ritual.requiresItemSacrifice() || ritual.getSacrifice() == null && sacrifice.isEmpty() ||
				APIUtils.areObjectsEqual(sacrifice, ritual.getSacrifice(), ritual.isSacrificeNBTSensitive()))
			return true;

		return false;
	}
}
