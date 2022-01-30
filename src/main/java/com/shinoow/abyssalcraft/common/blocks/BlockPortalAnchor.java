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

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPortalAnchor;
import com.shinoow.abyssalcraft.common.entity.EntityPortal;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPortalAnchor extends BlockACBasic {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockPortalAnchor() {
		super(Material.ROCK, 10.0F, 24.0F, SoundType.STONE);
		setTranslationKey("portal_anchor");
		setDefaultState(blockState.getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.15F, 0.0F, 0.15F, 0.85F, 0.5F, 0.85F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityPortalAnchor();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

		if(!worldIn.isRemote && state.getValue(ACTIVE))
			worldIn.getEntitiesWithinAABB(EntityPortal.class, new AxisAlignedBB(pos).grow(2))
			.stream().forEach(e -> worldIn.removeEntity(e));

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(ACBlocks.monolith_stone);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(ACBlocks.monolith_stone);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(ACTIVE, (meta & 1) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(ACTIVE) ? 1 : 0;
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(ACTIVE).build();
	}
}
