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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDrT;

public class BiomeGenForestDreadlands extends BiomeGenBase
{

	private WorldGenTrees WorldGenDreadTrees;

	public BiomeGenForestDreadlands(int par1) {
		super(par1);
		this.topBlock = (Block)AbyssalCraft.dreadgrass;
		this.fillerBlock = (Block)AbyssalCraft.dreadstone;
		this.WorldGenDreadTrees = new WorldGenDrT(false);
		this.theBiomeDecorator.treesPerChunk = 20;
		this.theBiomeDecorator.flowersPerChunk= -1;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();

	}

	public WorldGenAbstractTree func_150567_a(Random par1Random)
	{
		return (par1Random.nextInt(5) == 0 ? this.worldGeneratorTrees : (par1Random.nextInt(10) == 0 ? this.WorldGenDreadTrees : this.worldGeneratorTrees));
	}

}
