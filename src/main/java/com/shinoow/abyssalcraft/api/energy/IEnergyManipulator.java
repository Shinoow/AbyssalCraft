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
package com.shinoow.abyssalcraft.api.energy;

import java.util.Set;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

/**
 * Interface to use on tile entities that can manipulate Ley Lines (WIP)<br>
 * WARNING: Any methods in this interface might end up getting removed,<br>
 * so I would suggest not using it until this text (and the WIP part) is removed.
 *
 * @author shinoow
 *
 * @since 1.4.5
 */
public interface IEnergyManipulator {

	/**
	 * Gets the quanta of Potential Energy that the tile entity can drain
	 */
	float getEnergyQuanta();

	/**
	 * Activates the Amplifier boost
	 * @param amp Current Amplifier
	 * @param deity Current Deity
	 */
	void setActive(AmplifierType amp, DeityType deity);

	/**
	 * Checks if the Amplifier boost is active
	 */
	boolean isActive();

	/**
	 * Returns the Deity associated with this manipulator (can be null)
	 */
	DeityType getDeity(IBlockState state);

	/**
	 * Used to calculate Amplification through Charms.<br>
	 * Should be called whenever something that can be amplified is calculated.
	 * @param type Type to check
	 */
	float getAmplifier(AmplifierType type);

	/**
	 * Returns the Active Deity (assigned in {@link #setActive(AmplifierType, DeityType)})
	 */
	DeityType getActiveDeity();

	/**
	 * Returns the active Deity (assigned in {@link #setActive(AmplifierType, DeityType)})
	 */
	AmplifierType getActiveAmplifier();

	/**
	 * Sets the Active Deity
	 * @param deity Deity Type
	 */
	void setActiveDeity(DeityType deity);

	/**
	 * Sets the Active Amplifier
	 * @param amplifier Amplifier Type
	 */
	void setActiveAmplifier(AmplifierType amplifier);

	/**
	 * Increases the tolerance value (tolerance determines a disruption is fired)
	 * @param num Amount to increase with
	 */
	void addTolerance(int num);

	/**
	 * Returns the manipulator's current tolerance value (tolerance determines a disruption is fired)
	 */
	int getTolerance();

	/**
	 * Verifies if the manipulator can transfer PE to nearby Players and/or Collectors.<br>
	 * This is called in PEUtils before any energy transfer, so use this for something like checking the
	 * capacity of a PE buffer (provided the manipulator has one, otherwise just return true and call it a day).
	 */
	boolean canTransferPE();

	/**
	 * Fires off a Disruption, which can lead to bad things happening
	 */
	void disrupt();

	/**
	 * Returns the current positions of the currently tracked Energy Collectors within range of the manipulator
	 */
	default Set<BlockPos> getEnergyCollectors(){
		return null;
	}
}
