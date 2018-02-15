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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.client.ClientProxy;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStaff extends ItemACBasic implements IStaffOfRending{

	public ItemStaff() {
		super("staff");
		setCreativeTab(ACTabs.tabTools);
		setFull3D();
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs par2CreativeTab, NonNullList<ItemStack> par3List){
		if(isInCreativeTab(par2CreativeTab)){
			par3List.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("Mode", 1);
			par3List.add(stack);
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B){
		l.add(I18n.format("tooltip.staff"));
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		l.add(I18n.format("tooltip.staff.mode.1")+": "+TextFormatting.GOLD+I18n.format(is.getTagCompound().getInteger("Mode") == 1 ? "item.drainstaff.normal.name" : "item.gatewaykey.name"));
		l.add(I18n.format("tooltip.staff.mode.2", TextFormatting.GOLD+ClientProxy.staff_mode.getDisplayName()+TextFormatting.GRAY));
		if(is.getTagCompound().getInteger("Mode") == 1)
			ACItems.staff_of_rending.addInformation(is, player, l, B);
		else if(is.getTagCompound().getInteger("Mode") == 0)
			ACItems.rlyehian_gateway_key.addInformation(is, player, l, B);
	}

	@Override
	public boolean isFull3D(){
		return true;
	}

	@Override
	public void increaseEnergy(ItemStack stack, String type){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("energy"+type, getEnergy(stack, type) + getDrainAmount(stack));
	}

	@Override
	public void setEnergy(int amount, ItemStack stack, String type){
		stack.getTagCompound().setInteger("energy"+type, amount);
	}

	@Override
	public int getEnergy(ItemStack par1ItemStack, String type)
	{
		return par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("energy"+type) ? (int)par1ItemStack.getTagCompound().getInteger("energy"+type) : 0;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		if(stack.getTagCompound().getInteger("Mode") != 1) return new ActionResult(EnumActionResult.PASS, stack);
		else return ACItems.staff_of_rending.onItemRightClick(world, player, hand);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){

		ItemStack stack = player.getHeldItem(hand);

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		if(stack.getTagCompound().getInteger("Mode") != 0) return EnumActionResult.PASS;
		else return ACItems.rlyehian_gateway_key.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
	}

	@Override
	public int getDrainAmount(ItemStack stack){
		return 5;
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}
}
