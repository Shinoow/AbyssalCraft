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
package com.shinoow.abyssalcraft.api.item;

import net.minecraft.item.ItemStack;

/**
 * Items with this interface are recognized as crystallized elements by AbyssalCraft.
 *
 * @author shinoow
 *
 * @since 1.3
 */
public interface ICrystal {

	/**
	 * Retrieves the crystal color
	 * @param stack Current Item Stack
	 */
	int getColor(ItemStack stack);

	/**
	 * Retrieves the molecular formula for the crystal compound
	 * @param stack Current Item Stack
	 */
	String getFormula(ItemStack stack);

}
