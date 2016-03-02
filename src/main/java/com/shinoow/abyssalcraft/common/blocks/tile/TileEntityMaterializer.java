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
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;

public class TileEntityMaterializer extends TileEntity implements ISidedInventory, ITickable {

	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 1};
	private static final int[] slotsSides = new int[] {1};
	/**
	 * The ItemStacks that hold the items currently being used in the materializer
	 */
	private ItemStack[] materializerItemStacks = new ItemStack[30];

	private String containerName;

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return materializerItemStacks.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return materializerItemStacks[par1];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 */
	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (materializerItemStacks[par1] != null)
		{
			ItemStack itemstack;

			if (materializerItemStacks[par1].stackSize <= par2)
			{
				itemstack = materializerItemStacks[par1];
				materializerItemStacks[par1] = null;
				return itemstack;
			}
			else
			{
				itemstack = materializerItemStacks[par1].splitStack(par2);

				if (materializerItemStacks[par1].stackSize == 0)
					materializerItemStacks[par1] = null;

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
		if (materializerItemStacks[par1] != null)
		{
			ItemStack itemstack = materializerItemStacks[par1];
			materializerItemStacks[par1] = null;
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
		materializerItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getName()
	{
		return hasCustomName() ? containerName : "container.abyssalcraft.materializer";
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomName()
	{
		return containerName != null && containerName.length() > 0;
	}

	@Override
	public IChatComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
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
		materializerItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < materializerItemStacks.length)
				materializerItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}

		if (par1.hasKey("CustomName", 8))
			containerName = par1.getString("CustomName");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1)
	{
		super.writeToNBT(par1);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); ++i)
			if (materializerItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				materializerItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}

		par1.setTag("Items", nbttaglist);

		if (hasCustomName())
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

	@Override
	public void update()
	{
		//		boolean flag = materializerBurnTime > 0;
		//		boolean flag1 = false;
		//
		//		if (materializerBurnTime > 0)
		//			--materializerBurnTime;
		//
		//		if (!worldObj.isRemote)
		//		{
		//			if (materializerBurnTime == 0 && canMaterialize())
		//			{
		//				currentItemBurnTime = materializerBurnTime = getItemBurnTime(materializerItemStacks[1]);
		//
		//				if (materializerBurnTime > 0)
		//				{
		//					flag1 = true;
		//
		//					if (materializerItemStacks[1] != null)
		//					{
		//						--materializerItemStacks[1].stackSize;
		//
		//						if (materializerItemStacks[1].stackSize == 0)
		//							materializerItemStacks[1] = materializerItemStacks[1].getItem().getContainerItem(materializerItemStacks[1]);
		//					}
		//				}
		//			}
		//
		//			if (isMaterializing() && canMaterialize())
		//			{
		//				++materializerProcessTime;
		//
		//				if (materializerProcessTime == 200)
		//				{
		//					materializerProcessTime = 0;
		//					processItem();
		//					flag1 = true;
		//				}
		//			} else
		//				materializerProcessTime = 0;
		//
		//			if (flag != materializerBurnTime > 0)
		//			{
		//				flag1 = true;
		//				BlockMaterializer.updateMaterializerBlockState(materializerBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
		//			}
		//		}
		//
		//		if (flag1)
		//			markDirty();
		test();
	}

	private void test()
	{
		//		if (materializerItemStacks[0] != null)
		//		{
		//			List<ItemStack> list = MaterializerRecipes.instance().getMaterializationResult(materializerItemStacks[0]);
		//
		//			if(list != null){
		//				Iterator<ItemStack> iter = list.iterator();
		//
		//				for(int i = 2; i < materializerItemStacks.length; i++)
		//					if(iter.hasNext())
		//						materializerItemStacks[i] = iter.next();
		//			}
		//		}
	}

	/**
	 * Turn one item from the materializer source stack into the appropriate processed item in the materializer result stack
	 */
	public void processItem()
	{
		//		if (canMaterialize())
		//		{
		//			ItemStack itemstack = MaterializerRecipes.instance().getMaterializationResult(materializerItemStacks[0]);
		//
		//			if (materializerItemStacks[2] == null)
		//				materializerItemStacks[2] = itemstack.copy();
		//			else if (materializerItemStacks[2].getItem() == itemstack.getItem())
		//				materializerItemStacks[2].stackSize += itemstack.stackSize;
		//
		//			--materializerItemStacks[0].stackSize;
		//
		//			if (materializerItemStacks[0].stackSize <= 0)
		//				materializerItemStacks[0] = null;
		//		}
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
		return false;
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
	public boolean canInsertItem(int par1, ItemStack par2ItemStack, EnumFacing facing)
	{
		return false;
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
	 * side
	 */
	@Override
	public boolean canExtractItem(int par1, ItemStack par2ItemStack, EnumFacing facing)
	{
		return false;
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
}