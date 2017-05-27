/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStoneTablet extends ItemACBasic {

	public ItemStoneTablet() {
		super("stonetablet");
		setMaxStackSize(1);

		addPropertyOverride(new ResourceLocation("cursed"), (stack, worldIn, entityIn) -> isCursed(stack) ? 1.0F : 0.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, NonNullList<ItemStack> par3List){
		par3List.add(new ItemStack(par1Item));
		ItemStack stack = new ItemStack(par1Item);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("ItemInventory", new NBTTagList());
		stack.getTagCompound().setFloat("PotEnergy", 0);
		par3List.add(stack);
		ItemStack stack1 = new ItemStack(par1Item);
		setCursed(stack1);
		par3List.add(stack1);
	}

	public boolean hasInventory(ItemStack stack){
		if(stack.isEmpty()) return false;

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		return stack.getTagCompound().hasKey("ItemInventory") && stack.getTagCompound().hasKey("PotEnergy");
	}

	public boolean isCursed(ItemStack stack){
		if(stack.isEmpty()) return false;

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		return stack.getTagCompound().hasKey("Cursed");

	}

	public void setCursed(ItemStack stack){
		if(stack.isEmpty()) return;

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setBoolean("Cursed", true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List<String> l, boolean B)
	{
		if(isCursed(is))
			l.add(TextFormatting.DARK_PURPLE+I18n.format("tooltip.stonetablet.cursed"));
		if(hasInventory(is)){
			l.add((int)is.getTagCompound().getFloat("PotEnergy") + " PE");
			l.add(I18n.format("tooltip.stonetablet.contents", is.getTagCompound().getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND).tagCount()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return hasInventory(stack);
	}
}
