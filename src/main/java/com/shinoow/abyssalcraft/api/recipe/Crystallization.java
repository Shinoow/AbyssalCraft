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
package com.shinoow.abyssalcraft.api.recipe;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.item.ItemStack;

/**
 * A Crystallizer recipe
 * @author shinoow
 *
 * @since 2.0
 */
public class Crystallization {
	public final ItemStack INPUT, OUTPUT1, OUTPUT2;
	public final float XP;

	public Crystallization(ItemStack input, ItemStack output1, ItemStack output2, float xp) {
		INPUT = input;
		OUTPUT1 = output1;
		OUTPUT2 = output2;
		XP = xp;
	}

	public Crystallization(ItemStack input, ItemStack output, float xp) {
		this(input, output, ItemStack.EMPTY, xp);
	}

	public ItemStack[] getOutputs() {
		return new ItemStack[] {OUTPUT1, OUTPUT2};
	}

	/**
	 * Shorthand for checking if something is an output
	 */
	public boolean anyMatch(ItemStack stack) {
		return APIUtils.areStacksEqual(stack, OUTPUT1) ||
				APIUtils.areStacksEqual(stack, OUTPUT2);
	}
}
