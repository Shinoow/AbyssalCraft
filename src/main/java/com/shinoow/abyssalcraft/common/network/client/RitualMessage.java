/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.event.RitualEvent;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class RitualMessage extends AbstractClientMessage<RitualMessage> {

	private String id, disruption;
	private BlockPos pos;
	private boolean failed;

	public RitualMessage(){}

	public RitualMessage(String id, BlockPos pos){
		this(id, pos, false, "");
	}

	public RitualMessage(String id, BlockPos pos, boolean failed, String disruption){
		this.id = id;
		this.pos = pos;
		this.failed = failed;
		this.disruption = disruption;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		id = ByteBufUtils.readUTF8String(buffer);
		failed = buffer.readBoolean();
		pos = buffer.readBlockPos();
		disruption = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeUTF8String(buffer, id);
		buffer.writeBoolean(failed);
		buffer.writeBlockPos(pos);
		ByteBufUtils.writeUTF8String(buffer, disruption);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		NecronomiconRitual ritual = RitualRegistry.instance().getRitualById(id);

		if(ritual != null)
			if(failed)
				MinecraftForge.EVENT_BUS.post(new RitualEvent.Failed(player, ritual, DisruptionHandler.instance().disruptionFromName(disruption), player.world, pos));
			else if(!MinecraftForge.EVENT_BUS.post(new RitualEvent.Post(player, ritual, player.world, pos)))
				ritual.completeRitual(player.world, pos, player);
	}
}
