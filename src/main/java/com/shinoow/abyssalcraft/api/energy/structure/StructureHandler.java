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
package com.shinoow.abyssalcraft.api.energy.structure;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Handler for Places of Power (the "proper" way of generating PE through energy manipulators)
 *
 * @author shinoow
 *
 * @since 1.16.0
 *
 */
public class StructureHandler {

	private final List<IPlaceOfPower> structures = new ArrayList<>();

	private final Logger logger = LogManager.getLogger("StructureHandler");

	private static final StructureHandler instance = new StructureHandler();

	public static StructureHandler instance(){
		return instance;
	}

	/**
	 * Registers a Place of Power
	 * @param place The Place of Power
	 */
	public void registerStructure(IPlaceOfPower place){
		for(IPlaceOfPower entry : structures)
			if(place.getIdentifier().equals(entry.getIdentifier())){
				logger.log(Level.ERROR, "Place of Power already registered: {}", place.getIdentifier());
				return;
			}
		structures.add(place);
	}

	/**
	 * Used to fetch a list of Places of Power
	 * @return An ArrayList containing all registered Places of Power
	 */
	public List<IPlaceOfPower> getStructures(){
		return structures;
	}

	/**
	 * Fetches a Place of Power based on its identifier
	 * @param name A identifier
	 * @return A Place of Power associated with that identifier, or null if none were found
	 */
	public IPlaceOfPower getStructureByName(String name) {
		for(IPlaceOfPower str : structures)
			if(str.getIdentifier().equals(name))
				return str;
		return null;
	}

	/**
	 * Attempts to form a Place of Power
	 * @param world Current World
	 * @param pos Current position
	 * @param booktype Book Type used
	 * @param player Player trying to form the structure
	 * @return True if it succeeded, otherwise false
	 */
	public boolean tryFormStructure(World world, BlockPos pos, int booktype, EntityPlayer player) {

		for(IPlaceOfPower place : structures)
			if(booktype >= place.getBookType() && place.canConstruct(world, pos, player)) {
				if(!world.isRemote)
					place.construct(world, pos);
				return true;
			}
		return false;
	}
}
