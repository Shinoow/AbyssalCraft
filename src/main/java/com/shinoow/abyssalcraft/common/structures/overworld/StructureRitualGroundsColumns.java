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

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureRitualGroundsColumns extends StructureDarklandsBase {

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {

		IBlockState chiseled_brick = ACBlocks.chiseled_darkstone_brick.getDefaultState();
		IBlockState brick_slab = ACBlocks.darkstone_brick_slab.getDefaultState();
		IBlockState cobble = ACBlocks.darkstone_cobblestone.getDefaultState();

		for(int i = -3; i < 4; i++)
			for(int j = -5; j < 6; j++){

				boolean flag = i > -2 && i < 2;
				boolean flag1 = i > -4 && i < 4;

				for(int k = 0; k < 4; k++){
					if(j == -5 || j == 5)
						if(flag){
							worldIn.setBlockToAir(pos.add(i, k+1, j));
							worldIn.setBlockToAir(pos.add(j, k+1, i));
						}
					if(j > -5 && j < 5){
						worldIn.setBlockToAir(pos.add(i, k+1, j));
						worldIn.setBlockToAir(pos.add(j, k+1, i));
					}
				}

				if(j == -5 || j == 5)
					if(flag){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 0, i), getBrick(rand));
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 0, j), getBrick(rand));

						setBlockAndNotifyAdequately(worldIn, pos.add(j, 4, i), brick_slab);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 4, j), brick_slab);
					}
				if(j == -4 || j == 4)
					if(flag1){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 0, i),  getBrick(rand));
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 0, j), getBrick(rand));
						if(i != 0){
							setBlockAndNotifyAdequately(worldIn, pos.add(j, 4, i), brick_slab);
							setBlockAndNotifyAdequately(worldIn, pos.add(i, 4, j), brick_slab);
						}
					}
				if(j > -4 && j < 4)
					if(flag1){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 0, i), getBrick(rand));
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 0, j), getBrick(rand));
						if((j == -3 || j == 3) && (i == -3 || i == 3)){
							setBlockAndNotifyAdequately(worldIn, pos.add(j, 4, i), brick_slab);
							setBlockAndNotifyAdequately(worldIn, pos.add(i, 4, j), brick_slab);
						}
					}

			}

		setBlockAndNotifyAdequately(worldIn, pos.up(), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(3, 1, 0), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(-3, 1, 0), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(0, 1, 3), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(0, 1, -3), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(2, 1, 2), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(2, 1, -2), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(-2, 1, 2), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(-2, 1, -2), cobble);

		for(int i = 0; i < 3; i++){
			setBlockAndNotifyAdequately(worldIn, pos.add(4, 1 + i, 2), i == 1 ? chiseled_brick : getBrick(rand));
			setBlockAndNotifyAdequately(worldIn, pos.add(4, 1 + i, -2), i == 1 ? chiseled_brick : getBrick(rand));
			setBlockAndNotifyAdequately(worldIn, pos.add(-4, 1 + i, 2), i == 1 ? chiseled_brick : getBrick(rand));
			setBlockAndNotifyAdequately(worldIn, pos.add(-4, 1 + i, -2), i == 1 ? chiseled_brick : getBrick(rand));

			setBlockAndNotifyAdequately(worldIn, pos.add(2, 1 + i, 4), i == 1 ? chiseled_brick : getBrick(rand));
			setBlockAndNotifyAdequately(worldIn, pos.add(-2, 1 + i, 4), i == 1 ? chiseled_brick : getBrick(rand));
			setBlockAndNotifyAdequately(worldIn, pos.add(2, 1 + i, -4), i == 1 ? chiseled_brick : getBrick(rand));
			setBlockAndNotifyAdequately(worldIn, pos.add(-2, 1 + i, -4), i == 1 ? chiseled_brick : getBrick(rand));
		}

		return true;
	}

}
