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
package com.shinoow.abyssalcraft.common.util;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;

public class RitualUtil {

	private static Map<Block, Integer> ritualBlocks = Maps.newHashMap();
	private static Map<Block, Integer> altarMeta = Maps.newHashMap();

	public static void addBlocks(){
		ritualBlocks.put(Blocks.cobblestone, 0);
		ritualBlocks.put(AbyssalCraft.Darkstone_cobble, 0);
		ritualBlocks.put(AbyssalCraft.abybrick, 1);
		ritualBlocks.put(AbyssalCraft.cstonebrick, 1);
		ritualBlocks.put(AbyssalCraft.dreadbrick, 2);
		ritualBlocks.put(AbyssalCraft.abydreadbrick, 2);
		ritualBlocks.put(AbyssalCraft.ethaxiumbrick, 3);
		ritualBlocks.put(AbyssalCraft.darkethaxiumbrick, 3);

		altarMeta.put(Blocks.cobblestone, 0);
		altarMeta.put(AbyssalCraft.Darkstone_cobble, 1);
		altarMeta.put(AbyssalCraft.abybrick, 2);
		altarMeta.put(AbyssalCraft.cstonebrick, 3);
		altarMeta.put(AbyssalCraft.dreadbrick, 4);
		altarMeta.put(AbyssalCraft.abydreadbrick, 5);
		altarMeta.put(AbyssalCraft.ethaxiumbrick, 6);
		altarMeta.put(AbyssalCraft.darkethaxiumbrick, 7);
	}

	/**
	 * Checks if an altar can be created
	 * @param world World object
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 * @param bookType Level of the current Necronomicon held
	 * @return True if a Ritual Altar can be constructed, otherwise false
	 */
	public static boolean tryAltar(World world, BlockPos pos, int bookType){
		IBlockState ritualBlock = world.getBlockState(pos);
		int x = 0;
		int y = 0;
		int z = 0;
		if(ritualBlock != null && ritualBlocks.containsKey(ritualBlock.getBlock()))
			if(bookType >= ritualBlocks.get(ritualBlock.getBlock()))
				if(world.getBlockState(pos.add(x -3, y, z)) == ritualBlock &&
				world.getBlockState(pos.add(x, y, z -3)) == ritualBlock &&
				world.getBlockState(pos.add(x + 3, y, z)) == ritualBlock &&
				world.getBlockState(pos.add(x, y, z + 3)) == ritualBlock &&
				world.getBlockState(pos.add(x -2, y,z + 2)) == ritualBlock &&
				world.getBlockState(pos.add(x -2, y, z -2)) == ritualBlock &&
				world.getBlockState(pos.add(x + 2, y, z + 2)) == ritualBlock &&
				world.getBlockState(pos.add(x + 2, y, z -2)) == ritualBlock)
					if(world.isAirBlock(pos.add(x -3, y, z -1)) && world.isAirBlock(pos.add(x -3, y, z + 1)) &&
							world.isAirBlock(pos.add(x -4, y, z)) && world.isAirBlock(pos.add(x -4, y, z -1)) &&
							world.isAirBlock(pos.add(x -4, y, z + 1)) && world.isAirBlock(pos.add(x -3, y, z -2)) &&
							world.isAirBlock(pos.add(x -3, y, z -3)) && world.isAirBlock(pos.add(x -2, y, z -3)) &&
							world.isAirBlock(pos.add(x -1, y, z -3)) && world.isAirBlock(pos.add(x -1, y, z -4)) &&
							world.isAirBlock(pos.add(x, y, z -4)) && world.isAirBlock(pos.add(x + 1, y, z -4)) &&
							world.isAirBlock(pos.add(x + 1, y, z -3)) && world.isAirBlock(pos.add(x + 2, y, z -3)) &&
							world.isAirBlock(pos.add(x + 3, y, z -3)) && world.isAirBlock(pos.add(x + 3, y, z -2)) &&
							world.isAirBlock(pos.add(x + 3, y, z -1)) && world.isAirBlock(pos.add(x + 4, y, z -1)) &&
							world.isAirBlock(pos.add(x + 4, y, z)) && world.isAirBlock(pos.add(x + 4, y, z + 1)) &&
							world.isAirBlock(pos.add(x + 3, y, z + 1)) && world.isAirBlock(pos.add(x + 3, y, z + 2)) &&
							world.isAirBlock(pos.add(x + 3, y, z + 3)) && world.isAirBlock(pos.add(x + 2, y, z + 3)) &&
							world.isAirBlock(pos.add(x + 1, y, z + 3)) && world.isAirBlock(pos.add(x + 1, y, z + 4)) &&
							world.isAirBlock(pos.add(x, y, z + 4)) && world.isAirBlock(pos.add(x -1, y, z + 4)) &&
							world.isAirBlock(pos.add(x -1, y, z + 3)) && world.isAirBlock(pos.add(x -2, y, z + 3)) &&
							world.isAirBlock(pos.add(x-3, y, z + 3)) && world.isAirBlock(pos.add(x -3, y, z + 2)) &&
							world.isAirBlock(pos.add(x-1, y, z + 0)) && world.isAirBlock(pos.add(x + 1, y, z)) &&
							world.isAirBlock(pos.add(x, y, z -1)) && world.isAirBlock(pos.add(x, y, z + 1)) &&
							world.isAirBlock(pos.add(x-1, y, z + 1)) && world.isAirBlock(pos.add(x -2, y, z)) &&
							world.isAirBlock(pos.add(x-2, y, z)) && world.isAirBlock(pos.add(x -2, y, z -1)) &&
							world.isAirBlock(pos.add(x-1, y, z -1)) && world.isAirBlock(pos.add(x -1, y, z -2)) &&
							world.isAirBlock(pos.add(x, y, z -2)) && world.isAirBlock(pos.add(x + 1, y, z -2)) &&
							world.isAirBlock(pos.add(x + 1, y, z -1)) && world.isAirBlock(pos.add(x + 2, y, z -1)) &&
							world.isAirBlock(pos.add(x + 2, y, z)) && world.isAirBlock(pos.add(x + 2, y, z + 1)) &&
							world.isAirBlock(pos.add(x + 1, y, z + 1)) && world.isAirBlock(pos.add(x + 1, y, z + 2)) &&
							world.isAirBlock(pos.add(x, y, z + 2)) && world.isAirBlock(pos.add(x -1, y, z + 2)))
						if(RitualRegistry.instance().sameBookType(world.provider.getDimension(), ritualBlocks.get(ritualBlock.getBlock()))){
							createAltar(world, pos, ritualBlock.getBlock());
							return true;
						}
		return false;
	}

