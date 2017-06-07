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
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityStateTransformer;
import com.shinoow.abyssalcraft.common.items.ItemStoneTablet;

public class ContainerStateTransformer extends Container {

	private TileEntityStateTransformer tileEntityCompressorThing;
	private int lastProcessTime;

	public ContainerStateTransformer(InventoryPlayer par1InventoryPlayer, TileEntityStateTransformer par2TileEntityEnergyContainer){
		tileEntityCompressorThing = par2TileEntityEnergyContainer;
		addSlotToContainer(new SlotStoneTablet(par2TileEntityEnergyContainer, 0, 8, 71));
		int i;
		for(i = 0; i < 7; i++)
			for(int j = 0; j < 7; j++)
				addSlotToContainer(new SlotStateTransformer(par2TileEntityEnergyContainer, j + i * 7 + 1, 44 + j * 18, 17 + i * 18));

		for(i = 0; i < 3; i++)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 72));

		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142 + 72));
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendProgressBarUpdate(this, 0, tileEntityCompressorThing.processingTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		int processingTime = tileEntityCompressorThing.processingTime;

		for (int i = 0; i < listeners.size(); ++i)
		{
			IContainerListener icrafting = listeners.get(i);

			if (lastProcessTime != processingTime)
				icrafting.sendProgressBarUpdate(this, 0, processingTime);
		}

		lastProcessTime = processingTime;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if(par1 == 0)
			tileEntityCompressorThing.processingTime = par2;
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {

		return tileEntityCompressorThing.isUseableByPlayer(var1);
	}

	@Override
	public boolean enchantItem(EntityPlayer playerIn, int id)
	{
		tileEntityCompressorThing.mode = id;
		return false;
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

			if (par2 < 50)
			{
				if (!mergeItemStack(itemstack1, 50, 86, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 > 49)
			{
				if(itemstack1.getItem() instanceof ItemStoneTablet && !((ItemStoneTablet)itemstack1.getItem()).isCursed(itemstack1)){
					if (!mergeItemStack(itemstack1, 0, 1, false))
						return null;
				} else if(!(itemstack1.getItem() instanceof ItemStoneTablet)){
					if (!mergeItemStack(itemstack1, 1, 50, false))
						return null;
				} else if (par2 >= 50 && par2 < 77){
					if (!mergeItemStack(itemstack1, 77, 86, false))
						return null;
				}
				else if (par2 >= 77 && par2 < 86 && !mergeItemStack(itemstack1, 50, 77, false))
					return null;
			}
			else if (!mergeItemStack(itemstack1, 50, 86, false))
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

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player) {

		if (slot > 0  && slot < 50 && tileEntityCompressorThing.processingTime > 0)
			return null;
		return super.slotClick(slot, dragType, clickType, player);
	}
}
