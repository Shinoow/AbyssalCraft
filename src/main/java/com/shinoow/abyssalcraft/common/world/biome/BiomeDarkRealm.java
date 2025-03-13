/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world.biome;


import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityShadowGhoul;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.world.biome.IControlledSpawnList;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeDarkRealm extends Biome implements IControlledSpawnList {

	public BiomeDarkRealm(BiomeProperties par1){
		super(par1);
		setMobSpawns();
		topBlock = ACBlocks.darkstone.getDefaultState();
		fillerBlock = ACBlocks.darkstone.getDefaultState();
	}

	@Override
	public void setMobSpawns(){
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowCreature.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowMonster.class, 40, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowBeast.class, 10, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowGhoul.class, 1, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getDarkRealmSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDarkRealmGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDarkRealmFoliageColor();
	}
}
