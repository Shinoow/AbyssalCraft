/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;

public class ContainerSpellbook extends Container {

	private final InventorySpellbook inventory;

	public ItemStack book;

	public ContainerSpellbook(InventoryPlayer inventoryPlayer, ItemStack book)
	{
		inventory = new InventorySpellbook(book);
		this.book = book;
		int i = (8 - 4) * 18 - 1;
		int j;
		int k;

		addSlotToContainer(new Slot(inventory, 0, 51, 91));
		addSlotToContainer(new Slot(inventory, 1, 51, 66));
		addSlotToContainer(new Slot(inventory, 2, 76, 87));
		addSlotToContainer(new Slot(inventory, 3, 65, 116));
		addSlotToContainer(new Slot(inventory, 4, 37, 116));
		addSlotToContainer(new Slot(inventory, 5, 26, 87));
		addSlotToContainer(new Slot(inventory, 6, 134, 91));

		for (j = 0; j < 3; ++j)
			for (k = 0; k < 9; ++k)
				addSlotToContainer(new Slot(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));

		for (j = 0; j < 9; ++j)
			addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 161 + i));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);

		if (!playerIn.world.isRemote)
			for (int i = 0; i < 9; ++i)
			{
				ItemStack itemstack = inventory.removeStackFromSlot(i);

				if (!itemstack.isEmpty())
					playerIn.dropItem(itemstack, false);
			}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(par2);


		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 2)
			{
				if (!mergeItemStack(itemstack1, 3, 39, true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if (par2 >= 3 && par2 < 30)
				{
					if (!mergeItemStack(itemstack1, 30, 39, false))
						return ItemStack.EMPTY;
				}
				else if (par2 >= 30 && par2 < 39 && !mergeItemStack(itemstack1, 3, 30, false))
					return ItemStack.EMPTY;
			}
			else if (!mergeItemStack(itemstack1, 3, 39, false))
				return ItemStack.EMPTY;

			if (itemstack1.isEmpty())
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (itemstack1.getCount() == itemstack.getCount())
				return ItemStack.EMPTY;

			slot.onTake(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player) {

		if (slot >= 0 && getSlot(slot) != null && !getSlot(slot).getStack().isEmpty())
			if(getSlot(slot).getStack().getItem() instanceof ItemNecronomicon)
				return ItemStack.EMPTY;
		return super.slotClick(slot, dragType, clickType, player);
	}
}
