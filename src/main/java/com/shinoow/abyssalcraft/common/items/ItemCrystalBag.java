/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemCrystalBag extends ItemACBasic {

	public ItemCrystalBag(String par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		setInventorySize(stack);
		if (!world.isRemote)
			if (!player.isSneaking())
				player.openGui(AbyssalCraft.instance, AbyssalCraft.crystalbagGuiID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
		return stack;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B)
	{
		l.add(StatCollector.translateToLocal("tooltip.crystalbag"));
	}

	public void setInventorySize(ItemStack stack){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.stackTagCompound.hasKey("InvSize")){
			if(stack.getItem() == AbyssalCraft.crystalbag_s)
				stack.stackTagCompound.setInteger("InvSize", 18);
			if(stack.getItem() == AbyssalCraft.crystalbag_m)
				stack.stackTagCompound.setInteger("InvSize", 36);
			if(stack.getItem() == AbyssalCraft.crystalbag_l)
				stack.stackTagCompound.setInteger("InvSize", 54);
			if(stack.getItem() == AbyssalCraft.crystalbag_h)
				stack.stackTagCompound.setInteger("InvSize", 72);
		}
	}
}