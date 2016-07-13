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

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockEthaxiumPillar extends BlockRotatedPillar {

	public BlockEthaxiumPillar(float hardness) {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
		setHarvestLevel("pickaxe", 8);
		setHardness(hardness);
		setResistance(Float.MAX_VALUE);
		setStepSound(SoundType.STONE);
		setCreativeTab(ACTabs.tabBlock);
	}
}