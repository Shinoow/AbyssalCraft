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
package com.shinoow.abyssalcraft.common.world;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.structures.overworld.*;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

public class DarklandsStructureGenerator {

	static List<WorldGenerator> structures = Arrays.asList(new AChouse1(), new AChouse2(), new ACscion1(), new ACscion2(),
			new StructureRitualGrounds(), new StructureCircularShrine(), new StructureCircularShrineColumns(), new StructureRitualGroundsColumns(),
			new StructureElevatedShrine(), new StructureElevatedShrineLarge(), new StructureDarkShrine());
	static List<WorldGenerator> shrines = Arrays.asList(new StructureCircularShrine(), new StructureCircularShrineColumns(),
			new StructureElevatedShrine(), new StructureElevatedShrineLarge(), new StructureDarkShrine());
	static List<WorldGenerator> ritual_grounds = Arrays.asList(new StructureRitualGrounds(), new StructureRitualGroundsColumns());
	static List<WorldGenerator> houses = Arrays.asList(new AChouse1(), new AChouse2());
	static List<WorldGenerator> misc = Arrays.asList(new ACscion1(), new ACscion2());
	private static List<Biome> shrine_biomes = Lists.newArrayList(
			Biomes.PLAINS, Biomes.FOREST, Biomes.SWAMPLAND,
			Biomes.DESERT, Biomes.ICE_MOUNTAINS, Biomes.ICE_PLAINS,
			Biomes.ROOFED_FOREST, Biomes.BIRCH_FOREST, Biomes.TAIGA);
	private static StructureDarkShrine dark_shrine = new StructureDarkShrine();

	/**
	 * Executes the base generation logic, with random offsets and all
	 * @param world Current World
	 * @param random Random instance
	 * @param chunkX Chunk X
	 * @param chunkZ Chunk Z
	 * @param chance Generation chance
	 */
	public static void generateStructures(World world, Random random, int chunkX, int chunkZ, float chance) {
		Biome biome = world.getBiome(new BlockPos(chunkX, 0, chunkZ));
		if(biome instanceof IDarklandsBiome || biome == ACBiomes.dark_realm) {

			IBlockState baseState = biome == ACBiomes.dark_realm ? ACBlocks.darkstone.getDefaultState() : Blocks.GRASS.getDefaultState();

			if(world.provider.getDimension() == ACLib.dreadlands_id)
				baseState = ACBlocks.dreadlands_grass.getDefaultState();

			int x = chunkX + random.nextInt(16) + 8;
			int z = chunkZ + random.nextInt(16) + 8;
			generate(1, world, random, world.getHeight(new BlockPos(x, 0, z)), chance, baseState);

			x = chunkX + random.nextInt(16) + 8;
			z = chunkZ + random.nextInt(16) + 8;
			generate(2, world, random, world.getHeight(new BlockPos(x, 0, z)), chance, baseState);

			x = chunkX + random.nextInt(16) + 8;
			z = chunkZ + random.nextInt(16) + 8;
			generate(3, world, random, world.getHeight(new BlockPos(x, 0, z)), chance, baseState);

			x = chunkX + random.nextInt(16) + 8;
			z = chunkZ + random.nextInt(16) + 8;
			generate(4, world, random, world.getHeight(new BlockPos(x, 0, z)), chance,
					Blocks.GRASS.getDefaultState(), ACBlocks.darkstone.getDefaultState());

			x = chunkX + random.nextInt(16) + 8;
			z = chunkZ + random.nextInt(16) + 8;
			generate(0, world, random, world.getHeight(new BlockPos(x, 0, z)), chance, baseState);
		}
		else if(shrine_biomes.contains(biome)) {
			IBlockState[] blocks = {Blocks.SAND.getDefaultState(), Blocks.STONE.getDefaultState()};
			boolean generated = false;
			int x = chunkX + random.nextInt(16) + 8;
			int z = chunkZ + random.nextInt(16) + 8;
			if(random.nextInt(ACConfig.darkShrineSpawnRate) == 0)
				if(generate(1, world, random, world.getHeight(new BlockPos(x, 0, z)),
						chance, Blocks.GRASS.getDefaultState(), blocks))
					generated = true;

			x = chunkX + random.nextInt(16) + 8;
			z = chunkZ + random.nextInt(16) + 8;
			if(random.nextInt(ACConfig.darkRitualGroundsSpawnRate) == 0)
				if(generate(2, world, random, world.getHeight(new BlockPos(x, 0, z)),
						chance, Blocks.GRASS.getDefaultState(), blocks))
					generated = true;

			if(!generated && random.nextInt(ACConfig.darkShrineSpawnRate) == 0) {

				x = chunkX + random.nextInt(16) + 8;
				z = chunkZ + random.nextInt(16) + 8;
				BlockPos pos = world.getHeight(new BlockPos(x, 0, z));

				while(world.isAirBlock(pos) && pos.getY() > 2)
					pos = pos.down();
				if(!checkBlocks(world, pos, 3, Blocks.GRASS.getDefaultState(), blocks)) return;

				if(random.nextFloat() < chance)
					dark_shrine.generate(world, random, pos);
			}
		}
	}

	public static boolean generate(int type, World world, Random random, BlockPos pos, float chance){
		return generate(type, world, random, pos, chance, Blocks.GRASS.getDefaultState(), (IBlockState[])null);
	}

	public static boolean generate(int type, World world, Random random, BlockPos pos, float chance, IBlockState spawnBlock, IBlockState...extra){
		switch(type){
		case 0:
			return generateRandomStructure(structures, world, random, pos, 3, chance, spawnBlock, extra);
		case 1:
			return generateRandomStructure(shrines, world, random, pos, 3, chance, spawnBlock, extra);
		case 2:
			return generateRandomStructure(ritual_grounds, world, random, pos, 3, chance, spawnBlock, extra);
		case 3:
			return generateRandomStructure(houses, world, random, pos, 5, chance, spawnBlock, extra);
		case 4:
			return generateRandomStructure(misc, world, random, pos, 3, chance, spawnBlock, extra);
		default:
			return generateRandomStructure(structures, world, random, pos, 3, chance, spawnBlock, extra);
		}
	}

	private static boolean generateRandomStructure(List<WorldGenerator> structures, World world, Random random, BlockPos pos, int bounds, float chance, IBlockState spawnBlock, IBlockState...extra){

		WorldGenerator structure = structures.get(random.nextInt(structures.size()));

		while(world.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();
		if(!checkBlocks(world, pos, bounds, spawnBlock, extra)) return false;

		if(random.nextFloat() < chance)
			return structure.generate(world, random, pos);

		return false;
	}

	private static boolean checkBlocks(World world, BlockPos pos, int bounds, IBlockState spawnBlock, IBlockState...extra){
		if(spawnBlock == null) return true;
		if(extra != null)
			for(IBlockState state : extra)
				if(checkBlocks(world, pos, bounds, state))
					return true;

		return checkBlocks(world, pos, bounds, spawnBlock);
	}

	private static boolean checkBlocks(World world, BlockPos pos, int bounds, IBlockState state){
		boolean b = world.getBlockState(pos) != state;
		boolean b1 = world.getBlockState(pos.north(bounds)) != state;
		boolean b2 = world.getBlockState(pos.south(bounds)) != state;
		boolean b3 = world.getBlockState(pos.west(bounds)) != state;
		boolean b4 = world.getBlockState(pos.east(bounds)) != state;
		return b || b1 || b2 || b3 || b4 ? false : true;
	}
}
