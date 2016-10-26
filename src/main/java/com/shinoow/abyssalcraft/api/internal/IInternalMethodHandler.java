/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.internal;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;

public interface IInternalMethodHandler {

	/**
	 * Fires a message to the client triggering a Disruption<br>
	 * <b><i>You should probably NEVER ever call this method at all, ever.<br>
	 * Seriously, this method is called in the DisruptionHandler to send<br>
	 * a Disruption to the client while firing it server-side.</i></b>
	 * @param deity Deity Type
	 * @param name Disruption Unlocalized Name
	 * @param pos BlockPos
	 * @param id Dimension ID
	 */
	public void sendDisruption(DeityType deity, String name, BlockPos pos, int id);

	/**
	 * Spawns a particle
	 * @param particleName Particle name
	 * @param world Current World
	 * @param posX X Coordinate
	 * @param posY Y Coordinate
	 * @param posZ Z Coordinate
	 * @param velX X velocity
	 * @param velY Y velocity
	 * @param velZ Z velocity
	 */
	public void spawnParticle(String particleName, World world, double posX, double posY, double posZ, double velX, double velY, double velZ);
}
