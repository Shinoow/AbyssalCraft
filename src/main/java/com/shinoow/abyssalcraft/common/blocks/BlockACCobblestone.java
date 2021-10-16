/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockACCobblestone extends Block {

	public static final PropertyEnum<EnumCobblestoneType> TYPE = PropertyEnum.create("type", EnumCobblestoneType.class);

	public BlockACCobblestone() {
		super(Material.ROCK);
		setDefaultState(getDefaultState().withProperty(TYPE, EnumCobblestoneType.DARKSTONE));
		setHardness(2.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabBlock);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_)
	{
		return state.getValue(TYPE).getMapColor();
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return blockState.getValue(TYPE).getHardness();
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion)
	{
		return world.getBlockState(pos).getValue(TYPE).getResistance();
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return state.getValue(TYPE).getHarvestLevel();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(TYPE, EnumCobblestoneType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public int damageDropped (IBlockState state) {
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public void getSubBlocks(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		for(int i = 0; i < EnumCobblestoneType.values().length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(TYPE).build();
	}

	public static enum EnumCobblestoneType implements IStringSerializable
	{
		DARKSTONE(0, "darkstone", "darkstone_cobble", 0, 2.2F, 12.0F, MapColor.BLACK),
		ABYSSAL_STONE(1, "abyssalstone", "abyssalcobblestone", 2, 2.6F, 12.0F, MapColor.GREEN),
		DREADSTONE(2, "dreadstone", "dreadstonecobblestone", 4, 3.3F, 20.0F, MapColor.RED),
		ABYSSALNITE_STONE(3, "abyssalnitestone", "abyssalnitecobblestone", 4, 3.3F, 20.0F, MapColor.PURPLE),
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
