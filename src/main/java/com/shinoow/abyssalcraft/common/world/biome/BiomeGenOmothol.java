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


import net.minecraft.world.biome.BiomeGenBase;

import com.shinoow.abyssalcraft.common.entity.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenOmothol extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenOmothol(int par1){
		super(par1);
		waterColorMultiplier = 14745518;
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityRemnant.class, 70, 2, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityOmotholGhoul.class, 50, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityGatekeeperMinion.class, 30, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowCreature.class, 18, 1, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowMonster.class, 12, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowBeast.class, 3, 1, 1));
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
