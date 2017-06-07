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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.List;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockCrystalCluster extends BlockACBasic {

	public static final PropertyEnum TYPE = PropertyEnum.create("type", EnumCrystalType.class);

	public BlockCrystalCluster() {
		super(Material.ROCK, 4.0F, 8.0F, SoundType.GLASS);
		setCreativeTab(ACTabs.tabCrystals);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumCrystalType.IRON));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.7F, 0.8F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return "pickaxe";
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return 3;
	}

	@Override
	public int damageDropped (IBlockState state) {
		return ((EnumCrystalType)state.getValue(TYPE)).getMetadata();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < EnumCrystalType.values().length; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumCrystalType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumCrystalType)state.getValue(TYPE)).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(TYPE).build();
	}

	public static enum EnumCrystalType implements IStringSerializable
	{
		IRON(0, "iron"),
		GOLD(1, "gold"),
		SULFUR(2, "sulfur"),
		CARBON(3, "carbon"),
		OXYGEN(4, "oxygen"),
		HYDROGEN(5, "hydrogen"),
		NITROGEN(6, "nitrogen"),
		PHOSPHORUS(7, "phosphorus"),
		POTASSIUM(8, "potassium"),
		NITRATE(9, "nitrate"),
		METHANE(10, "methane"),
		REDSTONE(11, "redstone"),
		ABYSSALNITE(12, "abyssalnite"),
		CORALIUM(13, "coralium"),
		DREADIUM(14, "dreadium"),
		BLAZE(15, "blaze");

		private static final EnumCrystalType[] META_LOOKUP = new EnumCrystalType[values().length];
		private final int meta;
		private final String name;

		private EnumCrystalType(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMetadata()
		{
			return meta;
		}

		@Override
		public String toString()
		{
			return name;
		}

		public static EnumCrystalType byMetadata(int meta)
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
			for (EnumCrystalType enumtype : values())
				META_LOOKUP[enumtype.getMetadata()] = enumtype;
		}
	}
}
