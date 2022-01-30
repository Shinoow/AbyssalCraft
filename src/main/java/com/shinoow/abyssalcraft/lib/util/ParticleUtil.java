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
package com.shinoow.abyssalcraft.lib.util;

import java.util.function.BiConsumer;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
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
	public static void spawnParticleLine(BlockPos from, BlockPos to, int density, BiConsumer<Vec3d, Vec3d> func) {
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
	public static void spawnParticleLine(BlockPos from, BlockPos to, int density, int increments, BiConsumer<Vec3d, Vec3d> func) {
		Vec3d vec = new Vec3d(to.subtract(from)).normalize();

		double d = Math.sqrt(to.distanceSq(from));
		for(int i = 0; i < d * density; i+=increments){
			double i1 = i / (double)density;
			double xp = from.getX() + vec.x * i1 + .5;
			double yp = from.getY() + vec.y * i1 + .5;
			double zp = from.getZ() + vec.z * i1 + .5;
			func.accept(vec, new Vec3d(xp, yp, zp));
		}
	}

	/**
	 * Spawns the particles seen around Shadow mobs
	 * @param entity Entity to spawn the particles by
	 */
	public static void spawnShadowParticles(EntityLivingBase entity) {
		if(entity.getCreatureAttribute() == AbyssalCraftAPI.SHADOW)
			for (int i = 0; i < 2 * (entity.getBrightness() > 0.1f ? entity.getBrightness() : 0) && ACConfig.particleEntity; ++i)
				actuallySpawnThem(entity);
		else
			actuallySpawnThem(entity);
	}

	private static void actuallySpawnThem(EntityLivingBase entity) {
		entity.getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
				entity.posX + (entity.getRNG().nextDouble() - 0.5D) * entity.width,
				entity.posY + entity.getRNG().nextDouble() * entity.height,
				entity.posZ + (entity.getRNG().nextDouble() - 0.5D) * entity.width, 0.0D, 0.0D, 0.0D);
	}
}
