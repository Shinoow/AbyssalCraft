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

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Places of Power are structures capable of harvesting PE through energy manipulators without angering the gods<br>
 * Register an instance of a class implementing this interface to {@link StructureHandler }
 *
 * @author shinoow
 *
 * @since 1.16.0
 *
 */
public interface IPlaceOfPower {

	/**
	 * Returns the identifier (String associated with this Place of Power)
	 */
	String getIdentifier();

	/**
	 * Returns the Book Type required in order to form this Place of Power
	 */
	int getBookType();

	/**
	 * Returns the Research Item required in order to form this Place of Power
	 */
	IResearchItem getResearchItem();

	/**
	 * Returns a unlocalized string representing the description of the structure when viewed in the Necronomicon
	 */
	default String getDescription() {
		return "ac.structure."+getIdentifier()+".description";
	}

	/**
	 * If the Place of Power amplifies any stats of statues used in it, handle that here
	 * @param type Amplifier Type to amplify
	 * @return A value to increase the selected stat with, or 0
	 */
	float getAmplifier(AmplifierType type);

	/**
	 * Constructs the Place of Power
	 * @param world Current world
	 * @param pos Current position (where the Player constructing this right-clicked)
	 */
	void construct(World world, BlockPos pos);

	/**
	 * Check that the structure is still valid, then take actions accordingly
	 * @param world Current World
	 * @param pos Position of base block
	 */
	void validate(World world, BlockPos pos);

	/**
	 * Checks whether or not the structure can be constructed (the Book Type and Unlock Condition are checked prior to this)
	 * @param world Current World
	 * @param pos Current Position (where the Player constructing this right-clicked)
	 * @param player Player attempting to construct this
	 * @return True if the structure can be constructed, otherwise false
	 */
	boolean canConstruct(World world, BlockPos pos, EntityPlayer player);

	/**
	 * Returns a multidimensional array of Block States depicting how the structure looks when assembled
	 */
	IBlockState[][][] getRenderData();

	/**
	 * Returns the position of the block that forms the structure when activated
	 */
	BlockPos getActivationPointForRender();

	/**
	 * Returns a String (either the localization name or a fixed one) with semi-colon (;) separated values listing the
	 * blocks required to build this structure
	 */
	default String getRequiredBlockNames(){
		return "ac.structure."+getIdentifier()+".blocks";
	}
}
