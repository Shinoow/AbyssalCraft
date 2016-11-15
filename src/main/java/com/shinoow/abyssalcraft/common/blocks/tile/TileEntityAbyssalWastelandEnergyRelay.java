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
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.tileentity.TileEntity;

public class TileEntityAbyssalWastelandEnergyRelay extends TileEntityTieredEnergyRelay {

	@Override
	public TileEntity getContainerTile() {

		return this;
	}

	@Override
	protected int getRange() {

		return 8;
	}

	@Override
	protected float getDrainQuanta() {

		return 15;
	}

	@Override
	protected float getTransferQuanta() {

		return 20;
	}
}
