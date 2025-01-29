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
package com.shinoow.abyssalcraft.api.block;

import com.shinoow.abyssalcraft.api.item.ICrystal;

import net.minecraft.block.state.IBlockState;

/**
 * Blocks with this interface are recognized as crystallized elements by AbyssalCraft.
 * The ICrystal methods can be delegated to the ItemBlock for this Block
 *
 * @author shinoow
 *
 * @since 2.0
 */
public interface ICrystalBlock extends ICrystal {

	/**
	 * BlockState-sensitive version of {@link ICrystal#getColor(net.minecraft.item.ItemStack)}
	 * @param state Current Block State
	 */
	int getColor(IBlockState state);
}
