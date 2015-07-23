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
import net.minecraft.init.Items;
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

		if(stack.getItem() == AbyssalCraft.pickaxeC){
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.abyblock, 2));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.Corb));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.pickaxeA));
			player.inventory.addItemStackToInventory(new ItemStack(Items.blaze_rod));
		}
		if(stack.getItem() == AbyssalCraft.axeC){
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.abyblock, 2));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.Corb));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.axeA));
			player.inventory.addItemStackToInventory(new ItemStack(Items.blaze_rod));
		}
		if(stack.getItem() == AbyssalCraft.shovelC){
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.abyblock, 2));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.Corb));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.shovelA));
			player.inventory.addItemStackToInventory(new ItemStack(Items.blaze_rod));
		}
		if(stack.getItem() == AbyssalCraft.swordC){
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.abyblock, 4));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.Corb));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.swordA));
			player.inventory.addItemStackToInventory(new ItemStack(Items.blaze_rod));
		}
		if(stack.getItem() == AbyssalCraft.hoeC){
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.abyblock));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.Corb));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.hoeA));
			player.inventory.addItemStackToInventory(new ItemStack(Items.blaze_rod));
		}
		if(stack.getItem() == AbyssalCraft.helmetC){
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.abyblock, 5));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.Corb));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.helmet));

		}
		if(stack.getItem() == AbyssalCraft.plateC){
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.abyblock, 7));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.Corb));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.plate));
		}
		if(stack.getItem() == AbyssalCraft.legsC){
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.abyblock, 6));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.Corb));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.legs));
		}
		if(stack.getItem() == AbyssalCraft.bootsC){
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.abyblock, 4));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.Corb));
			player.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.boots));
		}

		stack.stackSize--;

		return stack;
	}
}
