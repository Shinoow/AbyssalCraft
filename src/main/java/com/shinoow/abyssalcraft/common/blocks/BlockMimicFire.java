/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMimicFire extends BlockFire {

	@Override
	public int tickRate(World worldIn)
	{
		return 1;
	}

	@Override
	public boolean isBurning(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (worldIn.getGameRules().getBoolean("doFireTick"))
		{
			if (!canPlaceBlockAt(worldIn, pos))
				worldIn.setBlockToAir(pos);

			if (worldIn.isRaining() && canDie(worldIn, pos))
				worldIn.setBlockToAir(pos);
			else
			{
				int i = state.getValue(AGE).intValue();

				if (i < 4)
				{
					state = state.withProperty(AGE, Integer.valueOf(i + rand.nextInt(3) / 2));
					worldIn.setBlockState(pos, state, 4);
				}

				worldIn.scheduleUpdate(pos, this, tickRate(worldIn) + rand.nextInt(10));


				if (!canNeighborCatchFire(worldIn, pos))
				{
					if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || i > 3)
						worldIn.setBlockToAir(pos);

					return;
				}

				if (!this.canCatchFire(worldIn, pos.down(), EnumFacing.UP) && i >= 4 && rand.nextInt(4) == 0)
				{
					worldIn.setBlockToAir(pos);
					return;
				}


				boolean flag1 = worldIn.isBlockinHighHumidity(pos);
				int j = 0;

				if (flag1)
					j = -50;

				if(rand.nextFloat() < 0.2){
					tryCatchFire(worldIn, pos.east(), 300 + j, rand, i, EnumFacing.WEST);
					tryCatchFire(worldIn, pos.west(), 300 + j, rand, i, EnumFacing.EAST);
					tryCatchFire(worldIn, pos.down(), 250 + j, rand, i, EnumFacing.UP);
					tryCatchFire(worldIn, pos.up(), 250 + j, rand, i, EnumFacing.DOWN);
					tryCatchFire(worldIn, pos.north(), 300 + j, rand, i, EnumFacing.SOUTH);
					tryCatchFire(worldIn, pos.south(), 300 + j, rand, i, EnumFacing.NORTH);

					for (int k = -1; k <= 1; ++k)
						for (int l = -1; l <= 1; ++l)
							for (int i1 = -1; i1 <= 4; ++i1)
								if (k != 0 || i1 != 0 || l != 0)
								{
									int j1 = 100;

									if (i1 > 1)
										j1 += (i1 - 1) * 100;

									BlockPos blockpos = pos.add(k, i1, l);
									int k1 = getNeighborEncouragement(worldIn, blockpos);

									if (k1 > 0)
									{
										int l1 = (k1 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (i + 30);

										if (flag1)
											l1 /= 2;

										if (l1 > 0 && rand.nextInt(j1) <= l1 && (!worldIn.isRaining() || !canDie(worldIn, blockpos)))
										{
											int i2 = i + rand.nextInt(5) / 4;

											if (i2 > 4)
												i2 = 4;

											worldIn.setBlockState(blockpos, state.withProperty(AGE, Integer.valueOf(i2)), 3);
										}
									}
								}
				}
			}
		}
	}

	private void tryCatchFire(World worldIn, BlockPos pos, int chance, Random random, int age, EnumFacing face)
	{
		int i = worldIn.getBlockState(pos).getBlock().getFlammability(worldIn, pos, face);

		if (random.nextInt(chance) < i)
		{
			IBlockState iblockstate = worldIn.getBlockState(pos);

			if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos))
			{
				int j = age + random.nextInt(5) / 4;

				if (j > 15)
					j = 15;

				worldIn.setBlockState(pos, getDefaultState().withProperty(AGE, Integer.valueOf(j)), 3);
			} else
				worldIn.setBlockToAir(pos);

			if (iblockstate.getBlock() == Blocks.TNT)
				Blocks.TNT.onBlockDestroyedByPlayer(worldIn, pos, iblockstate.withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)));
		}
	}

	private boolean canNeighborCatchFire(World worldIn, BlockPos pos)
	{
		for (EnumFacing enumfacing : EnumFacing.values())
			if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite()))
				return true;

		return false;
	}

	private int getNeighborEncouragement(World worldIn, BlockPos pos)
	{
		if (!worldIn.isAirBlock(pos))
			return 0;
		else
		{
			int i = 0;

			for (EnumFacing enumfacing : EnumFacing.values())
				i = Math.max(worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getFlammability(worldIn, pos.offset(enumfacing), enumfacing.getOpposite()), i);

			return i;
		}
	}
}
