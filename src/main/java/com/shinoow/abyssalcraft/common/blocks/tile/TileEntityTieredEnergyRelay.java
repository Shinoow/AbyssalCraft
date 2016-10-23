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

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.common.blocks.BlockEnergyRelay;

public abstract class TileEntityTieredEnergyRelay extends TileEntityEnergyRelay {

	private int ticksExisted;

	@Override
	public void update() {
		++ticksExisted;

		if(ticksExisted % 20 == 0)
			if(worldObj.getBlockState(pos).getProperties().containsKey(BlockEnergyRelay.FACING))
				PEUtils.collectNearbyPE(this, worldObj, pos, worldObj.getBlockState(pos).getValue(BlockEnergyRelay.FACING).getOpposite(), getDrainQuanta());

		if(ticksExisted % 40 == 0 && canTransferPE())
			if(worldObj.getBlockState(pos).getProperties().containsKey(BlockEnergyRelay.FACING))
				transferPE(worldObj.getBlockState(pos).getValue(BlockEnergyRelay.FACING), getTransferQuanta());
	}

	@Override
	public void transferPE(EnumFacing facing, float energy) {

		int xp = pos.getX();
		int yp = pos.getY();
		int zp = pos.getZ();

		if(PEUtils.canTransfer(worldObj, pos, facing, getRange())){
			IEnergyContainer container = PEUtils.getContainer(worldObj, pos, facing, getRange());
			if(container != null)
				if(container.canAcceptPE()){
					if(!worldObj.isRemote)
						container.addEnergy(consumeEnergy(energy));
					BlockPos p = container.getContainerTile().getPos();
					for(double i = 0; i <= 0.7; i += 0.03) {
						int xPos = xp < p.getX() ? 1 : xp > p.getX() ? -1 : 0;
						int yPos = yp < p.getY() ? 1 : yp > p.getY() ? -1 : 0;
						int zPos = zp < p.getZ() ? 1 : zp > p.getZ() ? -1 : 0;
						double x = i * xPos;
						double y = i * yPos;
						double z = i * zPos;
						worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xp + 0.5, yp + 0.5, zp + 0.5, x, y, z);
					}
				}
		}
	}

	protected abstract int getRange();
	protected abstract float getDrainQuanta();
	protected abstract float getTransferQuanta();
}
