/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
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

import com.shinoow.abyssalcraft.api.block.ICrystalBlock;
import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DimensionCondition;
import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster.EnumCrystalType;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemCrystalClusterBlock extends ItemMetadataBlock implements ICrystal {

	public ItemCrystalClusterBlock(Block block) {
		super(block);
		setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return I18n.translateToLocalFormatted("crystalcluster.postfix", name(par1ItemStack.getItemDamage()));
	}

	public String name(int meta) {
		String s = EnumCrystalType.byMetadata(meta).getName();
		String name = s.substring(0, 1).toUpperCase() + s.substring(1);
		return I18n.translateToLocal("item.crystal."+name+".name");
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
