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
package com.shinoow.abyssalcraft.integration.jei.rending;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RendingEntry {

	public ItemStack output;
	public int dimension;
	public String type;
	public String description;

	public RendingEntry(ItemStack output, int dimension, String type, String description){
		this.output = output;
		this.dimension = dimension;
		this.type = StatCollector.translateToLocal(type);
		this.description = StatCollector.translateToLocal(description);
	}

	public RendingEntry(ItemStack output, String type, String description){
		this(output, -1, type, description);
	}
}
