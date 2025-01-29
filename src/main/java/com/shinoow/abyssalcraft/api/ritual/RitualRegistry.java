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
	private final Map<NecronomiconRitual, Integer> ritualToBookType = new HashMap<>();
	private final List<NecronomiconRitual> rituals = new ArrayList<>();

	private final Logger logger = LogManager.getLogger("RitualRegistry");

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
		if(bookType <= 4 && bookType >= 0)
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
	 * Checks if any Ritual-related action can be performed in this dimension with the current Necronomicon
	 * @param dim The dimension ID
	 * @param bookType The Necronomicon book type
	 * @return True if the action can be performed, otherwise false
	 *
	 * @since 1.4
	 */
	public boolean canPerformAction(int dim, int bookType){
		if(!dimToBookType.containsKey(dim)) return false;
		return bookType >= dimToBookType.get(dim);
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
		if(!dimToBookType.containsKey(dim)) return false;
		return bookType == dimToBookType.get(dim);
	}

	/**
	 * Registers a Necronomicon Ritual
	 * @param ritual The Ritual, contains all data used to perform it
	 *
	 * @since 1.4
	 */
	public void registerRitual(NecronomiconRitual ritual){
		if(ritual.getBookType() <= 4 && ritual.getBookType() >= 0){
			for(NecronomiconRitual entry : rituals)
				if(ritual.getID().equals(entry.getID())){
					logger.log(Level.ERROR, "Necronomicon Ritual already registered: {}", ritual.getID());
					return;
				}
			rituals.add(ritual);
		} else logger.log(Level.ERROR, "Necronomicon book type does not exist: {}", ritual.getBookType());
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
		return rituals.stream().filter(r -> r.getID() == id).findFirst().orElse(null);
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
		if(ritual.getDimension() == dimension || ritual.getDimension() == OreDictionary.WILDCARD_VALUE)
			if(ritual.getBookType() <= bookType)
				if(ritual.getOfferings() != null && offerings != null)
					if(APIUtils.areItemStackArraysEqual(ritual.getOfferings(), offerings, ritual.isNBTSensitive()))
						if(ritual.requiresItemSacrifice() || ritual.getSacrifice() == null && sacrifice.isEmpty() ||
						APIUtils.areObjectsEqual(sacrifice, ritual.getSacrifice(), ritual.isSacrificeNBTSensitive()))
							return true;
		return false;
	}
}
