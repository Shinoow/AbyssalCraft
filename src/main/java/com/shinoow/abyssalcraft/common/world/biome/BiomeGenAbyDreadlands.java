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

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDreadlandsStalagmite;

public class BiomeGenAbyDreadlands extends BiomeGenDreadlandsBase {

	@SuppressWarnings("unchecked")
	public BiomeGenAbyDreadlands(BiomeProperties par1) {
		super(par1);
		topBlock = AbyssalCraft.abydreadstone.getDefaultState();
		fillerBlock = AbyssalCraft.abydreadstone.getDefaultState();
		spawnableCreatureList.add(new SpawnListEntry(EntityAbygolem.class, 100, 3, 8));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadguard.class, 1, 1, 1));
	}

	@Override
	public void decorate(World par1World, Random par2Random, BlockPos pos)
	{
		super.decorate(par1World, par2Random, pos);

		if(AbyssalCraft.generateDreadlandsAbyssalniteOre)
			for(int rarity = 0; rarity < 10; rarity++) {
				int veinSize =  8 + par2Random.nextInt(12);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(AbyssalCraft.abydreadore.getDefaultState(), veinSize,
						BlockMatcher.forBlock(AbyssalCraft.dreadstone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone.getDefaultState(), 64,
					BlockMatcher.forBlock(AbyssalCraft.dreadstone)).generate(par1World, par2Random, pos.add(x, y, z));
		}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone.getDefaultState(), 48,
					BlockMatcher.forBlock(AbyssalCraft.dreadstone)).generate(par1World, par2Random, pos.add(x, y, z));
		}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone.getDefaultState(), 32,
					BlockMatcher.forBlock(AbyssalCraft.dreadstone)).generate(par1World, par2Random, pos.add(x, y, z));
		}
		if(AbyssalCraft.generateDreadlandsAbyssalniteOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize =  2 + par2Random.nextInt(4);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(55);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(AbyssalCraft.abydreadore.getDefaultState(), veinSize,
						BlockMatcher.forBlock(AbyssalCraft.dreadstone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(AbyssalCraft.generateDreadlandsStalagmite)
			for(int i = 0; i < 1; i++){
				int xPos = par2Random.nextInt(16) + 8;
				int zPos = par2Random.nextInt(16) + 8;
				new WorldGenDreadlandsStalagmite().generate(par1World, par2Random, par1World.getHeight(pos.add(xPos, 0, zPos)));
			}
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
