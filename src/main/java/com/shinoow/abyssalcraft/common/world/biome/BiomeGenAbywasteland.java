/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenAbywasteland extends BiomeGenBase
{
	@SuppressWarnings("unchecked")
	public BiomeGenAbywasteland(int par1)
	{
		super(par1);

		waterColorMultiplier = 0x24FF83;
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 5, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 5, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsghoul.class, 5, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsZombie.class, 5, 1, 5));
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);
		int var5 = 3 + par2Random.nextInt(6);
		int var6;
		int var7;
		int var8;

		for (var6 = 0; var6 < var5; ++var6)
		{
			var7 = par3 + par2Random.nextInt(16);
			var8 = par2Random.nextInt(28) + 4;
			int var9 = par4 + par2Random.nextInt(16);
			Block var10 = par1World.getBlock(var7, var8, var9);

			if (var10 == AbyssalCraft.abystone)
				par1World.setBlock(var7, var8, var9, AbyssalCraft.AbyCorOre);
		}
	}
	@Override
	public void genTerrainBlocks(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
	{
		topBlock = AbyssalCraft.abystone;
		fillerBlock = AbyssalCraft.abystone;
		genAbyssTerrain(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
	}

	public final void genAbyssTerrain(World p_150560_1_, Random p_150560_2_, Block[] p_150560_3_, byte[] p_150560_4_, int p_150560_5_, int p_150560_6_, double p_150560_7_)
	{
		Block block = topBlock;
		byte b0 = (byte)(field_150604_aj & 255);
		Block block1 = fillerBlock;
		int k = -1;
		int l = (int)(p_150560_7_ / 3.0D + 3.0D + p_150560_2_.nextDouble() * 0.25D);
		int i1 = p_150560_5_ & 15;
		int j1 = p_150560_6_ & 15;
		int k1 = p_150560_3_.length / 256;

		for (int l1 = 255; l1 >= 0; --l1)
		{
			int i2 = (j1 * 16 + i1) * k1 + l1;

			if (l1 <= 0 + p_150560_2_.nextInt(5))
				p_150560_3_[i2] = Blocks.bedrock;
			else
			{
				Block block2 = p_150560_3_[i2];

				if (block2 != null && block2.getMaterial() != Material.air)
				{
					if (block2 == AbyssalCraft.abystone)
						if (k == -1)
						{
							if (l <= 0)
							{
								block = null;
								b0 = 0;
								block1 = AbyssalCraft.abystone;
							}
							else if (l1 >= 59 && l1 <= 64)
							{
								block = topBlock;
								b0 = (byte)(field_150604_aj & 255);
								block1 = fillerBlock;
							}

							if (l1 < 63 && (block == null || block.getMaterial() == Material.air))
								if (getFloatTemperature(p_150560_5_, l1, p_150560_6_) < 0.15F)
								{
									block = Blocks.ice;
									b0 = 0;
								}
								else
								{
									block = Blocks.water;
									b0 = 0;
								}

							k = l;

							if (l1 >= 62)
							{
								p_150560_3_[i2] = block;
								p_150560_4_[i2] = b0;
							}
							else if (l1 < 56 - l)
							{
								block = null;
								block1 = AbyssalCraft.abystone;
								p_150560_3_[i2] = AbyssalCraft.abystone;
							} else
								p_150560_3_[i2] = block1;
						}
						else if (k > 0)
						{
							--k;
							p_150560_3_[i2] = block1;

							if (k == 0 && block1 == Blocks.sand)
							{
								k = p_150560_2_.nextInt(4) + Math.max(0, l1 - 63);
								block1 = Blocks.sandstone;
							}
						}
				} else
					k = -1;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)

	public int getSkyColorByTemp(float par1)
	{
		return 0;
	}
}
