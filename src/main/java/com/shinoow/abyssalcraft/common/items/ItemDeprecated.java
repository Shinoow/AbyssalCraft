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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemDeprecated extends Item {

	public ItemDeprecated(String name){
		super();
		setUnlocalizedName(name);
		setTextureName("abyssalcraft:deprecated");
		setCreativeTab(null);
		setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return EnumChatFormatting.STRIKETHROUGH + super.getItemStackDisplayName(par1ItemStack);
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
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(stack.getItem() == AbyssalCraft.crystalIron)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 0));
		if(stack.getItem() == AbyssalCraft.crystalGold)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 1));
		if(stack.getItem() == AbyssalCraft.crystalSulfur)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 2));
		if(stack.getItem() == AbyssalCraft.crystalCarbon)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 3));
		if(stack.getItem() == AbyssalCraft.crystalOxygen)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 4));
		if(stack.getItem() == AbyssalCraft.crystalHydrogen)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 5));
		if(stack.getItem() == AbyssalCraft.crystalNitrogen)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 6));
		if(stack.getItem() == AbyssalCraft.crystalPhosphorus)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 7));
		if(stack.getItem() == AbyssalCraft.crystalPotassium)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 8));
		if(stack.getItem() == AbyssalCraft.crystalNitrate)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 9));
		if(stack.getItem() == AbyssalCraft.crystalMethane)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 10));
		if(stack.getItem() == AbyssalCraft.crystalRedstone)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 11));
		if(stack.getItem() == AbyssalCraft.crystalAbyssalnite)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 12));
		if(stack.getItem() == AbyssalCraft.crystalCoralium)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 13));
		if(stack.getItem() == AbyssalCraft.crystalDreadium)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 14));
		if(stack.getItem() == AbyssalCraft.crystalBlaze)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 15));
		if(stack.getItem() == AbyssalCraft.crystalTin)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 16));
		if(stack.getItem() == AbyssalCraft.crystalCopper)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 17));
		if(stack.getItem() == AbyssalCraft.crystalSilicon)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 18));
		if(stack.getItem() == AbyssalCraft.crystalMagnesium)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 19));
		if(stack.getItem() == AbyssalCraft.crystalAluminium)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 20));
		if(stack.getItem() == AbyssalCraft.crystalSilica)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 21));
		if(stack.getItem() == AbyssalCraft.crystalAlumina)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 22));
		if(stack.getItem() == AbyssalCraft.crystalMagnesia)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 23));
		if(stack.getItem() == AbyssalCraft.crystalZinc)
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.crystal, 1, 24));

		stack.stackSize--;

		return stack;
	}
}
