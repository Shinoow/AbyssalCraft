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

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemCBucket extends ItemBucket{

	public ItemCBucket(Block par1){
		super(par1);
		setMaxStackSize(1);
		//		GameRegistry.registerItem(this, "cbucket");
		setUnlocalizedName("cbucket");
		setCreativeTab(AbyssalCraft.tabItems);
		setContainerItem(Items.bucket);
		//		setTextureName(modid + ":" + "Cbucket")
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B)
	{
		l.add(I18n.translateToLocal("tooltip.cbucket.1"));
		l.add(I18n.translateToLocal("tooltip.cbucket.2"));
		l.add(I18n.translateToLocal("tooltip.cbucket.3"));
	}

	@Override
	public boolean hasEffect(ItemStack is){
		return true;
	}
}
