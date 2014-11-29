/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.structures.overworld.*;

import cpw.mods.fml.common.IWorldGenerator;

public class AbyssalCraftWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider
			chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId) {
		case -1:
			generateNether(world, random, chunkX*16, chunkZ*16);
		case 0:
			generateSurface(world, random, chunkX*16, chunkZ*16);
		case 1:
			generateEnd(world, random, chunkX*16, chunkZ*16);
		case 50:
			generateAbyss(world, random, chunkX*16, chunkZ*16);
		case 51:
			generateDreadlands(world, random, chunkX*16, chunkZ*16);
		}
	}

	private void generateEnd(World world, Random random, int chunkX, int chunkZ) {}

	private void generateNether(World world, Random random, int chunkX, int chunkZ) {}

	private void generateAbyss(World world, Random random, int chunkX, int chunkZ) {}

	private void generateDreadlands(World world, Random random, int chunkX, int chunkZ) {}

	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {

		for(int k = 0; k < 2; k++) {
			int RandPosX = chunkX + random.nextInt(5);
			int RandPosY = random.nextInt(75);
			int RandPosZ = chunkZ + random.nextInt(5);
			new AChouse1().generate(world, random, RandPosX, RandPosY, RandPosZ);
		}

		for(int k = 0; k < 2; k++) {
			int RandPosX = chunkX + random.nextInt(5);
			int RandPosY = random.nextInt(75);
			int RandPosZ = chunkZ + random.nextInt(5);
			new AChouse2().generate(world, random, RandPosX, RandPosY, RandPosZ);
		}

		for(int k = 0; k < 2; k++) {
			int RandPosX = chunkX + random.nextInt(5);
			int RandPosY = random.nextInt(75);
			int RandPosZ = chunkZ + random.nextInt(5);
			new ACplatform1().generate(world, random, RandPosX, RandPosY, RandPosZ);
		}

		for(int k = 0; k < 2; k++) {
			int RandPosX = chunkX + random.nextInt(5);
			int RandPosY = random.nextInt(75);
			int RandPosZ = chunkZ + random.nextInt(5);
			new ACplatform2().generate(world, random, RandPosX, RandPosY, RandPosZ);
		}

		for(int k = 0; k < 2; k++) {
			int RandPosX = chunkX + random.nextInt(5);
			int RandPosY = random.nextInt(75);
			int RandPosZ = chunkZ + random.nextInt(5);
			new ACscion1().generate(world, random, RandPosX, RandPosY, RandPosZ);
		}

		for(int k = 0; k < 2; k++) {
			int RandPosX = chunkX + random.nextInt(5);
			int RandPosY = random.nextInt(75);
			int RandPosZ = chunkZ + random.nextInt(5);
			new ACscion2().generate(world, random, RandPosX, RandPosY, RandPosZ);
		}

		for(int rarity = 0; rarity < 3; rarity++) {
			int veinSize = 4;
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(40);
			int z = chunkZ + random.nextInt(16);
			if(world.getBiomeGenForCoords(chunkX, chunkZ)==BiomeGenBase.ocean)
				new WorldGenMinable(AbyssalCraft.Coraliumore, veinSize).generate(world, random, x, y, z);
			if(world.getBiomeGenForCoords(chunkX, chunkZ)==BiomeGenBase.deepOcean)
				new WorldGenMinable(AbyssalCraft.Coraliumore, veinSize).generate(world, random, x, y-20, z);
		}

		for(int rarity = 0; rarity < 3; rarity++) {
			int veinSize = 4;
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(30);
			int z = chunkZ + random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.nitreOre, veinSize).generate(world, random, x, y, z);
		}
	}
}