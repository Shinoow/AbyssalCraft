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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothOoze;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisruptionOoze extends DisruptionEntry {

	public DisruptionOoze() {
		super("ooze", null);
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {

		int num = world.rand.nextInt(4) + 2;
		if(!world.isRemote)
			for(int i = -1*num; i < num+1; i++)
				for(int j = -1*num; j < num+1; j++)
					for(int k = -1*num; k < num+1; k++) {
						BlockPos pos1 = pos.add(i, j, k);
						if(i > -2 && i < 2 && j > -2 && j < 2 && k > -2 && k < 2 && world.rand.nextBoolean() || world.rand.nextInt(3) == 0)
							if((world.getBlockState(pos1).getMaterial() == Material.AIR || world.getBlockState(pos1).getBlock().isReplaceable(world, pos1)) && ACBlocks.shoggoth_ooze.canPlaceBlockAt(world, pos1)
									&& !world.getBlockState(pos1).getMaterial().isLiquid())
								if(world.getBlockState(pos1).getBlock() != ACBlocks.shoggoth_ooze)
									world.setBlockState(pos1, ACBlocks.shoggoth_ooze.getDefaultState().withProperty(BlockShoggothOoze.LAYERS, 1 + world.rand.nextInt(5)));
								else {
									IBlockState state = world.getBlockState(pos1);
									world.setBlockState(pos1, state.withProperty(BlockShoggothOoze.LAYERS, state.getValue(BlockShoggothOoze.LAYERS) + 1));
								}
					}
	}
}
