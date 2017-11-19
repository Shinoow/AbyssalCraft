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

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;

public class TileEntityJzaharSpawner extends TileEntity {

	private int activatingRangeFromPlayer = 12;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbtTag);
	}

	public boolean isActivated() {
		return worldObj.getClosestPlayer(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D,
				activatingRangeFromPlayer) != null &&
				!worldObj.getClosestPlayer(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D,
						activatingRangeFromPlayer).capabilities.isCreativeMode &&
						worldObj.getClosestPlayer(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D,
								activatingRangeFromPlayer).posY >= yCoord -1;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote && isActivated()) {
			EntityJzahar mob = new EntityJzahar(worldObj);
			mob.setLocationAndAngles(xCoord, yCoord, zCoord, MathHelper.wrapAngleTo180_float(worldObj.rand.nextFloat() * 360.0F), 10.0F);
			worldObj.spawnEntityInWorld(mob);
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, mob.boundingBox.expand(64, 64, 64));
			for(EntityPlayer player : players)
				player.addStat(AbyssalCraft.locateJzahar, 1);
		}

	}
}