/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.recipe.TransmutatorRecipes;
import com.shinoow.abyssalcraft.common.blocks.BlockTransmutator;

public class TileEntityTransmutator extends TileEntity implements ISidedInventory, ITickable
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
	public ItemStack removeStackFromSlot(int par1)
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
	public String getName()
	{
		return hasCustomName() ? containerName : "container.abyssalcraft.transmutator";
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomName()
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
	public NBTTagCompound writeToNBT(NBTTagCompound par1)
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

		if (hasCustomName())
			par1.setString("CustomName", containerName);

		return par1;
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
	 * Transmuator is transmuting
	 */
	public boolean isTransmuting()
	{
		return transmutatorBurnTime > 0;
	}

	@Override
	public void update()
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

			if (isTransmuting() && canProcess())
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
				BlockTransmutator.updateTransmutatorBlockState(transmutatorBurnTime > 0, worldObj, pos);
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
			ItemStack itemstack = TransmutatorRecipes.instance().getTransmutationResult(transmutatorItemStacks[0]);
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
			ItemStack itemstack = TransmutatorRecipes.instance().getTransmutationResult(transmutatorItemStacks[0]);

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

			if (item == ACItems.coralium_plagued_flesh) return 100;
			if (item == ACItems.coralium_plagued_flesh_on_a_bone) return 100;
			if (item == ACItems.coralium_brick) return 200;
			if (item == ACItems.coralium_gem) return 200;
			if (item == ACItems.coralium_gem_cluster_2) return 400;
			if (item == ACItems.coralium_gem_cluster_3) return 600;
			if (item == ACItems.coralium_gem_cluster_4) return 800;
			if (item == ACItems.coralium_gem_cluster_5) return 1000;
			if (item == ACItems.coralium_gem_cluster_6) return 1200;
			if (item == ACItems.coralium_gem_cluster_7) return 1400;
			if (item == ACItems.coralium_gem_cluster_8) return 1600;
			if (item == ACItems.coralium_gem_cluster_9) return 1800;
			if (item == ACItems.coralium_pearl) return 2000;
			if (item == ACItems.transmutation_gem) return 10000;
			if (item == ACItems.chunk_of_coralium) return 16200;
			if (item == ACItems.liquid_coralium_bucket) return 20000;
			if (item == Item.getItemFromBlock(ACBlocks.liquid_coralium)) return 22000;
			if (item == Items.BLAZE_POWDER) return 1200;
			if (item == Items.BLAZE_ROD) return 2400;
			if (item == ACItems.methane) return 10000;
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
		return worldObj.getTileEntity(pos) != this ? false : par1EntityPlayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

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
	public int[] getSlotsForFace(EnumFacing face)
	{
		return face == EnumFacing.DOWN ? slotsBottom : face == EnumFacing.UP ? slotsTop : slotsSides;
	}

	/**
	 * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
	 * side
	 */
	@Override
	public boolean canInsertItem(int par1, ItemStack par2ItemStack, EnumFacing face)
	{
		return isItemValidForSlot(par1, par2ItemStack);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
	 * side
	 */
	@Override
	public boolean canExtractItem(int par1, ItemStack par2ItemStack, EnumFacing face)
	{
		if (face == EnumFacing.DOWN && par1 == 1)
		{
			Item item = par2ItemStack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET)
				return false;
		}

		return true;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public ITextComponent getDisplayName()
	{
		return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName(), new Object[0]);
	}
}
