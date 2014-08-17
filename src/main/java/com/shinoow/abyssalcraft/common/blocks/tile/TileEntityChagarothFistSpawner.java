/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
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