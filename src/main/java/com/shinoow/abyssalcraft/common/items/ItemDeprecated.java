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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.api.item.ACItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemDeprecated extends Item {

	public ItemDeprecated(String name){
		super();
		setUnlocalizedName(name);
		setCreativeTab(null);
		setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return TextFormatting.STRIKETHROUGH + super.getItemStackDisplayName(par1ItemStack);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B)
	{
		l.add("This Item has been removed.");
		l.add("Right-click to redeem materials.");
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {

		if(stack.getItem() == ACItems.liquid_antimatter_bucket)
			player.inventory.addItemStackToInventory(ACItems.liquid_antimatter_bucket_stack.copy());
		if(stack.getItem() == ACItems.liquid_coralium_bucket)
			player.inventory.addItemStackToInventory(ACItems.liquid_coralium_bucket_stack.copy());

		stack.stackSize--;

		return new ActionResult(EnumActionResult.PASS, stack);
	}
}