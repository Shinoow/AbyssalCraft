/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityIdolOfFading;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockIdolOfFading extends BlockContainer {

	public BlockIdolOfFading(){
		super(Material.ROCK, MapColor.BLACK);
		setHardness(1.0F);
		setResistance(18.0F);
		setSoundType(SoundType.STONE);
		setTranslationKey("idol_of_fading");
		setCreativeTab(ACTabs.tabDecoration);
		setLightLevel(0.5F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileEntityIdolOfFading();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1World, BlockPos pos)
	{
		return new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
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
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
}
