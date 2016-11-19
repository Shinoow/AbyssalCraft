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
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

import com.shinoow.abyssalcraft.lib.ACTabs;

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
	}
}
