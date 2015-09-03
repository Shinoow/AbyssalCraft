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
package com.shinoow.abyssalcraft.common.world.gen;

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TEDirectional;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenShoggothMonolith extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		while(world.isAirBlock(x, y, z) && y > 2)
			--y;

		//	if(world.getBlock(x, y, z).getMaterial() != Material.grass ||
		//	world.getBlock(x, y, z).getMaterial() != Material.ground)
		if(world.getBlock(x, y, z) != AbyssalCraft.shoggothBlock)
			return false;
		else {

			//	for(int x1 = 0; x1 < 7; x1++)
			//	for(int z1 = 0; z1 < 7; z1++)
			//	for(int x2 = 0; x2 < 7; x2++)
			//	for(int z2 = 0; z2 < 7; z2++)
			//	for(int y1 = -1; y1 < 4; y1++){
			//	if(world.getBlock(x + x1, y + y1, z + z1).getMaterial() == Material.grass ||
			//	world.getBlock(x + x1, y + y1, z + z1).getMaterial() == Material.ground)
			//	func_150515_a(world,x + x1, y + y1, z + z1, AbyssalCraft.shoggothBlock);
			//	if(world.getBlock(x - x2, y + y1, z - z2).getMaterial() == Material.grass ||
			//	world.getBlock(x - x2, y + y1, z - z2).getMaterial() == Material.ground)
			//	func_150515_a(world,x - x2, y + y1, z - z2, AbyssalCraft.shoggothBlock);
			//	if(world.getBlock(x + x1, y + y1, z - z2).getMaterial() == Material.grass ||
			//	world.getBlock(x + x1, y + y1, z - z2).getMaterial() == Material.ground)
			//	func_150515_a(world,x + x1, y + y1, z - z2, AbyssalCraft.shoggothBlock);
			//	if(world.getBlock(x - x2, y + y1, z  + z1).getMaterial() == Material.grass ||
			//	world.getBlock(x - x2, y + y1, z  + z1).getMaterial() == Material.ground)
			//	func_150515_a(world,x - x2, y + y1, z  + z1, AbyssalCraft.shoggothBlock);
			//	}

			int max = rand.nextInt(8) + 5;
			for(int i = 0; i < max; i++){
				setBlockAndNotifyAdequately(world, x, y + i, z, AbyssalCraft.omotholstone, 0);
				setBlockAndNotifyAdequately(world, x + 1, y + i, z, AbyssalCraft.omotholstone, 0);
				setBlockAndNotifyAdequately(world, x - 1, y + i, z, AbyssalCraft.omotholstone, 0);
				setBlockAndNotifyAdequately(world, x, y + i, z + 1, AbyssalCraft.omotholstone, 0);
				setBlockAndNotifyAdequately(world, x, y + i, z - 1, AbyssalCraft.omotholstone, 0);
				setBlockAndNotifyAdequately(world, x + 1, y + i, z + 1, AbyssalCraft.omotholstone, 0);
				setBlockAndNotifyAdequately(world, x - 1, y + i, z - 1, AbyssalCraft.omotholstone, 0);
				setBlockAndNotifyAdequately(world, x + 1, y + i, z - 1, AbyssalCraft.omotholstone, 0);
				setBlockAndNotifyAdequately(world, x - 1, y + i, z + 1, AbyssalCraft.omotholstone, 0);
			}

			setBlockAndNotifyAdequately(world, x, y + max, z, getStatue(rand), 0);
			TileEntity te = world.getTileEntity(x, y + max, z);

			if(te != null && te instanceof TEDirectional)
				((TEDirectional) te).setDirection(rand.nextInt(3));

			return true;
		}
	}

	private Block getStatue(Random rand){
		switch(rand.nextInt(3)){
		case 0:
			return Blocks.air;
		case 1:
			return AbyssalCraft.cthulhuStatue;
		case 2:
			return AbyssalCraft.hasturStatue;
		default:
			return Blocks.air;
		}
	}
}
