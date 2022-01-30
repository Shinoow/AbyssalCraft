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

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityMaterializer;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.TransferStackMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryMaterializer implements IInventory
{
	private String inventoryTitle;
	private final int slotsCount;
	private final NonNullList<ItemStack> inventoryContents;
	private boolean hasCustomName;
	private final TileEntityMaterializer tile;

	public InventoryMaterializer(String title, boolean customName, int slotCount, TileEntityMaterializer tile)
	{
		inventoryTitle = title;
		hasCustomName = customName;
		slotsCount = slotCount;
		inventoryContents = NonNullList.<ItemStack>withSize(slotCount, ItemStack.EMPTY);
		this.tile = tile;
	}

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return index >= 0 && index < inventoryContents.size() ? (ItemStack)inventoryContents.get(index) : ItemStack.EMPTY;
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = inventoryContents.get(index).copy();

		if (!itemstack.isEmpty())
		{
			itemstack.setCount(count);
			markDirty();
			if(tile.getWorld().isRemote)
				PacketDispatcher.sendToServer(new TransferStackMessage(index, itemstack));
			MaterializerRecipes.instance().processMaterialization(itemstack, tile.getStackInSlot(0));
			tile.isDirty = true;
		}

		return itemstack;
	}

	public ItemStack addItem(ItemStack stack)
	{
		ItemStack itemstack = stack.copy();

		for (int i = 0; i < slotsCount; ++i)
		{
			ItemStack itemstack1 = getStackInSlot(i);

			if (itemstack1.isEmpty())
			{
				setInventorySlotContents(i, itemstack);
				markDirty();
				return ItemStack.EMPTY;
			}

			if (ItemStack.areItemsEqual(itemstack1, itemstack))
			{
				int j = Math.min(getInventoryStackLimit(), itemstack1.getMaxStackSize());
				int k = Math.min(itemstack.getCount(), j - itemstack1.getCount());

				if (k > 0)
				{
					itemstack1.grow(k);
					itemstack.shrink(k);

					if (itemstack.isEmpty())
					{
						markDirty();
						return ItemStack.EMPTY;
					}
				}
			}
		}

		if (itemstack.getCount() != stack.getCount()) markDirty();

		return itemstack;
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack itemstack = inventoryContents.get(index);

		if (itemstack.isEmpty())
			return ItemStack.EMPTY;
		else
		{
			inventoryContents.set(index, ItemStack.EMPTY);
			return itemstack;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack)
	{
		inventoryContents.set(index, stack);

		if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());

		markDirty();
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return slotsCount;
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName()
	{
		return inventoryTitle;
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName()
	{
		return hasCustomName;
	}

	/**
	 * Sets the name of this inventory. This is displayed to the client on opening.
	 */
	public void setCustomName(String inventoryTitleIn)
	{
		hasCustomName = true;
		inventoryTitle = inventoryTitleIn;
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's username in chat
	 */
	@Override
	public ITextComponent getDisplayName()
	{
		return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName(), new Object[0]);
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
	 * hasn't changed and skip it.
	 */
	@Override
	public void markDirty()
	{
		//    	tile.isDirty = true;
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
	 * guis use Slot.isItemValid
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		inventoryContents.clear();
	}

	@Override
	public boolean isEmpty(){
		for (ItemStack itemstack : inventoryContents)
			if (!itemstack.isEmpty()) return false;

		return true;
	}
}
