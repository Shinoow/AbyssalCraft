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
package com.shinoow.abyssalcraft.common.structures.overworld;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDLT;

public class ACscion1 extends StructureDarklandsBase {

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {

		IBlockState grass = ACBlocks.darklands_grass.getDefaultState();
		IBlockState glowing_brick = ACBlocks.glowing_darkstone_bricks.getDefaultState();
		IBlockState chiseled_brick = ACBlocks.darkstone_brick.getStateFromMeta(1);
		IBlockState brick_slab = ACBlocks.darkstone_brick_slab.getDefaultState();

		boolean shouldGrass = world.getBlockState(pos).getMaterial() == Material.grass;

		for(int i = -3; i < 4; i++)
			for(int j = -4; j < 5; j++){

				boolean flag = i > -3 && i < 3;
				boolean flag1 = i == -3 || i == 3;
				boolean flag2 = i > -2 && i < 2;

				if(j == -3 || j == 3 && flag)
					for(int k = 0; k < 4; k++){
						world.setBlockToAir(pos.add(j, k+2, i));
						world.setBlockToAir(pos.add(i, k+2, j));
					}
				if(j == -2 || j == 2 && !flag2)
					for(int k = 0; k < 4; k++){
						world.setBlockToAir(pos.add(j, k+2, i));
						world.setBlockToAir(pos.add(i, k+2, j));
					}

				if(j == -4 || j == 4)
					if(flag && shouldGrass){
						setBlockAndNotifyAdequately(world, pos.add(j, 1, i), grass);
						setBlockAndNotifyAdequately(world, pos.add(i, 1, j), grass);
					}
				if(j == -3 || j == 3)
					if(shouldGrass){
						setBlockAndNotifyAdequately(world, pos.add(j, 1, i), flag1 ? grass : getBrick(random));
						setBlockAndNotifyAdequately(world, pos.add(i, 1, j), flag1 ? grass : getBrick(random));
					} else if(!flag1){
						setBlockAndNotifyAdequately(world, pos.add(j, 1, i), getBrick(random));
						setBlockAndNotifyAdequately(world, pos.add(i, 1, j), getBrick(random));
					}
				if(j > -3 && j < 3){
					setBlockAndNotifyAdequately(world, pos.add(j, 1, i), getBrick(random));
					if((j == -2 || j == 2) && flag2)
						for(int k = 0; k < 4; k++){
							if(k == 0){
								setBlockAndNotifyAdequately(world, pos.add(j, k+2, i), i == 0 ? ACBlocks.darkstone_brick_stairs.getStateFromMeta(j > 0 ? 1 : 0) : glowing_brick);
								setBlockAndNotifyAdequately(world, pos.add(i, k+2, j), i == 0 ? ACBlocks.darkstone_brick_stairs.getStateFromMeta(j > 0 ? 3 : 2) : glowing_brick);
							}
							if(k == 1){
								setBlockAndNotifyAdequately(world, pos.add(j, k+2, i), i == 0 ? Blocks.air.getDefaultState() : brick_slab);
								setBlockAndNotifyAdequately(world, pos.add(i, k+2, j), i == 0 ? Blocks.air.getDefaultState() : brick_slab);
							}
							if(k == 2){
								setBlockAndNotifyAdequately(world, pos.add(j, k+2, i), i == 0 ? brick_slab.withProperty(BlockSlab.HALF, EnumBlockHalf.TOP) : ACBlocks.darkstone_brick_stairs.getStateFromMeta(j > 0 ? 5 : 4));
								setBlockAndNotifyAdequately(world, pos.add(i, k+2, j), i == 0 ? brick_slab.withProperty(BlockSlab.HALF, EnumBlockHalf.TOP) : ACBlocks.darkstone_brick_stairs.getStateFromMeta(j > 0 ? 7 : 6));
							}
							if(k == 3){
								setBlockAndNotifyAdequately(world, pos.add(j, k+2, i), brick_slab);
								setBlockAndNotifyAdequately(world, pos.add(i, k+2, j), brick_slab);
							}
						}
					if(j > -2 && j < 2 && flag2)
						for(int k = 0; k < 4; k++)
							setBlockAndNotifyAdequately(world, pos.add(j, k+2, i), (j == 0 && i != 0 || j != 0 && i == 0) && k == 1 ? chiseled_brick : k == 3 ? brick_slab : getBrick(random));
				}
			}

		if(random.nextFloat() < 0.1){
			setBlockAndNotifyAdequately(world, pos.up(4), Blocks.dirt.getDefaultState());
			new WorldGenDLT(true).generate(world, random, pos.up(5));
		}

		return true;
	}
}
