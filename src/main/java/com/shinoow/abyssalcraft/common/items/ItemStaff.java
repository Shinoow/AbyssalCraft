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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.client.handlers.AbyssalCraftClientEventHooks;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.StaffOfRendingMessage;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

public class ItemStaff extends Item implements IStaffOfRending {

	public ItemStaff() {
		super();
		setUnlocalizedName("staff");
		setCreativeTab(ACTabs.tabTools);
		setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List<String> l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.staff"));
		int abyssal = getEnergy(is, "Abyssal");
		int dread = getEnergy(is, "Dread");
		int omothol = getEnergy(is, "Omothol");
		int shadow = getEnergy(is, "Shadow");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.1")+": " + abyssal + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.2")+": " + dread + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.3")+": " + omothol + "/100");
		l.add(StatCollector.translateToLocal("tooltip.drainstaff.energy.4")+": " + shadow + "/200");
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
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

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

		if(world.isRemote){

			MovingObjectPosition mov = AbyssalCraftClientEventHooks.getMouseOverExtended(50);

			if (mov != null)
				if (mov.entityHit != null && !mov.entityHit.isDead)
					if (mov.entityHit != player )
						PacketDispatcher.sendToServer(new StaffOfRendingMessage(mov.entityHit.getEntityId()));
		}

		return stack;
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