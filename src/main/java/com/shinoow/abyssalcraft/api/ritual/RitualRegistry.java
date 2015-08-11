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
package com.shinoow.abyssalcraft.api.ritual;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLLog;

/**
 * Registry class for Necronomicon Rituals
 * @author shinoow
 *
 * @since 1.4
 */
public class RitualRegistry {

	private Map<Integer, Integer> dimToBookType = Maps.newHashMap();
	private Map<NecronomiconRitual, Integer> ritualToBookType = Maps.newHashMap();
	private ArrayList<NecronomiconRitual> rituals = Lists.newArrayList();

	private static final RitualRegistry instance = new RitualRegistry();

	public static RitualRegistry instance(){
		return instance;
	}

	/**
	 * Adds a dimension to the list of dimensions where a ritual of the specific book type can be performed
	 * @param dim The Dimension ID
	 * @param bookType The Necronomicon book type required
	 * 
	 * @since 1.4
	 */
	public void addDimensionToBookType(int dim, int bookType){
		if(bookType <= 4 && bookType >= 0)
			dimToBookType.put(dim, bookType);
		else FMLLog.log("RitualRegistry", Level.ERROR, "Necronomicon book type does not exist: %d", bookType);
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
			if(!rituals.isEmpty()){
				for(NecronomiconRitual compare : rituals)
					if(!compare.getUnlocalizedName().equals(ritual.getUnlocalizedName())){
						rituals.add(ritual);
						break;
					} else FMLLog.log("RitualRegistry", Level.ERROR, "Necronomicon Ritual already registered: %s", ritual.getUnlocalizedName());
			} else rituals.add(ritual);
		} else FMLLog.log("RitualRegistry", Level.ERROR, "Necronomicon book type does not exist: %d", ritual.getBookType());
	}

	/**
	 * Used to fetch a list of rituals
	 * @return An ArrayList containing all registered Necronomicon Rituals
	 */
	public List<NecronomiconRitual> getRituals(){
		return rituals;
	}

	/**
	 * Attempts to fetch a ritual
	 * @param dimension The provided dimension
	 * @param bookType The provided book type
	 * @param offerings The provided offerings
	 * @return A Necronomicon Ritual, or null if none was found
	 * 
	 * @since 1.4
	 */
	public NecronomiconRitual getRitual(int dimension, int bookType, ItemStack[] offerings){

		for(NecronomiconRitual ritual : rituals)
			if(areRitualsSame(ritual, dimension, bookType, offerings)) return ritual;

		return null;
	}

	/**
	 * Used to check if a Necronomicon Ritual has the same values as the supplied values
	 * @param ritual The ritual in question
	 * @param dimension The supplied dimension ID
	 * @param bookType The supplied book type
	 * @param offerings The supplied offerings
	 * @return True if the rituals match, otherwise false
	 * 
	 * @since 1.4
	 */
	private boolean areRitualsSame(NecronomiconRitual ritual, int dimension, int bookType, ItemStack[] offerings){
		if(ritual.getDimension() == dimension || ritual.getDimension() == -1)
			if(ritual.getBookType() <= bookType)
				if(ritual.getOfferings() != null && offerings != null)
					if(areItemStackArraysEqual(ritual.getOfferings(), offerings))
						return true;
		return false;
	}

	private boolean areItemStackArraysEqual(ItemStack[] array1, ItemStack[] array2){

		List<ItemStack> compareList = Lists.newArrayList(array1);
		List<ItemStack> itemList = Lists.newArrayList();

		for(ItemStack item : array2)
			if(item != null)
				itemList.add(item);

		if(itemList.size() == compareList.size())
			for(ItemStack item : itemList)
				for(ItemStack compare : compareList)
					if(areStacksEqual(item, compare)){
						compareList.remove(compare);
						break;
					}

		return compareList.isEmpty();
	}

	public boolean areStacksEqual(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 == null || stack2 == null) return false;
		return stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE
				|| stack1.getItemDamage() == stack2.getItemDamage());
	}
}
