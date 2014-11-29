/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class BlockCoraliumstone extends Block {

	public BlockCoraliumstone() {
		super(Material.rock);
		setCreativeTab(AbyssalCraft.tabBlock);
		setTickRandomly(true);
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!par1World.isRemote)
			for (int l = 0; l < 1; ++l) {
				int i1 = par2 + par5Random.nextInt(3) - 1;
				int j1 = par3 + par5Random.nextInt(5) - 3;
				int k1 = par4 + par5Random.nextInt(3) - 1;

				if (par1World.getBlock(i1, j1, k1) == AbyssalCraft.Cwater && par1World.getBlockMetadata(i1, j1, k1) == 0)
					par1World.setBlock(i1, j1, k1, AbyssalCraft.cstone);
			}
	}
}