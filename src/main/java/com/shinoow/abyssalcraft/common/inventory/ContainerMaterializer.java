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

import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityMaterializer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMaterializer extends Container {

	private TileEntityMaterializer tileMaterializer;
	private int lastProcessTime;
	private int lastBurnTime;
	private int lastItemBurnTime;

	public ContainerMaterializer(InventoryPlayer par1InventoryPlayer, TileEntityMaterializer par2TileEntityMaterializer)
	{
		tileMaterializer = par2TileEntityMaterializer;
		addSlotToContainer(new SlotCrystalBag(par2TileEntityMaterializer, 0, 14, 17));
		addSlotToContainer(new SlotNecronomicon(par2TileEntityMaterializer, 1, 14, 53));
		int i;

		for(i = 0; i < 3; ++i)
			for(int j = 0; j < 6; ++j)
				addSlotToContainer(new SlotMaterializer(par1InventoryPlayer.player, par2TileEntityMaterializer, 2 + j + i * 6, 44 + j * 17, 17 + i * 17));

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
		par1ICrafting.sendProgressBarUpdate(this, 0, tileMaterializer.materializerProcessTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, tileMaterializer.materializerBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, tileMaterializer.currentItemBurnTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			if (lastProcessTime != tileMaterializer.materializerProcessTime)
				icrafting.sendProgressBarUpdate(this, 0, tileMaterializer.materializerProcessTime);

			if (lastBurnTime != tileMaterializer.materializerBurnTime)
				icrafting.sendProgressBarUpdate(this, 1, tileMaterializer.materializerBurnTime);

			if (lastItemBurnTime != tileMaterializer.currentItemBurnTime)
				icrafting.sendProgressBarUpdate(this, 2, tileMaterializer.currentItemBurnTime);
		}

		lastProcessTime = tileMaterializer.materializerProcessTime;
		lastBurnTime = tileMaterializer.materializerBurnTime;
		lastItemBurnTime = tileMaterializer.currentItemBurnTime;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			tileMaterializer.materializerProcessTime = par2;

		if (par1 == 1)
			tileMaterializer.materializerBurnTime = par2;

		if (par1 == 2)
			tileMaterializer.currentItemBurnTime = par2;

		if (par1 == 3)
			tileMaterializer.materializerProcessTime = par2;
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return tileMaterializer.isUseableByPlayer(par1EntityPlayer);
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
				if (MaterializerRecipes.instance().getMaterializationResult(itemstack1) != null)
				{
					if (!mergeItemStack(itemstack1, 0, 1, false))
						return null;
				}
				else if (TileEntityMaterializer.isItemFuel(itemstack1))
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
