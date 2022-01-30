/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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

/**
 * Interface to be used on Tile Entities that can be used as mater blocks in a Place of Power multiblock structure
 *
 * @author shinoow
 *
 * @since 1.16.0
 *
 */
public interface IStructureBase {

	/**
	 * Getter of the Place of Power this structure base is controlling
	 */
	IPlaceOfPower getMultiblock();

	/**
	 * Setter for the Place of Power this structure base is controlling
	 */
	void setMultiblock(IPlaceOfPower multiblock);

	/**
	 * Bridge method for {@link IPlaceOfPower#getAmplifier(AmplifierType)}
	 * @param type Amplifier Type to amplify
	 * @return A value to increase the selected stat with, or 0
	 */
	float getAmplifier(AmplifierType type);
}
