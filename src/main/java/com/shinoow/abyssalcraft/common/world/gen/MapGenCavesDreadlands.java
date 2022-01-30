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
package com.shinoow.abyssalcraft.common.world.gen;

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCavesHell;

public class MapGenCavesDreadlands extends MapGenCavesHell {

	@Override
	protected void addTunnel(long seed, int par2, int par3, ChunkPrimer primer, double x, double y, double z, float par8, float par9, float par10, int par11, int par12, double par13)
	{
		double d0 = par2 * 16 + 8;
		double d1 = par3 * 16 + 8;
		float f = 0.0F;
		float f1 = 0.0F;
		Random random = new Random(seed);

		if (par12 <= 0)
		{
			int i = range * 16 - 16;
			par12 = i - random.nextInt(i / 4);
		}

		boolean flag1 = false;

		if (par11 == -1)
		{
			par11 = par12 / 2;
			flag1 = true;
		}

		int j = random.nextInt(par12 / 2) + par12 / 4;

		for (boolean flag = random.nextInt(6) == 0; par11 < par12; ++par11)
		{
			double d2 = 1.5D + MathHelper.sin(par11 * (float)Math.PI / par12) * par8;
			double d3 = d2 * par13;
			float f2 = MathHelper.cos(par10);
			float f3 = MathHelper.sin(par10);
			x += MathHelper.cos(par9) * f2;
			y += f3;
			z += MathHelper.sin(par9) * f2;

			if (flag)
				par10 = par10 * 0.92F;
			else
				par10 = par10 * 0.7F;

			par10 = par10 + f1 * 0.1F;
			par9 += f * 0.1F;
			f1 = f1 * 0.9F;
			f = f * 0.75F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (!flag1 && par11 == j && par8 > 1.0F)
			{
				addTunnel(random.nextLong(), par2, par3, primer, x, y, z, random.nextFloat() * 0.5F + 0.5F, par9 - (float)Math.PI / 2F, par10 / 3.0F, par11, par12, 1.0D);
				addTunnel(random.nextLong(), par2, par3, primer, x, y, z, random.nextFloat() * 0.5F + 0.5F, par9 + (float)Math.PI / 2F, par10 / 3.0F, par11, par12, 1.0D);
				return;
			}

			if (flag1 || random.nextInt(4) != 0)
			{
				double d4 = x - d0;
				double d5 = z - d1;
				double d6 = par12 - par11;
				double d7 = par8 + 2.0F + 16.0F;

				if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7)
					return;

				if (x >= d0 - 16.0D - d2 * 2.0D && z >= d1 - 16.0D - d2 * 2.0D && x <= d0 + 16.0D + d2 * 2.0D && z <= d1 + 16.0D + d2 * 2.0D)
				{
					int j2 = MathHelper.floor(x - d2) - par2 * 16 - 1;
					int k = MathHelper.floor(x + d2) - par2 * 16 + 1;
					int k2 = MathHelper.floor(y - d3) - 1;
					int l = MathHelper.floor(y + d3) + 1;
					int l2 = MathHelper.floor(z - d2) - par3 * 16 - 1;
					int i1 = MathHelper.floor(z + d2) - par3 * 16 + 1;

					if (j2 < 0)
						j2 = 0;

					if (k > 16)
						k = 16;

					if (k2 < 1)
						k2 = 1;

					if (l > 120)
						l = 120;

					if (l2 < 0)
						l2 = 0;

					if (i1 > 16)
						i1 = 16;

					boolean flag2 = false;

					for (int j1 = j2; !flag2 && j1 < k; ++j1)
						for (int k1 = l2; !flag2 && k1 < i1; ++k1)
							for (int l1 = l + 1; !flag2 && l1 >= k2 - 1; --l1)
								if (l1 >= 0 && l1 < 128)
								{
									IBlockState iblockstate = primer.getBlockState(j1, l1, k1);

									if (iblockstate.getBlock() == Blocks.FLOWING_LAVA || iblockstate.getBlock() == Blocks.LAVA)
										flag2 = true;

									if (l1 != k2 - 1 && j1 != j2 && j1 != k - 1 && k1 != l2 && k1 != i1 - 1)
										l1 = k2;
								}

					if (!flag2)
					{
						for (int i3 = j2; i3 < k; ++i3)
						{
							double d10 = (i3 + par2 * 16 + 0.5D - x) / d2;

							for (int j3 = l2; j3 < i1; ++j3)
							{
								double d8 = (j3 + par3 * 16 + 0.5D - z) / d2;

								for (int i2 = l; i2 > k2; --i2)
								{
									double d9 = (i2 - 1 + 0.5D - y) / d3;

									if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D)
									{
										IBlockState iblockstate1 = primer.getBlockState(i3, i2, j3);

										if (iblockstate1 == ACBlocks.dreadstone.getDefaultState() || iblockstate1 == ACBlocks.abyssalnite_stone.getDefaultState() || iblockstate1.getBlock() == ACBlocks.dreadlands_dirt || iblockstate1.getBlock() == ACBlocks.dreadlands_grass)
											primer.setBlockState(i3, i2, j3, AIR);
									}
								}
							}
						}

						if (flag1)
							break;
					}
				}
			}
		}
	}
}
