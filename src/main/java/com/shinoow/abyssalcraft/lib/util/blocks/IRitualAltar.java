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
package com.shinoow.abyssalcraft.lib.util.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Internal interface for Ritual Altar TE's<br>
 * This shouldn't be used by other mods.
 * @author shinoow
 *
 */
public interface IRitualAltar extends ISingletonInventory {

	/**
	 * Attempts to perform a ritual
	 * @param world Current World
	 * @param pos TE BlockPos
	 * @param player Player interacting with the altar
	 */
	void performRitual(World world, BlockPos pos, EntityPlayer player);

	/**
	 * Used to determine if a Ritual can be performed
	 */
	boolean canPerform();

	/**
	 * Returns the cooldown until a new Ritual can be performed
	 */
	int getRitualCooldown();

	/**
	 * Determines if a Ritual is currently being performed
	 */
	boolean isPerformingRitual();

	/**
	 * Invoked on the client after a ritual has started (through a network packet). Sends over the necessary values for the visuals
	 * @param ritual Active ritual (should never be null, but it wouldn't cause a side-effect)
	 * @param sacrifice The animal to sacrifice (if any)
	 */
	void setRitualFields(@Nullable NecronomiconRitual ritual, @Nullable EntityLiving sacrifice);

	/**
	 * Adds the selected pedestal to the altar's pedestal list
	 */
	void addPedestal(IRitualPedestal pedestal);

	/**
	 * Returns the pedestals associated to this altar
	 */
	List<IRitualPedestal> getPedestals();

	/**
	 * Returns how particles on Ritual Pedestals are displayed for the current ritual
	 */
	EnumRitualParticle getRitualParticle();
}
