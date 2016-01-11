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
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrate;

public class BlockCrate extends BlockContainer
{
	private final Random random = new Random();

	public BlockCrate(){
		super(Material.wood);
		setCreativeTab(AbyssalCraft.tabDecoration);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
	{
		Block l = par1World.getBlock(par2, par3, par4 - 1);
		Block i1 = par1World.getBlock(par2, par3, par4 + 1);
		Block j1 = par1World.getBlock(par2 - 1, par3, par4);
		Block k1 = par1World.getBlock(par2 + 1, par3, par4);
		byte b0 = 0;
		int l1 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l1 == 0)
			b0 = 2;

		if (l1 == 1)
			b0 = 5;

		if (l1 == 2)
			b0 = 3;

		if (l1 == 3)
			b0 = 4;

		if (l != this && i1 != this && j1 != this && k1 != this)
			par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
		else
		{
			if ((l == this || i1 == this) && (b0 == 4 || b0 == 5))
			{
				if (l == this)
					par1World.setBlockMetadataWithNotify(par2, par3, par4 - 1, b0, 3);
				else
					par1World.setBlockMetadataWithNotify(par2, par3, par4 + 1, b0, 3);

				par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
			}

			if ((j1 == this || k1 == this) && (b0 == 2 || b0 == 3))
			{
				if (j1 == this)
					par1World.setBlockMetadataWithNotify(par2 - 1, par3, par4, b0, 3);
				else
					par1World.setBlockMetadataWithNotify(par2 + 1, par3, par4, b0, 3);

				par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
			}
		}

		if (par6ItemStack.hasDisplayName())
			((TileEntityCrate)par1World.getTileEntity(par2, par3, par4)).func_94043_a(par6ItemStack.getDisplayName());
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		TileEntityCrate TileEntityCrate = (TileEntityCrate)par1World.getTileEntity(par2, par3, par4);

		if (TileEntityCrate != null)
			TileEntityCrate.updateContainingBlockInfo();
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
	{
		TileEntityCrate TileEntityCrate = (TileEntityCrate)par1World.getTileEntity(par2, par3, par4);

		if (TileEntityCrate != null)
		{
			for (int j1 = 0; j1 < TileEntityCrate.getSizeInventory(); ++j1)
			{
				ItemStack itemstack = TileEntityCrate.getStackInSlot(j1);

				if (itemstack != null)
				{
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
					{
						int k1 = random.nextInt(21) + 10;

						if (k1 > itemstack.stackSize)
							k1 = itemstack.stackSize;

						itemstack.stackSize -= k1;
						entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (float)random.nextGaussian() * f3;
						entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float)random.nextGaussian() * f3;

						if (itemstack.hasTagCompound())
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
					}
				}
			}

			par1World.func_147453_f(par2, par3, par4, par5);
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (par1World.isRemote)
			return true;
		else
		{
			IInventory iinventory = func_149951_m(par1World, par2, par3, par4);

			if (iinventory != null)
				par5EntityPlayer.displayGUIChest(iinventory);

			return true;
		}
	}

	public IInventory func_149951_m(World par1World, int par2, int par3, int par4)
	{
		Object object = par1World.getTileEntity(par2, par3, par4);

		if (object == null)
			return null;
		else return (IInventory)object;
	}

	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		return Container.calcRedstoneFromInventory(func_149951_m(par1World, par2, par3, par4));
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		TileEntityCrate tileentitycrate = new TileEntityCrate();
		return tileentitycrate;
	}
}
