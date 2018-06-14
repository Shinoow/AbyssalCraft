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
package com.shinoow.abyssalcraft.common.inventory;

import com.shinoow.abyssalcraft.api.recipe.Materialization;
import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityMaterializer;
import com.shinoow.abyssalcraft.common.items.ItemCrystalBag;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMaterializer extends Container {

	private TileEntityMaterializer tileMaterializer;
	private static InventoryMaterializer basicInventory;

	public ContainerMaterializer(InventoryPlayer par1InventoryPlayer, TileEntityMaterializer par2TileEntityMaterializer)
	{
		tileMaterializer = par2TileEntityMaterializer;
		basicInventory = new InventoryMaterializer("tmp", true, 20, tileMaterializer);
		addSlotToContainer(new SlotCrystalBag(par2TileEntityMaterializer, 0, 14, 17));
		addSlotToContainer(new SlotNecronomicon(par2TileEntityMaterializer, 1, 14, 53));
		int i;

		for(i = 0; i < 3; ++i)
			for(int j = 0; j < 6; ++j)
				addSlotToContainer(new SlotMaterializer(par1InventoryPlayer.player, basicInventory, 2 + j + i * 6, 44 + j * 17, 17 + i * 17));

		for (i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2){}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return tileMaterializer.isUsableByPlayer(par1EntityPlayer);
	}

	private int getRecipeAmount(){

		int i = 0;

		for(int j = 2; j < 110; j++)
			if(tileMaterializer.getStackInSlot(j).isEmpty()){
				i = j;
				break;
			}

		return i;
	}

	public void scrollTo(float p_148329_1_)
	{
		int i = (getRecipeAmount() + 6 - 1) / 6 - 3;
		int j = (int)(p_148329_1_ * i + 0.5D);

		if (j < 0) j = 0;

		for (int k = 0; k < 3; ++k)
			for (int l = 0; l < 8; ++l)
			{
				int i1 = l + (k + j) * 6;

				if (i1 >= 2 && i1 < getRecipeAmount()) basicInventory.setInventorySlotContents(l + k * 6, tileMaterializer.getStackInSlot(i1));
				else
					basicInventory.setInventorySlotContents(l + k * 6, ItemStack.EMPTY);
			}
	}

	public boolean canScroll()
	{
		return getRecipeAmount() > 18;
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

			if (par2 != 1 && par2 != 0)
			{

				if(itemstack1.getItem() instanceof ItemCrystalBag) {
					if(!mergeItemStack(itemstack1, 0, 1, false))
						return ItemStack.EMPTY;
				} else if(itemstack1.getItem() instanceof ItemNecronomicon) {
					if(!mergeItemStack(itemstack1, 1, 2, false))
						return ItemStack.EMPTY;
				} else if (par2 >= 2 && par2 < 20){

					ItemStack itemstack2 = itemstack1.copy();
					int i = 1;
					if(itemstack2.getMaxStackSize() > 1)
						i = getMaxCount(itemstack2, inventorySlots.get(0).getStack().copy());
					itemstack2.setCount(i);
					if (!mergeItemStack(itemstack2, 20, 56, false))
						return ItemStack.EMPTY;
					else slot.decrStackSize(i);
				}
				else if (par2 >= 20 && par2 < 47)
				{
					if (!mergeItemStack(itemstack1, 47, 56, false))
						return ItemStack.EMPTY;
				}
				else if (par2 >= 47 && par2 < 56 && !mergeItemStack(itemstack1, 20, 47, false))
					return ItemStack.EMPTY;
			}
			else if (!mergeItemStack(itemstack1, 20, 56, false))
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

	private int getMaxCount(ItemStack stack, ItemStack bag) {

		int count = 0;

		ItemStack[] inventory = MaterializerRecipes.instance().extractItemsFromBag(bag);

		if(inventory == null) return 0;

		Materialization mat = MaterializerRecipes.instance().getMaterializationFor(stack);

		if(mat == null) return 0;

		while(MaterializerRecipes.instance().consumeCrystals(inventory, mat.input))
			count++;

		return count > stack.getMaxStackSize() ? stack.getMaxStackSize() : count;
	}
}
