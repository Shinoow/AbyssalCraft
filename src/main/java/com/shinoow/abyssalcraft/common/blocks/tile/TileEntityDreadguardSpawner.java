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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;

public class TileEntityDreadguardSpawner extends TileEntity implements ITickable {

	private int activatingRangeFromPlayer = 16;

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(pos, 1, nbtTag);
	}

	public boolean isActivated() {
		return world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
				activatingRangeFromPlayer, true) != null &&
				!world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
						activatingRangeFromPlayer, true).capabilities.isCreativeMode;
	}

	@Override
	public void update() {
		if (!world.isRemote && isActivated()) {
			EntityDreadguard mob = new EntityDreadguard(world);
			mob.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 10.0F);
			world.spawnEntity(mob);
			world.setBlockToAir(pos);
		}
	}
}