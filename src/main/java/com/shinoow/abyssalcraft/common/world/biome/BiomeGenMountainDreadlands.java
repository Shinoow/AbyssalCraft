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
package com.shinoow.abyssalcraft.common.world.biome;


import com.shinoow.abyssalcraft.common.entity.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenMountainDreadlands extends BiomeGenDreadlandsBase {

	@SuppressWarnings("unchecked")
	public BiomeGenMountainDreadlands(int par1) {
		super(par1);
		rootHeight = 1.3F;
		heightVariation = 0.9F;
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadling.class, 40, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadguard.class, 15, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityChagarothFist.class, 20, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityChagarothSpawn.class, 30, 1, 2));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int par1, int par2, int par3)
	{
		return 0x910000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int par1, int par2, int par3)
	{
		return 0x910000;
	}
}