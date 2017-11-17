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

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion;

public class TileEntityGatekeeperMinionSpawner extends TileEntity implements ITickable {

	private int activatingRangeFromPlayer = 16;

	@Override
	public void onLoad()
	{
		if(worldObj.isRemote)
			worldObj.loadedTileEntityList.remove(this);
	}

	public boolean isActivated() {
		return worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
			activatingRangeFromPlayer, true) != null &&
			!worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
				activatingRangeFromPlayer, true).capabilities.isCreativeMode;
	}

	@Override
	public void update() {
		if (isActivated() && !worldObj.isRemote) {
			EntityGatekeeperMinion mob = new EntityGatekeeperMinion(worldObj);
			mob.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(worldObj.rand.nextFloat() * 360.0F), 10.0F);
			mob.onInitialSpawn(worldObj.getDifficultyForLocation(pos), null);
			worldObj.spawnEntityInWorld(mob);
			worldObj.setBlockToAir(pos);
		}
	}
}
