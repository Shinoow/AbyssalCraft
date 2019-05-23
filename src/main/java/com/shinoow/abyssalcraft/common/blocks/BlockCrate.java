/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrate;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCrate extends BlockContainer {

	public BlockCrate(){
		super(Material.WOOD);
		setCreativeTab(ACTabs.tabDecoration);
		setSoundType(SoundType.WOOD);
	}

	@Override
	public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
	{
		if (par6ItemStack.hasDisplayName())
			((TileEntityCrate)par1World.getTileEntity(pos)).func_94043_a(par6ItemStack.getDisplayName());
	}

	@Override
	public void breakBlock(World par1World, BlockPos pos, IBlockState state)
	{
		TileEntity tileEntity = par1World.getTileEntity(pos);

		if (tileEntity instanceof TileEntityCrate)
		{
			InventoryHelper.dropInventoryItems(par1World, pos, (TileEntityCrate)tileEntity);
			par1World.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(par1World, pos, state);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, EnumFacing side, float par7, float par8, float par9)
	{
		if (!par1World.isRemote) {
			IInventory iinventory = getInventory(par1World, pos);

			if (iinventory != null)
				par5EntityPlayer.displayGUIChest(iinventory);
		}
		return true;
	}

	public IInventory getInventory(World par1World, BlockPos pos)
	{
		TileEntity tile = par1World.getTileEntity(pos);

		return tile instanceof IInventory ? (IInventory)tile : null;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World par1World, BlockPos pos)
	{
		return Container.calcRedstoneFromInventory(getInventory(par1World, pos));
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		TileEntityCrate tileentitycrate = new TileEntityCrate();
		return tileentitycrate;
	}
}
