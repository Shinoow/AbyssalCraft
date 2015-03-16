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
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemNecronomicon extends ItemACBasic {

	public ItemNecronomicon(String par1){
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(!par1ItemStack.hasTagCompound())
			par1ItemStack.setTagCompound(new NBTTagCompound());
		if(!par1ItemStack.stackTagCompound.hasKey("owner")){
			par1ItemStack.stackTagCompound.setString("owner", par3EntityPlayer.getCommandSenderName());
			par3EntityPlayer.openGui(AbyssalCraft.instance, AbyssalCraft.necronmiconGuiID, par2World, 0, 0, 0);
		}
		if(par1ItemStack.stackTagCompound.getString("owner").equals(par3EntityPlayer.getCommandSenderName()))
			par3EntityPlayer.openGui(AbyssalCraft.instance, AbyssalCraft.necronmiconGuiID, par2World, 0, 0, 0);
		else if(par2World.isRemote)
			SpecialTextUtil.JzaharText(StatCollector.translateToLocal("message.necronomicon.nope"));
		return par1ItemStack;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("owner"))
			l.add("Owner: " + is.stackTagCompound.getString("owner"));
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}
}