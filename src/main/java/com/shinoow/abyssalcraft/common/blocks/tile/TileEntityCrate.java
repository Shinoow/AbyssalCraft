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
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;

public class TileEntityCrate extends TileEntity implements IInventory, IInteractionObject
{
	private NonNullList<ItemStack> crateContents = NonNullList.withSize(36, ItemStack.EMPTY);
	private String customName;

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

	public void setCustomName(String par1Str)
	{
		customName = par1Str;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		crateContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);

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
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {

		return true;
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
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {

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

	private net.minecraftforge.items.IItemHandler itemHandler = new net.minecraftforge.items.wrapper.InvWrapper(this);

	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
	{
		if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) itemHandler;
		return super.getCapability(capability, facing);
	}
}
