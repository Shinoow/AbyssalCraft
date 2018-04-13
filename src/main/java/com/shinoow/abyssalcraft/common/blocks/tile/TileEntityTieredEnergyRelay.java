/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.common.blocks.BlockEnergyRelay;

import net.minecraft.util.EnumFacing;

public abstract class TileEntityTieredEnergyRelay extends TileEntityEnergyRelay {

	private int ticksExisted;

	@Override
	public void onLoad()
	{
		if(world.isRemote)
			world.tickableTileEntities.remove(this);
		ticksExisted = world.rand.nextInt(100);
	}

	@Override
	public void update() {
		if(world.isBlockPowered(pos))
			return;

		++ticksExisted;

		if(ticksExisted % 20 == 0)
			if(world.getBlockState(pos).getProperties().containsKey(BlockEnergyRelay.FACING))
				PEUtils.collectNearbyPE(this, world, pos, world.getBlockState(pos).getValue(BlockEnergyRelay.FACING).getOpposite(), getDrainQuanta());

		if(ticksExisted % 40 == 0 && canTransferPE())
			if(world.getBlockState(pos).getProperties().containsKey(BlockEnergyRelay.FACING))
				transferPE(world.getBlockState(pos).getValue(BlockEnergyRelay.FACING), getTransferQuanta());
	}

	@Override
	public void transferPE(EnumFacing facing, float energy) {

		if(PEUtils.canTransfer(world, pos, facing, getRange())){
			IEnergyContainer container = PEUtils.getContainer(world, pos, facing, getRange());
			if(container != null)
				if(container.canAcceptPE()){
					container.addEnergy(consumeEnergy(energy));
					AbyssalCraftAPI.getInternalMethodHandler().spawnPEStream(pos, container.getContainerTile().getPos(), world.provider.getDimension());
				}
		}
	}

	protected abstract int getRange();
	protected abstract float getDrainQuanta();
	protected abstract float getTransferQuanta();
}
