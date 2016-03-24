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
package com.shinoow.abyssalcraft.common.world;

import java.util.Random;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.structures.StructureShoggothPit;
import com.shinoow.abyssalcraft.common.structures.overworld.AChouse1;
import com.shinoow.abyssalcraft.common.structures.overworld.AChouse2;
import com.shinoow.abyssalcraft.common.structures.overworld.ACplatform1;
import com.shinoow.abyssalcraft.common.structures.overworld.ACplatform2;
import com.shinoow.abyssalcraft.common.structures.overworld.ACscion1;
import com.shinoow.abyssalcraft.common.structures.overworld.ACscion2;

public class AbyssalCraftWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator
			chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()) {
		case -1:
			generateNether(world, random, chunkX*16, chunkZ*16);
		case 0:
			generateSurface(world, random, chunkX*16, chunkZ*16);
		case 1:
			generateEnd(world, random, chunkX*16, chunkZ*16);
		}
	}

	private void generateEnd(World world, Random random, int chunkX, int chunkZ) {}

	private void generateNether(World world, Random random, int chunkX, int chunkZ) {}

	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {

		if(AbyssalCraft.generateDarklandsStructures){
			for(int k = 0; k < 2; k++) {
				int RandPosX = chunkX + random.nextInt(5);
				int RandPosY = random.nextInt(75);
				int RandPosZ = chunkZ + random.nextInt(5);
				new AChouse1().generate(world, random, new BlockPos(RandPosX, RandPosY, RandPosZ));
			}

			for(int k = 0; k < 2; k++) {
				int RandPosX = chunkX + random.nextInt(5);
				int RandPosY = random.nextInt(75);
				int RandPosZ = chunkZ + random.nextInt(5);
				new AChouse2().generate(world, random, new BlockPos(RandPosX, RandPosY, RandPosZ));
			}

			for(int k = 0; k < 2; k++) {
				int RandPosX = chunkX + random.nextInt(5);
				int RandPosY = random.nextInt(75);
				int RandPosZ = chunkZ + random.nextInt(5);
				new ACplatform1().generate(world, random, new BlockPos(RandPosX, RandPosY, RandPosZ));
			}

			for(int k = 0; k < 2; k++) {
				int RandPosX = chunkX + random.nextInt(5);
				int RandPosY = random.nextInt(75);
				int RandPosZ = chunkZ + random.nextInt(5);
				new ACplatform2().generate(world, random, new BlockPos(RandPosX, RandPosY, RandPosZ));
			}

			for(int k = 0; k < 2; k++) {
				int RandPosX = chunkX + random.nextInt(5);
				int RandPosY = random.nextInt(75);
				int RandPosZ = chunkZ + random.nextInt(5);
				new ACscion1().generate(world, random, new BlockPos(RandPosX, RandPosY, RandPosZ));
			}

			for(int k = 0; k < 2; k++) {
				int RandPosX = chunkX + random.nextInt(5);
				int RandPosY = random.nextInt(75);
				int RandPosZ = chunkZ + random.nextInt(5);
				new ACscion2().generate(world, random, new BlockPos(RandPosX, RandPosY, RandPosZ));
			}
		}

		if(AbyssalCraft.generateCoraliumOre){
			for(int rarity = 0; rarity < 3; rarity++) {
				int veinSize = 2 + random.nextInt(2);
				int x = chunkX + random.nextInt(16);
				int y = random.nextInt(40);
				int z = chunkZ + random.nextInt(16);
				if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(new BlockPos(x, 0, z)), Type.SWAMP))
					new WorldGenMinable(AbyssalCraft.Coraliumore.getDefaultState(), veinSize).generate(world, random, new BlockPos(x, y, z));
			}

			for(int rarity = 0; rarity < 6; rarity++) {
				int veinSize = 4 + random.nextInt(2);
				int x = chunkX + random.nextInt(16);
				int y = random.nextInt(40);
				int z = chunkZ + random.nextInt(16);
				if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(new BlockPos(x, 0, z)), Type.OCEAN) &&
						world.getBiomeGenForCoords(new BlockPos(x, 0, z))!=Biomes.deepOcean)
					new WorldGenMinable(AbyssalCraft.Coraliumore.getDefaultState(), veinSize).generate(world, random, new BlockPos(x, y, z));
				if(world.getBiomeGenForCoords(new BlockPos(x, 0, z))==Biomes.deepOcean)
					new WorldGenMinable(AbyssalCraft.Coraliumore.getDefaultState(), veinSize).generate(world, random, new BlockPos(x, y-20, z));
			}
		}

		if(AbyssalCraft.generateNitreOre)
			for(int rarity = 0; rarity < 3; rarity++) {
				int veinSize = 4 + random.nextInt(2);
				int x = chunkX + random.nextInt(16);
				int y = random.nextInt(30);
				int z = chunkZ + random.nextInt(16);

				new WorldGenMinable(AbyssalCraft.nitreOre.getDefaultState(), veinSize).generate(world, random, new BlockPos(x, y, z));
			}

		if(AbyssalCraft.generateShoggothLairs)
			for(int i = 0; i < 1; i++){
				int x = chunkX + random.nextInt(16);
				int z = chunkZ + random.nextInt(16);
				if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(new BlockPos(x, 0, z)), Type.SWAMP) ||
						BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(new BlockPos(x, 0, z)), Type.RIVER) &&
						!BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(new BlockPos(x, 0, z)), Type.OCEAN))
					if(random.nextInt(100) == 0)
						new StructureShoggothPit().generate(world, random, world.getHeight(new BlockPos(x, 0, z)));
			}
	}
}