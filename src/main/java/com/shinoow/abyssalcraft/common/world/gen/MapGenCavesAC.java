/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.MapGenCaves;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

public class MapGenCavesAC extends MapGenCaves {

	private boolean isACStone(Block block){
		return block == ACBlocks.darkstone || block == ACBlocks.abyssal_stone || block == ACBlocks.dreadstone ||
			block == ACBlocks.abyssalnite_stone || block == ACBlocks.coralium_stone || block == ACBlocks.omothol_stone;
	}

	private boolean isACMisc(Block block){
		return block == ACBlocks.abyssal_sand || block == ACBlocks.fused_abyssal_sand ||
			block == ACBlocks.dreadlands_dirt || block == ACBlocks.dreadlands_grass;
	}

	@Override
	protected boolean canReplaceBlock(IBlockState p_175793_1_, IBlockState p_175793_2_)
	{
		return isACStone(p_175793_1_.getBlock()) ? true : isACMisc(p_175793_1_.getBlock()) ? true :
			super.canReplaceBlock(p_175793_1_, p_175793_2_);
	}
}
