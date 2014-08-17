/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.world.biome;

import net.minecraft.world.biome.BiomeGenBase;

import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadgolem;

public class BiomeGenMountainDreadlands extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenMountainDreadlands(int par1) {
		super(par1);
		rootHeight = 1.3F;
		heightVariation = 0.9F;
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		theBiomeDecorator.treesPerChunk = -1;
		theBiomeDecorator.flowersPerChunk= -1;
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadgolem.class, 2, 1, 2));
		spawnableCreatureList.add(new SpawnListEntry(EntityAbygolem.class, 2, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadling.class, 2, 1, 2));
	}

	//	@Override
	//	public void decorate(World par1World, Random par2Random, int par3, int par4)
	//	{
	//		super.decorate(par1World, par2Random, par3, par4);
	//		int var5 = 3 + par2Random.nextInt(6);
	//		int var6;
	//		int var7;
	//		int var8;
	//
	//		for (var5 = 0; var5 < 1; ++var5)
	//		{
	//			var6 = par3 + par2Random.nextInt(16);
	//			var7 = par2Random.nextInt(64);
	//			var8 = par4 + par2Random.nextInt(16);
	//			new chagarothlair().generate(par1World, par2Random, var6, var7, var8);
	//		}
	//	}
}