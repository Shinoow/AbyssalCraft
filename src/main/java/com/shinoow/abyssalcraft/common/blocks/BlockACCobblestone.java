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

import java.util.HashMap;
import java.util.Map;

import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

public class BlockACCobblestone extends Block {

	public static final Map<EnumCobblestoneType, Block> VARIANTS = new HashMap<>();

	public BlockACCobblestone(EnumCobblestoneType type) {
		super(Material.ROCK, type.getMapColor());
		setHardness(type.getHardness());
		setResistance(type.getResistance());
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabBlock);
		setHarvestLevel("pickaxe", type.getHarvestLevel());
		VARIANTS.put(type, this);
	}

	public enum EnumCobblestoneType implements IStringSerializable
	{
		DARKSTONE(0, "darkstone", "darkstone_cobble", 0, 2.2F, 12.0F, MapColor.BLACK),
		ABYSSAL_STONE(1, "abyssalstone", "abyssalcobblestone", 2, 2.6F, 12.0F, MapColor.GREEN),
		DREADSTONE(2, "dreadstone", "dreadstonecobblestone", 4, 3.3F, 20.0F, MapColor.RED),
		ELYSIAN_STONE(3, "elysianstone", "elysian_cobblestone", 4, 3.3F, 20.0F, MapColor.PURPLE),
		CORALIUM_STONE(4, "coraliumstone", "coraliumcobblestone", 0, 2.0F, 10.0F, MapColor.CYAN);

		private static final EnumCobblestoneType[] META_LOOKUP = new EnumCobblestoneType[values().length];
		private final int meta;
		private final String name;
		private final String state;
		private final int harvest;
		private final float hardness;
		private final float resistance;
		private final MapColor mapColor;

		private EnumCobblestoneType(int meta, String name, String state, int harvest, float hardness, float resistance, MapColor mapColor)
		{
			this.meta = meta;
			this.name = name;
			this.state = state;
			this.harvest = harvest;
			this.hardness = hardness;
			this.resistance = resistance;
			this.mapColor = mapColor;
		}

		public int getMeta()
		{
			return meta;
		}

		public MapColor getMapColor()
		{
			return mapColor;
		}

		public int getHarvestLevel(){
			return harvest;
		}

		public float getHardness(){
			return hardness;
		}

		public float getResistance(){
			return resistance;
		}

		@Override
		public String toString()
		{
			return name;
		}

		public String getState(){
			return state;
		}

		public static EnumCobblestoneType byMetadata(int meta)
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

		static
		{
			for (EnumCobblestoneType enumtype : values())
				META_LOOKUP[enumtype.getMeta()] = enumtype;
		}
	}
}
