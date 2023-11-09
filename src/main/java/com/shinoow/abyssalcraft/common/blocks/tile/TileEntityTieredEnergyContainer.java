/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.tileentity.TileEntity;

public class TileEntityTieredEnergyContainer extends TileEntityEnergyContainer {

	@Override
	public int getMaxEnergy() {
		int base = 10000;
		switch(getBlockMetadata()){
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
	public TileEntity getContainerTile() {

		return this;
	}

	@Override
	public String getName() {

		return "container.abyssalcraft.tieredenergycontainer";
	}
}
