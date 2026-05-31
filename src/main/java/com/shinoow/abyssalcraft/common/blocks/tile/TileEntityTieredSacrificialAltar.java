/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2026 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;

public class TileEntityTieredSacrificialAltar extends TileEntitySacrificialAltar {

	@Override
	public int getMaxEnergy() {
		int base = 5000;

		return (int) (base * (1.5 + 0.5 * (BlockUtil.getTier(getBlockType()) - 1)));
	}

	@Override
	protected int getCooldownStartNumber() {
		int base = 1200;
		return base - 200 * BlockUtil.getTier(getBlockType());
	}
	
	@Override
	protected int getMaxTargets() {
		// One more target per tier
		return super.getMaxTargets() + BlockUtil.getTier(getBlockType());
	}
}
