/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class StructureShoggothPit extends WorldGenerator {

	public StructureShoggothPit() {}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		while(world.isAirBlock(x, y, z) && y > 2)
			--y;
		if(y <= 1) return false;

		if(world.getBlock(x, y, z).getMaterial() == Material.leaves ||
				world.getBlock(x, y, z).getMaterial() == Material.wood ||
				world.getBlock(x, y, z).getMaterial() == Material.vine ||
				world.getBlock(x, y, z).getMaterial() == Material.cactus ||
				world.getBlock(x, y, z).getMaterial() == Material.plants)
			return false;
		else {

			for(int i = -5; i < 6; i++)
				for(int j = 0; j < 18; j++)
					for(int k = 0; k < 5; k++)
						world.setBlockToAir(x + i, y - 2 - k, z - 9 - j);
			for(int i = -3; i < 4; i++)
				for(int j = 0; j < 6; j++){
					world.setBlockToAir(x + i, y, z - 1 - j);
					world.setBlockToAir(x + i, y - 1, z - 2 - j);
					world.setBlockToAir(x + i, y - 2, z - 3 - j);
					world.setBlockToAir(x + i, y - 3, z - 4 - j);
					world.setBlockToAir(x + i, y - 4, z - 5 - j);
					world.setBlockToAir(x + i, y - 5, z - 6 - j);
					world.setBlockToAir(x + i, y - 6, z - 7 - j);
				}
			for(int i = -4; i < 5; i++){
				world.setBlock(x + i, y, z, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + i, y, z - 7, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + i, y - 1, z - 1, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + i, y - 1, z - 8, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + i, y - 2, z - 2, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + i, y - 3, z - 3, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + i, y - 4, z - 4, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + i, y - 5, z - 5, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + i, y - 6, z - 6, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + i, y - 7, z - 7, AbyssalCraft.monolithStone, 0, 2);
			}
			for(int i = 1; i < 7; i++){
				world.setBlock(x - 4, y, z - i, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 4, y, z - i, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x - 4, y - 1, z - 1 - i, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 4, y - 1, z - 1 - i, AbyssalCraft.monolithStone, 0, 2);
			}
			for(int i = 2; i < 8; i++){
				world.setBlock(x - 4, y - 2, z - i, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 4, y - 2, z - i, AbyssalCraft.monolithStone, 0, 2);
			}
			for(int i = 3; i < 8; i++){
				world.setBlock(x - 4, y - 3, z - i, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 4, y - 3, z - i, AbyssalCraft.monolithStone, 0, 2);
			}
			for(int i = 4; i < 8; i++){
				world.setBlock(x - 4, y - 4, z - i, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 4, y - 4, z - i, AbyssalCraft.monolithStone, 0, 2);
			}
			for(int i = 5; i < 8; i++){
				world.setBlock(x - 4, y - 5, z - i, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 4, y - 5, z - i, AbyssalCraft.monolithStone, 0, 2);
			}
			for(int i = 6; i < 8; i++){
				world.setBlock(x - 4, y - 6, z - i, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 4, y - 6, z - i, AbyssalCraft.monolithStone, 0, 2);
			}
			for(int i = 2; i < 7; i++){
				world.setBlock(x - 5, y - i, z - 8, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x - 4, y - i, z - 8, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 5, y - i, z - 8, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 4, y - i, z - 8, AbyssalCraft.monolithStone, 0, 2);
			}
			for(int i = -6; i < 7; i++)
				for(int j = 0; j < 19; j++){
					world.setBlock(x + i, y - 1, z - 8 - j, AbyssalCraft.monolithStone, 0, 2);
					world.setBlock(x + i, y - 7, z - 8 - j, AbyssalCraft.monolithStone, 0, 2);
				}
			for(int i = 0; i < 19; i++)
				for(int j = 0; j < 7; j++){
					world.setBlock(x - 6, y - 1 - j, z - 8 - i, AbyssalCraft.monolithStone, 0, 2);
					world.setBlock(x + 6, y - 1 - j, z - 8 - i, AbyssalCraft.monolithStone, 0, 2);
				}
			for(int i = -6; i < 7; i++)
				for(int j = 0; j < 7; j++)
					world.setBlock(x + i, y - 1 - j, z - 27, AbyssalCraft.monolithStone, 0, 2);
			for(int i = 0; i < 2; i++){
				world.setBlock(x - 5, y - 5 - i, z - 10, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x - 5, y - 5 - i, z - 12, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x - 5, y - 5 - i, z - 14, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x - 5, y - 5 - i, z - 16, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 5, y - 5 - i, z - 10, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 5, y - 5 - i, z - 12, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 5, y - 5 - i, z - 14, AbyssalCraft.monolithStone, 0, 2);
				world.setBlock(x + 5, y - 5 - i, z - 16, AbyssalCraft.monolithStone, 0, 2);
			}
			for(int i = -4; i < 5; i++)
				for(int j = 0; j < 9; j++)
					world.setBlock(x + i, y - 8, z - 17 - j, AbyssalCraft.monolithStone, 0, 2);
			for(int i = -3; i < 4; i++)
				for(int j = 0; j < 7; j++)
					world.setBlock(x + i, y - 9, z - 18 - j, AbyssalCraft.monolithStone, 0, 2);
			for(int i = -2; i < 3; i++)
				for(int j = 0; j < 9; j++)
					world.setBlockToAir(x + i, y - 7, z - 17 - j);
			for(int i = 0; i < 7; i++){
				world.setBlockToAir(x - 3, y - 7, z - 18 - i);
				world.setBlockToAir(x + 3, y - 7, z - 18 - i);
			}
			for(int i = 0; i < 5; i++){
				world.setBlockToAir(x - 4, y - 7, z - 19 - i);
				world.setBlockToAir(x + 4, y - 7, z - 19 - i);
			}
			for(int i = -2; i < 3; i++)
				for(int j = 0; j < 7; j++)
					world.setBlock(x + i, y - 8, z - 18 - j, AbyssalCraft.shoggothBiomass, 0, 2);
			for(int i = 0; i < 5; i++){
				world.setBlock(x - 3, y - 8, z - 19 - i, AbyssalCraft.shoggothBiomass, 0, 2);
				world.setBlock(x + 3, y - 8, z - 19 - i, AbyssalCraft.shoggothBiomass, 0, 2);
			}
			return true;
		}
	}
}
