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
package com.shinoow.abyssalcraft.common.handlers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;

public class CrystalFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.getItem() == Item.getItemFromBlock(ACBlocks.crystal_cluster) ||
				fuel.getItem() == Item.getItemFromBlock(ACBlocks.crystal_cluster2))
			return 12150;
		if(fuel.getItem() == ACItems.crystal)
			return 1350;
		if(fuel.getItem() == ACItems.crystal_shard)
			return 150;
		if(fuel.getItem() == ACItems.crystal_fragment)
			return 17;
		if(APIUtils.isCrystal(fuel))
			return 1000;
		return 0;
	}
}
