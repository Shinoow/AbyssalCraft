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

public class TileEntityTieredEnergyContainer extends TileEntityEnergyContainer {

	@Override
	public int getMaxEnergy() {
		int base = 10000;
		int meta = BlockUtil.getTier(getBlockType()) - 1;
		switch(meta){
		case 0:
			return base * 2;
		case 1:
			return base * 6;
		case 2:
			return base * 24;
		case 3:
			return base * 120;
		default:
			return base;
		}
	}

	@Override
	public String getName() {

		return "container.abyssalcraft.tieredenergycontainer";
	}
}
