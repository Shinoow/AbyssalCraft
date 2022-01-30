/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRendingPedestalBlock extends ItemPEContainerBlock {

	public ItemRendingPedestalBlock(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		int abyssal = getEnergy(is, "Abyssal");
		int dread = getEnergy(is, "Dread");
		int omothol = getEnergy(is, "Omothol");
		int shadow = getEnergy(is, "Shadow");
		l.add(I18n.format("tooltip.drainstaff.energy.abyssal")+": " + abyssal + "/100");
		l.add(I18n.format("tooltip.drainstaff.energy.dread")+": " + dread + "/100");
		l.add(I18n.format("tooltip.drainstaff.energy.omothol")+": " + omothol + "/100");
		l.add(I18n.format("tooltip.drainstaff.energy.shadow")+": " + shadow + "/200");
	}

	public int getEnergy(ItemStack par1ItemStack, String type)
	{
		return par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("energy"+type) ? (int)par1ItemStack.getTagCompound().getInteger("energy"+type) : 0;
	}
}
