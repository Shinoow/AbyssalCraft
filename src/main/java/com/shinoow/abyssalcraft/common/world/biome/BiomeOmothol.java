/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world.biome;


import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.lib.ACClientVars;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeOmothol extends Biome {

	public BiomeOmothol(BiomeProperties par1){
		super(par1);
		setMobSpawns();
	}

	public final void setMobSpawns(){
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityRemnant.class, 3, 2, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityOmotholGhoul.class, 50, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityGatekeeperMinion.class, 3, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowCreature.class, 18, 1, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowMonster.class, 12, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowBeast.class, 3, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getOmotholSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getOmotholGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getOmotholFoliageColor();
	}
}
