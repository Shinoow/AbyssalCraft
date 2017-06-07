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
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.recipe.CrystallizerRecipes;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystallizer;

public class TileEntityCrystallizer extends TileEntity implements ISidedInventory, ITickable {

	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 1, 3};
	private static final int[] slotsSides = new int[] {1};
	/**
	 * The ItemStacks that hold the items currently being used in the crystallizer
	 */
	private ItemStack[] crystallizerItemStacks = new ItemStack[4];
	/** The number of ticks that the crystallizer will keep doing it's thing */
	public int crystallizerShapeTime;
	/**
	 * The number of ticks that a fresh copy of the currently-thingy item would keep the crystalizer stuffing for
	 */
	public int currentItemShapingTime;
	/** The number of ticks that the current item has been cooking for */
	public int crystallizerFormTime;
	private String containerName;

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return crystallizerItemStacks.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return crystallizerItemStacks[par1];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 */
	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (crystallizerItemStacks[par1] != null)
		{
			ItemStack itemstack;

			if (crystallizerItemStacks[par1].stackSize <= par2)
			{
				itemstack = crystallizerItemStacks[par1];
				crystallizerItemStacks[par1] = null;
				return itemstack;
			}
			else
			{
				itemstack = crystallizerItemStacks[par1].splitStack(par2);

				if (crystallizerItemStacks[par1].stackSize == 0)
					crystallizerItemStacks[par1] = null;

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
		if (crystallizerItemStacks[par1] != null)
		{
			ItemStack itemstack = crystallizerItemStacks[par1];
			crystallizerItemStacks[par1] = null;
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
		crystallizerItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getName()
	{
		return hasCustomName() ? containerName : "container.abyssalcraft.crystallizer";
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
		crystallizerItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < crystallizerItemStacks.length)
				crystallizerItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}

		crystallizerShapeTime = par1.getShort("ShapeTime");
		crystallizerFormTime = par1.getShort("FormTime");
		currentItemShapingTime = getCrystallizationTime(crystallizerItemStacks[1]);

		if (par1.hasKey("CustomName", 8))
			containerName = par1.getString("CustomName");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1)
	{
		super.writeToNBT(par1);
		par1.setShort("ShapeTime", (short)crystallizerShapeTime);
		par1.setShort("FormTime", (short)crystallizerFormTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < crystallizerItemStacks.length; ++i)
			if (crystallizerItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				crystallizerItemStacks[i].writeToNBT(nbttagcompound1);
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
	public int getFormProgressScaled(int par1)
	{
		return crystallizerFormTime * par1 / 200;
	}

	/**
	 * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
	 * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
	 */
	@SideOnly(Side.CLIENT)
	public int getShapeTimeRemainingScaled(int par1)
	{
		if (currentItemShapingTime == 0)
			currentItemShapingTime = 200;

		return crystallizerShapeTime * par1 / currentItemShapingTime;
	}

	/**
	 * Furnace isBurning
	 */
	public boolean isCrystallizing()
	{
		return crystallizerShapeTime > 0;
	}

	@Override
	public void update()
	{
		boolean flag = crystallizerShapeTime > 0;
		boolean flag1 = false;

		if (crystallizerShapeTime > 0)
			--crystallizerShapeTime;

		if (!worldObj.isRemote)
		{
			if (crystallizerShapeTime == 0 && canCrystallize())
			{
				currentItemShapingTime = crystallizerShapeTime = getCrystallizationTime(crystallizerItemStacks[1]);

				if (crystallizerShapeTime > 0)
				{
					flag1 = true;

					if (crystallizerItemStacks[1] != null)
					{
						--crystallizerItemStacks[1].stackSize;

						if (crystallizerItemStacks[1].stackSize == 0)
							crystallizerItemStacks[1] = crystallizerItemStacks[1].getItem().getContainerItem(crystallizerItemStacks[1]);
					}
				}
			}

			if (isCrystallizing() && canCrystallize())
			{
				++crystallizerFormTime;

				if (crystallizerFormTime == 200)
				{
					crystallizerFormTime = 0;
					crystalizeItem();
					flag1 = true;
				}
			} else
				crystallizerFormTime = 0;

			if (flag != crystallizerShapeTime > 0)
			{
				flag1 = true;
				BlockCrystallizer.updateCrystallizerBlockState(crystallizerShapeTime > 0, worldObj, pos);
			}
		}

		if (flag1)
			markDirty();
	}

	/**
	 * Returns true if the crystallizer can crystallize an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canCrystallize()
	{
		if (crystallizerItemStacks[0] == null || CrystallizerRecipes.instance().getCrystallizationResult(crystallizerItemStacks[0]) == null)
			return false;
		else
		{
			ItemStack[] itemstack = CrystallizerRecipes.instance().getCrystallizationResult(crystallizerItemStacks[0]);

			ItemStack stack = crystallizerItemStacks[2], stack1 = crystallizerItemStacks[3];

			if(itemstack[0] == null && itemstack[1] == null || itemstack[0] == null) return false;
			if(stack == null && stack1 == null) return true;
			if(itemstack[1] == null){
				if(stack == null) return true;
				if(!stack.isItemEqual(itemstack[0])) return false;

				int result = stack.stackSize + itemstack[0].stackSize;
				return result <= getInventoryStackLimit() && result <= stack.getMaxStackSize();
			} else {
				if(stack == null && stack1 == null) return true;
				if(stack != null && !stack.isItemEqual(itemstack[0])) return false;
				if(stack1 != null && !stack1.isItemEqual(itemstack[1])) return false;

				int result = (stack != null ? stack.stackSize : 0) + itemstack[0].stackSize;
				int result2 = (stack1 != null ? stack1.stackSize : 0) + itemstack[1].stackSize;
				boolean b1 = stack != null ? result <= stack.getMaxStackSize() : true;
				boolean b2 = stack1 != null ? result2 <= stack1.getMaxStackSize() : true;
				return result <= getInventoryStackLimit() && result2 <= getInventoryStackLimit() && b1 && b2;
			}
		}
	}

	/**
	 * Turn one item from the crystallizer source stack into the appropriate crystallized item in the crystallizer result stack
	 */
	public void crystalizeItem()
	{
		if (canCrystallize())
		{
			ItemStack[] itemstack = CrystallizerRecipes.instance().getCrystallizationResult(crystallizerItemStacks[0]);

			if (crystallizerItemStacks[2] == null)
				crystallizerItemStacks[2] = itemstack[0].copy();
			else if (crystallizerItemStacks[2].getItem() == itemstack[0].getItem())
				crystallizerItemStacks[2].stackSize += itemstack[0].stackSize;
			if(itemstack[1] != null)
				if (crystallizerItemStacks[3] == null)
					crystallizerItemStacks[3] = itemstack[1].copy();
				else if (crystallizerItemStacks[3].getItem() == itemstack[1].getItem())
					crystallizerItemStacks[3].stackSize += itemstack[1].stackSize;

			--crystallizerItemStacks[0].stackSize;

			if (crystallizerItemStacks[0].stackSize <= 0)
				crystallizerItemStacks[0] = null;
		}
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
	 * fuel
	 */
	// TODO: create new fuel types
	public static int getCrystallizationTime(ItemStack par1ItemStack)
	{
		if (par1ItemStack == null)
			return 0;
		else
		{
			Item item = par1ItemStack.getItem();

			if (item == ACItems.dread_fragment) return 100;
			if (item == ACItems.dreaded_shard_of_abyssalnite) return 1000;
			if (item == ACItems.dreaded_chunk_of_abyssalnite) return 1600;
			if (item == Items.BLAZE_POWDER) return 1200;
			if (item == Items.BLAZE_ROD) return 2400;
			if (item == ACItems.methane) return 10000;
			return AbyssalCraftAPI.getFuelValue(par1ItemStack, FuelType.CRYSTALLIZER);
		}
	}

	public static boolean isItemFuel(ItemStack par1ItemStack)
	{
		/**
		 * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
		 * fuel
		 */
		return getCrystallizationTime(par1ItemStack) > 0;
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

	net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
	net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
	net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
	{
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
			else if (facing == EnumFacing.UP)
				return (T) handlerTop;
			else
				return (T) handlerSide;
		return super.getCapability(capability, facing);
	}
}
