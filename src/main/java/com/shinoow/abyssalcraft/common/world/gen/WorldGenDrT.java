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
package com.shinoow.abyssalcraft.common.world.gen;

import static java.lang.Math.*;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

public class WorldGenDrT extends WorldGenTrees {

	public WorldGenDrT(boolean flag)
	{
		super(flag);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos blockPos) {
		return this.generate(world, rand, blockPos.getX(), blockPos.getY(), blockPos.getZ());
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {

		int height = rand.nextInt(3) + 9;
		int leaveheight = rand.nextInt(3);
		int branches = rand.nextInt(8) + 4;
		int branchLenght = 6;

		IBlockState j1 = world.getBlockState(new BlockPos(x, y -1, z));

		if (j1.getBlock() != Blocks.dirt && j1.getBlock() != ACBlocks.dreadlands_dirt && j1.getBlock() != ACBlocks.dreadstone && j1.getMaterial() != Material.grass || y >= 256 - height - 1)
			return false;

		if(j1.getBlock() != ACBlocks.dreadlands_grass && j1.getBlock() != ACBlocks.dreadstone && j1.getBlock() != ACBlocks.dreadlands_dirt)
			setBlockAndNotifyAdequately(world, new BlockPos(x, y -1, z), Blocks.dirt.getDefaultState());
		else setBlockAndNotifyAdequately(world, new BlockPos(x, y -1, z), ACBlocks.dreadlands_dirt.getDefaultState());

		for (int i = 0; i < height; i++)
			world.setBlockState(new BlockPos(x, y + i, z), ACBlocks.dreadlands_log.getDefaultState());
		world.setBlockState(new BlockPos(x, y + height, z), ACBlocks.dreadlands_leaves.getDefaultState());
		createTrunk(world, rand, x, y, z);

		int dir = rand.nextInt((int) (360f / branches));
		float xd, yd, hd, c;
		for (int b = 0; b < branches; b++) {
			c = 0;
			hd = height - rand.nextFloat() * leaveheight - 2f;
			dir += (int) (360f / branches);
			xd = (float) cos(dir * PI / 180f);
			yd = (float) sin(dir * PI / 180f);

			while (c < branchLenght) {
				c++;
				hd += 0.5f;
				world.setBlockState(new BlockPos(x + (int) (c * xd), y + (int) hd, z + (int) (c * yd)), ACBlocks.dreadlands_log.getStateFromMeta(12));
				if(world.isAirBlock(new BlockPos(x + (int) (c * xd), y + (int) hd + 1, z + (int) (c * yd))))
					world.setBlockState(new BlockPos(x + (int) (c * xd), y + (int) hd + 1, z + (int) (c * yd)), ACBlocks.dreadlands_leaves.getDefaultState());
			}
		}

		return true;
	}

	private void createTrunk(World world, Random rand, int x, int y, int z) {
		int[] pos = new int[] {0, 0, 1, 0, 0, 1, -1, 0, 0, -1};
		int sh;
		for (int t = 0; t < 5; t++) {
			sh = rand.nextInt(3) + y;
			while (sh > y - 1) {
				world.setBlockState(new BlockPos(x + pos[t * 2], sh, z + pos[t * 2 + 1]), ACBlocks.dreadlands_log.getStateFromMeta(12));
				sh--;
			}
		}
	}
}
