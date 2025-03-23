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

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyManipulator;

import net.minecraft.util.math.BlockPos;

/**
 * Interface to use on Tile Entities that can gain stat boosts when part of a Place of Power multiblock structure<br>
 * Things that would fall into the category are {@link IEnergyManipulator}s, but {@link IEnergyContainer}s could<br>
 * also gain something from it
 *
 * @author shinoow
 *
 * @since 1.16.0
 *
 */
public interface IStructureComponent {

	/**
	 * Checks if the structure component is currently part of a Place of Power multiblock structure
	 */
	boolean isInMultiblock();

	/**
	 * Setter for whether or not this structure component is part of a Place of Power multiblock structure
	 */
	void setInMultiblock(boolean bool);

	/**
	 * Getter for the position of the structure base for the Place of Power this component is part of
	 */
	@Nullable BlockPos getBasePosition();

	/**
	 * Setter for the structure base position for the Place of Power this component is part of
	 */
	void setBasePosition(BlockPos pos);
}
