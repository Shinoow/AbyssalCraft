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

import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystalCluster extends BlockACBasic {

	public static final PropertyEnum TYPE = PropertyEnum.create("type", EnumCrystalType.class);

	public BlockCrystalCluster() {
		super(Material.rock, 4.0F, 8.0F, Block.soundTypeGlass);
		setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.7F, 0.8F);
		setCreativeTab(ACTabs.tabCrystals);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumCrystalType.IRON));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		return 16777215;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state)
	{
		return ACLib.crystalColors[((EnumCrystalType)state.getValue(TYPE)).getMetadata()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
	{
		return ACLib.crystalColors[((EnumCrystalType)worldIn.getBlockState(pos).getValue(TYPE)).getMetadata()];
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
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

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
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
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TYPE });
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
		BLAZE(15, "blaze"),
		TIN(16, "tin"),
		COPPER(17, "copper"),
		SILICON(18, "silicon"),
		MAGNESIUM(19, "magnesium"),
		ALUMINIUM(20, "aluminium"),
		SILICA(21, "silica"),
		ALUMINA(22, "alumina"),
		MAGNESIA(23, "magnesia"),
		ZINC(24, "zinc");

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
