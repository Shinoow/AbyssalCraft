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
package com.shinoow.abyssalcraft.common.structures.overworld;

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothOoze;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureCircularShrineColumns extends StructureDarklandsBase {

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {

		IBlockState chiseled_brick = ACBlocks.chiseled_darkstone_brick.getDefaultState();
		IBlockState brick_slab = ACBlocks.darkstone_brick_slab.getDefaultState();

		for(int i = -3; i < 4; i++)
			for(int j = -5; j < 6; j++){

				boolean flag = i > -3 && i < 3;
				boolean flag1 = i > -4 && i < 4;

				for(int k = 0; k < 4; k++){
					if(j == -5 || j == 5)
						if(flag){
							worldIn.setBlockToAir(pos.add(i, k+2, j));
							worldIn.setBlockToAir(pos.add(j, k+2, i));
						}
					if(j == -4 || j == 4)
						if(flag1){
							worldIn.setBlockToAir(pos.add(i, k+2, j));
							worldIn.setBlockToAir(pos.add(j, k+2, i));
						}
					if(j > -3 && j < 3 && flag){
						worldIn.setBlockToAir(pos.add(i, k+2, j));
						worldIn.setBlockToAir(pos.add(j, k+2, i));
					}
				}

				if(j == -5 || j == 5)
					if(flag){
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 1, j), brick_slab);
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 1, i), brick_slab);
					}
				if(j == -4 || j == 4){
					setBlockAndNotifyAdequately(worldIn, pos.add(i, 1, j), flag ? getBrick(rand) : brick_slab);
					setBlockAndNotifyAdequately(worldIn, pos.add(j, 1, i), flag ? getBrick(rand) : brick_slab);
					if(flag){
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 5, j), brick_slab);
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 5, i), brick_slab);
					}
					if(i == 0)
						for(int k = 0; k < 3; k++){
							setBlockAndNotifyAdequately(worldIn, pos.add(i, k+2, j), k == 1 ? chiseled_brick : getBrick(rand));
							setBlockAndNotifyAdequately(worldIn, pos.add(j, k+2, i), k == 1 ? chiseled_brick : getBrick(rand));
						}
				}
				if(j == -3 || j == 3){
					if(flag1)
						for(int k = 1; k < 6; k++){
							setBlockAndNotifyAdequately(worldIn, pos.add(i, k, j), k == 1 ? getBrick(rand) : (i < -1 || i > 1) && k == 5 ? brick_slab : Blocks.AIR.getDefaultState());
							setBlockAndNotifyAdequately(worldIn, pos.add(j, k, i), k == 1 ? getBrick(rand) : (i < -1 || i > 1) && k == 5 ? brick_slab : Blocks.AIR.getDefaultState());
						}
					if(i == -3 || i == 3)
						for(int k = 0; k < 3; k++){
							setBlockAndNotifyAdequately(worldIn, pos.add(i, k+2, j), k == 1 ? chiseled_brick : getBrick(rand));
							setBlockAndNotifyAdequately(worldIn, pos.add(j, k+2, i), k == 1 ? chiseled_brick : getBrick(rand));
						}
				}
				if(j == -2 || j == 2){
					setBlockAndNotifyAdequately(worldIn, pos.add(i, 1, j), i > -2 && i < 2 ? ACBlocks.monolith_stone.getDefaultState() : getBrick(rand));
					setBlockAndNotifyAdequately(worldIn, pos.add(j, 1, i), i > -2 && i < 2 ? ACBlocks.monolith_stone.getDefaultState() : getBrick(rand));
				}
				if(j > -2 && j < 2 && i > -2 && i < 2)
					setBlockAndNotifyAdequately(worldIn, pos.add(i, 1, j), ACBlocks.shoggoth_ooze.getDefaultState().withProperty(BlockShoggothOoze.LAYERS, 8));
			}

		placeStatue(worldIn, rand, pos.up(2));

		return true;
	}
}
