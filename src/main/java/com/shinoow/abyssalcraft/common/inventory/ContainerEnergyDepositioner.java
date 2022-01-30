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

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEnergyDepositioner;
import com.shinoow.abyssalcraft.common.items.ItemStoneTablet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerEnergyDepositioner extends Container {

	private TileEntityEnergyDepositioner tileEntityPEGenerator;
	private int lastProcessTime;
	private int lastPE;

	public ContainerEnergyDepositioner(InventoryPlayer par1InventoryPlayer, TileEntityEnergyDepositioner par2TileEntityEnergyContainer){
		tileEntityPEGenerator = par2TileEntityEnergyContainer;
		addSlotToContainer(new SlotStoneTablet(par2TileEntityEnergyContainer, 0, 44, 38));
		addSlotToContainer(new SlotDepositionerOutput(par2TileEntityEnergyContainer, 1, 116, 38));
		int i;

		for(i = 0; i < 3; i++)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, tileEntityPEGenerator.getField(0));
		listener.sendWindowProperty(this, 1, tileEntityPEGenerator.getField(1));
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		int processingTime = tileEntityPEGenerator.getField(0);
		int pe = tileEntityPEGenerator.getField(1);

		for (int i = 0; i < listeners.size(); ++i)
		{
			IContainerListener icrafting = listeners.get(i);

			if (lastProcessTime != processingTime)
				icrafting.sendWindowProperty(this, 0, processingTime);

			if (lastPE != pe)
				icrafting.sendWindowProperty(this, 1, pe);
		}

		lastProcessTime = processingTime;
		lastPE = pe;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		tileEntityPEGenerator.setField(par1, par2);
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {

		return tileEntityPEGenerator.isUsableByPlayer(var1);
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

			if (par2 == 1)
			{
				if (!mergeItemStack(itemstack1, 2, 38, true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if(itemstack1.getItem() instanceof ItemStoneTablet && !((ItemStoneTablet)itemstack1.getItem()).isCursed(itemstack1)
						&& ((ItemStoneTablet)itemstack1.getItem()).hasInventory(itemstack1)){
					if (!mergeItemStack(itemstack1, 0, 1, false))
						return ItemStack.EMPTY;
				} else if (par2 >= 2 && par2 < 29){
					if (!mergeItemStack(itemstack1, 29, 38, false))
						return ItemStack.EMPTY;
				}
				else if (par2 >= 29 && par2 < 38 && !mergeItemStack(itemstack1, 2, 29, false))
					return ItemStack.EMPTY;
			}
			else if (!mergeItemStack(itemstack1, 2, 38, false))
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