	/**
	 * Creates the altar
	 * @param world World object
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 * @param block Ritual Block
	 */
	private static void createAltar(World world, BlockPos pos, Block block){
		if(altarMeta.containsKey(block)){
			int meta = altarMeta.get(block);
			int x = 0;
			int y = 0;
			int z = 0;

			world.setBlockState(pos, AbyssalCraft.ritualaltar.getStateFromMeta(meta), 2);
			world.setBlockState(pos.add(x -3, y, z), AbyssalCraft.ritualpedestal.getStateFromMeta(meta), 2);
			world.setBlockState(pos.add(x, y, z -3), AbyssalCraft.ritualpedestal.getStateFromMeta(meta), 2);
			world.setBlockState(pos.add(x + 3, y, z), AbyssalCraft.ritualpedestal.getStateFromMeta(meta), 2);
			world.setBlockState(pos.add(x, y, z + 3), AbyssalCraft.ritualpedestal.getStateFromMeta(meta), 2);
			world.setBlockState(pos.add(x -2, y, z + 2), AbyssalCraft.ritualpedestal.getStateFromMeta(meta), 2);
			world.setBlockState(pos.add(x -2, y, z -2), AbyssalCraft.ritualpedestal.getStateFromMeta(meta), 2);
			world.setBlockState(pos.add(x + 2, y, z + 2), AbyssalCraft.ritualpedestal.getStateFromMeta(meta), 2);
			world.setBlockState(pos.add(x + 2, y, z -2), AbyssalCraft.ritualpedestal.getStateFromMeta(meta), 2);
		}
	}
}
