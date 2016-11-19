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

import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemAntiBucket extends ItemBucket{

	public ItemAntiBucket(Block par1){
		super(par1);
		setMaxStackSize(1);
		setUnlocalizedName("antibucket");
		setCreativeTab(ACTabs.tabItems);
		setContainerItem(Items.bucket);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List<String> l, boolean B)
	{
		l.add(StatCollector.translateToLocal("tooltip.antibucket"));
	}
}