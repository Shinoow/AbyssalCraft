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
package com.shinoow.abyssalcraft.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.shinoow.abyssalcraft.api.event.ACEvents;
import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityMaterializer;

public class SlotMaterializer extends Slot
{
	/** The player that is using the GUI where this slot resides. */
	private EntityPlayer thePlayer;
	private int stackSize;
	private TileEntityMaterializer materializer;

	public SlotMaterializer(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
	{
		super(par2IInventory, par3, par4, par5);
		thePlayer = par1EntityPlayer;
		if(par2IInventory instanceof TileEntityMaterializer)
			materializer = (TileEntityMaterializer) par2IInventory;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}

	@Override
	public ItemStack decrStackSize(int par1)
	{
		if (getHasStack())
			stackSize += Math.min(par1, getStack().stackSize);

		return super.decrStackSize(par1);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		this.onCrafting(par2ItemStack);
		super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
	}

	@Override
	protected void onCrafting(ItemStack par1ItemStack, int par2)
	{
		stackSize += par2;
	}

	@Override
	protected void onCrafting(ItemStack par1ItemStack)
	{
		if(materializer != null){
			par1ItemStack.onCrafting(thePlayer.worldObj, thePlayer, stackSize);

			MinecraftForge.EVENT_BUS.post(new ACEvents.ItemMaterializedEvent(thePlayer, par1ItemStack));

			MaterializerRecipes.instance().processMaterialization(par1ItemStack, materializer.getStackInSlot(0));
		}
	}
}
