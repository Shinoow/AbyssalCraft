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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;

public abstract class BlockACSlab extends BlockSlab {

	public static final PropertyBool VARIANT_PROPERTY = PropertyBool.create("variant");

	private Block singleSlab;

	private static final int HALF_META_BIT = 8;

	public BlockACSlab(Block par1SingleSlab, Material par3Material, String tooltype, int harvestlevel)
	{
		this(par1SingleSlab, par3Material);
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACSlab(Block par1SingleSlab, Material par3Material)
	{
		super(par3Material);
		setCreativeTab(null);
		singleSlab = par1SingleSlab;

		setDefaultState(blockState.getBaseState().withProperty(VARIANT_PROPERTY, false));
	}

	public BlockACSlab(Material par3Material, String tooltype, int harvestlevel)
	{
		this(par3Material);
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACSlab(Material par3Material)
	{
		super(par3Material);
		singleSlab = this;
		setCreativeTab(AbyssalCraft.tabBlock);
		useNeighborBrightness = true;

		setDefaultState(blockState.getBaseState().withProperty(VARIANT_PROPERTY, false).withProperty(HALF, EnumBlockHalf.BOTTOM));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(singleSlab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
	{
		return new ItemStack(singleSlab);
	}

	@Override
	public String getUnlocalizedName(int meta) {

		return this.getUnlocalizedName();
	}

	@Override
	public IProperty<?> getVariantProperty() {

		return VARIANT_PROPERTY;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {

		return false;
	}

	@Override
	public final IBlockState getStateFromMeta(final int meta) {
		IBlockState blockState = getDefaultState();
		blockState = blockState.withProperty(VARIANT_PROPERTY, false);
		if (!isDouble()) {
			EnumBlockHalf value = EnumBlockHalf.BOTTOM;
			if ((meta & HALF_META_BIT) != 0)
				value = EnumBlockHalf.TOP;

			blockState = blockState.withProperty(HALF, value);
		}

		return blockState;
	}

	@Override
	public final int getMetaFromState(final IBlockState state) {
		if (isDouble())
			return 0;

		if (state.getValue(HALF) == EnumBlockHalf.TOP)
			return HALF_META_BIT;
		else
			return 0;
	}

	@Override
	public final int damageDropped(final IBlockState state) {
		return 0;
	}

	@Override
	protected final BlockStateContainer createBlockState() {
		if (isDouble())
			return new BlockStateContainer(this, new IProperty[] {VARIANT_PROPERTY});
		else
			return new BlockStateContainer(
					this,
					new IProperty[] {VARIANT_PROPERTY, HALF});
	}
}
