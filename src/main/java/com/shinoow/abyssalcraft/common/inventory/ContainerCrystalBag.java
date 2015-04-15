/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.inventory;

import invtweaks.api.container.ChestContainer;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ICrystal;

import cpw.mods.fml.common.Optional.Interface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
				addSlotToContainer(new SlotCrystal(inventoryItem, k + j * 9, 8 + k * 18, 18 + j * 18));

		for (j = 0; j < 3; ++j)
			for (k = 0; k < 9; ++k)
				addSlotToContainer(new Slot(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));

		for (j = 0; j < 9; ++j)
			addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 161 + i));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return inventory.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();


			if (par2 < rows * 9)
			{

				if (!mergeItemStack(itemstack1, rows * 9, inventorySlots.size(), true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			} else if (itemstack1.getItem() instanceof ICrystal || AbyssalCraftAPI.getCrystals().contains(itemstack1))
				if (!mergeItemStack(itemstack1, 0, InventoryCrystalBag.INV_SIZE, false))
					return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {

		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() != null)
			if(!(getSlot(slot).getStack().getItem() instanceof ICrystal) ||
					!AbyssalCraftAPI.getCrystals().contains(getSlot(slot).getStack()))
				return null;
		return super.slotClick(slot, button, flag, player);
	}

	public InventoryCrystalBag getBagInventory(){
		return inventory;
	}
}