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

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockLuminousThistle extends BlockBush {

	public BlockLuminousThistle(){
		setSoundType(SoundType.PLANT);
		setTranslationKey("luminousthistle");
		setCreativeTab(ACTabs.tabDecoration);
		setLightLevel(0.5F);
	}

	@Override
	protected boolean canSustainBush(IBlockState ground)
	{
		return ground.getBlock() == ACBlocks.fused_abyssal_sand || ground.getBlock() == ACBlocks.abyssal_sand ||
				ground.getMaterial() == Material.GRASS || super.canSustainBush(ground);
	}
}
