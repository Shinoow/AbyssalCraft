/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
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

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityMaterializer extends TileEntity implements IInventory, ITickable {

	/**
	 * The ItemStacks that hold the items currently being used in the materializer
	 */
	private NonNullList<ItemStack> materializerItemStacks = NonNullList.<ItemStack>withSize(550, ItemStack.EMPTY);

	private String containerName, invName;

	public boolean isDirty;

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return materializerItemStacks.size();
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int par1)
	{
		return materializerItemStacks.get(par1);
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

		if (!materializerItemStacks.get(par1).isEmpty())
		{
			ItemStack itemstack;

			if (materializerItemStacks.get(par1).getCount() <= par2)
			{
				if(par1 > 1) {
					itemstack = materializerItemStacks.get(par1).copy();
					if(canMaterialize(itemstack, getStackInSlot(0)))
						MaterializerRecipes.instance().processMaterialization(itemstack, getStackInSlot(0));
					else itemstack = ItemStack.EMPTY;
				} else {
					itemstack = materializerItemStacks.get(par1);
					materializerItemStacks.set(par1, ItemStack.EMPTY);
				}
				return itemstack;
			}
			else
			{
				if(par1 > 1) {
					itemstack = materializerItemStacks.get(par1).copy();
					if(canMaterialize(itemstack, getStackInSlot(0)))
						MaterializerRecipes.instance().processMaterialization(itemstack, getStackInSlot(0));
					else itemstack = ItemStack.EMPTY;
				}else
					itemstack = materializerItemStacks.get(par1).splitStack(par2);

				return itemstack;
			}
		} else
			return ItemStack.EMPTY;
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
		return par1 > 1 ? ItemStack.EMPTY : ItemStackHelper.getAndRemove(materializerItemStacks, par1);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		if(par1 == 0) isDirty = true;

		if(par1 == 1 && !par2ItemStack.isEmpty()) clippyQuote();

		if(par1 < 2) {
			materializerItemStacks.set(par1, par2ItemStack);

			if (!par2ItemStack.isEmpty() && par2ItemStack.getCount() > getInventoryStackLimit())
				par2ItemStack.setCount(getInventoryStackLimit());
		}
	}

	private void clippyQuote(){
		switch(world.rand.nextInt(7)){
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
		par1.getTagList("Items", 10);
		materializerItemStacks = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(par1, materializerItemStacks);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1)
	{
		super.writeToNBT(par1);
		ItemStack stack = getStackInSlot(0).copy();
		ItemStack stack1 = getStackInSlot(1).copy();
		clear();
		materializerItemStacks.set(0, stack);
		materializerItemStacks.set(1, stack1);

		ItemStackHelper.saveAllItems(par1, materializerItemStacks);

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
		for(int i = 2; i < materializerItemStacks.size(); i++)
			materializerItemStacks.set(i, ItemStack.EMPTY);
		if (!materializerItemStacks.get(0).isEmpty())
		{
			List<ItemStack> list = MaterializerRecipes.instance().getMaterializationResult(materializerItemStacks.get(0));

			if(!list.isEmpty())
				for(int i = 0; i < (list.size() > 548 ? 548 : list.size()); i++)
					materializerItemStacks.set(i+2, list.get(i));
		}
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return world.getTileEntity(pos) != this ? false : par1EntityPlayer.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
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
		materializerItemStacks.clear();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : materializerItemStacks)
			if (!itemstack.isEmpty())
				return false;

		return true;
	}

	private boolean canMaterialize(ItemStack stack, ItemStack bag){

		for(ItemStack stack1 : MaterializerRecipes.instance().getMaterializationResult(bag))
			if(APIUtils.areStacksEqual(stack, stack1))
				return true;

		return false;
	}
}
