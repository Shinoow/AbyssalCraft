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
package com.shinoow.abyssalcraft.common.network.server;

import java.io.IOException;
import java.util.UUID;

import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.NecroDataCapMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class PrepareSyncMessage extends AbstractServerMessage<PrepareSyncMessage> {

	private UUID playerUUID;

	public PrepareSyncMessage() {}

	public PrepareSyncMessage(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		playerUUID = buffer.readUniqueId();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeUniqueId(playerUUID);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(player.getUniqueID().equals(playerUUID))
			PacketDispatcher.sendTo(new NecroDataCapMessage(player), (EntityPlayerMP)player);
	}
}
