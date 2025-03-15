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

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.util.StructureUtil;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class AbyssalCraftWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator
			chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider instanceof WorldProviderSurface && world.getWorldType() != WorldType.FLAT)
			generateSurface(world, random, chunkX*16, chunkZ*16);
	}

	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {

		boolean blacklisted = InitHandler.INSTANCE.isDimBlacklistedFromStructureGen(world.provider.getDimension());
		if(ACConfig.generateDarklandsStructures && !blacklisted)
			DarklandsStructureGenerator.generateStructures(world, random, chunkX, chunkZ, 0.03F);

		if(!InitHandler.INSTANCE.isDimBlacklistedFromOreGen(world.provider.getDimension())) {
			if(ACConfig.generateCoraliumOre){
				for(int rarity = 0; rarity < InitHandler.coraliumOreGeneration[0]/2; rarity++) {
					int veinSize = InitHandler.coraliumOreGeneration[1];
					int x = chunkX + random.nextInt(16);
					int y = random.nextInt(InitHandler.coraliumOreGeneration[2]);
					int z = chunkZ + random.nextInt(16);
					if(BiomeDictionary.hasType(world.getBiome(new BlockPos(x, 0, z)), Type.SWAMP))
						new WorldGenMinable(ACBlocks.coralium_ore.getDefaultState(), veinSize).generate(world, random, new BlockPos(x, y, z));
				}

				for(int rarity = 0; rarity < InitHandler.coraliumOreGeneration[0]; rarity++) {
					int veinSize = InitHandler.coraliumOreGeneration[1];
					int x = chunkX + random.nextInt(16);
					int y = random.nextInt(InitHandler.coraliumOreGeneration[2]);
					int z = chunkZ + random.nextInt(16);
					if(BiomeDictionary.hasType(world.getBiome(new BlockPos(x, 0, z)), Type.OCEAN) &&
							world.getBiome(new BlockPos(x, 0, z))!=Biomes.DEEP_OCEAN)
						new WorldGenMinable(ACBlocks.coralium_ore.getDefaultState(), veinSize).generate(world, random, new BlockPos(x, y, z));
					if(world.getBiome(new BlockPos(x, 0, z))==Biomes.DEEP_OCEAN)
						new WorldGenMinable(ACBlocks.coralium_ore.getDefaultState(), veinSize).generate(world, random, new BlockPos(x, y-20, z));
				}
			}

			if(ACConfig.generateNitreOre)
				for(int rarity = 0; rarity < 3; rarity++) {
					int veinSize = 4 + random.nextInt(2);
					int x = chunkX + random.nextInt(16);
					int y = random.nextInt(30);
					int z = chunkZ + random.nextInt(16);

					new WorldGenMinable(ACBlocks.nitre_ore.getDefaultState(), veinSize).generate(world, random, new BlockPos(x, y, z));
				}
		}

		if(ACConfig.generateShoggothLairs && !blacklisted)
			StructureUtil.INSTANCE.generateShoggothLair(world, random, chunkX, chunkZ, false);

		if(ACConfig.generateGraveyards && !blacklisted)
			StructureUtil.INSTANCE.generateGraveyard(world, random, chunkX, chunkZ, false);
	}
}
