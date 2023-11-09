/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import com.shinoow.abyssalcraft.api.necronomicon.condition.DimensionCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemTieredEnergyContainerBlock extends ItemMetadataPEContainerBlock {

	public ItemTieredEnergyContainerBlock(Block b) {
		super(b);
	}

	@Override
	public IUnlockCondition getUnlockCondition(ItemStack stack) {
		if(stack.getMetadata() > 0)
			return new DimensionCondition(getDim(stack.getMetadata()));
		return super.getUnlockCondition(stack);
	}

	private int getDim(int meta){
		return meta == 1 ? ACLib.abyssal_wasteland_id : meta == 2 ? ACLib.dreadlands_id : meta == 3 ? ACLib.omothol_id : 0;
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		int base = 10000;
		switch(stack.getItemDamage()){
		case 0:
			return base * 2;
		case 1:
			return base * 6;
		case 2:
			return base * 24;
		case 3:
			return base * 120;
		default:
			return base;
		}
	}
}
