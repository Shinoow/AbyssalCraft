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

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemCoraliumcluster extends Item {

	public final String gemName;

	public ItemCoraliumcluster(String name, String par2Str) {
		super();
		gemName = par2Str;
		maxStackSize = 16;
		setUnlocalizedName(name);
		setCreativeTab(ACTabs.tabItems);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List par3List, ITooltipFlag par4)
	{
		par3List.add(gemName + " Gems");
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return I18n.translateToLocal("item.ccluster.name");
	}
}
