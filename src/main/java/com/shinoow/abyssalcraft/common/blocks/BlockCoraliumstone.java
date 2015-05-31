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
