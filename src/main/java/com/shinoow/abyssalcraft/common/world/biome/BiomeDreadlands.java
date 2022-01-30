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

import java.util.Random;

import com.shinoow.abyssalcraft.common.entity.EntityDreadgolem;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDreadlandsStalagmite;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeDreadlands extends BiomeDreadlandsBase
{

	public BiomeDreadlands(BiomeProperties par1) {
		super(par1);
	}

	@Override
	public final void setMobSpawns(){
		super.setMobSpawns();
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadgolem.class, 100, 1, 5));
	}

	@Override
	public void decorate(World world, Random rand, BlockPos pos)
	{
		super.decorate(world, rand, pos);

		if(ACConfig.generateDreadlandsStalagmite)
			for(int i = 0; i < 1; i++){
				int xPos = rand.nextInt(16) + 8;
				int zPos = rand.nextInt(16) + 8;
				new WorldGenDreadlandsStalagmite().generate(world, rand, world.getHeight(pos.add(xPos, 0, zPos)));
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getDreadlandsSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDreadlandsGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDreadlandsFoliageColor();
	}
}
