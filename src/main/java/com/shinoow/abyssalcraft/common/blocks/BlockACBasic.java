/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
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