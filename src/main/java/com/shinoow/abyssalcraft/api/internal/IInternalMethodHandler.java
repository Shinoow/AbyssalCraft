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
package com.shinoow.abyssalcraft.api.internal;

import java.util.Random;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

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
	void sendDisruption(DeityType deity, String name, BlockPos pos, int id);

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
	void spawnParticle(String particleName, World world, double posX, double posY, double posZ, double velX, double velY, double velZ);

	/**
	 * Spawns a PE stream between two Positions
	 * @param posFrom Position of origin
	 * @param posTo Target position
	 * @param dimension Dimension ID
	 */
	void spawnPEStream(BlockPos posFrom, BlockPos posTo, int dimension);

	/**
	 * Spawns a PE stream between a BlockPos and a Entity
	 * @param posFrom Position of origin
	 * @param target Target entity
	 * @param dimension Dimension ID
	 */
	void spawnPEStream(BlockPos posFrom, Entity target, int dimension);

	/**
	 * Attempts to generate a Darklands Structure.<br>
	 * This should be called during world generation for the result.
	 * @param type Structure type
	 * <ul>
	 * <li>0 = Any structure</li>
	 * <li>1 = Shrine structures</li>
	 * <li>2 = Ritual Ground structures</li>
	 * <li>3 = Buildings</li>
	 * <li>4 = Misc structures</li>
	 * </ul>
	 * @param world Current World
	 * @param random Random instance
	 * @param pos A BlockPos <br>
	 * <i>world.getHeight(new BlockPos(chunkX*16 + random.nextInt(16) + rand.nextInt(5) * (random.nextBoolean() ? -1 : 1),
	 * 0, chunkZ*16 + random.nextInt(16) rand.nextInt(5) * (random.nextBoolean() ? -1 : 1))<br></i>
	 * is what's used for the Darklands
	 * @param chance Percentage chance of a structure generating (default is 0.03 = 3% chance)
	 */
	void generateDarklandsStructure(int type, World world, Random random, BlockPos pos, float chance);

	/**
	 * Attempts to generate a Darklands Structure.<br>
	 * This should be called during world generation for the result.
	 * @param type Structure type
	 * <ul>
	 * <li>0 = Any structure</li>
	 * <li>1 = Shrine structures</li>
	 * <li>2 = Ritual Ground structures</li>
	 * <li>3 = Buildings</li>
	 * <li>4 = Misc structures</li>
	 * </ul>
	 * @param world Current World
	 * @param random Random instance
	 * @param pos A BlockPos <br>
	 * <i>world.getHeight(new BlockPos(chunkX*16 + random.nextInt(16) + rand.nextInt(5) * (random.nextBoolean() ? -1 : 1),
	 * 0, chunkZ*16 + random.nextInt(16) rand.nextInt(5) * (random.nextBoolean() ? -1 : 1))<br></i>
	 * is what's used for the Darklands
	 * @param chance Percentage chance of a structure generating (default is 0.03 = 3% chance)
	 * @param spawnBlock BlockState that the structures can generate on
	 * @param extra (OPTIONAL) Additional BlockStates the structure can generate on
	 */
	void generateDarklandsStructure(int type, World world, Random random, BlockPos pos, float chance, IBlockState spawnBlock, IBlockState...extra);

	/**
	 * Fires a message to the client on the completion of a ritual<br>
	 * <b><i>You should probably NEVER ever call this method at all, ever.<br>
	 * Seriously, this method is called in NecronomiconRitual to send<br>
	 * the ritual to the client while completing it server-side.</i></b>
	 * @param pos Current position
	 * @param player Player who completed the ritual
	 * @param ritual Ritual name
	 */
	void completeRitualClient(BlockPos pos, EntityPlayer player, String ritual);

	/**
	 * Checks the configurable immunity and carrier lists for whether or not the entity is immune or a plague carrier<br>
	 * (YOU SHOULD NEVER NEED TO CALL THIS, INTERNAL USE ONLY)
	 * @param entity Entity ID String
	 * @param list Which immunity/carrier list to check
	 * @return True whether or not the Entity either is immune or a carrier
	 */
	boolean isImmuneOrCarrier(String entity, int list);

	/**
	 * Performs a ray trace call to find an Entity<br>
	 * CLIENT SIDE ONLY (will always be null server-side)
	 * @param dist Search distance
	 * @return A target Entity, or null
	 */
	RayTraceResult rayTraceTarget(float dist);

	/**
	 * Sends a message to the server to run the logic for the spell<br>
	 * on the Entity associated with the ID
	 * @param id Entity ID
	 * @param spell Spell ID
	 * @param scrollType Scroll Type on the scroll casting the spell
	 */
	void processEntitySpell(int id, String spell, ScrollType scrollType);
}
