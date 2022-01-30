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

import com.shinoow.abyssalcraft.common.items.ItemConfigurator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerConfigurator extends Container
{
	/** The Item Inventory for this Container */
	private final InventoryConfigurator inventory;

	private int rows;

	public ContainerConfigurator(InventoryPlayer inventoryPlayer, InventoryConfigurator inventoryItem)
	{
		inventory = inventoryItem;
		rows = inventoryItem.getSizeInventory() / 9;

		addSlotToContainer(new Slot(inventoryItem, 0, 44, 17));
		addSlotToContainer(new Slot(inventoryItem, 1, 62, 17));
		addSlotToContainer(new Slot(inventoryItem, 2, 80, 17));
		addSlotToContainer(new Slot(inventoryItem, 3, 98, 17));
		addSlotToContainer(new Slot(inventoryItem, 4, 116, 17));
		int i;

		for(i = 0; i < 3; i++)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return inventory.isUsableByPlayer(player);
	}

	@Override
	public boolean enchantItem(EntityPlayer playerIn, int id)
	{
		inventory.setField(id, inventory.getField(id) == 1 ? 0 : 1);
		return false;
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


			if (par2 <= 4)
			{
				if (!mergeItemStack(itemstack1, 5, 40, true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if(par2 > 4) {

				if(!mergeItemStack(itemstack1, 0, 4, false))
					return ItemStack.EMPTY;
				else if (par2 >= 5 && par2 < 32)
				{
					if (!mergeItemStack(itemstack1, 32, 40, false))
						return ItemStack.EMPTY;
				}
				else if (par2 >= 32 && par2 < 41 && !mergeItemStack(itemstack1, 5, 31, false))
					return ItemStack.EMPTY;
			} else if (!mergeItemStack(itemstack1, 5, 40, false))
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
			if(getSlot(slot).getStack().getItem() instanceof ItemConfigurator)
				return ItemStack.EMPTY;
		return super.slotClick(slot, dragType, clickType, player);
	}

	public InventoryConfigurator getConfiguratorInventory(){
		return inventory;
	}
}
