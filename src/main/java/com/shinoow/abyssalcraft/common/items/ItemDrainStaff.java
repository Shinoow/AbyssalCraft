/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.StaffOfRendingMessage;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.item.ItemMetadata;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemDrainStaff extends ItemMetadata implements IStaffOfRending {

	public ItemDrainStaff(){
		super("drainstaff", "normal", "aw", "dl", "omt");
		setCreativeTab(ACTabs.tabTools);
		setMaxStackSize(1);
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
		if(world.isRemote){

			RayTraceResult mov = AbyssalCraftClientEventHooks.getMouseOverExtended(50);

			if (mov != null)
				if (mov.entityHit != null && !mov.entityHit.isDead)
					if (mov.entityHit != player )
						PacketDispatcher.sendToServer(new StaffOfRendingMessage(mov.entityHit.getEntityId(), hand));
		}

		if(getEnergy(stack, "Shadow") >= 200){
			setEnergy(0, stack, "Shadow");
			player.inventory.addItemStackToInventory(new ItemStack(ACItems.shadow_gem));
		}
		if(getEnergy(stack, "Abyssal") >= 100){
			setEnergy(0, stack, "Abyssal");
			player.inventory.addItemStackToInventory(new ItemStack(ACItems.essence, 1, 0));
		}
		if(getEnergy(stack, "Dread") >= 100){
			setEnergy(0, stack, "Dread");
			player.inventory.addItemStackToInventory(new ItemStack(ACItems.essence, 1, 1));
		}
		if(getEnergy(stack, "Omothol") >= 100){
			setEnergy(0, stack, "Omothol");
			player.inventory.addItemStackToInventory(new ItemStack(ACItems.essence, 1, 2));
		}

		return new ActionResult(EnumActionResult.PASS, stack);
	}

	@Override
	public int getDrainAmount(ItemStack stack){
		return stack.getItemDamage() + 1 + MathHelper.clamp(EnchantmentHelper.getEnchantmentLevel(AbyssalCraftAPI.sapping, stack), 0, 3);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B){
		int abyssal = getEnergy(is, "Abyssal");
		int dread = getEnergy(is, "Dread");
		int omothol = getEnergy(is, "Omothol");
		int shadow = getEnergy(is, "Shadow");
		l.add(I18n.format("tooltip.drainstaff.energy.1")+": " + abyssal + "/100");
		l.add(I18n.format("tooltip.drainstaff.energy.2")+": " + dread + "/100");
		l.add(I18n.format("tooltip.drainstaff.energy.3")+": " + omothol + "/100");
		l.add(I18n.format("tooltip.drainstaff.energy.4")+": " + shadow + "/200");
	}

	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return getItemStackLimit(stack) == 1;
	}

	@Override
	public int getItemEnchantability(ItemStack stack)
	{
		return 5 * (stack.getItemDamage() + 1);
	}
}
