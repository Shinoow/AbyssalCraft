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
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.recipe.EngraverRecipes;
import com.shinoow.abyssalcraft.common.blocks.BlockEngraver;

public class TileEntityEngraver extends TileEntity implements ISidedInventory, ITickable {

	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 1};
	private static final int[] slotsSides = new int[] {1};

	private NonNullList<ItemStack> engraverItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);

	public int engraverProcessTime;
	private String containerName;

	@Override
	public int getSizeInventory() {
		return engraverItemStacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return engraverItemStacks.get(var1);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {

		return ItemStackHelper.getAndSplit(engraverItemStacks, var1, var2);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		return ItemStackHelper.getAndRemove(engraverItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {

		engraverItemStacks.set(var1, var2);

		if(var2 != null && var2.getCount() > getInventoryStackLimit())
			var2.setCount(getInventoryStackLimit());

	}



	@Override
	public String getName() {

		return hasCustomName() ? containerName : "container.abyssalcraft.engraver";
	}

	@Override
	public boolean hasCustomName() {

		return containerName != null && containerName.length() > 0;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName(), new Object[0]);
	}

	public void func_145951_a(String par1)
	{
		containerName = par1;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1)
	{
		super.readFromNBT(par1);
		engraverItemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(par1, engraverItemStacks);
		engraverProcessTime = par1.getShort("ProcessTime");

		if (par1.hasKey("CustomName", 8))
			containerName = par1.getString("CustomName");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1)
	{
		super.writeToNBT(par1);
		par1.setShort("ProcessTime", (short)engraverProcessTime);
		ItemStackHelper.saveAllItems(par1, engraverItemStacks);

		if (hasCustomName())
			par1.setString("CustomName", containerName);

		return par1;
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
		return !engraverItemStacks.get(1).isEmpty();
	}

	@Override
	public void update()
	{

		boolean flag1 = false;

		if (!world.isRemote)
		{
			if (isEngraving() && canEngrave())
			{
				++engraverProcessTime;

				if (engraverProcessTime == 200)
				{
					engraverProcessTime = 0;
					engraveItem();
					flag1 = true;
					engraverItemStacks.get(1).setItemDamage(engraverItemStacks.get(1).getItemDamage() + 1);
					if (engraverItemStacks.get(1).getItemDamage() == engraverItemStacks.get(1).getMaxDamage())
						engraverItemStacks.set(1, engraverItemStacks.get(1).getItem().getContainerItem(engraverItemStacks.get(1)));
				}
			} else
				engraverProcessTime = 0;

			if (engraverProcessTime > 0)
			{
				flag1 = true;
				BlockEngraver.updateEngraverBlockState(world, pos);
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
		if (engraverItemStacks.get(0).isEmpty() || EngraverRecipes.instance().getEngravingResult(engraverItemStacks.get(0), (ItemEngraving)engraverItemStacks.get(1).getItem()).isEmpty())
			return false;
		else
		{
			ItemStack itemstack = EngraverRecipes.instance().getEngravingResult(engraverItemStacks.get(0), (ItemEngraving)engraverItemStacks.get(1).getItem());
			if (itemstack.isEmpty()) return false;
			if (engraverItemStacks.get(2).isEmpty()) return true;
			if (!engraverItemStacks.get(2).isItemEqual(itemstack)) return false;
			int result = engraverItemStacks.get(2).getCount() + itemstack.getCount();
			return result <= getInventoryStackLimit() && result <= engraverItemStacks.get(2).getMaxStackSize();
		}
	}

	/**
	 * Turn one item from the engraver source stack into the appropriate processed item in the engraver result stack
	 */
	public void engraveItem()
	{
		if (canEngrave())
		{
			ItemStack itemstack = EngraverRecipes.instance().getEngravingResult(engraverItemStacks.get(0), (ItemEngraving)engraverItemStacks.get(1).getItem());

			if (engraverItemStacks.get(2).isEmpty())
				engraverItemStacks.set(2, itemstack.copy());
			else if (engraverItemStacks.get(2).getItem() == itemstack.getItem())
				engraverItemStacks.get(2).grow(itemstack.getCount());

			engraverItemStacks.get(0).shrink(1);

			if (engraverItemStacks.get(0).getCount() <= 0)
				engraverItemStacks.set(0, ItemStack.EMPTY);
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return world.getTileEntity(pos) != this ? false : par1EntityPlayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return par1 == 2 ? false : par1 == 1 ? par2ItemStack.getItem() instanceof ItemEngraving : true;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side){
		return side == EnumFacing.DOWN ? slotsBottom : side == EnumFacing.UP ? slotsTop : slotsSides;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {

		return isItemValidForSlot(index, stack);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public int getField(int id) {

		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : engraverItemStacks)
			if (!itemstack.isEmpty())
				return false;

		return true;
	}
}
