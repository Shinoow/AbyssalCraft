/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.recipe.EngraverRecipes;
import com.shinoow.abyssalcraft.common.blocks.BlockEngraver;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityEngraver extends TEDirectional implements ISidedInventory {

	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 1};
	private static final int[] slotsSides = new int[] {1};

	private ItemStack[] engraverItemStacks = new ItemStack[3];

	public int engraverProcessTime;
	private String containerName;

	@Override
	public int getSizeInventory() {
		return engraverItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return engraverItemStacks[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {

		if(engraverItemStacks[var1] != null){
			ItemStack itemstack;
			if(engraverItemStacks[var1].stackSize <= var2){
				itemstack = engraverItemStacks[var1];
				engraverItemStacks[var1] = null;
				return itemstack;
			} else {
				itemstack = engraverItemStacks[var1].splitStack(var2);
				if(engraverItemStacks[var1].stackSize == 0)
					engraverItemStacks[var1] = null;

				return itemstack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {

		if(engraverItemStacks[var1] != null){
			ItemStack itemstack = engraverItemStacks[var1];
			engraverItemStacks[var1] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {

		engraverItemStacks[var1] = var2;

		if(var2 != null && var2.stackSize > getInventoryStackLimit())
			var2.stackSize = getInventoryStackLimit();

	}

	@Override
	public String getInventoryName()
	{
		return hasCustomInventoryName() ? containerName : "container.abyssalcraft.engraver";
	}

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
		engraverItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < engraverItemStacks.length)
				engraverItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}

		engraverProcessTime = par1.getShort("ProcessTime");

		if (par1.hasKey("CustomName", 8))
			containerName = par1.getString("CustomName");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1)
	{
		super.writeToNBT(par1);
		par1.setShort("ProcessTime", (short)engraverProcessTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < engraverItemStacks.length; ++i)
			if (engraverItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				engraverItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}

		par1.setTag("Items", nbttaglist);

		if (hasCustomInventoryName())
			par1.setString("CustomName", containerName);
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@SideOnly(Side.CLIENT)
	public int getProcessProgressScaled(int par1)
	{
		return engraverProcessTime * par1 / 200;
	}

	public boolean isEngraving(){
		return engraverItemStacks[1] != null;
	}

	@Override
	public void updateEntity()
	{

		boolean flag1 = false;

		if (!worldObj.isRemote)
		{
			if (isEngraving() && canEngrave())
			{
				++engraverProcessTime;

				if (engraverProcessTime == 200)
				{
					engraverProcessTime = 0;
					engraveItem();
					flag1 = true;
					engraverItemStacks[1].setItemDamage(engraverItemStacks[1].getItemDamage() + 1);
					if (engraverItemStacks[1].getItemDamage() == engraverItemStacks[1].getMaxDamage())
						engraverItemStacks[1] = engraverItemStacks[1].getItem().getContainerItem(engraverItemStacks[1]);
				}
			} else
				engraverProcessTime = 0;

			if (engraverProcessTime > 0)
			{
				flag1 = true;
				BlockEngraver.updateEngraverBlockState(worldObj, xCoord, yCoord, zCoord);
			}
		}

		if (flag1)
			markDirty();
	}

	/**
	 * Returns true if the engraver can engrave an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private boolean canEngrave()
	{
		if (engraverItemStacks[0] == null || EngraverRecipes.instance().getEngravingResult(engraverItemStacks[0], (ItemEngraving)engraverItemStacks[1].getItem()) == null)
			return false;
		else
		{
			ItemStack itemstack = EngraverRecipes.instance().getEngravingResult(engraverItemStacks[0], (ItemEngraving)engraverItemStacks[1].getItem());
			if (itemstack == null) return false;
			if (engraverItemStacks[2] == null) return true;
			if (!engraverItemStacks[2].isItemEqual(itemstack)) return false;
			int result = engraverItemStacks[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= engraverItemStacks[2].getMaxStackSize();
		}
	}

	/**
	 * Turn one item from the engraver source stack into the appropriate processed item in the engraver result stack
	 */
	public void engraveItem()
	{
		if (canEngrave())
		{
			ItemStack itemstack = EngraverRecipes.instance().getEngravingResult(engraverItemStacks[0], (ItemEngraving)engraverItemStacks[1].getItem());

			if (engraverItemStacks[2] == null)
				engraverItemStacks[2] = itemstack.copy();
			else if (engraverItemStacks[2].getItem() == itemstack.getItem())
				engraverItemStacks[2].stackSize += itemstack.stackSize;

			--engraverItemStacks[0].stackSize;

			if (engraverItemStacks[0].stackSize <= 0)
				engraverItemStacks[0] = null;
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return par1 == 2 ? false : par1 == 1 ? par2ItemStack.getItem() instanceof ItemEngraving : true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {

		return var1 == 0 ? slotsBottom : var1 == 1 ? slotsTop : slotsSides;
	}

	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) {

		return isItemValidForSlot(var1, var2);
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) {
		return false;
	}
}
