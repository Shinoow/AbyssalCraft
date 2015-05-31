/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.shinoow.abyssalcraft.common.blocks.BlockCrate;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCrate extends TileEntity implements IInventory
{
	private ItemStack[] crateContents = new ItemStack[36];
	public int numUsingPlayers;
	private int ticksSinceSync;
	private int cachedCrateType;
	private String customName;

	public TileEntityCrate(){
		cachedCrateType = -1;
	}

	@SideOnly(Side.CLIENT)
	public TileEntityCrate(int par1){
		cachedCrateType = par1;
	}

	@Override
	public int getSizeInventory()
	{
		return 36;
	}

	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return crateContents[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (crateContents[par1] != null)
		{
			ItemStack itemstack;

			if (crateContents[par1].stackSize <= par2)
			{
				itemstack = crateContents[par1];
				crateContents[par1] = null;
				markDirty();
				return itemstack;
			}
			else
			{
				itemstack = crateContents[par1].splitStack(par2);

				if (crateContents[par1].stackSize == 0)
					crateContents[par1] = null;

				markDirty();
				return itemstack;
			}
		} else
			return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (crateContents[par1] != null)
		{
			ItemStack itemstack = crateContents[par1];
			crateContents[par1] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		crateContents[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();

		markDirty();
	}

	@Override
	public String getInventoryName()
	{
		return hasCustomInventoryName() ? customName : "container.abyssalcraft.crate";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return customName != null && customName.length() > 0;
	}

	public void func_94043_a(String par1Str)
	{
		customName = par1Str;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		crateContents = new ItemStack[getSizeInventory()];

		if (par1NBTTagCompound.hasKey("CustomName", 8))
			customName = par1NBTTagCompound.getString("CustomName");

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < crateContents.length)
				crateContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < crateContents.length; ++i)
			if (crateContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				crateContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}

		par1NBTTagCompound.setTag("Items", nbttaglist);

		if (hasCustomInventoryName())
			par1NBTTagCompound.setString("CustomName", customName);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		++ticksSinceSync;
		if (!worldObj.isRemote && numUsingPlayers != 0 && (ticksSinceSync + xCoord + yCoord + zCoord) % 200 == 0)
			numUsingPlayers = 0;
	}

	@Override
	public boolean receiveClientEvent(int par1, int par2)
	{
		if (par1 == 1)
		{
			numUsingPlayers = par2;
			return true;
		} else
			return super.receiveClientEvent(par1, par2);
	}

	@Override
	public void openInventory()
	{
		if (numUsingPlayers < 0)
			numUsingPlayers = 0;

		++numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, numUsingPlayers);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());
	}

	@Override
	public void closeInventory()
	{
		if (getBlockType() != null && getBlockType() instanceof BlockCrate)
		{
			--numUsingPlayers;
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, numUsingPlayers);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());
		}
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		updateContainingBlockInfo();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {

		return true;
	}

	public int func_98041_l()
	{
		if (cachedCrateType == -1)
			if (worldObj == null || !(getBlockType() instanceof BlockCrate))
				return 0;

		return cachedCrateType;
	}

}
