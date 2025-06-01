/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAbyssalSand extends BlockACBasic {

	public BlockAbyssalSand() {
		super(Material.SAND, 0.5F, 2.5F, SoundType.SAND);
		setTranslationKey("abyssalsand");
		setTickRandomly(true);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(!worldIn.isRemote) {
			if (!worldIn.isAreaLoaded(pos, 3)) return; // don't load unloaded chunk

			if(worldIn.provider.getDimension() != ACLib.abyssal_wasteland_id) return;

			if (rand.nextInt(10) == 0
					&& !worldIn.getBlockState(pos.up()).getMaterial().isLiquid()
					&& worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) <= 2
					&& worldIn.getBiome(pos) != ACBiomes.abyssal_desert)
				worldIn.setBlockState(pos, ACBlocks.fused_abyssal_sand.getDefaultState());
		}
	}

	@Override
	public int tickRate(World worldIn)
	{
		return 40;
	}
}
