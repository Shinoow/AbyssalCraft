/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world.biome;


import com.shinoow.abyssalcraft.common.entity.EntityChagarothFist;
import com.shinoow.abyssalcraft.common.entity.EntityChagarothSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenMountainDreadlands extends BiomeGenDreadlandsBase {

	@SuppressWarnings("unchecked")
	public BiomeGenMountainDreadlands(int par1) {
		super(par1);
		rootHeight = 1.3F;
		heightVariation = 0.9F;
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadling.class, 40, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadguard.class, 20, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityChagarothFist.class, 25, 1, 2));
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
