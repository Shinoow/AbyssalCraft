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
import net.minecraft.block.material.Material;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class BlockACBasic extends Block {

	/**
	 * Ultra-generic AbyssalCraft block
	 * @param material Block material
	 * @param tooltype Tool to harvest with
	 * @param harvestlevel Harvest level required
	 * @param hardness Block hardness
	 * @param resistance Block resistance
	 * @param stepsound Block step sound
	 */
	public BlockACBasic(Material material, String tooltype, int harvestlevel, float hardness, float resistance, SoundType stepsound) {
		super(material);
		setHarvestLevel(tooltype, harvestlevel);
		setHardness(hardness);
		setResistance(resistance);
		setStepSound(stepsound);
		setCreativeTab(AbyssalCraft.tabBlock);
	}

	/**
	 * Ultra-generic AbyssalCraft block
	 * @param material Block material
	 * @param hardness Block hardness
	 * @param resistance Block resistance
	 * @param stepsound Block step sound
	 */
	public BlockACBasic(Material material, float hardness, float resistance, SoundType stepsound) {
		super(material);
		setHardness(hardness);
		setResistance(resistance);
		setStepSound(stepsound);
		setCreativeTab(AbyssalCraft.tabBlock);
	}
}
