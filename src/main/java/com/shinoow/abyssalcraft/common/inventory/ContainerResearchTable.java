/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.inventory;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityResearchTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerResearchTable extends Container {

	private TileEntityResearchTable tileTransmutator;
	private int lastProcessTime;
	private int lastBurnTime;
	private int lastItemBurnTime;

	public ContainerResearchTable(InventoryPlayer par1InventoryPlayer, TileEntityResearchTable par2TileEntityTransmutator)
	{
		tileTransmutator = par2TileEntityTransmutator;
		int i;
		int k = (8 - 4) * 18 - 1;

		for (i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 103 + i * 18 + k));

		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 161 + k));
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return super.transferStackInSlot(par1EntityPlayer, par2);
	}
}
