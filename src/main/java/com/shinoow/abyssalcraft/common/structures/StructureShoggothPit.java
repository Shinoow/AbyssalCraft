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
package com.shinoow.abyssalcraft.common.structures;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

public class StructureShoggothPit extends WorldGenerator {

	public StructureShoggothPit() {}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		while(world.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();
		if(pos.getY() <= 1) return false;

		if(world.getBlockState(pos).getMaterial() == Material.LEAVES ||
				world.getBlockState(pos).getMaterial() == Material.WOOD ||
				world.getBlockState(pos).getMaterial() == Material.VINE ||
				world.getBlockState(pos).getMaterial() == Material.CACTUS)
			return false;
		else {

			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			for(int i = -5; i < 6; i++)
				for(int j = 0; j < 18; j++)
					for(int k = 0; k < 5; k++)
						world.setBlockToAir(new BlockPos(x + i, y - 2 - k, z - 9 - j));
			for(int i = -3; i < 4; i++)
				for(int j = 0; j < 6; j++){
					world.setBlockToAir(new BlockPos(x + i, y, z - 1 - j));
					world.setBlockToAir(new BlockPos(x + i, y - 1, z - 2 - j));
					world.setBlockToAir(new BlockPos(x + i, y - 2, z - 3 - j));
					world.setBlockToAir(new BlockPos(x + i, y - 3, z - 4 - j));
					world.setBlockToAir(new BlockPos(x + i, y - 4, z - 5 - j));
					world.setBlockToAir(new BlockPos(x + i, y - 5, z - 6 - j));
					world.setBlockToAir(new BlockPos(x + i, y - 6, z - 7 - j));
				}
			for(int i = -4; i < 5; i++){
				world.setBlockState(new BlockPos(x + i, y, z), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + i, y, z - 7), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + i, y - 1, z - 1), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + i, y - 1, z - 8), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + i, y - 2, z - 2), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + i, y - 3, z - 3), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + i, y - 4, z - 4), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + i, y - 5, z - 5), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + i, y - 6, z - 6), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + i, y - 7, z - 7), ACBlocks.monolith_stone.getDefaultState(), 2);
			}
			for(int i = 1; i < 7; i++){
				world.setBlockState(new BlockPos(x - 4, y, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 4, y, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x - 4, y - 1, z - 1 - i), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 4, y - 1, z - 1 - i), ACBlocks.monolith_stone.getDefaultState(), 2);
			}
			for(int i = 2; i < 8; i++){
				world.setBlockState(new BlockPos(x - 4, y - 2, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 4, y - 2, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
			}
			for(int i = 3; i < 8; i++){
				world.setBlockState(new BlockPos(x - 4, y - 3, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 4, y - 3, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
			}
			for(int i = 4; i < 8; i++){
				world.setBlockState(new BlockPos(x - 4, y - 4, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 4, y - 4, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
			}
			for(int i = 5; i < 8; i++){
				world.setBlockState(new BlockPos(x - 4, y - 5, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 4, y - 5, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
			}
			for(int i = 6; i < 8; i++){
				world.setBlockState(new BlockPos(x - 4, y - 6, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 4, y - 6, z - i), ACBlocks.monolith_stone.getDefaultState(), 2);
			}
			for(int i = 2; i < 7; i++){
				world.setBlockState(new BlockPos(x - 5, y - i, z - 8), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x - 4, y - i, z - 8), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 5, y - i, z - 8), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 4, y - i, z - 8), ACBlocks.monolith_stone.getDefaultState(), 2);
			}
			for(int i = -6; i < 7; i++)
				for(int j = 0; j < 19; j++){
					world.setBlockState(new BlockPos(x + i, y - 1, z - 8 - j), ACBlocks.monolith_stone.getDefaultState(), 2);
					world.setBlockState(new BlockPos(x + i, y - 7, z - 8 - j), ACBlocks.monolith_stone.getDefaultState(), 2);
				}
			for(int i = 0; i < 19; i++)
				for(int j = 0; j < 7; j++){
					world.setBlockState(new BlockPos(x - 6, y - 1 - j, z - 8 - i), ACBlocks.monolith_stone.getDefaultState(), 2);
					world.setBlockState(new BlockPos(x + 6, y - 1 - j, z - 8 - i), ACBlocks.monolith_stone.getDefaultState(), 2);
				}
			for(int i = -6; i < 7; i++)
				for(int j = 0; j < 7; j++)
					world.setBlockState(new BlockPos(x + i, y - 1 - j, z - 27), ACBlocks.monolith_stone.getDefaultState(), 2);
			for(int i = 0; i < 2; i++){
				world.setBlockState(new BlockPos(x - 5, y - 5 - i, z - 10), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x - 5, y - 5 - i, z - 12), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x - 5, y - 5 - i, z - 14), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x - 5, y - 5 - i, z - 16), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 5, y - 5 - i, z - 10), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 5, y - 5 - i, z - 12), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 5, y - 5 - i, z - 14), ACBlocks.monolith_stone.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 5, y - 5 - i, z - 16), ACBlocks.monolith_stone.getDefaultState(), 2);
			}
			for(int i = -4; i < 5; i++)
				for(int j = 0; j < 9; j++)
					world.setBlockState(new BlockPos(x + i, y - 8, z - 17 - j), ACBlocks.monolith_stone.getDefaultState(), 2);
			for(int i = -3; i < 4; i++)
				for(int j = 0; j < 7; j++)
					world.setBlockState(new BlockPos(x + i, y - 9, z - 18 - j), ACBlocks.monolith_stone.getDefaultState(), 2);
			for(int i = -2; i < 3; i++)
				for(int j = 0; j < 9; j++)
					world.setBlockToAir(new BlockPos(x + i, y - 7, z - 17 - j));
			for(int i = 0; i < 7; i++){
				world.setBlockToAir(new BlockPos(x - 3, y - 7, z - 18 - i));
				world.setBlockToAir(new BlockPos(x + 3, y - 7, z - 18 - i));
			}
			for(int i = 0; i < 5; i++){
				world.setBlockToAir(new BlockPos(x - 4, y - 7, z - 19 - i));
				world.setBlockToAir(new BlockPos(x + 4, y - 7, z - 19 - i));
			}
			for(int i = -2; i < 3; i++)
				for(int j = 0; j < 7; j++)
					world.setBlockState(new BlockPos(x + i, y - 8, z - 18 - j), ACBlocks.shoggoth_biomass.getDefaultState(), 2);
			for(int i = 0; i < 5; i++){
				world.setBlockState(new BlockPos(x - 3, y - 8, z - 19 - i), ACBlocks.shoggoth_biomass.getDefaultState(), 2);
				world.setBlockState(new BlockPos(x + 3, y - 8, z - 19 - i), ACBlocks.shoggoth_biomass.getDefaultState(), 2);
			}
			return true;
		}
	}
}
