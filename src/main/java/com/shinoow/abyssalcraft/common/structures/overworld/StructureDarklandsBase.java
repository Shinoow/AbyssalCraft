/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures.overworld;

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.BlockACBrick;
import com.shinoow.abyssalcraft.common.blocks.BlockACBrick.EnumBrickType;
import com.shinoow.abyssalcraft.common.blocks.BlockStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockStatue.EnumDeityType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class StructureDarklandsBase extends WorldGenerator {

	protected void placeStatue(World world, Random rand, BlockPos pos){
		if(rand.nextFloat() < 0.3F){
			setBlockAndNotifyAdequately(world, pos, ACBlocks.monolith_pillar.getDefaultState());
			setBlockAndNotifyAdequately(world, pos.up(), getStatue(rand).withProperty(BlockStatue.FACING, EnumFacing.byHorizontalIndex(rand.nextInt(3))));
		}
	}

	protected IBlockState getBrick(Random rand){
		IBlockState brick = ACBlocks.darkstone_brick.getDefaultState();
		IBlockState cracked_brick = ACBlocks.darkstone_brick.getDefaultState().withProperty(BlockACBrick.TYPE, EnumBrickType.CRACKED);
		return rand.nextFloat() < 0.2 ? cracked_brick : brick;
	}

	private IBlockState getStatue(Random rand){
		return ACBlocks.statue.getDefaultState().withProperty(BlockStatue.TYPE, EnumDeityType.byMetadata(rand.nextInt(7)));
	}
}
