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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ItemRendingPedestalBlock extends ItemPEContainerBlock {

	public ItemRendingPedestalBlock(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List<String> l, boolean B){
		super.addInformation(is, player, l, B);
		int abyssal = getEnergy(is, "Abyssal");
		int dread = getEnergy(is, "Dread");
		int omothol = getEnergy(is, "Omothol");
		int shadow = getEnergy(is, "Shadow");
		l.add(I18n.translateToLocal("tooltip.drainstaff.energy.1")+": " + abyssal + "/100");
		l.add(I18n.translateToLocal("tooltip.drainstaff.energy.2")+": " + dread + "/100");
		l.add(I18n.translateToLocal("tooltip.drainstaff.energy.3")+": " + omothol + "/100");
		l.add(I18n.translateToLocal("tooltip.drainstaff.energy.4")+": " + shadow + "/200");
	}

	public int getEnergy(ItemStack par1ItemStack, String type)
	{
		return par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("energy"+type) ? (int)par1ItemStack.getTagCompound().getInteger("energy"+type) : 0;
	}
}
