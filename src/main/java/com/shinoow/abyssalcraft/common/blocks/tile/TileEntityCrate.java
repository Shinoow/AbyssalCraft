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
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.blocks.BlockCrate;

public class TileEntityCrate extends TileEntity implements IInventory, ITickable, IInteractionObject
{
	private NonNullList<ItemStack> crateContents = NonNullList.withSize(36, ItemStack.EMPTY);
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
		return crateContents.get(par1);
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(crateContents, par1, par2);

		if (!itemstack.isEmpty())
			markDirty();

		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		return ItemStackHelper.getAndRemove(crateContents, par1);
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		crateContents.set(par1, par2ItemStack);

		if (!par2ItemStack.isEmpty() && par2ItemStack.getCount() > getInventoryStackLimit())
			par2ItemStack.setCount(getInventoryStackLimit());

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
		crateContents = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);


		ItemStackHelper.loadAllItems(par1NBTTagCompound, crateContents);


		if (par1NBTTagCompound.hasKey("CustomName", 8))
			customName = par1NBTTagCompound.getString("CustomName");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);

		ItemStackHelper.saveAllItems(par1NBTTagCompound, crateContents);

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
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return world.getTileEntity(pos) != this ? false : par1EntityPlayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
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
		if (!world.isRemote && numUsingPlayers != 0 && (ticksSinceSync + pos.getX() + pos.getY() + pos.getZ()) % 200 == 0)
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
		world.addBlockEvent(pos, getBlockType(), 1, numUsingPlayers);
		world.notifyNeighborsOfStateChange(pos, getBlockType(), false);
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (getBlockType() != null && getBlockType() instanceof BlockCrate)
		{
			--numUsingPlayers;
			world.addBlockEvent(pos, getBlockType(), 1, numUsingPlayers);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), false);
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
			if (world == null || !(getBlockType() instanceof BlockCrate))
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
		crateContents.clear();

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

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : crateContents)
			if (!itemstack.isEmpty())
				return false;

		return true;
	}

}
