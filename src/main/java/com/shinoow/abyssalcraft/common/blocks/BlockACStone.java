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

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockACStone extends Block {

	public static final PropertyEnum<EnumStoneType> TYPE = PropertyEnum.create("type", EnumStoneType.class);

	public BlockACStone() {
		super(Material.ROCK);
		setDefaultState(getDefaultState().withProperty(TYPE, EnumStoneType.DARKSTONE));
		setHardness(2.0F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabBlock);
		setHarvestLevel("pickaxe", 0);
		setTickRandomly(true);
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
		return getDefaultState().withProperty(TYPE, EnumStoneType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon)
			return state.getValue(TYPE) != EnumStoneType.OMOTHOL_STONE &&
			state.getValue(TYPE) != EnumStoneType.ETHAXIUM;
		else if (entity instanceof net.minecraft.entity.boss.EntityWither ||
				entity instanceof net.minecraft.entity.projectile.EntityWitherSkull)
			return state.getValue(TYPE) != EnumStoneType.ETHAXIUM;
		return super.canEntityDestroy(state, world, pos, entity);
	}

	@Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
		if (!par1World.isRemote && state.getValue(TYPE) == EnumStoneType.CORALIUM_STONE)
			for(EnumFacing face : EnumFacing.values())
				if (par1World.getBlockState(pos.offset(face)).getBlock() == ACBlocks.liquid_coralium && par5Random.nextFloat() < 0.3)
					par1World.setBlockState(pos.offset(face), state);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int j)
	{
		return damageDropped(state) < 5 ? Item.getItemFromBlock(ACBlocks.cobblestone) : super.getItemDropped(state, random, j);
	}

	@Override
	public int damageDropped (IBlockState state) {
		return state.getValue(TYPE).getMeta();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		for(int i = 0; i < EnumStoneType.values().length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(TYPE).build();
	}

	public static enum EnumStoneType implements IStringSerializable
	{
		DARKSTONE(0, "darkstone", "darkstone", 0, 2.2F, 12.0F, MapColor.BLACK),
		ABYSSAL_STONE(1, "abyssalstone", "abystone", 2, 2.6F, 12.0F, MapColor.GREEN),
		DREADSTONE(2, "dreadstone", "dreadstone", 4, 3.3F, 20.0F, MapColor.RED),
		ABYSSALNITE_STONE(3, "abyssalnitestone", "abydreadstone", 4, 3.3F, 20.0F, MapColor.PURPLE),
		CORALIUM_STONE(4, "coraliumstone", "cstone", 0, 2.0F, 10.0F, MapColor.CYAN),
		ETHAXIUM(5, "ethaxium", "ethaxium", 8, 100.0F, Float.MAX_VALUE, MapColor.CLOTH),
		OMOTHOL_STONE(6, "omotholstone", "omotholstone", 6, 10.0F, 12.0F, MapColor.BLACK),
		MONOLITH_STONE(7, "monolithstone", "monolithstone", 0, 6.0F, 24.0F, MapColor.BLACK);

		private static final EnumStoneType[] META_LOOKUP = new EnumStoneType[values().length];
		private final int meta;
		private final String name;
		private final String state;
		private final int harvest;
		private final float hardness;
		private final float resistance;
		private final MapColor mapColor;

		private EnumStoneType(int meta, String name, String state, int harvest, float hardness, float resistance, MapColor mapColor)
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

		public static EnumStoneType byMetadata(int meta)
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
			for (EnumStoneType enumtype : values())
				META_LOOKUP[enumtype.getMeta()] = enumtype;
		}
	}
}
