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

	public enum EnumType implements IStringSerializable
	{
		GENERIC(0, "generic", MapColor.AIR);

		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;
		private final MapColor mapColor;

		private EnumType(int meta, String name, MapColor mapColor)
		{
			this(meta, name, name, mapColor);
		}

		private EnumType(int meta, String name, String unlocalizedName, MapColor mapColor)
		{
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.mapColor = mapColor;
		}

		public int getMetadata()
		{
			return meta;
		}

		public MapColor func_181070_c()
		{
			return mapColor;
		}

		@Override
		public String toString()
		{
			return name;
		}

		public static EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length)
				meta = 0;

			return META_LOOKUP[meta];
		}

		@Override
		public String getName()
		{
			return name;
		}

		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}

		static
		{
			for (EnumType enumtype : values())
				META_LOOKUP[enumtype.getMetadata()] = enumtype;
		}
	}
}
