/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.common.blocks.BlockCrate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCrate extends TileEntity implements IInventory, ITickable, IInteractionObject
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
	public ItemStack removeStackFromSlot(int par1)
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
	public String getName()
	{
		return hasCustomName() ? customName : "container.abyssalcraft.crate";
	}

	@Override
	public boolean hasCustomName()
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
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
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

		if (hasCustomName())
			par1NBTTagCompound.setString("CustomName", customName);

		return par1NBTTagCompound;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getTileEntity(pos) != this ? false : par1EntityPlayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}

	@Override
	public void update()
	{
		++ticksSinceSync;
		if (!worldObj.isRemote && numUsingPlayers != 0 && (ticksSinceSync + pos.getX() + pos.getY() + pos.getZ()) % 200 == 0)
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
	public void openInventory(EntityPlayer player)
	{
		if (numUsingPlayers < 0)
			numUsingPlayers = 0;

		++numUsingPlayers;
		worldObj.addBlockEvent(pos, getBlockType(), 1, numUsingPlayers);
		worldObj.notifyBlockOfStateChange(pos, getBlockType());
		worldObj.notifyBlockOfStateChange(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), getBlockType());
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (getBlockType() != null && getBlockType() instanceof BlockCrate)
		{
			--numUsingPlayers;
			worldObj.addBlockEvent(pos, getBlockType(), 1, numUsingPlayers);
			worldObj.notifyBlockOfStateChange(pos, getBlockType());
			worldObj.notifyBlockOfStateChange(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()), getBlockType());
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

	@Override
	public ITextComponent getDisplayName()
	{
		return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName(), new Object[0]);
	}

	@Override
	public int getField(int id) {

		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {
		for(ItemStack stack : crateContents)
			stack = null;

	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory,
		EntityPlayer playerIn) {

		return new ContainerChest(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {

		return "minecraft:chest";
	}

	private net.minecraftforge.items.IItemHandler itemHandler = new net.minecraftforge.items.wrapper.InvWrapper(this);

	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
	{
		if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) itemHandler;
		return super.getCapability(capability, facing);
	}
}
