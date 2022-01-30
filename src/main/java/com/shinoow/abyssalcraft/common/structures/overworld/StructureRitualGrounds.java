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

public class StructureRitualGrounds extends StructureDarklandsBase {

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {

		IBlockState glowing_brick = ACBlocks.glowing_darkstone_bricks.getDefaultState();
		IBlockState brick_slab = ACBlocks.darkstone_brick_slab.getDefaultState();
		IBlockState cobble = ACBlocks.darkstone_cobblestone.getDefaultState();

		for(int i = -4; i < 5; i++)
			for(int j = -6; j < 7; j++){

				boolean flag = i > -2 && i < 2;
				boolean flag1 = i > -4 && i < 4;

				for(int k = 0; k < 2; k++){
					if(j == -6 || j == 6)
						if(flag){
							worldIn.setBlockToAir(pos.add(i, k+2, j));
							worldIn.setBlockToAir(pos.add(j, k+2, i));
						}
					if(j == -5 || j == 5)
						if(flag1){
							worldIn.setBlockToAir(pos.add(i, k+2, j));
							worldIn.setBlockToAir(pos.add(j, k+2, i));
						}
					if(j > -5 && j < 5){
						worldIn.setBlockToAir(pos.add(i, k+2, j));
						worldIn.setBlockToAir(pos.add(j, k+2, i));
					}
				}

				if(j == -6 || j == 6)
					if(flag){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 1, i), brick_slab);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 1, j), brick_slab);
					}
				if(j == -5 || j == 5)
					if(flag1){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 1, i), flag ? getBrick(rand) : brick_slab);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 1, j), flag ? getBrick(rand) : brick_slab);
					}
				if(j == -4 || j == 4){
					setBlockAndNotifyAdequately(worldIn, pos.add(j, 1, i), i == -4 || i == 4 ? brick_slab : flag ? cobble : getBrick(rand));
					setBlockAndNotifyAdequately(worldIn, pos.add(i, 1, j), i == -4 || i == 4 ? brick_slab : flag ? cobble : getBrick(rand));
				}
				if(j > -4 && j < 4)
					if(flag1){
						setBlockAndNotifyAdequately(worldIn, pos.add(j, 1, i), cobble);
						setBlockAndNotifyAdequately(worldIn, pos.add(i, 1, j), cobble);
					}

			}

		setBlockAndNotifyAdequately(worldIn, pos.up(2), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(3, 2, 0), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(-3, 2, 0), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(0, 2, 3), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(0, 2, -3), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(2, 2, 2), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(2, 2, -2), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(-2, 2, 2), cobble);
		setBlockAndNotifyAdequately(worldIn, pos.add(-2, 2, -2), cobble);

		for(int i = 0; i < 2; i++){
			setBlockAndNotifyAdequately(worldIn, pos.add(4, 2 + i, 2), i == 0 ? getBrick(rand) : glowing_brick);
			setBlockAndNotifyAdequately(worldIn, pos.add(4, 2 + i, -2), i == 0 ? getBrick(rand) : glowing_brick);
			setBlockAndNotifyAdequately(worldIn, pos.add(-4, 2 + i, 2), i == 0 ? getBrick(rand) : glowing_brick);
			setBlockAndNotifyAdequately(worldIn, pos.add(-4, 2 + i, -2), i == 0 ? getBrick(rand) : glowing_brick);

			setBlockAndNotifyAdequately(worldIn, pos.add(2, 2 + i, 4), i == 0 ? getBrick(rand) : glowing_brick);
			setBlockAndNotifyAdequately(worldIn, pos.add(-2, 2 + i, 4), i == 0 ? getBrick(rand) : glowing_brick);
			setBlockAndNotifyAdequately(worldIn, pos.add(2, 2 + i, -4), i == 0 ? getBrick(rand) : glowing_brick);
			setBlockAndNotifyAdequately(worldIn, pos.add(-2, 2 + i, -4), i == 0 ? getBrick(rand) : glowing_brick);
		}

		return true;
	}
}
