/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.common.blocks.BlockTieredEnergyRelay;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTieredEnergyRelay extends TileEntityEnergyRelay {

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		getWorld().markBlockRangeForRenderUpdate(getPos(), getPos());
		super.onDataPacket(net, packet);
	}


	@Override
	public TileEntity getContainerTile() {

		return this;
	}

	@Override
	public int getMaxEnergy() {

		int base = 600;

		return base + 100 * ((BlockTieredEnergyRelay)getBlockType()).TYPE.getMeta();
	}

	@Override
	protected int getRange(){

		int base = 6;

		return base + 2 * ((BlockTieredEnergyRelay)getBlockType()).TYPE.getMeta();
	}

	@Override
	protected float getDrainQuanta(){
		int base = 20;

		return base + 10 * ((BlockTieredEnergyRelay)getBlockType()).TYPE.getMeta();
	}

	@Override
	protected float getTransferQuanta(){
		int base = 30;

		return base + 10 * ((BlockTieredEnergyRelay)getBlockType()).TYPE.getMeta();
	}
}
