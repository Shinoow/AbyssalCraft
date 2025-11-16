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

import net.minecraft.item.ItemStack;

public class AnvilForging {

	public final ItemStack INPUT1, INPUT2;
	private final ItemStack OUTPUT;
	public AnvilForgingType TYPE;
	private int PRICE;

	public AnvilForging(ItemStack input1, ItemStack input2, ItemStack output) {
		INPUT1 = input1;
		INPUT2 = input2;
		OUTPUT = output;
		TYPE = AnvilForgingType.DEFAULT;
		PRICE = 1;
	}

	public AnvilForging setType(AnvilForgingType type) {
		TYPE = type;
		return this;
	}

	public AnvilForging setPrice(int price) {
		PRICE = price;
		return this;
	}

	public ItemStack getOutput() {
		return OUTPUT.copy();
	}

	public int getPrice() {
		return PRICE;
	}
}
