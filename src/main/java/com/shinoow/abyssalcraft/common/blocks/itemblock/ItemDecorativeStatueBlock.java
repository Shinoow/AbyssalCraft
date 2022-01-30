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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemDecorativeStatueBlock extends ItemBlockAC {

	public ItemDecorativeStatueBlock(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List par3List, ITooltipFlag par4)
	{
		par3List.add(I18n.translateToLocal("tooltip.decorativestatue"));
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return I18n.translateToLocalFormatted("decorativestatue.prefix", super.getItemStackDisplayName(par1ItemStack));
	}
}
