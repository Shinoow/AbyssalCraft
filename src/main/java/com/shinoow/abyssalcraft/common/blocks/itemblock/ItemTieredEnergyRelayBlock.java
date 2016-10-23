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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemTieredEnergyRelayBlock extends ItemMetadataPEContainerBlock {

	public ItemTieredEnergyRelayBlock(Block b) {
		super(b);
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		return 500;
	}
}
