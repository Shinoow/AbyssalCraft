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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDreadlandsStalagmite;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeAbyDreadlands extends BiomeDreadlandsBase {

	public BiomeAbyDreadlands(BiomeProperties par1) {
		super(par1);
		topBlock = ACBlocks.abyssalnite_stone.getDefaultState();
		fillerBlock = ACBlocks.abyssalnite_stone.getDefaultState();
	}

	@Override
	public final void setMobSpawns(){
		super.setMobSpawns();
		spawnableCreatureList.add(new SpawnListEntry(EntityAbygolem.class, 100, 3, 8));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadguard.class, 1, 1, 1));
	}

	@Override
	public void decorate(World par1World, Random par2Random, BlockPos pos)
	{
		super.decorate(par1World, par2Random, pos);

		if(ACConfig.generateDreadlandsAbyssalniteOre)
			for(int rarity = 0; rarity < 10; rarity++) {
				int veinSize =  8 + par2Random.nextInt(12);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.dreadlands_abyssalnite_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.dreadstone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par2Random.nextInt(16);
			int y = par2Random.nextInt(64) + 5;
			int z = par2Random.nextInt(16);
			new WorldGenMinable(ACBlocks.abyssalnite_stone.getDefaultState(), 48,
					state -> state != null && state == ACBlocks.dreadstone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
		}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par2Random.nextInt(16);
			int y = par2Random.nextInt(64) + 5;
			int z = par2Random.nextInt(16);
			new WorldGenMinable(ACBlocks.abyssalnite_stone.getDefaultState(), 36,
					state -> state != null && state == ACBlocks.dreadstone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
		}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par2Random.nextInt(16);
			int y = par2Random.nextInt(64) + 5;
			int z = par2Random.nextInt(16);
			new WorldGenMinable(ACBlocks.abyssalnite_stone.getDefaultState(), 24,
					state -> state != null && state == ACBlocks.dreadstone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
		}
		if(ACConfig.generateDreadlandsAbyssalniteOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize =  2 + par2Random.nextInt(4);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(55) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.dreadlands_abyssalnite_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.dreadstone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(ACConfig.generateDreadlandsStalagmite)
			for(int i = 0; i < 1; i++){
				int xPos = par2Random.nextInt(16) + 8;
				int zPos = par2Random.nextInt(16) + 8;
				new WorldGenDreadlandsStalagmite().generate(par1World, par2Random, par1World.getHeight(pos.add(xPos, 0, zPos)));
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getPurifiedDreadlandsSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getPurifiedDreadlandsGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getPurifiedDreadlandsFoliageColor();
	}
}
