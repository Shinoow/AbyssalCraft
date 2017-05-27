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
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.common.blocks.BlockEnergyRelay;

public abstract class TileEntityTieredEnergyRelay extends TileEntityEnergyRelay {

	private int ticksExisted;

	@Override
	public void update() {
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
					if(!world.isRemote)
						container.addEnergy(consumeEnergy(energy));
					BlockPos p = container.getContainerTile().getPos();

					Vec3d vec = new Vec3d(p.subtract(pos)).normalize();

					double d = Math.sqrt(p.distanceSq(pos));

					for(int i = 0; i < d * 15; i++){
						double i1 = i / 15D;
						double xp = pos.getX() + vec.xCoord * i1 + .5;
						double yp = pos.getY() + vec.yCoord * i1 + .5;
						double zp = pos.getZ() + vec.zCoord * i1 + .5;
						AbyssalCraft.proxy.spawnParticle("PEStream", world, xp, yp, zp, vec.xCoord * .1, .15, vec.zCoord * .1);
					}
				}
		}
	}

	protected abstract int getRange();
	protected abstract float getDrainQuanta();
	protected abstract float getTransferQuanta();
}
