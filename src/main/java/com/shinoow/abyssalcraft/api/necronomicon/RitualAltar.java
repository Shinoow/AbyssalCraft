/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.necronomicon;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.block.ACBlocks;

/**
 * Utility for creating Necronomicon Ritual Altars
 * @author shinoow
 *
 * @since 1.3.5
 */
public class RitualAltar {

	private static Map<Block, Integer> ritualBlocks = Maps.newHashMap();
	private static Map<Block, Integer> altarMeta = Maps.newHashMap();

	public static void addBlocks(){
		ritualBlocks.put(Blocks.cobblestone, 0);
		ritualBlocks.put(ACBlocks.darkstone_cobblestone, 0);
		ritualBlocks.put(ACBlocks.abyssal_stone_brick, 1);
		ritualBlocks.put(ACBlocks.coralium_stone_brick, 1);
		ritualBlocks.put(ACBlocks.dreadstone_brick, 2);
		ritualBlocks.put(ACBlocks.abyssalnite_stone_brick, 2);
		ritualBlocks.put(ACBlocks.ethaxium_brick, 3);
		ritualBlocks.put(ACBlocks.dark_ethaxium_brick, 3);

		altarMeta.put(Blocks.cobblestone, 0);
		altarMeta.put(ACBlocks.darkstone_cobblestone, 1);
		altarMeta.put(ACBlocks.abyssal_stone_brick, 2);
		altarMeta.put(ACBlocks.coralium_stone_brick, 3);
		altarMeta.put(ACBlocks.dreadstone_brick, 4);
		altarMeta.put(ACBlocks.abyssalnite_stone_brick, 5);
		altarMeta.put(ACBlocks.ethaxium_brick, 6);
		altarMeta.put(ACBlocks.dark_ethaxium_brick, 7);
	}

	/**
	 * Checks if an altar can
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param bookType
	 * @return
	 */
	public static boolean tryAltar(World world, int x, int y, int z, int bookType){
		Block ritualBlock = world.getBlock(x, y, z);
		if(ritualBlock != null && ritualBlocks.containsKey(ritualBlock))
			if(bookType >= ritualBlocks.get(ritualBlock))
				if(world.getBlock(x - 3, y, z) == ritualBlock &&
				world.getBlock(x, y, z - 3) == ritualBlock &&
				world.getBlock(x + 3, y, z) == ritualBlock &&
				world.getBlock(x, y, z + 3) == ritualBlock &&
				world.getBlock(x - 2, y, z + 2) == ritualBlock &&
				world.getBlock(x - 2, y, z - 2) == ritualBlock &&
				world.getBlock(x + 2, y, z + 2) == ritualBlock &&
				world.getBlock(x + 2, y, z - 2) == ritualBlock){
					createAltar(world, x, y, z, ritualBlock);
					return true;
				}
		return false;
	}

	private static void createAltar(World world, int x, int y, int z, Block block){
		if(altarMeta.containsKey(block)){
			int meta = altarMeta.get(block);

			world.setBlock(x, y, z, ACBlocks.ritual_altar, meta, 2);
			world.setBlock(x - 3, y, z, ACBlocks.ritual_pedestal, meta, 2);
			world.setBlock(x, y, z - 3, ACBlocks.ritual_pedestal, meta, 2);
			world.setBlock(x + 3, y, z, ACBlocks.ritual_pedestal, meta, 2);
			world.setBlock(x, y, z + 3, ACBlocks.ritual_pedestal, meta, 2);
			world.setBlock(x - 2, y, z + 2, ACBlocks.ritual_pedestal, meta, 2);
			world.setBlock(x - 2, y, z - 2, ACBlocks.ritual_pedestal, meta, 2);
			world.setBlock(x + 2, y, z + 2, ACBlocks.ritual_pedestal, meta, 2);
			world.setBlock(x + 2, y, z - 2, ACBlocks.ritual_pedestal, meta, 2);
		}
	}
}
