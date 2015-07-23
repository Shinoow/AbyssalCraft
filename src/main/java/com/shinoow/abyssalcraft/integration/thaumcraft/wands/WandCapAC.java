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

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;

public class WandCapAC extends WandCap {

	public WandCapAC(String tag, float discount, ItemStack item, int craftCost) {
		super(tag, discount, item, craftCost);
		setTexture(new ResourceLocation("abyssalcraft","textures/model/wands/wand_cap_"+getTag()+".png"));
	}

	public WandCapAC(String tag, float discount, List<Aspect> specialAspects, float discountSpecial, ItemStack item, int craftCost) {
		super(tag, discount, specialAspects, discountSpecial, item, craftCost);
	}

	@Override
	public String getResearch() {
		return "";
	}
}
