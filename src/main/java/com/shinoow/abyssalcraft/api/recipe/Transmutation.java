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

/**
 * A Transmutator recipe
 * @author shinoow
 *
 * @since 2.0
 */
public class Transmutation {

	public final ItemStack INPUT, OUTPUT;
	public final float XP;

	public Transmutation(ItemStack input, ItemStack output, float xp) {
		INPUT = input;
		OUTPUT = output;
		XP = xp;
	}
}
