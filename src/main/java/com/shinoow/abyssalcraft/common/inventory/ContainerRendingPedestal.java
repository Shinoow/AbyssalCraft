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
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRendingPedestal;

public class ContainerRendingPedestal extends Container {

	private TileEntityRendingPedestal tileRendingPedestal;
	public ContainerRendingPedestal(InventoryPlayer par1InventoryPlayer, TileEntityRendingPedestal par2TileEntityRendingPedestal){
		tileRendingPedestal = par2TileEntityRendingPedestal;
		addSlotToContainer(new SlotEnergyContainer(par2TileEntityRendingPedestal, 0, 26, 52));
		addSlotToContainer(new SlotRendingStaff(par2TileEntityRendingPedestal, 1, 26, 21));
		addSlotToContainer(new SlotRendingOutput(par2TileEntityRendingPedestal, 2, 73, 52));
		addSlotToContainer(new SlotRendingOutput(par2TileEntityRendingPedestal, 3, 94, 52));
		addSlotToContainer(new SlotRendingOutput(par2TileEntityRendingPedestal, 4, 114, 52));
		addSlotToContainer(new SlotRendingOutput(par2TileEntityRendingPedestal, 5, 135, 52));
		int i;

		for(i = 0; i < 3; i++)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {

		return tileRendingPedestal.isUseableByPlayer(var1);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(par2);


		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 2)
			{
				if (!mergeItemStack(itemstack1, 3, 39, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				//				if (EngraverRecipes.instance().getEngravingResult(itemstack1) != null)
				//				{
				//					if (!mergeItemStack(itemstack1, 0, 1, false))
				//						return null;
				//				}
				if (par2 >= 3 && par2 < 30)
				{
					if (!mergeItemStack(itemstack1, 30, 39, false))
						return null;
				}
				else if (par2 >= 30 && par2 < 39 && !mergeItemStack(itemstack1, 3, 30, false))
					return null;
			}
			else if (!mergeItemStack(itemstack1, 3, 39, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}
}
