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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.RitualUtil;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class StructureJzaharTemple extends WorldGenerator {

	public StructureJzaharTemple() {}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		while(world.isAirBlock(x, y, z) && y > 2)
			--y;
		if(y <= 1) return false;

		firstFloor(world, rand, x, y, z);
		secondFloor(world, rand, x, y, z);
		return true;
	}

	private void firstFloor(World world, Random rand, int x, int y, int z){//TODO: first floor starts here
		for(int x1 = -10; x1 < 11; x1++)
			for(int y1 = 1; y1 < 11; y1++)
				for(int z1 = 67; z1 < 90; z1++)
					world.setBlockToAir(x + x1, y+ y1, z+ z1);

		for(int x1 = -11; x1 < 12; x1++)
			for(int z1 = 66; z1 < 91; z1++){
				world.setBlock(x+x1, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
				world.setBlock(x+x1, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
				for(int y1 = 0; y1 < 11; y1++){
					world.setBlock(x+11, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x-11, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+y1, z+90, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+y1, z+66, AbyssalCraft.darkethaxiumbrick, 0, 2);
					if(y1 > 0){
						world.setBlock(x+10, y+y1, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x-10, y+y1, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+10, y+y1, z+67, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x-10, y+y1, z+67, AbyssalCraft.darkethaxiumbrick, 0, 2);
						if(y1 == 1 || y1 == 10){
							world.setBlock(x+x1, y+y1, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
							world.setBlock(x-10, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
							world.setBlock(x+10, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						}
						if(y1 > 1 && y1 < 10){
							if(x1 < 11)
								world.setBlock(x+x1, y+y1, z+89, Blocks.bookshelf, 0, 2);
							if(z1 > 66 && z1 < 90){
								world.setBlock(x-10, y+y1, z+z1, Blocks.bookshelf, 0, 2);
								world.setBlock(x+10, y+y1, z+z1, Blocks.bookshelf, 0, 2);
							}
						}
					}
				}
			}
		for(int x1 = -5; x1 < 6; x1++)
			for(int z1 = -5; z1 < 6; z1++)
				world.setBlock(x+x1, y+1, z+z1 + 78, AbyssalCraft.darkethaxiumslab1, 0, 2);
		for(int x1 = -5; x1 < 6; x1++)
			for(int z1 = -5; z1 < 6; z1++){
				if(x1 == -5 || x1 == 5)
					if(z1 < -2 || z1 > 2)
						world.setBlockToAir(x+x1, y+1, z+z1 + 78);
				if(z1 == -5 || z1 == 5)
					if(x1 < -2 || x1 > 2)
						world.setBlockToAir(x+x1, y+1, z+z1 + 78);
			}
		for(int x1 = -4; x1 < 5; x1++)
			for(int z1 = -4; z1 < 5; z1++)
				world.setBlock(x+x1, y+1, z+z1 + 78, AbyssalCraft.darkethaxiumbrick, 0, 2);
		for(int x1 = -4; x1 < 5; x1++)
			for(int z1 = -4; z1 < 5; z1++){
				if(x1 == -4 || x1 == 4)
					if(z1 < -1 || z1 > 1)
						world.setBlock(x+x1, y+1, z+z1 + 78, AbyssalCraft.darkethaxiumslab1, 0, 2);
				if(z1 == -4 || z1 == 4)
					if(x1 < -1 || x1 > 1)
						world.setBlock(x+x1, y+1, z+z1 + 78, AbyssalCraft.darkethaxiumslab1, 0, 2);
			}
		world.setBlock(x, y+2, z+78, AbyssalCraft.darkethaxiumbrick, 0, 2);
		world.setBlock(x, y+2, z+75, AbyssalCraft.darkethaxiumbrick, 0, 2);
		world.setBlock(x+0, y+2, z+81, AbyssalCraft.darkethaxiumbrick, 0, 2);
		world.setBlock(x-3, y+2, z+78, AbyssalCraft.darkethaxiumbrick, 0, 2);
		world.setBlock(x+3, y+2, z+78, AbyssalCraft.darkethaxiumbrick, 0, 2);
		world.setBlock(x-2, y+2, z+76, AbyssalCraft.darkethaxiumbrick, 0, 2);
		world.setBlock(x+2, y+2, z+76, AbyssalCraft.darkethaxiumbrick, 0, 2);
		world.setBlock(x-2, y+2, z+80, AbyssalCraft.darkethaxiumbrick, 0, 2);
		world.setBlock(x+2, y+2, z+80, AbyssalCraft.darkethaxiumbrick, 0, 2);
		RitualUtil.tryAltar(world, x, y+2, z+78, 4);
		for(int x1 = -2; x1 < 3; x1++)
			for(int y1 = 1; y1 < 7; y1++)
				world.setBlockToAir(x+x1, y+y1, z+66);
		for(int x1 = -7; x1 < 8; x1++)
			for(int y1 = 0; y1 < 12; y1++)
				for(int z1 = 0; z1 < 66; z1++){
					world.setBlock(x+x1, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					if(z1 > 5 && z1 < 61){
						world.setBlock(x-8, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+8, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 7 && z1 < 59){
						world.setBlock(x+-9, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+9, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 9 && z1 < 57){
						world.setBlock(x+-10, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+10, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 11 && z1 < 55){
						world.setBlock(x+-11, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+11, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 13 && z1 < 53){
						world.setBlock(x+-12, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+12, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 15 && z1 < 51){
						world.setBlock(x+-13, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+13, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 17 && z1 < 49){
						world.setBlock(x+-14, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+14, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 20 && z1 < 46){
						world.setBlock(x+-15, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+15, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 24 && z1 < 42){
						world.setBlock(x+-16, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+16, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 29 && z1 < 37){
						world.setBlock(x+-17, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+17, y, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}

					if(z1 > -1 && z1 < 66){
						world.setBlock(x+-8, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+8, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 5 && z1 < 61){
						world.setBlock(x+-9, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+9, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 7 && z1 < 59){
						world.setBlock(x+-10, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+10, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 9 && z1 < 57){
						world.setBlock(x+-11, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+11, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 11 && z1 < 55){
						world.setBlock(x+-12, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+12, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 13 && z1 < 53){
						world.setBlock(x+-13, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+13, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 15 && z1 < 51){
						world.setBlock(x+-14, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+14, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 17 && z1 < 49){
						world.setBlock(x+-15, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+15, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 20 && z1 < 46){
						world.setBlock(x+-16, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+16, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 24 && z1 < 42){
						world.setBlock(x+-17, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+17, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 29 && z1 < 37){
						world.setBlock(x+-18, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+18, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}

					if(y1 > 0 && y1 < 11){
						world.setBlockToAir(x+x1, y+y1, z+z1);
						if(z1 > 5 && z1 < 61){
							world.setBlockToAir(x+-8, y+y1, z+z1);
							world.setBlockToAir(x+8, y+y1, z+z1);
						}
						if(z1 > 7 && z1 < 59){
							world.setBlockToAir(x+-9, y+y1, z+z1);
							world.setBlockToAir(x+9, y+y1, z+z1);
						}
						if(z1 > 9 && z1 < 57){
							world.setBlockToAir(x+-10, y+y1, z+z1);
							world.setBlockToAir(x+10, y+y1, z+z1);
						}
						if(z1 > 11 && z1 < 55){
							world.setBlockToAir(x+-11, y+y1, z+z1);
							world.setBlockToAir(x+11, y+y1, z+z1);
						}
						if(z1 > 13 && z1 < 53){
							world.setBlockToAir(x+-12, y+y1, z+z1);
							world.setBlockToAir(x+12, y+y1, z+z1);
						}
						if(z1 > 15 && z1 < 51){
							world.setBlockToAir(x+-13, y+y1, z+z1);
							world.setBlockToAir(x+13, y+y1, z+z1);
						}
						if(z1 > 17 && z1 < 49){
							world.setBlockToAir(x+-14, y+y1, z+z1);
							world.setBlockToAir(x+14, y+y1, z+z1);
						}
						if(z1 > 20 && z1 < 46){
							world.setBlockToAir(x+-15, y+y1, z+z1);
							world.setBlockToAir(x+15, y+y1, z+z1);
						}
						if(z1 > 24 && z1 < 42){
							world.setBlockToAir(x+-16, y+y1, z+z1);
							world.setBlockToAir(x+16, y+y1, z+z1);
						}
						if(z1 > 29 && z1 < 37){
							world.setBlockToAir(x+-17, y+y1, z+z1);
							world.setBlockToAir(x+17, y+y1, z+z1);
						}
						if(z1 == 1)
							world.setBlock(x+x1, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						if(z1 == 0){
							world.setBlock(x+-5, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+-7, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+5, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+7, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
						}
					}
				}
		for(int x1 = -2; x1 < 3; x1++)
			for(int y1 = 1; y1 < 7; y1++)
				world.setBlockToAir(x+x1, y+y1, z+1);
		for(int x1 = -3; x1 < 4; x1++)
			for(int y1 = 1; y1 < 8; y1++){
				if(y1 < 7){
					world.setBlock(x+-3, y+y1, z+65, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+3, y+y1, z+65, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+-3, y+y1, z, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+3, y+y1, z, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(y1 == 7){
					world.setBlock(x+x1, y+y1, z+65, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+y1, z, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
			}
		world.setBlock(x, y+7, z+65, AbyssalCraft.darkethaxiumbrick, 1, 2);
		world.setBlock(x, y+7, z, AbyssalCraft.darkethaxiumbrick, 1, 2);
		for(int z1 = -2; z1 < 3; z1++)
			for(int y1 = 1; y1 < 7; y1++){
				world.setBlockToAir(x+-18, y+y1, z+z1 + 33);
				world.setBlockToAir(x+18, y+y1, z+z1 + 33);
			}
		for(int z1 = -3; z1 < 4; z1++)
			for(int y1 = 1; y1 < 8; y1++){
				if(y1 < 7){
					world.setBlock(x+-17, y+y1, z+36, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+17, y+y1, z+36, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+-17, y+y1, z+30, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+17, y+y1, z+30, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(y1 == 7){
					world.setBlock(x+-17, y+y1, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+17, y+y1, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
			}
		world.setBlock(x+-17, y+7, z+33, AbyssalCraft.darkethaxiumbrick, 1, 2);
		world.setBlock(x+17, y+7, z+33, AbyssalCraft.darkethaxiumbrick, 1, 2);
		for(int z1 = 8; z1 < 59; z1++)
			for(int y1 = 1; y1 < 11; y1++){
				if(z1 == 8 || z1 == 58){
					world.setBlock(x+-6, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+6, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 12 || z1 == 54){
					world.setBlock(x+-8, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+8, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 16 || z1 == 50){
					world.setBlock(x+-10, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+10, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 21 || z1 == 45){
					world.setBlock(x+-12, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+12, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 25 || z1 == 41){
					world.setBlock(x+-13, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+13, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 30 || z1 == 36){
					world.setBlock(x+-14, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+14, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
			}
		for(int x1 = 0; x1 < 10; x1++)
			for(int z1 = -3; z1 < 4; z1++){
				world.setBlock(x+x1 + 19, y, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				world.setBlock(x+x1 + 19, y+7, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				for(int y1 = 1; y1 < 8; y1++){
					world.setBlock(x+x1 + 19, y+y1, z+36, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1 + 19, y+y1, z+30, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
			}
		for(int x1 = 0; x1 < 10; x1++)
			for(int z1 = -2; z1 < 3; z1++)
				for(int y1 = 1; y1 < 7; y1++)
					world.setBlockToAir(x+x1 + 19, y+y1, z+z1 + 33);
		for(int x1 = 0; x1 < 8; x1++)
			for(int y1 = 0; y1 < 19; y1++)
				for(int z1 = 0; z1 < 16; z1++){
					world.setBlock(x+x1 + 29, y+y1, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
					if(y1 > 0 && y1 < 18 && x1 > 0 && x1 < 7 && z1 > 0 && z1 < 15)
						world.setBlockToAir(x+x1 + 29, y+y1, z+z1 + 30);
					if(y1 > 0 && y1 < 7 || y1 > 11 && y1 < 18)
						if(x1 == 0)
							if(z1 > 0 && z1 < 6)
								world.setBlockToAir(x+x1 + 29, y+y1, z+z1 + 30);
					if(y1 == 11)
						if(z1 > 0 && z1 < 7 && x1 > 0 && x1 < 5){
							world.setBlock(x+x1 + 29, y+y1, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(x1 < 4){
								world.setBlock(x+x1 + 29, y+y1 - 1, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
								world.setBlock(x+x1 + 29, y+y1 - 2, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
								world.setBlock(x+x1 + 29, y+y1 - 3, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
								world.setBlock(x+x1 + 29, y+y1 - 4, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
							}
						}
					if(y1 == 12)
						if(x1 == 4)
							if(z1 > 0 && z1 < 7)
								world.setBlock(x+x1 + 29, y+y1, z+z1 + 30, AbyssalCraft.darkethaxiumfence, 0, 2);
					if(y1 < 12){
						if(y1 < 7)
							if(z1 > 11)
								world.setBlock(x+x1 + 29, y+y1, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
						if(x1 > 0 && x1 < 4){
							if(y1 <= 11)
								world.setBlock(x+x1 + 29, y+y1, z+36, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 10)
								world.setBlock(x+x1 + 29, y+y1, z+37, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 9)
								world.setBlock(x+x1 + 29, y+y1, z+38, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 8)
								world.setBlock(x+x1 + 29, y+y1, z+39, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 7)
								world.setBlock(x+x1 + 29, y+y1, z+40, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 6)
								world.setBlock(x+x1 + 29, y+y1, z+41, AbyssalCraft.darkethaxiumbrick, 0, 2);
							world.setBlock(x+x1 + 29, y+11, z+37, AbyssalCraft.darkethaxiumstairs, 3, 2);
							world.setBlock(x+x1 + 29, y+10, z+38, AbyssalCraft.darkethaxiumstairs, 3, 2);
							world.setBlock(x+x1 + 29, y+9, z+39, AbyssalCraft.darkethaxiumstairs, 3, 2);
							world.setBlock(x+x1 + 29, y+8, z+40, AbyssalCraft.darkethaxiumstairs, 3, 2);
							world.setBlock(x+x1 + 29, y+7, z+41, AbyssalCraft.darkethaxiumstairs, 3, 2);
						}
						if(x1 > 3 && x1 < 7){
							if(y1 <= 1)
								world.setBlock(x+x1 + 29, y+y1, z+37, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 2)
								world.setBlock(x+x1 + 29, y+y1, z+38, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 3)
								world.setBlock(x+x1 + 29, y+y1, z+39, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 4)
								world.setBlock(x+x1 + 29, y+y1, z+40, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 5)
								world.setBlock(x+x1 + 29, y+y1, z+41, AbyssalCraft.darkethaxiumbrick, 0, 2);
							world.setBlock(x+x1 + 29, y+1, z+36, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 + 29, y+2, z+37, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 + 29, y+3, z+38, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 + 29, y+4, z+39, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 + 29, y+5, z+40, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 + 29, y+6, z+41, AbyssalCraft.darkethaxiumstairs, 2, 2);
						}
					}
				}
		for(int x1 = -9; x1 < 1; x1++)
			for(int z1 = -3; z1 < 4; z1++){
				world.setBlock(x+x1 - 19, y, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				world.setBlock(x+x1 - 19, y+7, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				for(int y1 = 1; y1 < 8; y1++){
					world.setBlock(x+x1 - 19, y+y1, z+36, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1 - 19, y+y1, z+30, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
			}
		for(int x1 = -9; x1 < 1; x1++)
			for(int z1 = -2; z1 < 3; z1++)
				for(int y1 = 1; y1 < 7; y1++)
					world.setBlockToAir(x+x1 - 19, y+y1, z+z1 + 33);
		for(int x1 = 0; x1 < 8; x1++)
			for(int y1 = 0; y1 < 19; y1++)
				for(int z1 = 0; z1 < 16; z1++){
					world.setBlock(x+x1 - 36, y+y1, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
					if(y1 > 0 && y1 < 18 && x1 > 0 && x1 < 7 && z1 > 0 && z1 < 15)
						world.setBlockToAir(x+x1 - 36, y+y1, z+z1 + 30);
					if(y1 > 0 && y1 < 7 || y1 > 11 && y1 < 18)
						if(x1 == 7)
							if(z1 > 0 && z1 < 6)
								world.setBlockToAir(x+x1 - 36, y+y1, z+z1 + 30);
					if(y1 == 11)
						if(z1 > 0 && z1 < 7 && x1 > 2 && x1 < 8){
							world.setBlock(x+x1 - 36, y+y1, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(x1 > 3){
								world.setBlock(x+x1 - 36, y+y1 - 1, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
								world.setBlock(x+x1 - 36, y+y1 - 2, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
								world.setBlock(x+x1 - 36, y+y1 - 3, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
								world.setBlock(x+x1 - 36, y+y1 - 4, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
							}
						}
					if(y1 == 12)
						if(x1 == 3)
							if(z1 > 0 && z1 < 7)
								world.setBlock(x+x1 - 36, y+y1, z+z1 + 30, AbyssalCraft.darkethaxiumfence, 0, 2);
					if(y1 < 12){
						if(y1 < 7)
							if(z1 > 11)
								world.setBlock(x+x1 - 36, y+y1, z+z1 + 30, AbyssalCraft.darkethaxiumbrick, 0, 2);
						if(x1 > 3 && x1 < 7){
							if(y1 <= 11)
								world.setBlock(x+x1 - 36, y+y1, z+36, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 10)
								world.setBlock(x+x1 - 36, y+y1, z+37, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 9)
								world.setBlock(x+x1 - 36, y+y1, z+38, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 8)
								world.setBlock(x+x1 - 36, y+y1, z+39, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 7)
								world.setBlock(x+x1 - 36, y+y1, z+40, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 6)
								world.setBlock(x+x1 - 36, y+y1, z+41, AbyssalCraft.darkethaxiumbrick, 0, 2);
							world.setBlock(x+x1 - 36, y+11, z+37, AbyssalCraft.darkethaxiumstairs, 3, 2);
							world.setBlock(x+x1 - 36, y+10, z+38, AbyssalCraft.darkethaxiumstairs, 3, 2);
							world.setBlock(x+x1 - 36, y+9, z+39, AbyssalCraft.darkethaxiumstairs, 3, 2);
							world.setBlock(x+x1 - 36, y+8, z+40, AbyssalCraft.darkethaxiumstairs, 3, 2);
							world.setBlock(x+x1 - 36, y+7, z+41, AbyssalCraft.darkethaxiumstairs, 3, 2);
						}
						if(x1 > 0 && x1 < 4){
							if(y1 <= 1)
								world.setBlock(x+x1 - 36, y+y1, z+37, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 2)
								world.setBlock(x+x1 - 36, y+y1, z+38, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 3)
								world.setBlock(x+x1 - 36, y+y1, z+39, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 4)
								world.setBlock(x+x1 - 36, y+y1, z+40, AbyssalCraft.darkethaxiumbrick, 0, 2);
							if(y1 <= 5)
								world.setBlock(x+x1 - 36, y+y1, z+41, AbyssalCraft.darkethaxiumbrick, 0, 2);
							world.setBlock(x+x1 - 36, y+1, z+36, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 - 36, y+2, z+37, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 - 36, y+3, z+38, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 - 36, y+4, z+39, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 - 36, y+5, z+40, AbyssalCraft.darkethaxiumstairs, 2, 2);
							world.setBlock(x+x1 - 36, y+6, z+41, AbyssalCraft.darkethaxiumstairs, 2, 2);
						}
					}
				}
		world.setBlock(x+0, y+1, z+4, AbyssalCraft.gatekeeperminionspawner, 0, 2);
		world.setBlock(x+0, y+1, z+66, AbyssalCraft.gatekeeperminionspawner, 0, 2);
		world.setBlock(x+-18, y+1, z+33, AbyssalCraft.gatekeeperminionspawner, 0, 2);
		world.setBlock(x+18, y+1, z+33, AbyssalCraft.gatekeeperminionspawner, 0, 2);
		world.setBlock(x+0, y+1, z+33, AbyssalCraft.gatekeeperminionspawner, 0, 2);
	}

	private void secondFloor(World world, Random rand, int x, int y, int z){//TODO: second floor starts here
		for(int x1 = -7; x1 < 8; x1++)
			for(int y1 = 12; y1 < 24; y1++)
				for(int z1 = 67; z1 < 90; z1++)
					world.setBlockToAir(x+x1, y+y1, z+z1);
		for(int x1 = -8; x1 < 9; x1++)
			for(int z1 = 66; z1 < 91; z1++){
				world.setBlock(x+x1, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
				for(int y1 = 11; y1 < 24; y1++){
					world.setBlock(x+8, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+-8, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+y1, z+90, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+y1, z+66, AbyssalCraft.darkethaxiumbrick, 0, 2);
					if(y1 > 11)
						if(x1 == -7 || x1 == 7){
							world.setBlock(x+x1, y+y1, z+67, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+69, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+71, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+73, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+75, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+77, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+79, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+81, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+83, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+85, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+87, AbyssalCraft.darkethaxiumpillar, 0, 2);
							world.setBlock(x+x1, y+y1, z+89, AbyssalCraft.darkethaxiumpillar, 0, 2);
						}
				}
			}
		for(int x1 = -2; x1 < 3; x1++)
			for(int z1 = 77; z1 < 81; z1++){
				world.setBlock(x+x1, y+12, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
				if(x1 == -2 || x1 == 2){
					world.setBlock(x+x1, y+13, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					if(z1 > 77)
						world.setBlock(x+x1, y+14, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
				if(x1 > -2 && x1 < 2){
					world.setBlock(x+x1, y+13, z+80, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+14, z+80, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+15, z+80, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
			}
		world.setBlock(x+0, y+13, z+78, AbyssalCraft.jzaharspawner, 0, 2);
		world.setBlock(x+-1, y+12, z+76, AbyssalCraft.darkethaxiumslab1, 0, 2);
		world.setBlock(x+0, y+12, z+76, AbyssalCraft.darkethaxiumslab1, 0, 2);
		world.setBlock(x+1, y+12, z+76, AbyssalCraft.darkethaxiumslab1, 0, 2);
		world.setBlock(x+-2, y+13, z+77, AbyssalCraft.darkethaxiumbrick, 1, 2);
		world.setBlock(x+2, y+13, z+77, AbyssalCraft.darkethaxiumbrick, 1, 2);
		world.setBlock(x+-2, y+14, z+78, AbyssalCraft.darkethaxiumslab1, 0, 2);
		world.setBlock(x+2, y+14, z+78, AbyssalCraft.darkethaxiumslab1, 0, 2);
		world.setBlock(x+-2, y+15, z+80, AbyssalCraft.darkethaxiumstairs, 0, 2);
		world.setBlock(x+2, y+15, z+80, AbyssalCraft.darkethaxiumstairs, 1, 2);
		for(int x1 = -3; x1 < 4; x1++)
			for(int y1 = 13; y1 < 22; y1++){
				if(x1 > -2 && x1 < 2){
					world.setBlock(x+x1, y+13, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+21, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
				if(x1 == -2 || x1 == 2){
					world.setBlock(x+x1, y+14, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+20, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
				if(y1 > 14 && y1 < 20){
					world.setBlock(x+-3, y+y1, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+3, y+y1, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
			}
		for(int x1 = -4; x1 < 5; x1++)
			for(int y1 = 12; y1 < 23; y1++){
				if(x1 > -2 && x1 < 2){
					world.setBlock(x+x1, y+12, z+88, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+x1, y+22, z+88, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(x1 == -2 || x1 == 2){
					world.setBlock(x+x1, y+13, z+88, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+x1, y+21, z+88, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(x1 == -3 || x1 == 3){
					world.setBlock(x+x1, y+14, z+88, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+x1, y+20, z+88, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(y1 > 14 && y1 < 20){
					world.setBlock(x+-4, y+y1, z+88, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+4, y+y1, z+88, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
			}
		world.setBlock(x+-4, y+17, z+88, AbyssalCraft.darkethaxiumbrick, 1, 2);
		world.setBlock(x+4, y+17, z+88, AbyssalCraft.darkethaxiumbrick, 1, 2);
		for(int x1 = -7; x1 < 8; x1++)
			for(int z1 = 66; z1 < 90; z1++){
				world.setBlock(x+x1, y+24, z+66, AbyssalCraft.darkethaxiumbrick, 0, 2);
				world.setBlock(x+x1, y+24, z+89, AbyssalCraft.darkethaxiumbrick, 0, 2);
				world.setBlock(x+7, y+24, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
				world.setBlock(x+-7, y+24, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
				if(x1 > -7 && x1 < 7){
					world.setBlock(x+x1, y+25, z+66, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+25, z+88, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
				if(z1 < 88){
					world.setBlock(x+6, y+25, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+-6, y+25, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
				if(x1 > -6 && x1 < 6){
					world.setBlock(x+x1, y+26, z+66, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+26, z+87, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
				if(z1 < 87){
					world.setBlock(x+5, y+26, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+-5, y+26, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
				if(x1 > -5 && x1 < 5 && z1 < 87)
					world.setBlock(x+x1, y+27, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
			}
		for(int x1 = -7; x1 < 8; x1++)
			for(int y1 = 11; y1 < 23; y1++)
				for(int z1 = 5; z1 < 66; z1++){
					world.setBlock(x+x1, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1, y+22, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					if(z1 > 5 && z1 < 61){
						world.setBlock(x+-8, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+8, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 7 && z1 < 59){
						world.setBlock(x+-9, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+9, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 9 && z1 < 57){
						world.setBlock(x+-10, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+10, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 11 && z1 < 55){
						world.setBlock(x+-11, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+11, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 13 && z1 < 53){
						world.setBlock(x+-12, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+12, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 15 && z1 < 51){
						world.setBlock(x+-13, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+13, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 17 && z1 < 49){
						world.setBlock(x+-14, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+14, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 20 && z1 < 46){
						world.setBlock(x+-15, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+15, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 24 && z1 < 42){
						world.setBlock(x+-16, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+16, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 29 && z1 < 37){
						world.setBlock(x+-17, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+17, y+11, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}

					if(z1 > 4 && z1 < 66){
						world.setBlock(x+-8, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+8, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 5 && z1 < 61){
						world.setBlock(x+-9, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+9, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 7 && z1 < 59){
						world.setBlock(x+-10, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+10, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 9 && z1 < 57){
						world.setBlock(x+-11, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+11, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 11 && z1 < 55){
						world.setBlock(x+-12, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+12, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 13 && z1 < 53){
						world.setBlock(x+-13, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+13, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 15 && z1 < 51){
						world.setBlock(x+-14, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+14, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 17 && z1 < 49){
						world.setBlock(x+-15, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+15, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 20 && z1 < 46){
						world.setBlock(x+-16, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+16, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 24 && z1 < 42){
						world.setBlock(x+-17, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+17, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
					if(z1 > 29 && z1 < 37){
						world.setBlock(x+-18, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
						world.setBlock(x+18, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}

					if(y1 > 11 && y1 < 22){
						world.setBlockToAir(x+x1, y+y1, z+z1);
						if(z1 > 5 && z1 < 61){
							world.setBlockToAir(x+-8, y+y1, z+z1);
							world.setBlockToAir(x+8, y+y1, z+z1);
						}
						if(z1 > 7 && z1 < 59){
							world.setBlockToAir(x+-9, y+y1, z+z1);
							world.setBlockToAir(x+9, y+y1, z+z1);
						}
						if(z1 > 9 && z1 < 57){
							world.setBlockToAir(x+-10, y+y1, z+z1);
							world.setBlockToAir(x+10, y+y1, z+z1);
						}
						if(z1 > 11 && z1 < 55){
							world.setBlockToAir(x+-11, y+y1, z+z1);
							world.setBlockToAir(x+11, y+y1, z+z1);
						}
						if(z1 > 13 && z1 < 53){
							world.setBlockToAir(x+-12, y+y1, z+z1);
							world.setBlockToAir(x+12, y+y1, z+z1);
						}
						if(z1 > 15 && z1 < 51){
							world.setBlockToAir(x+-13, y+y1, z+z1);
							world.setBlockToAir(x+13, y+y1, z+z1);
						}
						if(z1 > 17 && z1 < 49){
							world.setBlockToAir(x+-14, y+y1, z+z1);
							world.setBlockToAir(x+14, y+y1, z+z1);
						}
						if(z1 > 20 && z1 < 46){
							world.setBlockToAir(x+-15, y+y1, z+z1);
							world.setBlockToAir(x+15, y+y1, z+z1);
						}
						if(z1 > 24 && z1 < 42){
							world.setBlockToAir(x+-16, y+y1, z+z1);
							world.setBlockToAir(x+16, y+y1, z+z1);
						}
						if(z1 > 29 && z1 < 37){
							world.setBlockToAir(x+-17, y+y1, z+z1);
							world.setBlockToAir(x+17, y+y1, z+z1);
						}
						if(z1 == 5)
							world.setBlock(x+x1, y+y1, z+z1, AbyssalCraft.darkethaxiumbrick, 0, 2);
					}
				}
		for(int x1 = -2; x1 < 3; x1++)
			for(int y1 = 12; y1 < 18; y1++)
				world.setBlockToAir(x+x1, y+y1, z+66);
		for(int x1 = -3; x1 < 4; x1++)
			for(int y1 = 12; y1 < 19; y1++){
				if(y1 < 18){
					world.setBlock(x+-3, y+y1, z+65, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+3, y+y1, z+65, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(y1 == 18)
					world.setBlock(x+x1, y+y1, z+65, AbyssalCraft.darkethaxiumbrick, 0, 2);
			}
		world.setBlock(x+0, y+18, z+65, AbyssalCraft.darkethaxiumbrick, 1, 2);
		for(int z1 = -2; z1 < 3; z1++)
			for(int y1 = 12; y1 < 18; y1++){
				world.setBlockToAir(x+-18, y+y1, z+z1 + 33);
				world.setBlockToAir(x+18, y+y1, z+z1 + 33);
			}
		for(int z1 = -3; z1 < 4; z1++)
			for(int y1 = 12; y1 < 19; y1++){
				if(y1 < 18){
					world.setBlock(x+-17, y+y1, z+36, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+17, y+y1, z+36, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+-17, y+y1, z+30, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+17, y+y1, z+30, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(y1 == 18){
					world.setBlock(x+-17, y+y1, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+17, y+y1, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
			}
		world.setBlock(x+-17, y+18, z+33, AbyssalCraft.darkethaxiumbrick, 1, 2);
		world.setBlock(x+17, y+18, z+33, AbyssalCraft.darkethaxiumbrick, 1, 2);
		for(int z1 = 8; z1 < 60; z1++)
			for(int y1 = 12; y1 < 22; y1++){
				if(z1 == 8 || z1 == 58){
					world.setBlock(x+-6, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+6, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 12 || z1 == 54){
					world.setBlock(x+-8, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+8, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 16 || z1 == 50){
					world.setBlock(x+-10, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+10, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 21 || z1 == 45){
					world.setBlock(x+-12, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+12, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 25 || z1 == 41){
					world.setBlock(x+-13, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+13, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
				if(z1 == 30 || z1 == 36){
					world.setBlock(x+-14, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
					world.setBlock(x+14, y+y1, z+z1, AbyssalCraft.darkethaxiumpillar, 0, 2);
				}
			}
		for(int x1 = 0; x1 < 10; x1++)
			for(int z1 = -3; z1 < 4; z1++){
				world.setBlock(x+x1 + 19, y+11, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				world.setBlock(x+x1 + 19, y+18, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				for(int y1 = 12; y1 < 19; y1++){
					world.setBlock(x+x1 + 19, y+y1, z+36, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1 + 19, y+y1, z+30, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
			}
		for(int x1 = 0; x1 < 10; x1++)
			for(int z1 = -2; z1 < 3; z1++)
				for(int y1 = 12; y1 < 18; y1++)
					world.setBlockToAir(x+x1 + 19, y+y1, z+z1 + 33);
		for(int x1 = -9; x1 < 1; x1++)
			for(int z1 = -3; z1 < 4; z1++){
				world.setBlock(x+x1 - 19, y+11, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				world.setBlock(x+x1 - 19, y+18, z+z1 + 33, AbyssalCraft.darkethaxiumbrick, 0, 2);
				for(int y1 = 12; y1 < 19; y1++){
					world.setBlock(x+x1 - 19, y+y1, z+36, AbyssalCraft.darkethaxiumbrick, 0, 2);
					world.setBlock(x+x1 - 19, y+y1, z+30, AbyssalCraft.darkethaxiumbrick, 0, 2);
				}
			}
		for(int x1 = -9; x1 < 1; x1++)
			for(int z1 = -2; z1 < 3; z1++)
				for(int y1 = 12; y1 < 18; y1++)
					world.setBlockToAir(x+x1 - 19, y+y1, z+z1 + 33);
		world.setBlock(x+0, y+12, z+7, AbyssalCraft.gatekeeperminionspawner, 0, 2);
		world.setBlock(x+0, y+12, z+66, AbyssalCraft.gatekeeperminionspawner, 0, 2);
		world.setBlock(x+-18, y+12, z+33, AbyssalCraft.gatekeeperminionspawner, 0, 2);
		world.setBlock(x+18, y+12, z+33, AbyssalCraft.gatekeeperminionspawner, 0, 2);
		world.setBlock(x+0, y+12, z+33, AbyssalCraft.gatekeeperminionspawner, 0, 2);
	}
}
