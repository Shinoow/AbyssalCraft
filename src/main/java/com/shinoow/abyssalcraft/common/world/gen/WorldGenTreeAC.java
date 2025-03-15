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
package com.shinoow.abyssalcraft.common.world.gen;

import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenTreeAC extends WorldGenTrees {

	protected boolean fixed;

	public WorldGenTreeAC(boolean p_i2027_1_) {
		super(p_i2027_1_);
	}

	/**
	 * Sets a fixed height and fixed trunk height
	 */
	public void setFixed() {
		fixed = true;
	}
}
