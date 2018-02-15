/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import java.util.List;

import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityMaterializer extends TileEntity implements ISidedInventory, ITickable {

	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 1};
	private static final int[] slotsSides = new int[] {1};
	/**
	 * The ItemStacks that hold the items currently being used in the materializer
	 */
	private ItemStack[] materializerItemStacks = new ItemStack[110];

	private String containerName, invName;

	public boolean isDirty;

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
		isDirty = true;
		if(par1 == 1) setDisplayName(null);

		if (materializerItemStacks[par1] != null)
		{
			ItemStack itemstack;

			if (materializerItemStacks[par1].stackSize <= par2)
			{
				if(par1 > 1)
					itemstack = materializerItemStacks[par1].copy();
				else {
					itemstack = materializerItemStacks[par1];
					materializerItemStacks[par1] = null;
				}
				return itemstack;
			}
			else
			{
				if(par1 > 1)
					itemstack = materializerItemStacks[par1].copy();
				else {
					itemstack = materializerItemStacks[par1].splitStack(par2);

					if (materializerItemStacks[par1].stackSize == 0)
						materializerItemStacks[par1] = null;

				}

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
		if(par1 == 0) isDirty = true;
		if(par1 == 1) setDisplayName(null);
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
		if(par1 == 0) isDirty = true;

		if(par1 == 1 && par2ItemStack != null) clippyQuote();

		materializerItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
			par2ItemStack.stackSize = getInventoryStackLimit();
	}

	private void clippyQuote(){
		switch(worldObj.rand.nextInt(7)){
		case 0:
			setDisplayName("Hi, I'm Clippy! What are we","materializing today?");
			break;
		case 1:
			setDisplayName("It looks like you're trying to","create air. Do you need help?");
			break;
		case 2:
			setDisplayName("The Necronomicon has a recipe","list!");
			break;
		case 3:
			setDisplayName("Do you need assistance?");
			break;
		case 4:
			setDisplayName("Did you know gold consists of","gold atoms?");
			break;
		case 5:
			setDisplayName("I see that you have been using","your mouse.");
			break;
		case 6:
			setDisplayName("Did you mean 'Aluminium'?");
			break;
		}
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getName()
	{
		return hasCustomName() ? containerName : "container.abyssalcraft.materializer";
	}

	public String getSecondName(){
		return hasSecondCustomName() ? invName : "container.inventory";
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomName()
	{
		return containerName != null && containerName.length() > 0;
	}

	public boolean hasSecondCustomName(){
		return invName != null && invName.length() > 0;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName(), new Object[0]);
	}

	public void setDisplayName(String par1){
		setDisplayName(par1, null);
	}

	public void setDisplayName(String par1, String par2)
	{
		containerName = par1;
		invName = par2;
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
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1)
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

	@Override
	public void update()
	{
		if(isDirty){
			refreshRecipes();
			isDirty = false;
		}
	}

	private void refreshRecipes()
	{
		for(int i = 2; i < materializerItemStacks.length; i++)
			materializerItemStacks[i] = null;
		if (materializerItemStacks[0] != null)
		{
			List<ItemStack> list = MaterializerRecipes.instance().getMaterializationResult(materializerItemStacks[0]);

			if(!list.isEmpty())
				for(int i = 0; i < (list.size() > 108 ? 108 : list.size()); i++)
					materializerItemStacks[i+2] = list.get(i);
		}
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
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {

	}
}
