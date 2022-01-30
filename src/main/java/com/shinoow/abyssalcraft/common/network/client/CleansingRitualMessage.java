/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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
import com.shinoow.abyssalcraft.common.util.BiomeUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class CleansingRitualMessage extends AbstractClientMessage<CleansingRitualMessage> {

	private int x, z, biomeID;
	private boolean batched;

	public CleansingRitualMessage() {}

	public CleansingRitualMessage(int x, int z, int biomeID){
		this(x, z, biomeID, false);
	}

	public CleansingRitualMessage(int x, int z, int biomeID, boolean batched){
		this.x = x;
		this.z = z;
		this.biomeID = biomeID;
		this.batched = batched;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		x = ByteBufUtils.readVarInt(buffer, 5);
		z = ByteBufUtils.readVarInt(buffer, 5);
		biomeID = ByteBufUtils.readVarInt(buffer, 5);
		batched = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		ByteBufUtils.writeVarInt(buffer, x, 5);
		ByteBufUtils.writeVarInt(buffer, z, 5);
		ByteBufUtils.writeVarInt(buffer, biomeID, 5);
		buffer.writeBoolean(batched);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		BiomeUtil.updateBiome(player.world, new BlockPos(x, 0, z), biomeID, false);

		if(x % 14 == 0 || z % 14 == 0 || !batched)
			Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(x - 7, 0, z - 7, x + 7, 255, z + 7);
	}

}
