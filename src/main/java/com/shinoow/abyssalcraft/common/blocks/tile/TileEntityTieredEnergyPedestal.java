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

import com.shinoow.abyssalcraft.common.blocks.BlockTieredEnergyPedestal;

import net.minecraft.tileentity.TileEntity;

public class TileEntityTieredEnergyPedestal extends TileEntityEnergyPedestal {

	@Override
	public int getMaxEnergy() {
		int base = 5000;

		return (int) (base * (1.5 + 0.5 * ((BlockTieredEnergyPedestal)getBlockType()).TYPE.getMeta()));
	}

	@Override
	public TileEntity getContainerTile() {

		return this;
	}
}
