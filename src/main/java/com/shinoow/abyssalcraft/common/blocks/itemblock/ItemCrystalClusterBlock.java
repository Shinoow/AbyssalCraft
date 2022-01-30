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

import com.shinoow.abyssalcraft.api.block.ICrystalBlock;
import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DimensionCondition;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ItemCrystalClusterBlock extends ItemBlockAC implements ICrystal {

	public ItemCrystalClusterBlock(Block block) {
		super(block);
		setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return I18n.translateToLocalFormatted("crystalcluster.postfix", name());
	}

	public String name() {
		int meta = ((BlockCrystalCluster)block).index;
		String s = ACLib.crystalNames[meta];
		return I18n.translateToLocal("item.crystal."+s+".name");
	}

	@Override
	public int getColor(ItemStack stack) {

		return ((ICrystalBlock) block).getColor(stack);
	}

	@Override
	public String getFormula(ItemStack stack) {

		return ((ICrystalBlock) block).getFormula(stack);
	}
}
