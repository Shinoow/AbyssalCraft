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
package com.shinoow.abyssalcraft.common.items;

import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.condition.BiomePredicateCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DimensionCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.item.ItemMetadata;

import net.minecraft.item.ItemStack;

public class ItemMetadataMisc extends ItemMetadata {

	public ItemMetadataMisc(String name, String...names) {
		super(name, names);
	}

	@Override
	public IUnlockCondition getUnlockCondition(ItemStack stack) {
		if(stack.getItem() == ACItems.skin || stack.getItem() == ACItems.essence)
			return new DimensionCondition(getDim(stack.getMetadata()));
		if(stack.getItem() == ACItems.ingot_nugget)
			switch(stack.getMetadata()){
			case 0:
				return new BiomePredicateCondition(b -> b instanceof IDarklandsBiome);
			case 1:
				return new DimensionCondition(ACLib.abyssal_wasteland_id);
			case 2:
				return new DimensionCondition(ACLib.dreadlands_id);
			case 3:
				return new DimensionCondition(ACLib.omothol_id);
			}
		return super.getUnlockCondition(stack);
	}

	private int getDim(int meta){
		return meta == 0 ? ACLib.abyssal_wasteland_id : meta == 1 ? ACLib.dreadlands_id : meta == 2 ? ACLib.omothol_id : 0;
	}
}
