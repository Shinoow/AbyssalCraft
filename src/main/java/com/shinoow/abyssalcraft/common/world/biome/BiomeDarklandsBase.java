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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityDreadedGhoul;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.world.biome.IAlternateSpawnList;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BiomeDarklandsBase extends Biome implements IDarklandsBiome, IAlternateSpawnList {

	protected static final IBlockState ABYSSAL_STONE = ACBlocks.abyssal_stone.getDefaultState();
	protected static final IBlockState LIQUID_CORALIUM = ACBlocks.liquid_coralium.getDefaultState();
	protected static final IBlockState DREADSTONE = ACBlocks.dreadstone.getDefaultState();

	protected boolean staticTopBlock, staticFillerBlock;

	protected List<SpawnListEntry> aw, dl;

	public BiomeDarklandsBase(BiomeProperties properties) {
		super(properties);
		aw = new ArrayList<>();
		dl = new ArrayList<>();
		aw.add(new SpawnListEntry(EntityZombie.class, 50, 1, 5));
		aw.add(new SpawnListEntry(EntitySkeleton.class, 50, 1, 5));
		aw.add(new SpawnListEntry(EntityDepthsGhoul.class, 1, 1, 3));
		aw.add(new SpawnListEntry(EntityAbyssalZombie.class, 60, 1, 5));
		aw.add(new SpawnListEntry(EntitySkeletonGoliath.class, 15, 1, 1));
		aw.add(new SpawnListEntry(EntityShadowCreature.class, 70, 3, 3));
		aw.add(new SpawnListEntry(EntityShadowMonster.class, 50, 2, 2));
		aw.add(new SpawnListEntry(EntityShadowBeast.class, 20, 1, 1));
		dl.add(new SpawnListEntry(EntityDreadSpawn.class, 30, 1, 2));
		dl.add(new SpawnListEntry(EntityDreadling.class, 40, 1, 2));
		dl.add(new SpawnListEntry(EntityChagarothFist.class, 2, 1, 1));
		dl.add(new SpawnListEntry(EntityDemonPig.class, 5, 1, 2));
		dl.add(new SpawnListEntry(EntityDemonCow.class, 5, 1, 2));
		dl.add(new SpawnListEntry(EntityDemonChicken.class, 5, 1, 2));
		dl.add(new SpawnListEntry(EntityDemonSheep.class, 5, 1, 2));
		dl.add(new SpawnListEntry(EntityGreaterDreadSpawn.class, 5, 1, 1));
		dl.add(new SpawnListEntry(EntityDreadguard.class, 8, 1, 1));
		dl.add(new SpawnListEntry(EntityLesserDreadbeast.class, 1, 0, 1));
		dl.add(new SpawnListEntry(EntityShadowCreature.class, 70, 3, 3));
		dl.add(new SpawnListEntry(EntityShadowMonster.class, 50, 2, 2));
		dl.add(new SpawnListEntry(EntityShadowBeast.class, 20, 1, 1));
		dl.add(new SpawnListEntry(EntityDreadedGhoul.class, 1, 1, 3));
		decorator.sandPatchesPerChunk = -1; // no sand in Darklands biomes
	}

	@Override
	public void decorate(World par1World, Random par2Random, BlockPos pos)
	{
		super.decorate(par1World, par2Random, pos);
		int var5 = 3 + par2Random.nextInt(6);

		if(ACConfig.generateAbyssalniteOre)
			for (int rarity = 0; rarity < var5; ++rarity)
			{
				int veinSize = 1 + par2Random.nextInt(3);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(28) + 4;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(getAbyssalniteOre(par1World.provider.getDimension()), veinSize, getPredicate(par1World.provider.getDimension())).generate(par1World, par2Random, pos.add(x, y, z));
			}

		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par2Random.nextInt(16);
			new WorldGenMinable(ACBlocks.darkstone.getDefaultState(), 32, getPredicate(par1World.provider.getDimension())).generate(par1World, par2Random, pos.add(x, y, z));
		}
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
	{
		if(worldIn.provider.getDimension() != ACLib.abyssal_wasteland_id && worldIn.provider.getDimension() != ACLib.dreadlands_id)
			super.genTerrainBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
		else generateDarklandsTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}

	private int getSeaLevel(World world) {
		return world.provider.getDimension() == ACLib.abyssal_wasteland_id ? 49 :
			world.provider.getDimension() == ACLib.dreadlands_id ? 50 : world.getSeaLevel();
	}

	protected IBlockState getBaseBlock(int dim) {
		if(dim == ACLib.abyssal_wasteland_id)
			return ABYSSAL_STONE;
		if(dim == ACLib.dreadlands_id)
			return DREADSTONE;
		return Blocks.STONE.getDefaultState();
	}

	protected IBlockState getAbyssalniteOre(int dim) {

		if(dim == ACLib.abyssal_wasteland_id)
			return  ACBlocks.abyssal_abyssalnite_ore.getDefaultState();
		if(dim == ACLib.dreadlands_id)
			return ACBlocks.dreadlands_abyssalnite_ore.getDefaultState();

		return ACBlocks.abyssalnite_ore.getDefaultState();
	}

	protected Predicate<IBlockState> getPredicate(int dim){
		return e -> e == getBaseBlock(dim);
	}

	protected IBlockState getTopBlock(int dim) {
		return !staticTopBlock && dim == ACLib.dreadlands_id ? ACBlocks.dreadlands_grass.getDefaultState() : topBlock;
	}

	protected IBlockState getFillerBlock(int dim) {
		return !staticFillerBlock && dim == ACLib.dreadlands_id ? ACBlocks.dreadlands_dirt.getDefaultState() : fillerBlock;
	}


	public final void generateDarklandsTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int p_180628_4_, int p_180628_5_, double p_180628_6_)
	{
		int i = getSeaLevel(worldIn);
		int dim = worldIn.provider.getDimension();

		IBlockState BASE_BLOCK = getBaseBlock(dim);

		IBlockState iblockstate = getTopBlock(dim);
		IBlockState iblockstate1 = getFillerBlock(dim);
		int j = -1;
		int k = (int)(p_180628_6_ / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int l = p_180628_4_ & 15;
		int i1 = p_180628_5_ & 15;

		for (int j1 = 255; j1 >= 0; --j1)
			if (j1 < 7)
			{
				if(j1 == 0)
					chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
				else if(j1 == 6)
					chunkPrimerIn.setBlockState(i1, j1, l, ACBlocks.darkstone_cobblestone.getDefaultState());
				else if(j1 == 1)
					chunkPrimerIn.setBlockState(i1, j1, l, ACBlocks.darkstone.getDefaultState());
				else {
					IBlockState state = AIR;

					if(i1 % 4 == 2 && l % 4 == 2)
						state = (j1 == 4 ? ACBlocks.chiseled_darkstone_brick : ACBlocks.darkstone_brick).getDefaultState();
					chunkPrimerIn.setBlockState(i1, j1, l, state);

				}
			}
			else
			{
				IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

				if (iblockstate2.getMaterial() == Material.AIR)
					j = -1;
				else if (iblockstate2.getBlock() == BASE_BLOCK.getBlock())
					if (j == -1)
					{
						if (k <= 0)
						{
							iblockstate = AIR;
							iblockstate1 = BASE_BLOCK;
						}
						else if (j1 >= i - 4 && j1 <= i + 1)
						{
							iblockstate = getTopBlock(dim);
							iblockstate1 = getFillerBlock(dim);
						}

						if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR) && i == 49)
							iblockstate = LIQUID_CORALIUM;

						j = k;

						if (j1 >= i - 1)
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
						else if (j1 < i - 7 - k)
						{
							iblockstate = AIR;
							iblockstate1 = BASE_BLOCK;
							chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
						} else
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
					}
					else if (j > 0)
					{
						--j;
						chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
					}
			}
	}

	@Override
	public List<SpawnListEntry> getAbyssalWastelandList() {

		return aw;
	}

	@Override
	public List<SpawnListEntry> getDreadlandsList() {

		return dl;
	}
}
