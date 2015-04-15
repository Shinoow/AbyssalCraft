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
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMaterializer extends Slot
{
	private int stackSize;

	public SlotMaterializer(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
	{
		super(par2IInventory, par3, par4, par5);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}

	@Override
	public ItemStack decrStackSize(int par1)
	{
		if (getHasStack())
			stackSize += Math.min(par1, getStack().stackSize);

		return super.decrStackSize(par1);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		this.onCrafting(par2ItemStack);
		super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
	}

	@Override
	protected void onCrafting(ItemStack par1ItemStack, int par2)
	{
		stackSize += par2;
	}
}