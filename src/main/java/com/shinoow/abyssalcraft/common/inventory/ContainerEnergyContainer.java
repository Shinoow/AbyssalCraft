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
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEnergyContainer;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.WindowPropertyMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerEnergyContainer extends Container {

	private TileEntityEnergyContainer tileEnergyContainer;
	private int lastPE;

	public ContainerEnergyContainer(InventoryPlayer par1InventoryPlayer, TileEntityEnergyContainer par2TileEntityEnergyContainer){
		tileEnergyContainer = par2TileEntityEnergyContainer;
		addSlotToContainer(new SlotEnergyContainer(par2TileEntityEnergyContainer, 0, 44, 38));
		addSlotToContainer(new SlotEnergyContainer(par2TileEntityEnergyContainer, 1, 116, 38));
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
		if(listener instanceof EntityPlayerMP)
			PacketDispatcher.sendTo(new WindowPropertyMessage(windowId, 0, tileEnergyContainer.getField(0)), (EntityPlayerMP) listener);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		int pe = tileEnergyContainer.getField(0);

		for (int i = 0; i < listeners.size(); ++i)
		{
			IContainerListener icrafting = listeners.get(i);

			if (lastPE != pe && icrafting instanceof EntityPlayerMP)
				PacketDispatcher.sendTo(new WindowPropertyMessage(windowId, 0, pe), (EntityPlayerMP) icrafting);
		}

		lastPE = pe;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		tileEnergyContainer.setField(par1, par2);
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {

		return tileEnergyContainer.isUsableByPlayer(var1);
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
				if(itemstack1.getItem() instanceof IEnergyContainerItem){
					if (!mergeItemStack(itemstack1, 0, 2, false))
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
