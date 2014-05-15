/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.blocks;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;

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

public class Crate extends BlockContainer
{
	private final Random random = new Random();

	public Crate()
	{
		super(Material.wood);
		this.setCreativeTab(AbyssalCraft.tabDecoration);
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
	{
		Block l = par1World.getBlock(par2, par3, par4 - 1);
		Block i1 = par1World.getBlock(par2, par3, par4 + 1);
		Block j1 = par1World.getBlock(par2 - 1, par3, par4);
		Block k1 = par1World.getBlock(par2 + 1, par3, par4);
		byte b0 = 0;
		int l1 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l1 == 0)
		{
			b0 = 2;
		}

		if (l1 == 1)
		{
			b0 = 5;
		}

		if (l1 == 2)
		{
			b0 = 3;
		}

		if (l1 == 3)
		{
			b0 = 4;
		}

		if (l != this && i1 != this && j1 != this && k1 != this)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
		}
		else
		{
			if ((l == this || i1 == this) && (b0 == 4 || b0 == 5))
			{
				if (l == this)
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4 - 1, b0, 3);
				}
				else
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4 + 1, b0, 3);
				}

				par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
			}

			if ((j1 == this || k1 == this) && (b0 == 2 || b0 == 3))
			{
				if (j1 == this)
				{
					par1World.setBlockMetadataWithNotify(par2 - 1, par3, par4, b0, 3);
				}
				else
				{
					par1World.setBlockMetadataWithNotify(par2 + 1, par3, par4, b0, 3);
				}

				par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
			}
		}

		if (par6ItemStack.hasDisplayName())
		{
			((TileEntityCrate)par1World.getTileEntity(par2, par3, par4)).func_94043_a(par6ItemStack.getDisplayName());
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		TileEntityCrate TileEntityCrate = (TileEntityCrate)par1World.getTileEntity(par2, par3, par4);

		if (TileEntityCrate != null)
		{
			TileEntityCrate.updateContainingBlockInfo();
		}
	}

	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 */
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
					float f = this.random.nextFloat() * 0.8F + 0.1F;
					float f1 = this.random.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
					{
						int k1 = this.random.nextInt(21) + 10;

						if (k1 > itemstack.stackSize)
						{
							k1 = itemstack.stackSize;
						}

						itemstack.stackSize -= k1;
						entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
						entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}
					}
				}
			}

			par1World.func_147453_f(par2, par3, par4, par5);
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (par1World.isRemote)
		{
			return true;
		}
		else
		{
			IInventory iinventory = this.func_149951_m(par1World, par2, par3, par4);

			if (iinventory != null)
			{
				par5EntityPlayer.displayGUIChest(iinventory);
			}

			return true;
		}
	}

	/**
	 * Gets the inventory of the chest at the specified coords, accounting for blocks or ocelots on top of the chest,
	 * and double chests.
	 */
	public IInventory func_149951_m(World par1World, int par2, int par3, int par4)
	{
		Object object = (TileEntityCrate)par1World.getTileEntity(par2, par3, par4);

		if (object == null)
		{
			return null;
		}
		else if (par1World.isSideSolid(par2, par3 + 1, par4, DOWN))
		{
			return null;
		}

		else
		{
			return (IInventory)object;
		}
	}

	/**
	 * If this returns true, then comparators facing away from this block will use the value from
	 * getComparatorInputOverride instead of the actual redstone signal strength.
	 */
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	/**
	 * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
	 * strength when this block inputs to a comparator.
	 */
	@Override
	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		return Container.calcRedstoneFromInventory(this.func_149951_m(par1World, par2, par3, par4));
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		TileEntityCrate tileentitycrate = new TileEntityCrate();
		return tileentitycrate;
	}
}
