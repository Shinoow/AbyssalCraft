/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.api.recipe.TransmutatorRecipes;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTransmutator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTransmutator extends Container {

	private TileEntityTransmutator tileTransmutator;
	private int lastProcessTime;
	private int lastBurnTime;
	private int lastItemBurnTime;

	public ContainerTransmutator(InventoryPlayer par1InventoryPlayer, TileEntityTransmutator par2TileEntityTransmutator)
	{
		tileTransmutator = par2TileEntityTransmutator;
		addSlotToContainer(new Slot(par2TileEntityTransmutator, 0, 56, 17));
		addSlotToContainer(new Slot(par2TileEntityTransmutator, 1, 56, 53));
		addSlotToContainer(new SlotTransmutator(par1InventoryPlayer.player, par2TileEntityTransmutator, 2, 116, 35));
		int i;

		for (i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, tileTransmutator.transmutatorProcessTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, tileTransmutator.transmutatorBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, tileTransmutator.currentItemBurnTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			if (lastProcessTime != tileTransmutator.transmutatorProcessTime)
				icrafting.sendProgressBarUpdate(this, 0, tileTransmutator.transmutatorProcessTime);

			if (lastBurnTime != tileTransmutator.transmutatorBurnTime)
				icrafting.sendProgressBarUpdate(this, 1, tileTransmutator.transmutatorBurnTime);

			if (lastItemBurnTime != tileTransmutator.currentItemBurnTime)
				icrafting.sendProgressBarUpdate(this, 2, tileTransmutator.currentItemBurnTime);
		}

		lastProcessTime = tileTransmutator.transmutatorProcessTime;
		lastBurnTime = tileTransmutator.transmutatorBurnTime;
		lastItemBurnTime = tileTransmutator.currentItemBurnTime;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			tileTransmutator.transmutatorProcessTime = par2;

		if (par1 == 1)
			tileTransmutator.transmutatorBurnTime = par2;

		if (par1 == 2)
			tileTransmutator.currentItemBurnTime = par2;

		if (par1 == 3)
			tileTransmutator.transmutatorProcessTime = par2;
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return tileTransmutator.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(par2);

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
				if (TransmutatorRecipes.instance().getTransmutationResult(itemstack1) != null)
				{
					if (!mergeItemStack(itemstack1, 0, 1, false))
						return null;
				}
				else if (TileEntityTransmutator.isItemFuel(itemstack1))
				{
					if (!mergeItemStack(itemstack1, 1, 2, false))
						return null;
				}
				else if (par2 >= 3 && par2 < 30)
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
