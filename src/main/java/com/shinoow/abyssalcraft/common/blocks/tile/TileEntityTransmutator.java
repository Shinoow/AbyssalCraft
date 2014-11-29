/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.recipe.TransmutatorRecipes;
import com.shinoow.abyssalcraft.common.blocks.BlockTransmutator;
import com.shinoow.abyssalcraft.common.items.ItemCrystal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityTransmutator extends TileEntity implements ISidedInventory
{
	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 1};
	private static final int[] slotsSides = new int[] {1};
	/**
	 * The ItemStacks that hold the items currently being used in the transmutator
	 */
	private ItemStack[] transmutatorItemStacks = new ItemStack[3];
	/** The number of ticks that the transmutator will keep burning */
	public int transmutatorBurnTime;
	/**
	 * The number of ticks that a fresh copy of the currently-burning item would keep the transmutator burning for
	 */
	public int currentItemBurnTime;
	/** The number of ticks that the current item has been processing for */
	public int transmutatorProcessTime;
	private String containerName;

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return transmutatorItemStacks.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return transmutatorItemStacks[par1];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 */
	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (transmutatorItemStacks[par1] != null)
		{
			ItemStack itemstack;

			if (transmutatorItemStacks[par1].stackSize <= par2)
			{
				itemstack = transmutatorItemStacks[par1];
				transmutatorItemStacks[par1] = null;
				return itemstack;
			}
			else
			{
				itemstack = transmutatorItemStacks[par1].splitStack(par2);

				if (transmutatorItemStacks[par1].stackSize == 0)
					transmutatorItemStacks[par1] = null;

				return itemstack;
			}
		} else
			return null;
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	 * like when you close a workbench GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (transmutatorItemStacks[par1] != null)
		{
			ItemStack itemstack = transmutatorItemStacks[par1];
			transmutatorItemStacks[par1] = null;
			return itemstack;
		} else
			return null;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		transmutatorItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getInventoryName()
	{
		return hasCustomInventoryName() ? containerName : "container.abyssalcraft.transmutator";
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomInventoryName()
	{
		return containerName != null && containerName.length() > 0;
	}

	public void func_145951_a(String par1)
	{
		containerName = par1;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1)
	{
		super.readFromNBT(par1);
		NBTTagList nbttaglist = par1.getTagList("Items", 10);
		transmutatorItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < transmutatorItemStacks.length)
				transmutatorItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}

		transmutatorBurnTime = par1.getShort("BurnTime");
		transmutatorProcessTime = par1.getShort("ProcessTime");
		currentItemBurnTime = getItemBurnTime(transmutatorItemStacks[1]);

		if (par1.hasKey("CustomName", 8))
			containerName = par1.getString("CustomName");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1)
	{
		super.writeToNBT(par1);
		par1.setShort("BurnTime", (short)transmutatorBurnTime);
		par1.setShort("ProcessTime", (short)transmutatorProcessTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < transmutatorItemStacks.length; ++i)
			if (transmutatorItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				transmutatorItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}

		par1.setTag("Items", nbttaglist);

		if (hasCustomInventoryName())
			par1.setString("CustomName", containerName);
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * cooked
	 */
	@SideOnly(Side.CLIENT)
	public int getProcessProgressScaled(int par1)
	{
		return transmutatorProcessTime * par1 / 200;
	}

	/**
	 * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
	 * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
	 */
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if (currentItemBurnTime == 0)
			currentItemBurnTime = 200;

		return transmutatorBurnTime * par1 / currentItemBurnTime;
	}

	/**
	 * Transmuator is transmutating
	 */
	public boolean isTransmutating()
	{
		return transmutatorBurnTime > 0;
	}

	@Override
	public void updateEntity()
	{
		boolean flag = transmutatorBurnTime > 0;
		boolean flag1 = false;

		if (transmutatorBurnTime > 0)
			--transmutatorBurnTime;

		if (!worldObj.isRemote)
		{
			if (transmutatorBurnTime == 0 && canProcess())
			{
				currentItemBurnTime = transmutatorBurnTime = getItemBurnTime(transmutatorItemStacks[1]);

				if (transmutatorBurnTime > 0)
				{
					flag1 = true;

					if (transmutatorItemStacks[1] != null)
					{
						--transmutatorItemStacks[1].stackSize;

						if (transmutatorItemStacks[1].stackSize == 0)
							transmutatorItemStacks[1] = transmutatorItemStacks[1].getItem().getContainerItem(transmutatorItemStacks[1]);
					}
				}
			}

			if (isTransmutating() && canProcess())
			{
				++transmutatorProcessTime;

				if (transmutatorProcessTime == 200)
				{
					transmutatorProcessTime = 0;
					processItem();
					flag1 = true;
				}
			} else
				transmutatorProcessTime = 0;

			if (flag != transmutatorBurnTime > 0)
			{
				flag1 = true;
				BlockTransmutator.updateTransmutatorBlockState(transmutatorBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
			}
		}

		if (flag1)
			markDirty();
	}

	/**
	 * Returns true if the transmutator can process an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canProcess()
	{
		if (transmutatorItemStacks[0] == null)
			return false;
		else
		{
			ItemStack itemstack = TransmutatorRecipes.transmutation().getTransmutationResult(transmutatorItemStacks[0]);
			if (itemstack == null) return false;
			if (transmutatorItemStacks[2] == null) return true;
			if (!transmutatorItemStacks[2].isItemEqual(itemstack)) return false;
			int result = transmutatorItemStacks[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= transmutatorItemStacks[2].getMaxStackSize();
		}
	}

	/**
	 * Turn one item from the transmutator source stack into the appropriate processed item in the transmutator result stack
	 */
	public void processItem()
	{
		if (canProcess())
		{
			ItemStack itemstack = TransmutatorRecipes.transmutation().getTransmutationResult(transmutatorItemStacks[0]);

			if (transmutatorItemStacks[2] == null)
				transmutatorItemStacks[2] = itemstack.copy();
			else if (transmutatorItemStacks[2].getItem() == itemstack.getItem())
				transmutatorItemStacks[2].stackSize += itemstack.stackSize;

			--transmutatorItemStacks[0].stackSize;

			if (transmutatorItemStacks[0].stackSize <= 0)
				transmutatorItemStacks[0] = null;
		}
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the transmutator burning, or 0 if the item isn't
	 * fuel
	 */
	public static int getItemBurnTime(ItemStack par1ItemStack)
	{
		if (par1ItemStack == null)
			return 0;
		else
		{
			Item item = par1ItemStack.getItem();

			if (item == AbyssalCraft.Corflesh) return 100;
			if (item == AbyssalCraft.Corbone) return 100;
			if (item == AbyssalCraft.cbrick) return 200;
			if (item == AbyssalCraft.Coralium) return 200;
			if (item == AbyssalCraft.Coraliumcluster2) return 400;
			if (item == AbyssalCraft.Coraliumcluster3) return 600;
			if (item == AbyssalCraft.Coraliumcluster4) return 800;
			if (item == AbyssalCraft.Coraliumcluster5) return 1000;
			if (item == AbyssalCraft.Coraliumcluster6) return 1200;
			if (item == AbyssalCraft.Coraliumcluster7) return 1400;
			if (item == AbyssalCraft.Coraliumcluster8) return 1600;
			if (item == AbyssalCraft.Coraliumcluster9) return 1800;
			if (item == AbyssalCraft.Cpearl) return 2000;
			if (item == AbyssalCraft.Corb) return 10000;
			if (item == AbyssalCraft.Cchunk) return 16200;
			if (item == AbyssalCraft.Cbucket) return 20000;
			if (item == Item.getItemFromBlock(AbyssalCraft.Cwater)) return 22000;
			if (item instanceof ItemCrystal) return 1200;
			if (item == Items.blaze_powder) return 1200;
			if (item == Items.blaze_rod) return 2400;
			if (item == AbyssalCraft.methane) return 10000;
			return AbyssalCraftAPI.getFuelValue(par1ItemStack, FuelType.TRANSMUTATOR);
		}
	}

	public static boolean isItemFuel(ItemStack par1ItemStack)
	{
		/**
		 * Returns the number of ticks that the supplied fuel item will keep the transmutator burning, or 0 if the item isn't
		 * fuel
		 */
		return getItemBurnTime(par1ItemStack) > 0;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return par1 == 2 ? false : par1 == 1 ? isItemFuel(par2ItemStack) : true;
	}

	/**
	 * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
	 * block.
	 */
	@Override
	public int[] getAccessibleSlotsFromSide(int par1)
	{
		return par1 == 0 ? slotsBottom : par1 == 1 ? slotsTop : slotsSides;
	}

	/**
	 * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
	 * side
	 */
	@Override
	public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
	{
		return isItemValidForSlot(par1, par2ItemStack);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
	 * side
	 */
	@Override
	public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
	{
		return par3 != 0 || par1 != 1 || par2ItemStack.getItem() == Items.bucket;
	}
}