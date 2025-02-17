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
package com.shinoow.abyssalcraft.api.energy.disruption;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.event.ACEvents.DisruptionEvent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * Handler for disruptions (when something bad happens during Potential Energy manipulation)
 * @author shinoow
 *
 * @since 1.5
 */
public class DisruptionHandler {

	private final List<DisruptionEntry> disruptions = new ArrayList<>();

	private final Logger logger = LogManager.getLogger("DisruptionHandler");

	private static final DisruptionHandler instance = new DisruptionHandler();

	public static DisruptionHandler instance(){
		return instance;
	}

	private DisruptionHandler(){}

	/**
	 * Registers a Disruption Entry
	 * @param disruption The Disruption
	 *
	 * @since 1.5
	 */
	public void registerDisruption(DisruptionEntry disruption){
		for(DisruptionEntry entry : disruptions)
			if(disruption.getUnlocalizedName().equals(entry.getUnlocalizedName())){
				logger.log(Level.ERROR, "Disruption Entry already registered: {}", disruption.getUnlocalizedName());
				return;
			}
		disruptions.add(disruption);
	}

	/**
	 * Used to fetch a list of disruptions
	 * @return An ArrayList containing all registered Disruptions
	 *
	 * @since 1.5
	 */
	public List<DisruptionEntry> getDisruptions(){
		return disruptions;
	}

	/**
	 * Fetches a Disruption based on it's unlocalized name
	 * @param name A unlocalized name (the "ac.disruption." prefix will be stripped if present)
	 * @return A disruption with that unlocalized name, or null if none were found
	 *
	 * @since 1.8.2
	 */
	public DisruptionEntry disruptionFromName(String name){
		if(name.startsWith("ac.disruption."))
			name.substring("ac.disruption.".length());
		for(DisruptionEntry dis : disruptions)
			if(dis.getUnlocalizedName().substring("ac.disruption.".length()).equals(name))
				return dis;
		return null;
	}

	/**
	 * Generates a Disruption
	 * @param deity Deity tied to the manipulator
	 * @param world Current World
	 * @param pos Current BlockPos
	 * @param players Nearby players (16 block radius or larger)
	 *
	 * @since 1.5
	 */
	public void generateDisruption(DeityType deity, World world, BlockPos pos, List<EntityPlayer> players){

		DisruptionEntry disruption = getRandomDisruption(deity, world);

		if(disruption != null) {
			if(!MinecraftForge.EVENT_BUS.post(new DisruptionEvent(deity, world, pos, players, disruption)))
				disruption.disrupt(world, pos, players);
			AbyssalCraftAPI.getInternalMethodHandler().sendDisruption(deity, disruption.getUnlocalizedName().substring("ac.disruption.".length()), pos, world.provider.getDimension());
		}
	}

	/**
	 * Fetches a random Disruption
	 * @param deity Deity Type
	 * @param world Current World
	 * @return A random Disruption, or null if called client-side
	 *
	 * @since 1.17.0
	 */
	public DisruptionEntry getRandomDisruption(DeityType deity, World world) {
		if(world.isRemote) return null;
		List<DisruptionEntry> dis = new ArrayList<>();

		if(deity == null){
			for(DisruptionEntry entry : disruptions)
				if(entry.getDeity() == null)
					dis.add(entry);
		} else
			for(DisruptionEntry entry : disruptions)
				if(entry.getDeity() == deity || entry.getDeity() == null)
					dis.add(entry);

		return dis.get(world.rand.nextInt(dis.size()));
	}
}
