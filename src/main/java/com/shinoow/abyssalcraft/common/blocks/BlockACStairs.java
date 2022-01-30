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
package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockACStairs extends BlockStairs {

	public BlockACStairs(Block par1Block, String tooltype, int harvestlevel){
		super(par1Block.getDefaultState());
		setLightOpacity(0);
		setCreativeTab(ACTabs.tabBlock);
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACStairs(Block par1Block){
		super(par1Block.getDefaultState());
		setLightOpacity(0);
		setCreativeTab(ACTabs.tabBlock);

		if(getHarvestTool(getDefaultState()) == null)
			if(material == Material.ROCK || material == Material.IRON || material == Material.ANVIL)
				setHarvestLevel("pickaxe", 0);
			else if(material == Material.GROUND || material == Material.GRASS || material == Material.SAND ||
					material == Material.SNOW || material == Material.CRAFTED_SNOW)
				setHarvestLevel("shovel", 0);
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return state.getBlock() != ACBlocks.ethaxium_brick_stairs && state.getBlock() != ACBlocks.dark_ethaxium_brick_stairs;
		return super.canEntityDestroy(state, world, pos, entity);
	}
}
