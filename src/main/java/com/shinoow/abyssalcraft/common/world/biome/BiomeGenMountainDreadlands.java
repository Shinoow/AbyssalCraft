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

import com.shinoow.abyssalcraft.common.entity.Entityabygolem;
import com.shinoow.abyssalcraft.common.entity.Entitydreadgolem;

public class BiomeGenMountainDreadlands extends BiomeGenBase
{

	@SuppressWarnings("unchecked")
	public BiomeGenMountainDreadlands(int par1) {
		super(par1);
		this.rootHeight = 1.3F;
		this.heightVariation = 0.9F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.theBiomeDecorator.treesPerChunk = -1;
		this.theBiomeDecorator.flowersPerChunk= -1;
		this.spawnableMonsterList.add(new SpawnListEntry(Entitydreadgolem.class, 2, 1, 2));
		this.spawnableCreatureList.add(new SpawnListEntry(Entityabygolem.class, 2, 1, 2));
	}

}
