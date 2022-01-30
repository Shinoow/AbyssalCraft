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

import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.PrepareSyncMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class ShouldSyncMessage extends AbstractClientMessage<ShouldSyncMessage> {

	private long time;

	public ShouldSyncMessage() {}

	public ShouldSyncMessage(EntityPlayer player) {
		time = NecroDataCapability.getCap(player).getLastSyncTime();
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		time = buffer.readLong();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeLong(time);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		INecroDataCapability cap = NecroDataCapability.getCap(player);
		if(cap.getLastSyncTime() < time)
			PacketDispatcher.sendToServer(new PrepareSyncMessage(player.getUniqueID()));
	}

}
