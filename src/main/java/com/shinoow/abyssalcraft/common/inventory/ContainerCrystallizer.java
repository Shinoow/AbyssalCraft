/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.api.recipe.CrystallizerRecipes;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, tileCrystallizer.crystallizerFormTime);
		listener.sendWindowProperty(this, 1, tileCrystallizer.crystallizerShapeTime);
		listener.sendWindowProperty(this, 2, tileCrystallizer.currentItemShapingTime);
		listener.sendWindowProperty(this, 3, tileCrystallizer.crystallizerFormTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < listeners.size(); ++i)
		{
			IContainerListener icrafting = listeners.get(i);

			if (lastCookTime != tileCrystallizer.crystallizerFormTime)
				icrafting.sendWindowProperty(this, 0, tileCrystallizer.crystallizerFormTime);

			if (lastBurnTime != tileCrystallizer.crystallizerShapeTime)
				icrafting.sendWindowProperty(this, 1, tileCrystallizer.crystallizerShapeTime);

			if (lastItemBurnTime != tileCrystallizer.currentItemShapingTime)
				icrafting.sendWindowProperty(this, 2, tileCrystallizer.currentItemShapingTime);
			if (lastCookTime2 != tileCrystallizer.crystallizerFormTime)
				icrafting.sendWindowProperty(this, 3, tileCrystallizer.crystallizerFormTime);
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
		return tileCrystallizer.isUsableByPlayer(par1EntityPlayer);
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

			if (par2 == 2 || par2 == 3)
			{
				if (!mergeItemStack(itemstack1, 4, 39, true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if (!CrystallizerRecipes.instance().getCrystallizationResult(itemstack1)[0].isEmpty())
				{
					if (!mergeItemStack(itemstack1, 0, 1, false))
						return ItemStack.EMPTY;
				}
				else if (TileEntityCrystallizer.isItemFuel(itemstack1))
				{
					if (!mergeItemStack(itemstack1, 1, 2, false))
						return ItemStack.EMPTY;
				}
				else if (par2 >= 4 && par2 < 31)
				{
					if (!mergeItemStack(itemstack1, 31, 39, false))
						return ItemStack.EMPTY;
				}
				else if (par2 >= 31 && par2 < 40 && !mergeItemStack(itemstack1, 4, 30, false))
					return ItemStack.EMPTY;
			}
			else if (!mergeItemStack(itemstack1, 4, 39, false))
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
}
