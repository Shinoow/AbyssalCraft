/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world.biome;


import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.entity.EntitySacthoth;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;

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
		spawnableMonsterList.add(new SpawnListEntry(EntitySacthoth.class, 1, 0, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return 0x30217A;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return 0x30217A;
	}
}
