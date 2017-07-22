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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster2.EnumCrystalType2;
import com.shinoow.abyssalcraft.lib.ACLib;

public class ItemCrystalClusterBlock2 extends ItemMetadataBlock {

	public ItemCrystalClusterBlock2(Block block) {
		super(block);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B){
		l.add(I18n.translateToLocal("tooltip.crystal")+ ": " + ACLib.crystalAtoms[is.getItemDamage()+16]);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return I18n.translateToLocalFormatted("crystalcluster.postfix", name(par1ItemStack.getItemDamage()));
	}

	public String name(int meta) {
		String s = EnumCrystalType2.byMetadata(meta).getName();
		String name = s.substring(0, 1).toUpperCase() + s.substring(1);
		return I18n.translateToLocal("item.crystal."+name+".name");
	}
}
