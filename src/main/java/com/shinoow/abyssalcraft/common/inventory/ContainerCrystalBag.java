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

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.common.items.ItemCrystalBag;

import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional.Interface;

@Interface(iface = "invtweaks.api.container.ChestContainer", modid = "inventorytweaks")
@ChestContainer
public class ContainerCrystalBag extends Container
{
	/** The Item Inventory for this Container */
	private final InventoryCrystalBag inventory;

	private int rows;


	public ContainerCrystalBag(InventoryPlayer inventoryPlayer, InventoryCrystalBag inventoryItem)
	{
		inventory = inventoryItem;
		rows = inventoryItem.getSizeInventory() / 9;

		int i = (rows - 4) * 18;
		int j;
		int k;

		for (j = 0; j < rows; ++j)
			for (k = 0; k < 9; ++k)
				addSlotToContainer(new SlotCrystal(inventoryItem, k + j * 9, 8 + k * 18, 17 + j * 18));

		for (j = 0; j < 3; ++j)
			for (k = 0; k < 9; ++k)
				addSlotToContainer(new Slot(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));

		for (j = 0; j < 9; ++j)
			addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 161 + i));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return inventory.isUsableByPlayer(player);
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


			if (par2 < rows * 9)
			{

				if (!mergeItemStack(itemstack1, rows * 9, inventorySlots.size(), true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			} else if (APIUtils.isCrystal(itemstack1))
				if (!mergeItemStack(itemstack1, 0, InventoryCrystalBag.INV_SIZE, false))
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
			if(getSlot(slot).getStack().getItem() instanceof ItemCrystalBag)
				return ItemStack.EMPTY;
		return super.slotClick(slot, dragType, clickType, player);
	}

	public InventoryCrystalBag getBagInventory(){
		return inventory;
	}
}
