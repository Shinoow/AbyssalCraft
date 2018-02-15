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
package com.shinoow.abyssalcraft.common.network.client;

import java.io.IOException;

import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class CleansingRitualMessage extends AbstractClientMessage<CleansingRitualMessage> {

	private int x, z, biomeID;

	public CleansingRitualMessage() {}

	public CleansingRitualMessage(int x, int z, int biomeID){
		this.x = x;
		this.z = z;
		this.biomeID = biomeID;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		x = ByteBufUtils.readVarInt(buffer, 5);
		z = ByteBufUtils.readVarInt(buffer, 5);
		biomeID = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		ByteBufUtils.writeVarInt(buffer, x, 5);
		ByteBufUtils.writeVarInt(buffer, z, 5);
		ByteBufUtils.writeVarInt(buffer, biomeID, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		Chunk chunk = player.worldObj.getChunkFromBlockCoords(new BlockPos(x, 0, z));
		int chunkX = x & 0xF;
		int chunkZ = z & 0xF;
		chunk.getBiomeArray()[ chunkZ << 4 | chunkX ] = (byte) biomeID;
		Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(x - 7, 0, z - 7, x + 7, 255, z + 7);
	}

}
