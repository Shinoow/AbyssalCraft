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

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerSequentialBrewingStand extends Container
{
	private final IInventory tileBrewingStand;
	/** Instance of Slot. */
	private final Slot slot;
	/** Used to cache the brewing time to send changes to ICrafting listeners. */
	private int prevBrewTime;
	/** Used to cache the fuel remaining in the brewing stand to send changes to ICrafting listeners. */
	private int prevFuel;

	public ContainerSequentialBrewingStand(InventoryPlayer playerInventory, IInventory tileBrewingStandIn)
	{
		tileBrewingStand = tileBrewingStandIn;
		addSlotToContainer(new ContainerSequentialBrewingStand.Potion(tileBrewingStandIn, 0, 56, 51));
		addSlotToContainer(new ContainerSequentialBrewingStand.Potion(tileBrewingStandIn, 1, 79, 58));
		addSlotToContainer(new ContainerSequentialBrewingStand.Potion(tileBrewingStandIn, 2, 102, 51));
		slot = addSlotToContainer(new ContainerSequentialBrewingStand.Ingredient(tileBrewingStandIn, 3, 79, 17));
		addSlotToContainer(new ContainerSequentialBrewingStand.Fuel(tileBrewingStandIn, 4, 17, 17));
		addSlotToContainer(new Potion(tileBrewingStandIn, 5, 152, 17));
		addSlotToContainer(new Potion(tileBrewingStandIn, 6, 152, 35));
		addSlotToContainer(new Potion(tileBrewingStandIn, 7, 152, 53));

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int k = 0; k < 9; ++k)
			addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, tileBrewingStand);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < listeners.size(); ++i)
		{
			IContainerListener icontainerlistener = listeners.get(i);

			if (prevBrewTime != tileBrewingStand.getField(0))
				icontainerlistener.sendWindowProperty(this, 0, tileBrewingStand.getField(0));

			if (prevFuel != tileBrewingStand.getField(1))
				icontainerlistener.sendWindowProperty(this, 1, tileBrewingStand.getField(1));
		}

		prevBrewTime = tileBrewingStand.getField(0);
		prevFuel = tileBrewingStand.getField(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		tileBrewingStand.setField(id, data);
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return tileBrewingStand.isUsableByPlayer(playerIn);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 0 || index > 7)
			{
				if (this.slot.isItemValid(itemstack1))
				{
					if (!mergeItemStack(itemstack1, 3, 4, false))
						return ItemStack.EMPTY;
				}
				else if (ContainerSequentialBrewingStand.Potion.canHoldPotion(itemstack) && itemstack.getCount() == 1)
				{
					if (!mergeItemStack(itemstack1, 0, 3, false))
						return ItemStack.EMPTY;
				}
				else if (ContainerSequentialBrewingStand.Fuel.isValidBrewingFuel(itemstack))
				{
					if (!mergeItemStack(itemstack1, 4, 5, false))
						return ItemStack.EMPTY;
				}
				else if (index >= 8 && index < 35)
				{
					if (!mergeItemStack(itemstack1, 35, 44, false))
						return ItemStack.EMPTY;
				}
				else if (index >= 35 && index < 44)
				{
					if (!mergeItemStack(itemstack1, 8, 35, false))
						return ItemStack.EMPTY;
				}
				else if (!mergeItemStack(itemstack1, 8, 44, false))
					return ItemStack.EMPTY;
			}
			else
			{
				if (!mergeItemStack(itemstack1, 8, 44, true))
					return ItemStack.EMPTY;

				slot.onSlotChange(itemstack1, itemstack);
			}

			if (itemstack1.isEmpty())
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (itemstack1.getCount() == itemstack.getCount())
				return ItemStack.EMPTY;

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	static class Fuel extends Slot
	{
		public Fuel(IInventory iInventoryIn, int index, int xPosition, int yPosition)
		{
			super(iInventoryIn, index, xPosition, yPosition);
		}

		/**
		 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
		 */
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return isValidBrewingFuel(stack);
		}

		/**
		 * Returns true if the given ItemStack is usable as a fuel in the brewing stand.
		 */
		public static boolean isValidBrewingFuel(ItemStack itemStackIn)
		{
			return itemStackIn.getItem() == Items.BLAZE_POWDER;
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in
		 * the case of armor slots)
		 */
		@Override
		public int getSlotStackLimit()
		{
			return 64;
		}
	}

	static class Ingredient extends Slot
	{
		public Ingredient(IInventory iInventoryIn, int index, int xPosition, int yPosition)
		{
			super(iInventoryIn, index, xPosition, yPosition);
		}

		/**
		 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
		 */
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return BrewingRecipeRegistry.isValidIngredient(stack);
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in
		 * the case of armor slots)
		 */
		@Override
		public int getSlotStackLimit()
		{
			return 64;
		}
	}

	static class Potion extends Slot
	{
		public Potion(IInventory p_i47598_1_, int p_i47598_2_, int p_i47598_3_, int p_i47598_4_)
		{
			super(p_i47598_1_, p_i47598_2_, p_i47598_3_, p_i47598_4_);
		}

		/**
		 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
		 */
		@Override
		public boolean isItemValid(ItemStack stack)
		{
			return canHoldPotion(stack);
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in
		 * the case of armor slots)
		 */
		@Override
		public int getSlotStackLimit()
		{
			return 1;
		}

		@Override
		public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
		{
			PotionType potiontype = PotionUtils.getPotionFromItem(stack);

			if (thePlayer instanceof EntityPlayerMP)
			{
				ForgeEventFactory.onPlayerBrewedPotion(thePlayer, stack);
				CriteriaTriggers.BREWED_POTION.trigger((EntityPlayerMP)thePlayer, potiontype);
			}

			super.onTake(thePlayer, stack);
			return stack;
		}

		/**
		 * Returns true if this itemstack can be filled with a potion
		 */
		public static boolean canHoldPotion(ItemStack stack)
		{
			return BrewingRecipeRegistry.isValidInput(stack);
		}
	}
}
