/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

import com.shinoow.abyssalcraft.api.necronomicon.condition.DimensionCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTieredEnergyRelayBlock extends ItemMetadataPEContainerBlock {

	public ItemTieredEnergyRelayBlock(Block b) {
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
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		l.add(String.format("Range: %d Blocks", getRange(is.getItemDamage())));
	}

	protected int getRange(int meta){
		switch(meta){
		case 0:
			return 6;
		case 1:
			return 8;
		case 2:
			return 10;
		case 3:
			return 12;
		default:
			return 0;
		}
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {

		int base = 600;

		return base + 100 * stack.getMetadata();
	}
}
