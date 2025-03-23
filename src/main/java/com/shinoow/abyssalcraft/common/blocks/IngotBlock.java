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
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class IngotBlock extends Block {

	public static final PropertyEnum<EnumIngotType> TYPE = PropertyEnum.create("type", EnumIngotType.class);

	public IngotBlock() {
		super(Material.IRON);
		setDefaultState(getDefaultState().withProperty(TYPE, EnumIngotType.ABYSSALNITE));
		setHardness(4.0F);
		setResistance(12.0F);
		setSoundType(SoundType.METAL);
		setCreativeTab(ACTabs.tabBlock);
		setHarvestLevel("pickaxe", 2);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beaconpos) {
		return true;
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return state.getValue(TYPE) != EnumIngotType.ETHAXIUM;
		return super.canEntityDestroy(state, world, pos, entity);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_)
	{
		return state.getValue(TYPE).getMapColor();
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion)
	{
		return world.getBlockState(pos).getValue(TYPE) == EnumIngotType.ETHAXIUM ? Float.MAX_VALUE : super.getExplosionResistance(world, pos, exploder, explosion);
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return state.getValue(TYPE).getHarvestLevel();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(TYPE, EnumIngotType.byMetadata(meta));
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
		for(int i = 0; i < EnumIngotType.values().length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(TYPE).build();
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
