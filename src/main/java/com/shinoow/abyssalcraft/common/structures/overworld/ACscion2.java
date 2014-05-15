/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.structures.overworld;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ACscion2 extends WorldGenerator
{
	protected Block[] GetValidSpawnBlocks() {
		return new Block[] {
				AbyssalCraft.Darkgrass
		};
	}

	public boolean LocationIsValidSpawn(World world, int i, int j, int k){
		int distanceToAir = 0;
		int checkID = world.getBlockMetadata(i, j, k);

		while (checkID != 0){
			distanceToAir++;
			checkID = world.getBlockMetadata(i, j + distanceToAir, k);
		}

		if (distanceToAir > 1){
			return false;
		}
		j += distanceToAir - 1;

		Block blockID = world.getBlock(i, j, k);
		Block blockIDAbove = world.getBlock(i, j+1, k);
		Block blockIDBelow = world.getBlock(i, j-1, k);
		for (Block x : GetValidSpawnBlocks()){
			if (blockIDAbove != Blocks.air){
				return false;
			}
			if (blockID == x){
				return true;
			}else if (blockID == Blocks.snow && blockIDBelow == x){
				return true;
			}
		}
		return false;
	}

	public ACscion2() { }

	public boolean generate(World world, Random random, int i, int j, int k) {

		if(!LocationIsValidSpawn(world, i, j, k) || !LocationIsValidSpawn(world, i + 6, j, k) || !LocationIsValidSpawn(world, i + 6, j, k + 6) || !LocationIsValidSpawn(world, i, j, k + 6))
		{
			return false;
		}

		world.setBlock(i + 0, j + 0, k + 0, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 0, j + 0, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 0, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 0, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 0, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 0, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 0, k + 6, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 0, j + 1, k + 0, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 0, j + 1, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 1, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 1, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 1, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 1, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 1, k + 6, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 0, j + 2, k + 0, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 0, j + 2, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 2, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 2, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 2, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 2, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 2, k + 6, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 0, j + 3, k + 0, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 0, j + 3, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 3, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 3, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 3, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 3, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 3, k + 6, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 0, j + 4, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 4, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 4, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 4, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 4, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 0, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 0, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 0, k + 2, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 1, j + 0, k + 3, AbyssalCraft.DBstairs, k, k);
		world.setBlock(i + 1, j + 0, k + 4, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 1, j + 0, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 0, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 1, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 1, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 1, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 1, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 1, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 1, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 1, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 2, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 2, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 2, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 2, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 2, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 2, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 2, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 3, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 3, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 3, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 3, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 3, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 3, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 3, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 4, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 4, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 4, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 4, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 4, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 4, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 1, j + 4, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 0, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 0, k + 1, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 2, j + 0, k + 2, AbyssalCraft.Darkbrickslab1, 13, 13);
		world.setBlock(i + 2, j + 0, k + 3, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 2, j + 0, k + 4, AbyssalCraft.Darkbrickslab1, 13, 13);
		world.setBlock(i + 2, j + 0, k + 5, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 2, j + 0, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 1, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 1, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 1, k + 2, AbyssalCraft.Darkbrickslab1, 13, 13);
		world.setBlock(i + 2, j + 1, k + 3, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 2, j + 1, k + 4, AbyssalCraft.Darkbrickslab1, 13, 13);
		world.setBlock(i + 2, j + 1, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 1, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 2, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 2, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 2, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 2, k + 3, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 2, j + 2, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 2, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 2, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 3, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 3, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 3, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 3, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 3, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 3, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 3, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 4, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 4, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 4, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 4, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 4, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 4, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 2, j + 4, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 0, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 0, k + 1, AbyssalCraft.DBstairs, 2, 2);
		world.setBlock(i + 3, j + 0, k + 2, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 3, j + 0, k + 3, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 3, j + 0, k + 4, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 3, j + 0, k + 5, AbyssalCraft.DBstairs, 3, 3);
		world.setBlock(i + 3, j + 0, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 1, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 1, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 1, k + 2, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 3, j + 1, k + 3, Blocks.emerald_block, k, k);
		world.setBlock(i + 3, j + 1, k + 4, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 3, j + 1, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 1, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 2, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 2, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 2, k + 2, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 3, j + 2, k + 3, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 3, j + 2, k + 4, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 3, j + 2, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 2, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 3, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 3, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 3, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 3, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 3, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 3, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 3, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 4, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 4, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 4, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 4, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 4, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 4, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 3, j + 4, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 0, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 0, k + 1, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 4, j + 0, k + 2, AbyssalCraft.Darkbrickslab1, 13, 13);
		world.setBlock(i + 4, j + 0, k + 3, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 4, j + 0, k + 4, AbyssalCraft.Darkbrickslab1, 13, 13);
		world.setBlock(i + 4, j + 0, k + 5, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 4, j + 0, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 1, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 1, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 1, k + 2, AbyssalCraft.Darkbrickslab1, 13, 13);
		world.setBlock(i + 4, j + 1, k + 3, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 4, j + 1, k + 4, AbyssalCraft.Darkbrickslab1, 13, 13);
		world.setBlock(i + 4, j + 1, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 1, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 2, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 2, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 2, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 2, k + 3, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 4, j + 2, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 2, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 2, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 3, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 3, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 3, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 3, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 3, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 3, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 3, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 4, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 4, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 4, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 4, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 4, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 4, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 4, j + 4, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 0, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 0, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 0, k + 2, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 5, j + 0, k + 3, AbyssalCraft.DBstairs, 1, 1);
		world.setBlock(i + 5, j + 0, k + 4, AbyssalCraft.Darkbrickslab1, 5, 5);
		world.setBlock(i + 5, j + 0, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 0, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 1, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 1, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 1, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 1, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 1, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 1, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 1, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 2, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 2, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 2, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 2, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 2, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 2, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 2, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 3, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 3, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 3, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 3, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 3, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 3, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 3, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 4, k + 0, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 4, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 4, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 4, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 4, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 4, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 5, j + 4, k + 6, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 0, k + 0, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 6, j + 0, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 0, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 0, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 0, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 0, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 0, k + 6, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 6, j + 1, k + 0, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 6, j + 1, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 1, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 1, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 1, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 1, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 1, k + 6, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 6, j + 2, k + 0, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 6, j + 2, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 2, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 2, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 2, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 2, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 2, k + 6, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 6, j + 3, k + 0, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 6, j + 3, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 3, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 3, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 3, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 3, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 3, k + 6, AbyssalCraft.Darkstone_brick, k, k);
		world.setBlock(i + 6, j + 4, k + 1, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 4, k + 2, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 4, k + 3, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 4, k + 4, Blocks.air, 0, k);
		world.setBlock(i + 6, j + 4, k + 5, Blocks.air, 0, k);
		world.setBlock(i + 0, j + 4, k + 0, Blocks.redstone_torch, 5, 5);
		world.setBlock(i + 0, j + 4, k + 6, Blocks.redstone_torch, 5, 5);
		world.setBlock(i + 6, j + 4, k + 0, Blocks.redstone_torch, 5, 5);
		world.setBlock(i + 6, j + 4, k + 6, Blocks.redstone_torch, 5, 5);

		return true;
	}


}