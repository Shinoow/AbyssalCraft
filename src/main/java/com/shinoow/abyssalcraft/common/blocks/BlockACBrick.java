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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

public class BlockACBrick extends BlockACBasic {

	public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockACBrick.EnumBrickType.class);

	public BlockACBrick(int harvestlevel, float hardness, float resistance) {
		super(Material.ROCK, "pickaxe", harvestlevel, hardness, resistance, SoundType.STONE);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumBrickType.NORMAL));
	}

	public BlockACBrick(float hardness, float resistance) {
		super(Material.ROCK, hardness, resistance, SoundType.STONE);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumBrickType.NORMAL));
	}

	public BlockACBrick(int harvestlevel, float hardness, float resistance, MapColor mapColor) {
		super(Material.ROCK, "pickaxe", harvestlevel, hardness, resistance, SoundType.STONE, mapColor);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumBrickType.NORMAL));
	}

	public BlockACBrick(float hardness, float resistance, MapColor mapColor) {
		super(Material.ROCK, hardness, resistance, SoundType.STONE, mapColor);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumBrickType.NORMAL));
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return state.getBlock() != ACBlocks.ethaxium_brick && state.getBlock() != ACBlocks.dark_ethaxium_brick;
		return super.canEntityDestroy(state, world, pos, entity);
	}

	@Override
	public int damageDropped (IBlockState state) {
		return ((EnumBrickType)state.getValue(TYPE)).getMeta();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < EnumBrickType.values().length; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, EnumBrickType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBrickType)state.getValue(TYPE)).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer.Builder(this).add(TYPE).build();
	}

	public enum EnumBrickType implements IStringSerializable {
		NORMAL(0, "normal"),
		CHISELED(1, "chiseled"),
		CRACKED(2, "cracked");

		private static final BlockACBrick.EnumBrickType[] META_LOOKUP = new BlockACBrick.EnumBrickType[values().length];

		private int meta;
		private String name;

		private EnumBrickType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public static BlockACBrick.EnumBrickType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length)
				meta = 0;

			return META_LOOKUP[meta];
		}

		@Override
		public String getName() {
			return name;
		}

		public int getMeta() {
			return meta;
		}

		@Override
		public String toString() {
			return getName();
		}

		static {
			for(BlockACBrick.EnumBrickType type : values())
				META_LOOKUP[type.getMeta()] = type;
		}
	}
}
