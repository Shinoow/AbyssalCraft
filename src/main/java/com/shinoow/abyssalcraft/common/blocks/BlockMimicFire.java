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
package com.shinoow.abyssalcraft.common.blocks;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMimicFire extends BlockFire {

	@Override
	public int tickRate(World world)
	{
		return 1;
	}

	@Override
	public boolean isBurning(IBlockAccess world, int x, int y, int z){

		return true;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (world.getGameRules().getGameRuleBooleanValue("doFireTick"))
		{

			if (!canPlaceBlockAt(world, x, y, z))
				world.setBlockToAir(x, y, z);

			if (world.isRaining() && (world.canLightningStrikeAt(x, y, z) || world.canLightningStrikeAt(x - 1, y, z) || world.canLightningStrikeAt(x + 1, y, z) || world.canLightningStrikeAt(x, y, z - 1) || world.canLightningStrikeAt(x, y, z + 1)))
				world.setBlockToAir(x, y, z);
			else
			{
				int l = world.getBlockMetadata(x, y, z);

				if (l < 4)
					world.setBlockMetadataWithNotify(x, y, z, l + rand.nextInt(3) / 2, 4);

				world.scheduleBlockUpdate(x, y, z, this, tickRate(world) + rand.nextInt(10));

				if (!canNeighborBurn(world, x, y, z))
				{
					if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) || l > 3)
						world.setBlockToAir(x, y, z);
				}
				else if (!canCatchFire(world, x, y - 1, z, UP) && l >= 4 && rand.nextInt(4) == 0)
					world.setBlockToAir(x, y, z);
				else
				{
					boolean flag1 = world.isBlockHighHumidity(x, y, z);
					byte b0 = 0;

					if (flag1)
						b0 = -50;

					tryCatchFire(world, x + 1, y, z, 300 + b0, rand, l, WEST );
					tryCatchFire(world, x - 1, y, z, 300 + b0, rand, l, EAST );
					tryCatchFire(world, x, y - 1, z, 250 + b0, rand, l, UP   );
					tryCatchFire(world, x, y + 1, z, 250 + b0, rand, l, DOWN );
					tryCatchFire(world, x, y, z - 1, 300 + b0, rand, l, SOUTH);
					tryCatchFire(world, x, y, z + 1, 300 + b0, rand, l, NORTH);

					for (int i1 = x - 1; i1 <= x + 1; ++i1)
						for (int j1 = z - 1; j1 <= z + 1; ++j1)
							for (int k1 = y - 1; k1 <= y + 4; ++k1)
								if (i1 != x || k1 != y || j1 != z)
								{
									int l1 = 100;

									if (k1 > y + 1)
										l1 += (k1 - (y + 1)) * 100;

									int i2 = getChanceOfNeighborsEncouragingFire(world, i1, k1, j1);

									if (i2 > 0)
									{
										int j2 = (i2 + 40 + world.difficultySetting.getDifficultyId() * 7) / (l + 30);

										if (flag1)
											j2 /= 2;

										if (j2 > 0 && rand.nextInt(l1) <= j2 && (!world.isRaining() || !world.canLightningStrikeAt(i1, k1, j1)) && !world.canLightningStrikeAt(i1 - 1, k1, z) && !world.canLightningStrikeAt(i1 + 1, k1, j1) && !world.canLightningStrikeAt(i1, k1, j1 - 1) && !world.canLightningStrikeAt(i1, k1, j1 + 1))
										{
											int k2 = l + rand.nextInt(5) / 4;

											if (k2 > 15)
												k2 = 15;

											world.setBlock(i1, k1, j1, this, k2, 3);
										}
									}
								}
				}
			}
		}
	}

	private void tryCatchFire(World world, int x, int y, int z, int par5, Random rand, int meta, ForgeDirection face)
	{
		int j1 = world.getBlock(x, y, z).getFlammability(world, x, y, z, face);

		if (rand.nextInt(par5) < j1)
		{
			boolean flag = world.getBlock(x, y, z) == Blocks.tnt;

			if (rand.nextInt(meta + 10) < 5 && !world.canLightningStrikeAt(x, y, z))
			{
				int k1 = meta + rand.nextInt(5) / 4;

				if (k1 > 15)
					k1 = 15;

				world.setBlock(x, y, z, this, k1, 3);
			} else
				world.setBlockToAir(x, y, z);

			if (flag)
				Blocks.tnt.onBlockDestroyedByPlayer(world, x, y, z, 1);
		}
	}

	private boolean canNeighborBurn(World world, int x, int y, int z)
	{
		return canCatchFire(world, x + 1, y, z, WEST ) ||
				canCatchFire(world, x - 1, y, z, EAST ) ||
				canCatchFire(world, x, y - 1, z, UP   ) ||
				canCatchFire(world, x, y + 1, z, DOWN ) ||
				canCatchFire(world, x, y, z - 1, SOUTH) ||
				canCatchFire(world, x, y, z + 1, NORTH);
	}

	private int getChanceOfNeighborsEncouragingFire(World world, int x, int y, int z)
	{
		byte b0 = 0;

		if (!world.isAirBlock(x, y, z))
			return 0;
		else
		{
			int l = b0;
			l = getChanceToEncourageFire(world, x + 1, y, z, l, WEST );
			l = getChanceToEncourageFire(world, x - 1, y, z, l, EAST );
			l = getChanceToEncourageFire(world, x, y - 1, z, l, UP   );
			l = getChanceToEncourageFire(world, x, y + 1, z, l, DOWN );
			l = getChanceToEncourageFire(world, x, y, z - 1, l, SOUTH);
			l = getChanceToEncourageFire(world, x, y, z + 1, l, NORTH);
			return l;
		}
	}
}
