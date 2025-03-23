/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

public class ItemTieredEnergyCollectorBlock extends ItemMetadataPEContainerBlock {

	public ItemTieredEnergyCollectorBlock(Block b) {
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
		int base = 1000;
		switch(stack.getItemDamage()){
		case 0:
			return  (int) (base * 1.5);
		case 1:
			return base * 2;
		case 2:
			return (int) (base * 2.5);
		case 3:
			return base * 3;
		default:
			return base;
		}
	}
}
