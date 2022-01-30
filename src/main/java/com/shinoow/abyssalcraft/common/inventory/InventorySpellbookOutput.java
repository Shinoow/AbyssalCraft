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

import com.shinoow.abyssalcraft.api.spell.Spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventorySpellbookOutput implements IInventory {

	private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	public Spell spell;

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory.get(0);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		return ItemStackHelper.getAndRemove(inventory, 0);
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		return ItemStackHelper.getAndRemove(inventory, 0);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack)
	{
		inventory.set(0, itemstack);
	}

	@Override
	public String getName()
	{
		return "Spell output";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
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
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return true;
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
