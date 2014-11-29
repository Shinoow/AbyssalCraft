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


import net.minecraft.world.biome.BiomeGenBase;

import com.shinoow.abyssalcraft.common.entity.*;

import cpw.mods.fml.relauncher.*;

public class BiomeGenDarkRealm extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenDarkRealm(int par1){
		super(par1);
		waterColorMultiplier = 14745518;
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowCreature.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowMonster.class, 40, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowBeast.class, 10, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int par1, int par2, int par3)
	{
		return 0x30217A;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int par1, int par2, int par3)
	{
		return 0x30217A;
	}
}