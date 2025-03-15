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
package com.shinoow.abyssalcraft.common.util;

import java.util.Random;

import com.shinoow.abyssalcraft.common.structures.StructureGraveyard;
import com.shinoow.abyssalcraft.common.structures.StructureShoggothPit;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class StructureUtil {

	private StructureShoggothPit shoggothLair = new StructureShoggothPit();
	private StructureGraveyard graveyard = new StructureGraveyard();

	private StructureUtil() {}

	public static final StructureUtil INSTANCE = new StructureUtil();

	/**
	 * Attempt to generate a Shoggoth Lair in the current chunk
	 * @param world World context
	 * @param random Random context
	 * @param chunkX Chunk X coordinate
	 * @param chunkZ Chunk Z coordinate
	 * @param internal Whether to use fixed generation chance instead of config file
	 */
	public void generateShoggothLair(World world, Random random, int chunkX, int chunkZ, boolean internal) {

		int x = chunkX + random.nextInt(16) + 8;
		int z = chunkZ + random.nextInt(2) + 28;
		BlockPos pos1 = world.getHeight(new BlockPos(x, 0, z));

		if(world.getBlockState(pos1).getMaterial() == Material.PLANTS) pos1 = pos1.down();

		boolean flag = false;

		if(internal)
			flag = random.nextInt(200) == 0;
		else { // extra Overworld logic for biomes and whatnot
			boolean swamp = BiomeDictionary.hasType(world.getBiome(pos1), Type.SWAMP);
			if(swamp || BiomeDictionary.hasType(world.getBiome(pos1), Type.RIVER) &&
					!BiomeDictionary.hasType(world.getBiome(pos1), Type.OCEAN))
				if(swamp ? ACConfig.shoggothLairSpawnRate > 0 && random.nextInt(ACConfig.shoggothLairSpawnRate) == 0 :
					ACConfig.shoggothLairSpawnRateRivers > 0 && random.nextInt(ACConfig.shoggothLairSpawnRateRivers) == 0)
					flag = true;
		}

		if(flag && !world.isAirBlock(pos1.north(13)) && !world.isAirBlock(pos1.north(20)) && !world.isAirBlock(pos1.north(27)))
			shoggothLair.generate(world, random, pos1);
	}

	/**
	 * Attempt to generate a Graveyard in the current chunk
	 * @param world World context
	 * @param random Random context
	 * @param chunkX Chunk X coordinate
	 * @param chunkZ Chunk Z coordinate
	 * @param internal Whether to use fixed generation chance instead of config file
	 */
	public void generateGraveyard(World world, Random random, int chunkX, int chunkZ, boolean internal) {

		int size = random.nextInt(3);
		graveyard.setSize(size);
		int randInt = 16;
		int offInt = 8;
		if(size == 2) {
			randInt = 8;
			offInt = 16;
		}

		int x = chunkX + random.nextInt(randInt) + offInt;
		int z = chunkZ + random.nextInt(randInt) + offInt;
		BlockPos pos = world.getHeight(new BlockPos(x, 0, z));

		while(world.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();

		// Overworld et al uses the config option for generation chance, AC dims don't
		boolean flag = internal ? random.nextInt(50) == 0 : ACConfig.graveyardGenerationChance > 0 && random.nextInt(ACConfig.graveyardGenerationChance) == 0;

		IBlockState state = world.getBlockState(pos);
		if(flag && !state.getMaterial().isLiquid() && state.getMaterial() != Material.LEAVES
				&& state.getMaterial() != Material.PLANTS && state.getMaterial() != Material.VINE
				&& state.getMaterial() != Material.CACTUS)
			graveyard.generate(world, random, pos);
	}
}
