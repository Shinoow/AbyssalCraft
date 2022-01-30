/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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

import com.shinoow.abyssalcraft.api.block.ICrystalBlock;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCrystalCluster extends BlockACBasic implements ICrystalBlock {

	public static final Map<EnumCrystalType, Block> VARIANTS = new HashMap<>();

	public static final Map<EnumCrystalType2, Block> VARIANTS_2 = new HashMap<>();

	public int index;

	public BlockCrystalCluster() {
		super(Material.ROCK, "pickaxe", 3, 4.0F, 8.0F, SoundType.GLASS);
		setCreativeTab(ACTabs.tabCrystals);
	}

	public BlockCrystalCluster remap(EnumCrystalType type) {
		VARIANTS.put(type, this);
		index = type.getMetadata();
		setTranslationKey(type.getName() + "_crystal_cluster");
		return this;
	}

	public BlockCrystalCluster remap(EnumCrystalType2 type) {
		VARIANTS_2.put(type, this);
		index = type.getMetadata() + 16;
		setTranslationKey(type.getName() + "_crystal_cluster");
		return this;
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

	//	@Override
	//	public int damageDropped (IBlockState state) {
	//		return state.getValue(TYPE).getMetadata();
	//	}
	//
	//	@Override
	//	public void getSubBlocks(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
	//		for(int i = 0; i < EnumCrystalType.values().length; i++)
	//			par3List.add(new ItemStack(this, 1, i));
	//	}

	@Override
	public int getColor(ItemStack stack) {
		return ACClientVars.getCrystalColors()[index];
	}

	@Override
	public String getFormula(ItemStack stack) {
		return ACLib.crystalAtoms[index];
	}

	@Override
	public int getColor(IBlockState state) {
		return ACClientVars.getCrystalColors()[index];
	}

	public enum EnumCrystalType implements IStringSerializable
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

	public enum EnumCrystalType2 implements IStringSerializable
	{
		TIN(0, "tin"),
		COPPER(1, "copper"),
		SILICON(2, "silicon"),
		MAGNESIUM(3, "magnesium"),
		ALUMINIUM(4, "aluminium"),
		SILICA(5, "silica"),
		ALUMINA(6, "alumina"),
		MAGNESIA(7, "magnesia"),
		ZINC(8, "zinc"),
		CALCIUM(9, "calcium"),
		BERYLLIUM(10, "beryllium"),
		BERYL(11, "beryl");

		private static final EnumCrystalType2[] META_LOOKUP = new EnumCrystalType2[values().length];
		private final int meta;
		private final String name;

		private EnumCrystalType2(int meta, String name)
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

		public static EnumCrystalType2 byMetadata(int meta)
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
			for (EnumCrystalType2 enumtype : values())
				META_LOOKUP[enumtype.getMetadata()] = enumtype;
		}
	}
}
