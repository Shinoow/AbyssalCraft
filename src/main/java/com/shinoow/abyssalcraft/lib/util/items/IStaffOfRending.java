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
package com.shinoow.abyssalcraft.lib.util.items;

import net.minecraft.item.ItemStack;

/**
 * Internal interface for Staff of Rending type of items<br>
 * Could be used by other mods, but they should replicate the impl
 * @author shinoow
 *
 */
public interface IStaffOfRending {

	public void increaseEnergy(ItemStack stack, String type);

	public void setEnergy(int amount, ItemStack stack, String type);

	public int getEnergy(ItemStack par1ItemStack, String type);

	public int getDrainAmount(ItemStack stack);
}
