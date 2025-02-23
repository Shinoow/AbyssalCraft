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
package com.shinoow.abyssalcraft.common.world.gen;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

@SuppressWarnings("deprecation")
public class WorldGenDrT extends WorldGenTreeAC {

	private int maxHeight = -1;

	public WorldGenDrT(boolean flag)
	{
		super(flag);
	}

	public WorldGenDrT setMaxHeight(int height) {
		maxHeight = height;
		return this;
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

		if(fixed) {
			height = 6;
			branches = 6;
		}

		IBlockState j1 = world.getBlockState(new BlockPos(x, y -1, z));

		if (j1.getBlock() != Blocks.DIRT && j1.getBlock() != ACBlocks.dreadlands_dirt && j1.getBlock() != ACBlocks.dreadstone && j1.getMaterial() != Material.GRASS || y >= 256 - height - 1)
			return false;

		if(maxHeight > -1 && y > maxHeight)
			return false;

		if(j1.getBlock() != ACBlocks.dreadlands_grass && j1.getBlock() != ACBlocks.dreadstone && j1.getBlock() != ACBlocks.dreadlands_dirt)
			setBlockAndNotifyAdequately(world, new BlockPos(x, y -1, z), Blocks.DIRT.getDefaultState());
		else setBlockAndNotifyAdequately(world, new BlockPos(x, y -1, z), ACBlocks.dreadlands_dirt.getDefaultState());

		for (int i = 0; i < height; i++)
			setBlockAndNotifyAdequately(world, new BlockPos(x, y + i, z), ACBlocks.dreadwood_log.getDefaultState());
		setBlockAndNotifyAdequately(world, new BlockPos(x, y + height, z), ACBlocks.dreadwood_leaves.getDefaultState());
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
				setBlockAndNotifyAdequately(world, new BlockPos(x + (int) (c * xd), y + (int) hd, z + (int) (c * yd)), ACBlocks.dreadwood_log.getStateFromMeta(12));
				if(world.isAirBlock(new BlockPos(x + (int) (c * xd), y + (int) hd + 1, z + (int) (c * yd))))
					setBlockAndNotifyAdequately(world, new BlockPos(x + (int) (c * xd), y + (int) hd + 1, z + (int) (c * yd)), ACBlocks.dreadwood_leaves.getDefaultState());
			}
		}

		return true;
	}

	private void createTrunk(World world, Random rand, int x, int y, int z) {

		if(fixed) {
			setBlockAndNotifyAdequately(world, new BlockPos(x,y,z),ACBlocks.dreadwood_log.getDefaultState());
			setBlockAndNotifyAdequately(world, new BlockPos(x-1,y,z),ACBlocks.dreadwood_log.getStateFromMeta(12));
			setBlockAndNotifyAdequately(world, new BlockPos(x+1,y,z),ACBlocks.dreadwood_log.getStateFromMeta(12));
			setBlockAndNotifyAdequately(world, new BlockPos(x,y,z-1),ACBlocks.dreadwood_log.getStateFromMeta(12));
			setBlockAndNotifyAdequately(world, new BlockPos(x,y,z+1),ACBlocks.dreadwood_log.getStateFromMeta(12));
		} else {
			int[] pos = new int[] {1, 0, 0, 1, -1, 0, 0, -1};
			int sh;
			for (int t = 0; t < 4; t++) {
				sh = rand.nextInt(3) + y;
				int i = sh;
				while (sh > y - 1) {
					setBlockAndNotifyAdequately(world, new BlockPos(x + pos[t * 2], sh, z + pos[t * 2 + 1]), i == sh ? ACBlocks.dreadwood_log.getStateFromMeta(12) : ACBlocks.dreadwood_log.getDefaultState());
					sh--;
				}
			}
		}
	}
}
