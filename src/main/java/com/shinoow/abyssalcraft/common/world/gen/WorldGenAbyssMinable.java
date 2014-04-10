package com.shinoow.abyssalcraft.common.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class WorldGenAbyssMinable extends WorldGenerator
{
	/** The block ID of the ore to be placed using this generator. */
	private Block minableBlockId;

	/** The number of blocks to generate. */
	private int numberOfBlocks;

	public WorldGenAbyssMinable(Block par1, int par2)
	{
		minableBlockId = par1;
		numberOfBlocks = par2;
	}

	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		float f = par2Random.nextFloat() * (float)Math.PI;
		double d = (float)(par3 + 8) + (MathHelper.sin(f) * (float)numberOfBlocks) / 8F;
		double d1 = (float)(par3 + 8) - (MathHelper.sin(f) * (float)numberOfBlocks) / 8F;
		double d2 = (float)(par5 + 8) + (MathHelper.cos(f) * (float)numberOfBlocks) / 8F;
		double d3 = (float)(par5 + 8) - (MathHelper.cos(f) * (float)numberOfBlocks) / 8F;
		double d4 = (par4 + par2Random.nextInt(3)) - 2;
		double d5 = (par4 + par2Random.nextInt(3)) - 2;

		for (int i = 0; i <= numberOfBlocks; i++)
		{
			double d6 = d + ((d1 - d) * (double)i) / (double)numberOfBlocks;
			double d7 = d4 + ((d5 - d4) * (double)i) / (double)numberOfBlocks;
			double d8 = d2 + ((d3 - d2) * (double)i) / (double)numberOfBlocks;
			double d9 = (par2Random.nextDouble() * (double)numberOfBlocks) / 16D;
			double d10 = (double)(MathHelper.sin(((float)i * (float)Math.PI) / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
			double d11 = (double)(MathHelper.sin(((float)i * (float)Math.PI) / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
			int j = MathHelper.floor_double(d6 - d10 / 2D);
			int k = MathHelper.floor_double(d7 - d11 / 2D);
			int l = MathHelper.floor_double(d8 - d10 / 2D);
			int i1 = MathHelper.floor_double(d6 + d10 / 2D);
			int j1 = MathHelper.floor_double(d7 + d11 / 2D);
			int k1 = MathHelper.floor_double(d8 + d10 / 2D);

			for (int l1 = j; l1 <= i1; l1++)
			{
				double d12 = (((double)l1 + 0.5D) - d6) / (d10 / 2D);

				if (d12 * d12 >= 1.0D)
				{
					continue;
				}

				for (int i2 = k; i2 <= j1; i2++)
				{
					double d13 = (((double)i2 + 0.5D) - d7) / (d11 / 2D);

					if (d12 * d12 + d13 * d13 >= 1.0D)
					{
						continue;
					}

					for (int j2 = l; j2 <= k1; j2++)
					{
						double d14 = (((double)j2 + 0.5D) - d8) / (d10 / 2D);

						if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && par1World.getBlock(l1, i2, j2) == AbyssalCraft.abystone)
						{
							par1World.setBlock(l1, i2, j2, minableBlockId, j2, j2);
						}
					}
				}
			}
		}

		return true;
	}
}