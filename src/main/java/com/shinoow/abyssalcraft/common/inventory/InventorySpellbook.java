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
package com.shinoow.abyssalcraft.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventorySpellbook implements IInventory
{
	private String name;

	public static int INV_SIZE = 6;

	private NonNullList<ItemStack> inventory;

	private final ItemStack invItem;
	private final Container container;

	/**
	 * @param itemstack - the ItemStack to which this inventory belongs
	 */
	public InventorySpellbook(Container container, ItemStack stack)
	{
		this.container = container;
		invItem = stack;
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		inventory = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
		name = stack.getDisplayName();
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = ItemStackHelper.getAndSplit(inventory, slot, amount);

		if(!stack.isEmpty())
			container.onCraftMatrixChanged(this);

		return stack;

	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		return ItemStackHelper.getAndRemove(inventory, slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack)
	{
		inventory.set(slot, itemstack);

		if (!itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit())
			itemstack.setCount(getInventoryStackLimit());
		container.onCraftMatrixChanged(this);
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public boolean hasCustomName()
	{
		return name.length() > 0;
	}

	@Override
	public ITextComponent getDisplayName() {

		return null;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty(){}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return player.getHeldItem(EnumHand.MAIN_HAND) == invItem;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return slot > 6;
	}

	@Override
	public int getField(int id) {

		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : inventory)
			if (!itemstack.isEmpty())
				return false;

		return true;
	}
}
