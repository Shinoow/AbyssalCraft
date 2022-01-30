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

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRendingPedestal;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerRendingPedestal extends Container {

	private TileEntityRendingPedestal tileRendingPedestal;
	private int lastSE, lastAE, lastDE, lastOE, lastPE;

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
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, tileRendingPedestal.getField(0));
		listener.sendWindowProperty(this, 1, tileRendingPedestal.getField(1));
		listener.sendWindowProperty(this, 2, tileRendingPedestal.getField(2));
		listener.sendWindowProperty(this, 3, tileRendingPedestal.getField(3));
		listener.sendWindowProperty(this, 4, tileRendingPedestal.getField(4));
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		int se = tileRendingPedestal.getField(0);
		int ae = tileRendingPedestal.getField(1);
		int de = tileRendingPedestal.getField(2);
		int oe = tileRendingPedestal.getField(3);
		int pe = tileRendingPedestal.getField(4);

		for (int i = 0; i < listeners.size(); ++i)
		{
			IContainerListener icrafting = listeners.get(i);

			if (lastSE != se)
				icrafting.sendWindowProperty(this, 0, se);

			if (lastAE != ae)
				icrafting.sendWindowProperty(this, 1, ae);

			if (lastDE != de)
				icrafting.sendWindowProperty(this, 2, de);

			if (lastOE != oe)
				icrafting.sendWindowProperty(this, 3, oe);

			if (lastPE != pe)
				icrafting.sendWindowProperty(this, 4, pe);
		}

		lastSE = se;
		lastAE = ae;
		lastDE = de;
		lastOE = oe;
		lastPE = pe;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		tileRendingPedestal.setField(par1, par2);
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {

		return tileRendingPedestal.isUsableByPlayer(var1);
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

			if (par2 <= 5)
			{
				if (!mergeItemStack(itemstack1, 6, 42, true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if(itemstack1.getItem() instanceof IEnergyContainerItem){
					if(!mergeItemStack(itemstack1, 0, 1, false))
						return ItemStack.EMPTY;
				} else if(itemstack1.getItem() instanceof IStaffOfRending){
					if(!mergeItemStack(itemstack1, 1, 2, false))
						return ItemStack.EMPTY;
				} else if (par2 >= 6 && par2 < 33){
					if (!mergeItemStack(itemstack1, 33, 42, false))
						return ItemStack.EMPTY;
				}
				else if (par2 >= 33 && par2 < 42 && !mergeItemStack(itemstack1, 6, 33, false))
					return ItemStack.EMPTY;
			}
			else if (!mergeItemStack(itemstack1, 6, 42, false))
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
