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

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.BlockStatue;
import com.shinoow.abyssalcraft.lib.util.SoundUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenShoggothMonolith extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		while(world.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();

		// Instead of "only if block is Shoggoth Ooze", block can't be air,
		// and block underneath must be solid
		if(world.isAirBlock(pos) || !world.isSideSolid(pos.down(), EnumFacing.UP))
			return false;
		else {

			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			int max = rand.nextInt(8) + 5;
			for(int i = 0; i < max; i++){
				setBlockAndNotifyAdequately(world, new BlockPos(x, y + i, z), ACBlocks.monolith_stone.getDefaultState());
				setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + i, z), ACBlocks.monolith_stone.getDefaultState());
				setBlockAndNotifyAdequately(world, new BlockPos(x -1, y + i, z), ACBlocks.monolith_stone.getDefaultState());
				setBlockAndNotifyAdequately(world, new BlockPos(x, y + i, z + 1), ACBlocks.monolith_stone.getDefaultState());
				setBlockAndNotifyAdequately(world, new BlockPos(x, y + i, z -1), ACBlocks.monolith_stone.getDefaultState());
				setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + i, z + 1), ACBlocks.monolith_stone.getDefaultState());
				setBlockAndNotifyAdequately(world, new BlockPos(x -1, y + i, z -1), ACBlocks.monolith_stone.getDefaultState());
				setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + i, z -1), ACBlocks.monolith_stone.getDefaultState());
				setBlockAndNotifyAdequately(world, new BlockPos(x -1, y + i, z + 1), ACBlocks.monolith_stone.getDefaultState());
			}
			setBlockAndNotifyAdequately(world, pos, ACBlocks.shoggoth_biomass.getDefaultState());
			setBlockAndNotifyAdequately(world, new BlockPos(x, y + max, z), getStatue(rand).withProperty(BlockStatue.FACING, EnumFacing.byIndex(rand.nextInt(3))));

			SoundUtil.playSound(world, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 2, world.rand.nextFloat() * 0.1F * 0.9F);

			return true;
		}
	}

	private IBlockState getStatue(Random rand){
		switch(rand.nextInt(7)){
		case 0:
			return ACBlocks.cthulhu_statue.getDefaultState();
		case 1:
			return ACBlocks.hastur_statue.getDefaultState();
		case 2:
			return ACBlocks.jzahar_statue.getDefaultState();
		case 3:
			return ACBlocks.azathoth_statue.getDefaultState();
		case 4:
			return ACBlocks.nyarlathotep_statue.getDefaultState();
		case 5:
			return ACBlocks.yog_sothoth_statue.getDefaultState();
		case 6:
			return ACBlocks.shub_niggurath_statue.getDefaultState();
		default:
			return getStatue(rand);
		}
	}
}
