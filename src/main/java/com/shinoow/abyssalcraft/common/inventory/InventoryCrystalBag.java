/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.inventory;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;

public class InventoryCrystalBag implements IInventory
{
	private String name;

	public static int INV_SIZE;

	private NonNullList<ItemStack> inventory;

	private final ItemStack invItem;
	private final EnumHand hand;

	/**
	 * @param itemstack - the ItemStack to which this inventory belongs
	 */
	public InventoryCrystalBag(ItemStack stack, EnumHand hand)
	{
		invItem = stack;
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		INV_SIZE = stack.getTagCompound().getInteger("InvSize");
		inventory = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
		name = stack.getDisplayName();
		this.hand = hand;

		readFromNBT(stack.getTagCompound());
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
		ItemStack itemstack = ItemStackHelper.getAndSplit(inventory, slot, amount);

		if (!itemstack.isEmpty())
			markDirty();

		return itemstack;
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

		markDirty();
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
	public void markDirty()
	{
		for (int i = 0; i < getSizeInventory(); ++i)
			if (getStackInSlot(i).isEmpty())
				inventory.set(i, ItemStack.EMPTY);
		writeToNBT(invItem.getTagCompound());
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return APIUtils.areStacksEqual(player.getHeldItem(hand), invItem);
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return APIUtils.isCrystal(itemstack);
	}

	public void readFromNBT(NBTTagCompound tagcompound)
	{
		NBTTagList items = tagcompound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < items.tagCount(); ++i)
		{
			NBTTagCompound item = items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");

			if (slot >= 0 && slot < getSizeInventory())
				inventory.set(slot, new ItemStack(item));
		}
	}

	public void writeToNBT(NBTTagCompound tagcompound)
	{
		NBTTagList items = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); ++i)
			if (!getStackInSlot(i).isEmpty())
			{
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("Slot", i);
				getStackInSlot(i).writeToNBT(item);

				items.appendTag(item);
			}
		tagcompound.setTag("ItemInventory", items);
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
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
