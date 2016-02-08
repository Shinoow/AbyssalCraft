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
package com.shinoow.abyssalcraft.common.world.gen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class WorldGenDrT extends WorldGenTrees
{
	private final int minTreeHeight;
	private final boolean vinesGrow;
	public WorldGenDrT(boolean flag)
	{
		this(flag, 4, 0, 0, false);
	}

	public WorldGenDrT(boolean flag, int i, int j, int k, boolean flag1)
	{
		super(flag);
		minTreeHeight = i;
		vinesGrow = flag1;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		int l = random.nextInt(3) + minTreeHeight;
		boolean flag = true;

		if (pos.getY() < 1 || pos.getY() + l + 1 > 256)
			return false;

		for (int i1 = pos.getY(); i1 <= pos.getY() + 1 + l; i1++)
		{
			byte byte0 = 1;

			if (i1 == pos.getY())
				byte0 = 0;

			if (i1 >= pos.getY() + 1 + l - 2)
				byte0 = 2;

			for (int k1 = pos.getX() - byte0; k1 <= pos.getX() + byte0 && flag; k1++)
				for (int i2 = pos.getZ() - byte0; i2 <= pos.getZ() + byte0 && flag; i2++)
					if (i1 >= 0 && i1 < 256)
					{
						IBlockState i3 = world.getBlockState(new BlockPos(k1, i1, i2));

						if (i3 != Blocks.air.getDefaultState() && i3 != AbyssalCraft.dreadleaves.getDefaultState() && i3 != Blocks.dirt.getDefaultState() && i3 != AbyssalCraft.dreadlog.getDefaultState())
							flag = false;
					} else
						flag = false;
		}

		if (!flag)
			return false;

		IBlockState j1 = world.getBlockState(pos.down());

		if (j1 != Blocks.dirt.getDefaultState() && j1 != AbyssalCraft.dreadstone.getDefaultState() && j1 != Blocks.grass.getDefaultState() && j1 != AbyssalCraft.dreadgrass.getDefaultState() && j1 != AbyssalCraft.Darkgrass.getDefaultState() || pos.getY() >= 256 - l - 1)
			return false;

		setBlockAndNotifyAdequately(world, pos.down(), Blocks.dirt.getDefaultState());
		byte byte1 = 3;
		int l1 = 0;

		for (int j2 = pos.getY() - byte1 + l; j2 <= pos.getY() + l; j2++)
		{
			int j3 = j2 - (pos.getY() + l);
			int i4 = l1 + 1 - j3 / 2;

			for (int k4 = pos.getX() - i4; k4 <= pos.getX() + i4; k4++)
			{
				int i5 = k4 - pos.getX();

				for (int k5 = pos.getZ() - i4; k5 <= pos.getZ() + i4; k5++)
				{
					int l5 = k5 - pos.getZ();

					if (Math.abs(i5) != i4 || Math.abs(l5) != i4 || random.nextInt(2) != 0 && j3 != 0)
						setBlockAndNotifyAdequately(world, new BlockPos(k4, j2, k5), AbyssalCraft.dreadleaves.getDefaultState());
				}
			}
		}

		for (int k2 = 0; k2 < l; k2++)
		{
			IBlockState k3 = world.getBlockState(pos.up(k2));

			if (k3 != Blocks.air.getDefaultState() && k3 != AbyssalCraft.dreadleaves.getDefaultState())
				continue;

			setBlockAndNotifyAdequately(world, pos.up(k2), AbyssalCraft.dreadlog.getDefaultState());

			if (!vinesGrow || k2 <= 0)
				continue;

			if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(-1, k2, 0)))
				setBlockAndNotifyAdequately(world, pos.add(-1, k2, 0), Blocks.dirt.getDefaultState());

			if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(1, k2, 0)))
				setBlockAndNotifyAdequately(world, pos.add(1, k2, 0), Blocks.dirt.getDefaultState());

			if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(0, k2, -1)))
				setBlockAndNotifyAdequately(world, pos.add(0, k2, -1), Blocks.dirt.getDefaultState());

			if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(0, k2, 1)))
				setBlockAndNotifyAdequately(world, pos.add(0, k2, 1), Blocks.dirt.getDefaultState());
		}

		//		if (vinesGrow)
		//			for (int l2 = j - 3 + l; l2 <= j + l; l2++)
		//			{
		//				int l3 = l2 - (j + l);
		//				int j4 = 2 - l3 / 2;
		//
		//				for (int l4 = i - j4; l4 <= i + j4; l4++)
		//					for (int j5 = k - j4; j5 <= k + j4; j5++)
		//					{
		//						if (world.getBlock(l4, l2, j5) != AbyssalCraft.dreadlog)
		//							continue;
		//
		//						if (random.nextInt(4) == 0 && world.getBlock(l4 - 1, l2, j5) == Blocks.air)
		//							growVines(world, l4 - 1, l2, j5, 8);
		//
		//						if (random.nextInt(4) == 0 && world.getBlock(l4 + 1, l2, j5) == Blocks.air)
		//							growVines(world, l4 + 1, l2, j5, 2);
		//
		//						if (random.nextInt(4) == 0 && world.getBlock(l4, l2, j5 - 1) == Blocks.air)
		//							growVines(world, l4, l2, j5 - 1, 1);
		//
		//						if (random.nextInt(4) == 0 && world.getBlock(l4, l2, j5 + 1) == Blocks.air)
		//							growVines(world, l4, l2, j5 + 1, 4);
		//					}
		//			}

		return true;
	}

	//	private void growVines(World world, int i, int j, int k, int l)
	//	{
	//		setBlockAndNotifyAdequately(world, i, j, k, Blocks.dirt, l);
	//
	//		for (int i1 = 4; world.getBlockMetadata(i, --j, k) == 0 && i1 > 0; i1--)
	//			setBlockAndNotifyAdequately(world, i, j, k, Blocks.dirt, l);
	//	}
}
