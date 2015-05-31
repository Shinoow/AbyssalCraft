/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import com.shinoow.abyssalcraft.common.entity.EntityChagarothFist;

public class TileEntityChagarothFistSpawner extends TileEntity {

	private int activatingRangeFromPlayer = 16;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbtTag);
	}

	public boolean isActivated() {
		return worldObj.getClosestPlayer(xCoord + 0.5D,
				yCoord + 0.5D, zCoord + 0.5D,
				activatingRangeFromPlayer) != null;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote && isActivated()) {
			EntityChagarothFist mob = new EntityChagarothFist(worldObj);
			mob.setLocationAndAngles(xCoord, yCoord, zCoord, MathHelper.wrapAngleTo180_float(worldObj.rand.nextFloat() * 360.0F), 10.0F);
			worldObj.spawnEntityInWorld(mob);
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}

	}
}
