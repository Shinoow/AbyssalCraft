/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;

public class IngotBlock extends Block {

	private EnumIngotType TYPE;
	public static final Map<EnumIngotType, Block> VARIANTS = new HashMap<>();

	public IngotBlock(EnumIngotType type) {
		super(Material.IRON, type.getMapColor());
		TYPE = type;
		setHardness(4.0F);
		setResistance(type == EnumIngotType.ETHAXIUM ? Float.MAX_VALUE : 12.0F);
		setSoundType(SoundType.METAL);
		setCreativeTab(ACTabs.tabBlock);
		setHarvestLevel("pickaxe", type.getHarvestLevel());
		VARIANTS.put(TYPE, this);
	}

	public EnumIngotType getType() {
		return TYPE;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beaconpos) {
		return true;
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return TYPE != EnumIngotType.ETHAXIUM;
		return super.canEntityDestroy(state, world, pos, entity);
	}

	public enum EnumIngotType implements IStringSerializable
	{
		ABYSSALNITE(0, "abyssalnite", "abyblock", 2, MapColor.PURPLE, TextFormatting.DARK_AQUA),
		REFINED_CORALIUM(1, "refinedcoralium", "corblock", 5, MapColor.CYAN, TextFormatting.AQUA),
		DREADIUM(2, "dreadium", "dreadiumblock", 6, MapColor.RED, TextFormatting.DARK_RED),
		ETHAXIUM(3, "ethaxium", "ethaxiumblock", 8, MapColor.CLOTH, TextFormatting.AQUA);

		private static final EnumIngotType[] META_LOOKUP = new EnumIngotType[values().length];
		private final int meta;
		private final String name;
		private final String state;
		private final int harvest;
		private final MapColor mapColor;
		private final TextFormatting format;

		private EnumIngotType(int meta, String name, String state, int harvest, MapColor mapColor, TextFormatting format)
		{
			this.meta = meta;
			this.name = name;
			this.state = state;
			this.harvest = harvest;
			this.mapColor = mapColor;
			this.format = format;
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

		@Override
		public String toString()
		{
			return name;
		}

		public String getState(){
			return state;
		}

		public TextFormatting getFormat(){
			return format;
		}

		public static EnumIngotType byMetadata(int meta)
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
			for (EnumIngotType enumtype : values())
				META_LOOKUP[enumtype.getMeta()] = enumtype;
		}
	}
}
