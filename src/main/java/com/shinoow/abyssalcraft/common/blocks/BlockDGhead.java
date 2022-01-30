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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.tile.*;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.tileentity.TEDirectional;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.Method;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

@Interface(modid = "thaumcraft", iface = "thaumcraft.api.crafting.IInfusionStabiliserExt", striprefs = true)
public class BlockDGhead extends BlockContainer implements IInfusionStabiliserExt {

	public BlockDGhead() {
		super(Material.CLOTH);
		setSoundType(SoundType.CLOTH);
		setHardness(1.0F);
		setResistance(6.0F);
		setCreativeTab(ACTabs.tabDecoration);
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

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1World, BlockPos pos)
	{
		return new AxisAlignedBB(0.1F, 0.0F, 0.1F, 0.8F, 0.7F, 0.8F);
	}

	@Override
	public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		if (par5EntityLivingBase == null)
			return;

		TEDirectional tile = (TEDirectional) par1World.getTileEntity(pos);
		tile.direction = MathHelper.floor(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(this == ACBlocks.depths_ghoul_head)
			return new TileEntityDGhead();
		else if (this == ACBlocks.pete_head)
			return new TileEntityPhead();
		else if(this == ACBlocks.mr_wilson_head)
			return new TileEntityWhead();
		else if(this == ACBlocks.dr_orange_head)
			return new TileEntityOhead();
		return null;
	}

	@Override
	@Method(modid = "thaumcraft")
	public boolean canStabaliseInfusion(World world, BlockPos pos) {

		return true;
	}

	@Override
	@Method(modid = "thaumcraft")
	public float getStabilizationAmount(World world, BlockPos pos) {

		return 0.1f;
	}
}
