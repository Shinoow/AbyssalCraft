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
package com.shinoow.abyssalcraft.common.blocks.baseblocks;

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockACSlab extends BlockSlab {

	public static final PropertyBool VARIANT_PROPERTY = PropertyBool.create("variant");

	private Block singleSlab;
	private MapColor mapColor;

	private static final int HALF_META_BIT = 8;

	public BlockACSlab(Block par1SingleSlab, Material par3Material, String tooltype, int harvestlevel)
	{
		this(par1SingleSlab, par3Material);
		setHarvestLevel(tooltype, harvestlevel);
	}

	@SuppressWarnings("deprecation")
	public BlockACSlab(Block par1SingleSlab, Material par3Material)
	{
		super(par3Material);
		setCreativeTab(null);
		singleSlab = par1SingleSlab;
		mapColor = par1SingleSlab.getMapColor(par1SingleSlab.getDefaultState(), null, null);

		setDefaultState(blockState.getBaseState().withProperty(VARIANT_PROPERTY, false));

		if(getHarvestTool(getDefaultState()) == null)
			if(par3Material == Material.ROCK || par3Material == Material.IRON || par3Material == Material.ANVIL)
				setHarvestLevel("pickaxe", 0);
			else if(par3Material == Material.GROUND || par3Material == Material.GRASS || par3Material == Material.SAND ||
					par3Material == Material.SNOW || par3Material == Material.CRAFTED_SNOW)
				setHarvestLevel("shovel", 0);
	}

	public BlockACSlab(Material par3Material, String tooltype, int harvestlevel, MapColor mapColor)
	{
		this(par3Material, mapColor);
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACSlab(Material par3Material, MapColor mapColor)
	{
		super(par3Material);
		singleSlab = this;
		setCreativeTab(ACTabs.tabBlock);
		useNeighborBrightness = true;
		this.mapColor = mapColor;

		setDefaultState(blockState.getBaseState().withProperty(VARIANT_PROPERTY, false).withProperty(HALF, EnumBlockHalf.BOTTOM));

		if(getHarvestTool(getDefaultState()) == null)
			if(par3Material == Material.ROCK || par3Material == Material.IRON || par3Material == Material.ANVIL)
				setHarvestLevel("pickaxe", 0);
			else if(par3Material == Material.GROUND || par3Material == Material.GRASS || par3Material == Material.SAND ||
					par3Material == Material.SNOW || par3Material == Material.CRAFTED_SNOW)
				setHarvestLevel("shovel", 0);
	}

	public BlockACSlab mipp() {

		// Too much? Maybe, but fuck it
		AbyssalCraft.proxy.setRenderLayer(this);

		return this;
	}

	@SideOnly(Side.CLIENT)
	private BlockRenderLayer layer = super.getRenderLayer();

	@SideOnly(Side.CLIENT)
	public void setRenderLayer() {
		layer = BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return layer;
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return state.getBlock() != ACBlocks.ethaxium_brick_slab && state.getBlock() != ACBlocks.dark_ethaxium_brick_slab &&
			state.getBlock() != BlockHandler.ethaxiumslab2 && state.getBlock() != BlockHandler.darkethaxiumslab2;
		return super.canEntityDestroy(state, world, pos, entity);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_)
	{
		return mapColor;
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
	public String getTranslationKey(int meta) {

		return this.getTranslationKey();
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
		IBlockState blockState = getDefaultState().withProperty(VARIANT_PROPERTY, false);
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
		return !isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP ? HALF_META_BIT : 0;
	}

	@Override
	public final int damageDropped(final IBlockState state) {
		return 0;
	}

	@Override
	protected final BlockStateContainer createBlockState() {
		if (isDouble())
			return new BlockStateContainer.Builder(this).add(VARIANT_PROPERTY).build();
		else
			return new BlockStateContainer.Builder(this).add(VARIANT_PROPERTY, HALF).build();
	}
}
