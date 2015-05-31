/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.thaumcraft.wands;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.api.wands.WandRod;

public class WandRodAC extends WandRod {

	public WandRodAC(String tag, int capacity, ItemStack item, int craftCost, ResourceLocation texture) {
		super(tag, capacity, item, craftCost, texture);
	}

	public WandRodAC(String tag, int capacity, ItemStack item, int craftCost, IWandRodOnUpdate onUpdate, ResourceLocation texture) {
		super(tag, capacity, item, craftCost, onUpdate, texture);
	}

	public WandRodAC(String tag, int capacity, ItemStack item, int craftCost) {
		super(tag, capacity, item, craftCost);
	}

	public WandRodAC(String tag, int capacity, ItemStack item, int craftCost, IWandRodOnUpdate onUpdate) {
		super(tag, capacity, item, craftCost, onUpdate);
	}

	@Override
	public String getResearch() {
		return "";
	}
}
