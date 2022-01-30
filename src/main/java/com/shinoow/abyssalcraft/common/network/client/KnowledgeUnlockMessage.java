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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class KnowledgeUnlockMessage extends AbstractClientMessage<KnowledgeUnlockMessage> {

	private int type;
	private Object data;

	public KnowledgeUnlockMessage() {}

	public KnowledgeUnlockMessage(int type, Object data) {
		this.type = type;
		this.data = data;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		type = ByteBufUtils.readVarInt(buffer, 5);
		if(type == 2)
			data = ByteBufUtils.readVarInt(buffer, 5);
		else data = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		ByteBufUtils.writeVarInt(buffer, type, 5);
		if(type == 2)
			ByteBufUtils.writeVarInt(buffer, (int)data, 5);
		else ByteBufUtils.writeUTF8String(buffer, (String)data);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		INecroDataCapability cap = NecroDataCapability.getCap(player);

		switch(type) {
		case 0:
			cap.triggerBiomeUnlock((String)data);
			break;
		case 1:
			cap.triggerEntityUnlock((String)data);
			break;
		case 2:
			cap.triggerDimensionUnlock((int)data);
			break;
		case 3:
			cap.triggerArtifactUnlock((String)data);
			break;
		case 4:
			cap.triggerPageUnlock((String)data);
			break;
		case 5:
			cap.triggerWhisperUnlock((String)data);
			break;
		case 6:
			cap.triggerMiscUnlock((String)data);
			break;
		}
	}
}
