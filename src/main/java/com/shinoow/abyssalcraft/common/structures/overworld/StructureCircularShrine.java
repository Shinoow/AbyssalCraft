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

public class StructureCircularShrine extends StructureDarklandsBase {

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {

		IBlockState brick = ACBlocks.darkstone_brick.getDefaultState();
		IBlockState chiseled_brick = ACBlocks.chiseled_darkstone_brick.getDefaultState();
		IBlockState brick_slab = ACBlocks.darkstone_brick_slab.getDefaultState();
		IBlockState cobble = ACBlocks.darkstone_cobblestone.getDefaultState();
		IBlockState cobble_slab = ACBlocks.darkstone_cobblestone_slab.getDefaultState();
		IBlockState ooze = ACBlocks.shoggoth_ooze.getDefaultState().withProperty(BlockShoggothOoze.LAYERS, 8);
		IBlockState air = Blocks.AIR.getDefaultState();

		for(int i = -4; i < 5; i++)
			for(int j = -6; j < 7; j++){

				boolean flag = i > -2 && i < 2;
				boolean flag1 = i > -4 && i < 4;

				if(j == -6 || j == 6)
					if(flag){
						worldIn.setBlockToAir(pos.add(i, 1, j));
						worldIn.setBlockToAir(pos.add(j, 1, i));
					}
				if(j == -5 || j == 5)
					if(flag1){
						worldIn.setBlockToAir(pos.add(i, 1, j));
						worldIn.setBlockToAir(pos.add(j, 1, i));
					}
				if(j > -5 && j < -2 || j > 2 && j < 5){
					worldIn.setBlockToAir(pos.add(i, 1, j));
					worldIn.setBlockToAir(pos.add(j, 1, i));
				}

				if(j == -6 || j == 6)
					if(flag){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 0, i), brick);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 0, j), brick);
					}
				if(j == -5 || j == 5)
					if(flag1){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 0, i), flag ? cobble_slab : brick);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 0, j), flag ? cobble_slab : brick);
					}

				if(j == -4 || j == 4){
					if(flag){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, -1, i), cobble);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, -1, j), cobble);
					}
					setBlockAndNotifyAdequately(worldIn, pos.add(j, 0, i), i == -4 || i == 4 ? brick : flag ? air : cobble_slab);
					setBlockAndNotifyAdequately(worldIn, pos.add(i, 0, j), i == -4 || i == 4 ? brick : flag ? air : cobble_slab);
				}
				if(j == -3 || j == 3)
					if(flag1){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, -1, i), flag ? brick : cobble);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, -1, j), flag ? brick : cobble);

						setBlockAndNotifyAdequately(worldIn, pos.add(j, 0, i), flag ? brick_slab : air);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 0, j), flag ? brick_slab : air);

						if(i == 0)
							for(int k = 0; k < 2; k++){
								setBlockAndNotifyAdequately(worldIn, pos.add(i, k, j), k == 1 ? chiseled_brick : brick);
								setBlockAndNotifyAdequately(worldIn, pos.add(j, k, i), k == 1 ? chiseled_brick : brick);
							}
					}
				if(j == -2 || j == 2)
					if(i > -3 && i < 3){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, -1, i), flag ? ooze : brick);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, -1, j), flag ? ooze : brick);

						setBlockAndNotifyAdequately(worldIn, pos.add(j, 0, i), flag ? air : brick);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 0, j), flag ? air : brick);
						for(int k = 0; k < 2; k++){
							setBlockAndNotifyAdequately(worldIn, pos.add(i, k, j), flag ? air : k == 1 ? chiseled_brick : brick);
							setBlockAndNotifyAdequately(worldIn, pos.add(j, k, i), flag ? air : k == 1 ? chiseled_brick : brick);
						}
					}
				if(j > -2 && j < 2 && flag)
					for(int k = -1; k < 2; k++)
						setBlockAndNotifyAdequately(worldIn, pos.add(i, k, j), k == -1 ? ooze : air);
			}

		placeStatue(worldIn, rand, pos);

		return true;
	}
}
