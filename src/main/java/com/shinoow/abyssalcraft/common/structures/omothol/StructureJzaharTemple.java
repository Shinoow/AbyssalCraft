/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures.omothol;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.BlockDarkEthaxiumBrick;
import com.shinoow.abyssalcraft.common.blocks.BlockEthaxiumBrick.EnumBrickType;
import com.shinoow.abyssalcraft.common.util.RitualUtil;

public class StructureJzaharTemple extends WorldGenerator {

	public StructureJzaharTemple() {}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		while(world.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();
		if(pos.getY() <= 1) return false;

		firstFloor(world, rand, pos);
		secondFloor(world, rand, pos);

		return true;
	}

	private void firstFloor(World world, Random rand, BlockPos pos){//TODO: first floor starts here
		for(int x = -10; x < 11; x++)
			for(int y = 1; y < 11; y++)
				for(int z = 67; z < 90; z++)
					world.setBlockToAir(pos.add(x, y, z));

		for(int x = -11; x < 12; x++)
			for(int z = 66; z < 91; z++){
				world.setBlockState(pos.add(x, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				world.setBlockState(pos.add(x, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				for(int y = 0; y < 11; y++){
					world.setBlockState(pos.add(11, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(-11, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, y, 90), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, y, 66), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					if(y > 0){
						world.setBlockState(pos.add(10, y, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(-10, y, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(10, y, 67), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(-10, y, 67), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						if(y == 1 || y == 10){
							world.setBlockState(pos.add(x, y, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							world.setBlockState(pos.add(-10, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							world.setBlockState(pos.add(10, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						}
						if(y > 1 && y < 10){
							if(x < 11)
								world.setBlockState(pos.add(x, y, 89), Blocks.bookshelf.getDefaultState(), 2);
							if(z > 66 && z < 90){
								world.setBlockState(pos.add(-10, y, z), Blocks.bookshelf.getDefaultState(), 2);
								world.setBlockState(pos.add(10, y, z), Blocks.bookshelf.getDefaultState(), 2);
							}
						}
					}
				}
			}
		for(int x = -5; x < 6; x++)
			for(int z = -5; z < 6; z++)
				world.setBlockState(pos.add(x, 1, z + 78), AbyssalCraft.darkethaxiumslab1.getDefaultState(), 2);
		for(int x = -5; x < 6; x++)
			for(int z = -5; z < 6; z++){
				if(x == -5 || x == 5)
					if(z < -2 || z > 2)
						world.setBlockToAir(pos.add(x, 1, z + 78));
				if(z == -5 || z == 5)
					if(x < -2 || x > 2)
						world.setBlockToAir(pos.add(x, 1, z + 78));
			}
		for(int x = -4; x < 5; x++)
			for(int z = -4; z < 5; z++)
				world.setBlockState(pos.add(x, 1, z + 78), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		for(int x = -4; x < 5; x++)
			for(int z = -4; z < 5; z++){
				if(x == -4 || x == 4)
					if(z < -1 || z > 1)
						world.setBlockState(pos.add(x, 1, z + 78), AbyssalCraft.darkethaxiumslab1.getDefaultState(), 2);
				if(z == -4 || z == 4)
					if(x < -1 || x > 1)
						world.setBlockState(pos.add(x, 1, z + 78), AbyssalCraft.darkethaxiumslab1.getDefaultState(), 2);
			}
		world.setBlockState(pos.add(0, 2, 78), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		world.setBlockState(pos.add(0, 2, 75), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		world.setBlockState(pos.add(0, 2, 81), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		world.setBlockState(pos.add(-3, 2, 78), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		world.setBlockState(pos.add(3, 2, 78), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		world.setBlockState(pos.add(-2, 2, 76), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		world.setBlockState(pos.add(2, 2, 76), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		world.setBlockState(pos.add(-2, 2, 80), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		world.setBlockState(pos.add(2, 2, 80), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
		RitualUtil.tryAltar(world, pos.add(0, 2, 78), 4);
		for(int x = -2; x < 3; x++)
			for(int y = 1; y < 7; y++)
				world.setBlockToAir(pos.add(x, y, 66));
		for(int x = -7; x < 8; x++)
			for(int y = 0; y < 12; y++)
				for(int z = 0; z < 66; z++){
					world.setBlockState(pos.add(x, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					if(z > 5 && z < 61){
						world.setBlockState(pos.add(-8, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(8, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 7 && z < 59){
						world.setBlockState(pos.add(-9, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(9, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 9 && z < 57){
						world.setBlockState(pos.add(-10, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(10, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 11 && z < 55){
						world.setBlockState(pos.add(-11, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(11, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 13 && z < 53){
						world.setBlockState(pos.add(-12, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(12, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 15 && z < 51){
						world.setBlockState(pos.add(-13, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(13, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 17 && z < 49){
						world.setBlockState(pos.add(-14, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(14, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 20 && z < 46){
						world.setBlockState(pos.add(-15, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(15, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 24 && z < 42){
						world.setBlockState(pos.add(-16, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(16, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 29 && z < 37){
						world.setBlockState(pos.add(-17, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(17, 0, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}

					if(z > -1 && z < 66){
						world.setBlockState(pos.add(-8, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(8, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 5 && z < 61){
						world.setBlockState(pos.add(-9, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(9, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 7 && z < 59){
						world.setBlockState(pos.add(-10, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(10, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 9 && z < 57){
						world.setBlockState(pos.add(-11, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(11, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 11 && z < 55){
						world.setBlockState(pos.add(-12, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(12, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 13 && z < 53){
						world.setBlockState(pos.add(-13, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(13, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 15 && z < 51){
						world.setBlockState(pos.add(-14, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(14, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 17 && z < 49){
						world.setBlockState(pos.add(-15, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(15, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 20 && z < 46){
						world.setBlockState(pos.add(-16, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(16, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 24 && z < 42){
						world.setBlockState(pos.add(-17, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(17, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 29 && z < 37){
						world.setBlockState(pos.add(-18, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(18, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}

					if(y > 0 && y < 11){
						world.setBlockToAir(pos.add(x, y, z));
						if(z > 5 && z < 61){
							world.setBlockToAir(pos.add(-8, y, z));
							world.setBlockToAir(pos.add(8, y, z));
						}
						if(z > 7 && z < 59){
							world.setBlockToAir(pos.add(-9, y, z));
							world.setBlockToAir(pos.add(9, y, z));
						}
						if(z > 9 && z < 57){
							world.setBlockToAir(pos.add(-10, y, z));
							world.setBlockToAir(pos.add(10, y, z));
						}
						if(z > 11 && z < 55){
							world.setBlockToAir(pos.add(-11, y, z));
							world.setBlockToAir(pos.add(11, y, z));
						}
						if(z > 13 && z < 53){
							world.setBlockToAir(pos.add(-12, y, z));
							world.setBlockToAir(pos.add(12, y, z));
						}
						if(z > 15 && z < 51){
							world.setBlockToAir(pos.add(-13, y, z));
							world.setBlockToAir(pos.add(13, y, z));
						}
						if(z > 17 && z < 49){
							world.setBlockToAir(pos.add(-14, y, z));
							world.setBlockToAir(pos.add(14, y, z));
						}
						if(z > 20 && z < 46){
							world.setBlockToAir(pos.add(-15, y, z));
							world.setBlockToAir(pos.add(15, y, z));
						}
						if(z > 24 && z < 42){
							world.setBlockToAir(pos.add(-16, y, z));
							world.setBlockToAir(pos.add(16, y, z));
						}
						if(z > 29 && z < 37){
							world.setBlockToAir(pos.add(-17, y, z));
							world.setBlockToAir(pos.add(17, y, z));
						}
						if(z == 1)
							world.setBlockState(pos.add(x, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						if(z == 0){
							world.setBlockState(pos.add(-5, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(-7, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(5, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(7, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
						}
					}
				}
		for(int x = -2; x < 3; x++)
			for(int y = 1; y < 7; y++)
				world.setBlockToAir(pos.add(x, y, 1));
		for(int x = -3; x < 4; x++)
			for(int y = 1; y < 8; y++){
				if(y < 7){
					world.setBlockState(pos.add(-3, y, 65), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(3, y, 65), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(-3, y, 0), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(3, y, 0), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(y == 7){
					world.setBlockState(pos.add(x, y, 65), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, y, 0), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
			}
		world.setBlockState(pos.add(0, 7, 65), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		world.setBlockState(pos.add(0, 7, 0), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		for(int z = -2; z < 3; z++)
			for(int y = 1; y < 7; y++){
				world.setBlockToAir(pos.add(-18, y, z + 33));
				world.setBlockToAir(pos.add(18, y, z + 33));
			}
		for(int z = -3; z < 4; z++)
			for(int y = 1; y < 8; y++){
				if(y < 7){
					world.setBlockState(pos.add(-17, y, 36), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(17, y, 36), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(-17, y, 30), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(17, y, 30), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(y == 7){
					world.setBlockState(pos.add(-17, y, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(17, y, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
			}
		world.setBlockState(pos.add(-17, 7, 33), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		world.setBlockState(pos.add(17, 7, 33), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		for(int z = 8; z < 59; z++)
			for(int y = 1; y < 11; y++){
				if(z == 8 || z == 58){
					world.setBlockState(pos.add(-6, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(6, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 12 || z == 54){
					world.setBlockState(pos.add(-8, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(8, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 16 || z == 50){
					world.setBlockState(pos.add(-10, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(10, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 21 || z == 45){
					world.setBlockState(pos.add(-12, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(12, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 25 || z == 41){
					world.setBlockState(pos.add(-13, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(13, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 30 || z == 36){
					world.setBlockState(pos.add(-14, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(14, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
			}
		for(int x = 0; x < 10; x++)
			for(int z = -3; z < 4; z++){
				world.setBlockState(pos.add(x + 19, 0, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				world.setBlockState(pos.add(x + 19, 7, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				for(int y = 1; y < 8; y++){
					world.setBlockState(pos.add(x + 19, y, 36), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x + 19, y, 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
			}
		for(int x = 0; x < 10; x++)
			for(int z = -2; z < 3; z++)
				for(int y = 1; y < 7; y++)
					world.setBlockToAir(pos.add(x + 19, y, z + 33));
		for(int x = 0; x < 8; x++)
			for(int y = 0; y < 19; y++)
				for(int z = 0; z < 16; z++){
					world.setBlockState(pos.add(x + 29, y, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					if(y > 0 && y < 18 && x > 0 && x < 7 && z > 0 && z < 15)
						world.setBlockToAir(pos.add(x + 29, y, z + 30));
					if(y > 0 && y < 7 || y > 11 && y < 18)
						if(x == 0)
							if(z > 0 && z < 6)
								world.setBlockToAir(pos.add(x + 29, y, z + 30));
					if(y == 11)
						if(z > 0 && z < 7 && x > 0 && x < 5){
							world.setBlockState(pos.add(x + 29, y, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(x < 4){
								world.setBlockState(pos.add(x + 29, y - 1, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
								world.setBlockState(pos.add(x + 29, y - 2, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
								world.setBlockState(pos.add(x + 29, y - 3, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
								world.setBlockState(pos.add(x + 29, y - 4, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							}
						}
					if(y == 12)
						if(x == 4)
							if(z > 0 && z < 7)
								world.setBlockState(pos.add(x + 29, y, z + 30), AbyssalCraft.darkethaxiumfence.getDefaultState(), 2);
					if(y < 12){
						if(y < 7)
							if(z > 11)
								world.setBlockState(pos.add(x + 29, y, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						if(x > 0 && x < 4){
							if(y <= 11)
								world.setBlockState(pos.add(x + 29, y, 36), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 10)
								world.setBlockState(pos.add(x + 29, y, 37), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 9)
								world.setBlockState(pos.add(x + 29, y, 38), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 8)
								world.setBlockState(pos.add(x + 29, y, 39), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 7)
								world.setBlockState(pos.add(x + 29, y, 40), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 6)
								world.setBlockState(pos.add(x + 29, y, 41), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							world.setBlockState(pos.add(x + 29, 11, 37), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
							world.setBlockState(pos.add(x + 29, 10, 38), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
							world.setBlockState(pos.add(x + 29, 9, 39), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
							world.setBlockState(pos.add(x + 29, 8, 40), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
							world.setBlockState(pos.add(x + 29, 7, 41), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
						}
						if(x > 3 && x < 7){
							if(y <= 1)
								world.setBlockState(pos.add(x + 29, y, 37), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 2)
								world.setBlockState(pos.add(x + 29, y, 38), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 3)
								world.setBlockState(pos.add(x + 29, y, 39), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 4)
								world.setBlockState(pos.add(x + 29, y, 40), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 5)
								world.setBlockState(pos.add(x + 29, y, 41), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							world.setBlockState(pos.add(x + 29, 1, 36), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x + 29, 2, 37), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x + 29, 3, 38), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x + 29, 4, 39), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x + 29, 5, 40), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x + 29, 6, 41), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
						}
					}
				}
		for(int x = -9; x < 1; x++)
			for(int z = -3; z < 4; z++){
				world.setBlockState(pos.add(x - 19, 0, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				world.setBlockState(pos.add(x - 19, 7, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				for(int y = 1; y < 8; y++){
					world.setBlockState(pos.add(x - 19, y, 36), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x - 19, y, 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
			}
		for(int x = -9; x < 1; x++)
			for(int z = -2; z < 3; z++)
				for(int y = 1; y < 7; y++)
					world.setBlockToAir(pos.add(x - 19, y, z + 33));
		for(int x = 0; x < 8; x++)
			for(int y = 0; y < 19; y++)
				for(int z = 0; z < 16; z++){
					world.setBlockState(pos.add(x - 36, y, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					if(y > 0 && y < 18 && x > 0 && x < 7 && z > 0 && z < 15)
						world.setBlockToAir(pos.add(x - 36, y, z + 30));
					if(y > 0 && y < 7 || y > 11 && y < 18)
						if(x == 7)
							if(z > 0 && z < 6)
								world.setBlockToAir(pos.add(x - 36, y, z + 30));
					if(y == 11)
						if(z > 0 && z < 7 && x > 2 && x < 8){
							world.setBlockState(pos.add(x - 36, y, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(x > 3){
								world.setBlockState(pos.add(x - 36, y - 1, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
								world.setBlockState(pos.add(x - 36, y - 2, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
								world.setBlockState(pos.add(x - 36, y - 3, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
								world.setBlockState(pos.add(x - 36, y - 4, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							}
						}
					if(y == 12)
						if(x == 3)
							if(z > 0 && z < 7)
								world.setBlockState(pos.add(x - 36, y, z + 30), AbyssalCraft.darkethaxiumfence.getDefaultState(), 2);
					if(y < 12){
						if(y < 7)
							if(z > 11)
								world.setBlockState(pos.add(x - 36, y, z + 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						if(x > 3 && x < 7){
							if(y <= 11)
								world.setBlockState(pos.add(x - 36, y, 36), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 10)
								world.setBlockState(pos.add(x - 36, y, 37), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 9)
								world.setBlockState(pos.add(x - 36, y, 38), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 8)
								world.setBlockState(pos.add(x - 36, y, 39), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 7)
								world.setBlockState(pos.add(x - 36, y, 40), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 6)
								world.setBlockState(pos.add(x - 36, y, 41), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							world.setBlockState(pos.add(x - 36, 11, 37), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
							world.setBlockState(pos.add(x - 36, 10, 38), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
							world.setBlockState(pos.add(x - 36, 9, 39), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
							world.setBlockState(pos.add(x - 36, 8, 40), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
							world.setBlockState(pos.add(x - 36, 7, 41), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(3), 2);
						}
						if(x > 0 && x < 4){
							if(y <= 1)
								world.setBlockState(pos.add(x - 36, y, 37), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 2)
								world.setBlockState(pos.add(x - 36, y, 38), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 3)
								world.setBlockState(pos.add(x - 36, y, 39), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 4)
								world.setBlockState(pos.add(x - 36, y, 40), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							if(y <= 5)
								world.setBlockState(pos.add(x - 36, y, 41), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
							world.setBlockState(pos.add(x - 36, 1, 36), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x - 36, 2, 37), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x - 36, 3, 38), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x - 36, 4, 39), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x - 36, 5, 40), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
							world.setBlockState(pos.add(x - 36, 6, 41), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(2), 2);
						}
					}
				}
		world.setBlockState(pos.add(0, 1, 4), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
		world.setBlockState(pos.add(0, 1, 66), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
		world.setBlockState(pos.add(-18, 1, 33), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
		world.setBlockState(pos.add(18, 1, 33), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
		world.setBlockState(pos.add(0, 1, 33), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
	}

	private void secondFloor(World world, Random rand, BlockPos pos){//TODO: second floor starts here
		for(int x = -7; x < 8; x++)
			for(int y = 12; y < 24; y++)
				for(int z = 67; z < 90; z++)
					world.setBlockToAir(pos.add(x, y, z));
		for(int x = -8; x < 9; x++)
			for(int z = 66; z < 91; z++){
				world.setBlockState(pos.add(x, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				for(int y = 11; y < 24; y++){
					world.setBlockState(pos.add(8, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(-8, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, y, 90), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, y, 66), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					if(y > 11)
						if(x == -7 || x == 7){
							world.setBlockState(pos.add(x, y, 67), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 69), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 71), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 73), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 75), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 77), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 79), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 81), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 83), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 85), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 87), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
							world.setBlockState(pos.add(x, y, 89), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
						}
				}
			}
		for(int x = -2; x < 3; x++)
			for(int z = 77; z < 81; z++){
				world.setBlockState(pos.add(x, 12, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				if(x == -2 || x == 2){
					world.setBlockState(pos.add(x, 13, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					if(z > 77)
						world.setBlockState(pos.add(x, 14, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
				if(x > -2 && x < 2){
					world.setBlockState(pos.add(x, 13, 80), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 14, 80), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 15, 80), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
			}
		world.setBlockState(pos.add(0, 13, 78), AbyssalCraft.jzaharspawner.getDefaultState(), 2);
		world.setBlockState(pos.add(-1, 12, 76), AbyssalCraft.darkethaxiumslab1.getDefaultState(), 2);
		world.setBlockState(pos.add(0, 12, 76), AbyssalCraft.darkethaxiumslab1.getDefaultState(), 2);
		world.setBlockState(pos.add(1, 12, 76), AbyssalCraft.darkethaxiumslab1.getDefaultState(), 2);
		world.setBlockState(pos.add(-2, 13, 77), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		world.setBlockState(pos.add(2, 13, 77), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		world.setBlockState(pos.add(-2, 14, 78), AbyssalCraft.darkethaxiumslab1.getDefaultState(), 2);
		world.setBlockState(pos.add(2, 14, 78), AbyssalCraft.darkethaxiumslab1.getDefaultState(), 2);
		world.setBlockState(pos.add(-2, 15, 80), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(0), 2);
		world.setBlockState(pos.add(2, 15, 80), AbyssalCraft.darkethaxiumstairs.getStateFromMeta(1), 2);
		for(int x = -3; x < 4; x++)
			for(int y = 13; y < 22; y++){
				if(x > -2 && x < 2){
					world.setBlockState(pos.add(x, 13, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 21, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
				if(x == -2 || x == 2){
					world.setBlockState(pos.add(x, 14, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 20, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
				if(y > 14 && y < 20){
					world.setBlockState(pos.add(-3, y, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(3, y, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
			}
		for(int x = -4; x < 5; x++)
			for(int y = 12; y < 23; y++){
				if(x > -2 && x < 2){
					world.setBlockState(pos.add(x, 12, 88), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 22, 88), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(x == -2 || x == 2){
					world.setBlockState(pos.add(x, 13, 88), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 21, 88), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(x == -3 || x == 3){
					world.setBlockState(pos.add(x, 14, 88), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 20, 88), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(y > 14 && y < 20){
					world.setBlockState(pos.add(-4, y, 88), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(4, y, 88), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
			}
		world.setBlockState(pos.add(-4, 17, 88), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		world.setBlockState(pos.add(4, 17, 88), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		for(int x = -7; x < 8; x++)
			for(int z = 66; z < 90; z++){
				world.setBlockState(pos.add(x, 24, 66), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				world.setBlockState(pos.add(x, 24, 89), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				world.setBlockState(pos.add(7, 24, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				world.setBlockState(pos.add(-7, 24, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				if(x > -7 && x < 7){
					world.setBlockState(pos.add(x, 25, 66), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 25, 88), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
				if(z < 88){
					world.setBlockState(pos.add(6, 25, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(-6, 25, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
				if(x > -6 && x < 6){
					world.setBlockState(pos.add(x, 26, 66), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 26, 87), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
				if(z < 87){
					world.setBlockState(pos.add(5, 26, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(-5, 26, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
				if(x > -5 && x < 5 && z < 87)
					world.setBlockState(pos.add(x, 27, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
			}
		for(int x = -7; x < 8; x++)
			for(int y = 11; y < 23; y++)
				for(int z = 5; z < 66; z++){
					world.setBlockState(pos.add(x, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x, 22, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					if(z > 5 && z < 61){
						world.setBlockState(pos.add(-8, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(8, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 7 && z < 59){
						world.setBlockState(pos.add(-9, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(9, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 9 && z < 57){
						world.setBlockState(pos.add(-10, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(10, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 11 && z < 55){
						world.setBlockState(pos.add(-11, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(11, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 13 && z < 53){
						world.setBlockState(pos.add(-12, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(12, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 15 && z < 51){
						world.setBlockState(pos.add(-13, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(13, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 17 && z < 49){
						world.setBlockState(pos.add(-14, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(14, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 20 && z < 46){
						world.setBlockState(pos.add(-15, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(15, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 24 && z < 42){
						world.setBlockState(pos.add(-16, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(16, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 29 && z < 37){
						world.setBlockState(pos.add(-17, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(17, 11, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}

					if(z > 4 && z < 66){
						world.setBlockState(pos.add(-8, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(8, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 5 && z < 61){
						world.setBlockState(pos.add(-9, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(9, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 7 && z < 59){
						world.setBlockState(pos.add(-10, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(10, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 9 && z < 57){
						world.setBlockState(pos.add(-11, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(11, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 11 && z < 55){
						world.setBlockState(pos.add(-12, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(12, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 13 && z < 53){
						world.setBlockState(pos.add(-13, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(13, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 15 && z < 51){
						world.setBlockState(pos.add(-14, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(14, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 17 && z < 49){
						world.setBlockState(pos.add(-15, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(15, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 20 && z < 46){
						world.setBlockState(pos.add(-16, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(16, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 24 && z < 42){
						world.setBlockState(pos.add(-17, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(17, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
					if(z > 29 && z < 37){
						world.setBlockState(pos.add(-18, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
						world.setBlockState(pos.add(18, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}

					if(y > 11 && y < 22){
						world.setBlockToAir(pos.add(x, y, z));
						if(z > 5 && z < 61){
							world.setBlockToAir(pos.add(-8, y, z));
							world.setBlockToAir(pos.add(8, y, z));
						}
						if(z > 7 && z < 59){
							world.setBlockToAir(pos.add(-9, y, z));
							world.setBlockToAir(pos.add(9, y, z));
						}
						if(z > 9 && z < 57){
							world.setBlockToAir(pos.add(-10, y, z));
							world.setBlockToAir(pos.add(10, y, z));
						}
						if(z > 11 && z < 55){
							world.setBlockToAir(pos.add(-11, y, z));
							world.setBlockToAir(pos.add(11, y, z));
						}
						if(z > 13 && z < 53){
							world.setBlockToAir(pos.add(-12, y, z));
							world.setBlockToAir(pos.add(12, y, z));
						}
						if(z > 15 && z < 51){
							world.setBlockToAir(pos.add(-13, y, z));
							world.setBlockToAir(pos.add(13, y, z));
						}
						if(z > 17 && z < 49){
							world.setBlockToAir(pos.add(-14, y, z));
							world.setBlockToAir(pos.add(14, y, z));
						}
						if(z > 20 && z < 46){
							world.setBlockToAir(pos.add(-15, y, z));
							world.setBlockToAir(pos.add(15, y, z));
						}
						if(z > 24 && z < 42){
							world.setBlockToAir(pos.add(-16, y, z));
							world.setBlockToAir(pos.add(16, y, z));
						}
						if(z > 29 && z < 37){
							world.setBlockToAir(pos.add(-17, y, z));
							world.setBlockToAir(pos.add(17, y, z));
						}
						if(z == 5)
							world.setBlockState(pos.add(x, y, z), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					}
				}
		for(int x = -2; x < 3; x++)
			for(int y = 12; y < 18; y++)
				world.setBlockToAir(pos.add(x, y, 66));
		for(int x = -3; x < 4; x++)
			for(int y = 12; y < 19; y++){
				if(y < 18){
					world.setBlockState(pos.add(-3, y, 65), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(3, y, 65), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(y == 18)
					world.setBlockState(pos.add(x, y, 65), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
			}
		world.setBlockState(pos.add(0, 18, 65), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		for(int z = -2; z < 3; z++)
			for(int y = 12; y < 18; y++){
				world.setBlockToAir(pos.add(-18, y, z + 33));
				world.setBlockToAir(pos.add(18, y, z + 33));
			}
		for(int z = -3; z < 4; z++)
			for(int y = 12; y < 19; y++){
				if(y < 18){
					world.setBlockState(pos.add(-17, y, 36), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(17, y, 36), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(-17, y, 30), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(17, y, 30), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(y == 18){
					world.setBlockState(pos.add(-17, y, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(17, y, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
			}
		world.setBlockState(pos.add(-17, 18, 33), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		world.setBlockState(pos.add(17, 18, 33), AbyssalCraft.darkethaxiumbrick.getDefaultState().withProperty(BlockDarkEthaxiumBrick.TYPE, EnumBrickType.CHISELED), 2);
		for(int z = 8; z < 60; z++)
			for(int y = 12; y < 22; y++){
				if(z == 8 || z == 58){
					world.setBlockState(pos.add(-6, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(6, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 12 || z == 54){
					world.setBlockState(pos.add(-8, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(8, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 16 || z == 50){
					world.setBlockState(pos.add(-10, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(10, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 21 || z == 45){
					world.setBlockState(pos.add(-12, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(12, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 25 || z == 41){
					world.setBlockState(pos.add(-13, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(13, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
				if(z == 30 || z == 36){
					world.setBlockState(pos.add(-14, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
					world.setBlockState(pos.add(14, y, z), AbyssalCraft.darkethaxiumpillar.getDefaultState(), 2);
				}
			}
		for(int x = 0; x < 10; x++)
			for(int z = -3; z < 4; z++){
				world.setBlockState(pos.add(x + 19, 11, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				world.setBlockState(pos.add(x + 19, 18, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				for(int y = 12; y < 19; y++){
					world.setBlockState(pos.add(x + 19, y, 36), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x + 19, y, 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
			}
		for(int x = 0; x < 10; x++)
			for(int z = -2; z < 3; z++)
				for(int y = 12; y < 18; y++)
					world.setBlockToAir(pos.add(x + 19, y, z + 33));
		for(int x = -9; x < 1; x++)
			for(int z = -3; z < 4; z++){
				world.setBlockState(pos.add(x - 19, 11, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				world.setBlockState(pos.add(x - 19, 18, z + 33), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				for(int y = 12; y < 19; y++){
					world.setBlockState(pos.add(x - 19, y, 36), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
					world.setBlockState(pos.add(x - 19, y, 30), AbyssalCraft.darkethaxiumbrick.getDefaultState(), 2);
				}
			}
		for(int x = -9; x < 1; x++)
			for(int z = -2; z < 3; z++)
				for(int y = 12; y < 18; y++)
					world.setBlockToAir(pos.add(x - 19, y, z + 33));
		world.setBlockState(pos.add(0, 12, 7), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
		world.setBlockState(pos.add(0, 12, 66), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
		world.setBlockState(pos.add(-18, 12, 33), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
		world.setBlockState(pos.add(18, 12, 33), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
		world.setBlockState(pos.add(0, 12, 33), AbyssalCraft.gatekeeperminionspawner.getDefaultState(), 2);
	}
}