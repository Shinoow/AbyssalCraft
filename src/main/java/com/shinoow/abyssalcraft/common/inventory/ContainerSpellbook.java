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

import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class ContainerSpellbook extends Container {

	private final InventorySpellbook inventory;
	private final InventorySpellbookOutput output = new InventorySpellbookOutput();
	public ItemStack book;
	public Spell currentSpell;
	private EntityPlayer user;

	public ContainerSpellbook(EntityPlayer player, ItemStack book)
	{
		inventory = new InventorySpellbook(this, book);
		user = player;
		this.book = book;
		int i = (8 - 4) * 18 - 1;
		int j;
		int k;

		addSlotToContainer(new Slot(inventory, 0, 51, 91));
		addSlotToContainer(new Slot(inventory, 1, 51, 66));
		addSlotToContainer(new Slot(inventory, 2, 76, 87));
		addSlotToContainer(new Slot(inventory, 3, 65, 116));
		addSlotToContainer(new Slot(inventory, 4, 37, 116));
		addSlotToContainer(new Slot(inventory, 5, 26, 87));
		addSlotToContainer(new SlotSpellOutput(inventory, output, 0, 134, 91));

		for (j = 0; j < 3; ++j)
			for (k = 0; k < 9; ++k)
				addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));

		for (j = 0; j < 9; ++j)
			addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 161 + i));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return true;
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		ItemStack[] stacks = new ItemStack[5];
		for(int i = 1; i < 6; i++)
			stacks[i-1] = inventory.getStackInSlot(i);

		ItemStack stack = inventory.getStackInSlot(0).copy();

		stack.setCount(1);

		output.spell = currentSpell = SpellRegistry.instance().getSpell(((ItemNecronomicon) book.getItem()).getBookType(), stack, stacks);

		output.setInventorySlotContents(0, currentSpell != null && isUnlocked(currentSpell) ? SpellRegistry.instance().inscribeSpell(currentSpell, stack) : ItemStack.EMPTY);
	}

	public boolean isUnlocked(Spell spell){
		return NecroDataCapability.getCap(user).isUnlocked(spell.getUnlockCondition(), user);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);

		if (!playerIn.world.isRemote)
			for (int i = 0; i < 6; ++i)
			{
				ItemStack itemstack = inventory.removeStackFromSlot(i);

				if (!itemstack.isEmpty())
					playerIn.dropItem(itemstack, false);
			}
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

			if (par2 == 2)
			{
				if (!mergeItemStack(itemstack1, 3, 39, true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if (par2 >= 3 && par2 < 30)
				{
					if (!mergeItemStack(itemstack1, 30, 39, false))
						return ItemStack.EMPTY;
				}
				else if (par2 >= 30 && par2 < 39 && !mergeItemStack(itemstack1, 3, 30, false))
					return ItemStack.EMPTY;
			}
			else if (!mergeItemStack(itemstack1, 3, 39, false))
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

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player) {

		if (slot >= 0 && getSlot(slot) != null && !getSlot(slot).getStack().isEmpty())
			if(getSlot(slot).getStack().getItem() instanceof ItemNecronomicon)
				return ItemStack.EMPTY;
		return super.slotClick(slot, dragType, clickType, player);
	}
}
