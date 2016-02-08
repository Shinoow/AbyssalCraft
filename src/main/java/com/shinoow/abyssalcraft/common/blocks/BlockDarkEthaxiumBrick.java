/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.common.blocks.BlockEthaxiumBrick.EnumBrickType;

public class BlockDarkEthaxiumBrick extends BlockACBasic {

	public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockEthaxiumBrick.EnumBrickType.class);

	public BlockDarkEthaxiumBrick() {
		super(Material.rock, "pickaxe", 8, 150.0F, Float.MAX_VALUE, soundTypeStone);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumBrickType.NORMAL));

	}

	@Override
	public int damageDropped (IBlockState state) {
		return ((EnumBrickType)state.getValue(TYPE)).getMeta();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
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
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TYPE });
	}
}