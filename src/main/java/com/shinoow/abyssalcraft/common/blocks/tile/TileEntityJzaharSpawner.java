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
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;

public class TileEntityJzaharSpawner extends TileEntity implements ITickable {

	private int activatingRangeFromPlayer = 12;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(pos, 1, nbtTag);
	}

	public boolean isActivated() {
		return worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
				activatingRangeFromPlayer) != null &&
				!worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
						activatingRangeFromPlayer).capabilities.isCreativeMode &&
						worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
								activatingRangeFromPlayer).posY >= pos.getY();
	}

	@Override
	public void update() {
		if (!worldObj.isRemote && isActivated()) {
			EntityJzahar mob = new EntityJzahar(worldObj);
			mob.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapAngleTo180_float(worldObj.rand.nextFloat() * 360.0F), 10.0F);
			worldObj.spawnEntityInWorld(mob);
			worldObj.setBlockToAir(pos);
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, mob.getEntityBoundingBox().expand(64, 64, 64));
			for(EntityPlayer player : players)
				player.addStat(AbyssalCraft.locateJzahar, 1);
		}
	}
}