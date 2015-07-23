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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.api.recipe.CrystallizerRecipes;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCrystallizer extends Container {

	private TileEntityCrystallizer tileCrystallizer;
	private int lastCookTime;
	private int lastCookTime2;
	private int lastBurnTime;
	private int lastItemBurnTime;

	public ContainerCrystallizer(InventoryPlayer par1InventoryPlayer, TileEntityCrystallizer par2TileEntityCrystalizer)
	{
		tileCrystallizer = par2TileEntityCrystalizer;
		addSlotToContainer(new Slot(par2TileEntityCrystalizer, 0, 56, 17));
		addSlotToContainer(new Slot(par2TileEntityCrystalizer, 1, 56, 53));
		addSlotToContainer(new SlotCrystallizer(par1InventoryPlayer.player, par2TileEntityCrystalizer, 2, 116, 35));
		addSlotToContainer(new SlotCrystallizer(par1InventoryPlayer.player, par2TileEntityCrystalizer, 3, 132, 35));
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
		par1ICrafting.sendProgressBarUpdate(this, 0, tileCrystallizer.crystallizerFormTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, tileCrystallizer.crystallizerShapeTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, tileCrystallizer.currentItemShapingTime);
		par1ICrafting.sendProgressBarUpdate(this, 3, tileCrystallizer.crystallizerFormTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			if (lastCookTime != tileCrystallizer.crystallizerFormTime)
				icrafting.sendProgressBarUpdate(this, 0, tileCrystallizer.crystallizerFormTime);

			if (lastBurnTime != tileCrystallizer.crystallizerShapeTime)
				icrafting.sendProgressBarUpdate(this, 1, tileCrystallizer.crystallizerShapeTime);

			if (lastItemBurnTime != tileCrystallizer.currentItemShapingTime)
				icrafting.sendProgressBarUpdate(this, 2, tileCrystallizer.currentItemShapingTime);
			if (lastCookTime2 != tileCrystallizer.crystallizerFormTime)
				icrafting.sendProgressBarUpdate(this, 3, tileCrystallizer.crystallizerFormTime);
		}

		lastCookTime = tileCrystallizer.crystallizerFormTime;
		lastCookTime2 = tileCrystallizer.crystallizerFormTime;
		lastBurnTime = tileCrystallizer.crystallizerShapeTime;
		lastItemBurnTime = tileCrystallizer.currentItemShapingTime;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			tileCrystallizer.crystallizerFormTime = par2;

		if (par1 == 1)
			tileCrystallizer.crystallizerShapeTime = par2;

		if (par1 == 2)
			tileCrystallizer.currentItemShapingTime = par2;

		if (par1 == 3)
			tileCrystallizer.crystallizerFormTime = par2;
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return tileCrystallizer.isUseableByPlayer(par1EntityPlayer);
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
				if (CrystallizerRecipes.instance().getCrystallizationResult(itemstack1) != null)
				{
					if (!mergeItemStack(itemstack1, 0, 1, false))
						return null;
				}
				else if (TileEntityCrystallizer.isItemFuel(itemstack1))
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
