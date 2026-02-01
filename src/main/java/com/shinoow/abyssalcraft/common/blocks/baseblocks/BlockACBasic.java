/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2026 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.baseblocks;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		this(material, tooltype, harvestlevel, hardness, resistance, stepsound, material.getMaterialMapColor());
	}

	/**
	 * Ultra-generic AbyssalCraft block
	 * @param material Block material
	 * @param hardness Block hardness
	 * @param resistance Block resistance
	 * @param stepsound Block step sound
	 */
	public BlockACBasic(Material material, float hardness, float resistance, SoundType stepsound) {
		this(material, hardness, resistance, stepsound, material.getMaterialMapColor());
	}

	/**
	 * Ultra-generic AbyssalCraft block
	 * @param material Block material
	 * @param tooltype Tool to harvest with
	 * @param harvestlevel Harvest level required
	 * @param hardness Block hardness
	 * @param resistance Block resistance
	 * @param stepsound Block step sound
	 * @param mapColor Map Color
	 */
	public BlockACBasic(Material material, String tooltype, int harvestlevel, float hardness, float resistance, SoundType stepsound, MapColor mapColor) {
		this(material, hardness, resistance, stepsound, mapColor);
		setHarvestLevel(tooltype, harvestlevel);
	}

	/**
	 * Ultra-generic AbyssalCraft block
	 * @param material Block material
	 * @param hardness Block hardness
	 * @param resistance Block resistance
	 * @param stepsound Block step sound
	 * @param mapColor Map Color
	 */
	public BlockACBasic(Material material, float hardness, float resistance, SoundType stepsound, MapColor mapColor) {
		super(material, mapColor);
		setHardness(hardness);
		setResistance(resistance);
		setSoundType(stepsound);
		setCreativeTab(ACTabs.tabBlock);
		if(getHarvestTool(getDefaultState()) == null)
			if(material == Material.ROCK || material == Material.IRON || material == Material.ANVIL)
				setHarvestLevel("pickaxe", 0);
			else if(material == Material.GROUND || material == Material.GRASS || material == Material.SAND ||
					material == Material.SNOW || material == Material.CRAFTED_SNOW)
				setHarvestLevel("shovel", 0);
	}

	public BlockACBasic mipp() {

		// Too much? Maybe, but fuck it
		AbyssalCraft.proxy.setRenderLayer(this);

		return this;
	}

	@SideOnly(Side.CLIENT)
	private BlockRenderLayer layer = super.getRenderLayer();

	@SideOnly(Side.CLIENT)
	public void setRenderLayer() {
		layer = BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return layer;
	}
}
