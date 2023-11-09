/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.util;

import java.util.function.BiFunction;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Utility class for particle-related stuff
 * @author shinoow
 *
 */
public class ParticleUtil {

	/**
	 * Spawn particles in a line between two positions
	 * @param from Origin BlockPos
	 * @param to Desination BlockPos
	 * @param density How densely the particles will spawn (higher number = more particles)
	 * @param func Function to spawn the particles in (first vector is the one between the
	 * positions, the second is the coordinates for the particle to spawn at), world context
	 * should be supplied by the invoker of the method
	 */
	public static void spawnParticleLine(BlockPos from, BlockPos to, int density, BiFunction<Vec3d, Vec3d, Boolean> func) {
		spawnParticleLine(from, to, density, 1, func);
	}

	/**
	 * Spawn particles in a line between two positions
	 * @param from Origin BlockPos
	 * @param to Desination BlockPos
	 * @param density How densely the particles will spawn (higher number = more particles)
	 * @param increments How much to increment the spawning loop by (affects density)
	 * @param func Function to spawn the particles in (first vector is the one between the
	 * positions, the second is the coordinates for the particle to spawn at), world context
	 * should be supplied by the invoker of the method
	 */
	public static void spawnParticleLine(BlockPos from, BlockPos to, int density, int increments, BiFunction<Vec3d, Vec3d, Boolean> func) {
		Vec3d vec = new Vec3d(to.subtract(from)).normalize();

		double d = Math.sqrt(to.distanceSq(from));
		for(int i = 0; i < d * density; i+=increments){
			double i1 = i / (double)density;
			double xp = from.getX() + vec.x * i1 + .5;
			double yp = from.getY() + vec.y * i1 + .5;
			double zp = from.getZ() + vec.z * i1 + .5;
			func.apply(vec, new Vec3d(xp, yp, zp));
		}
	}
}
