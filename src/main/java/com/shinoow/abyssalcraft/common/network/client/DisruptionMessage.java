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
import java.util.List;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.event.ACEvents.DisruptionEvent;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class DisruptionMessage extends AbstractClientMessage<DisruptionMessage> {

	private String deity, name;
	private BlockPos pos;

	public DisruptionMessage(){}

	public DisruptionMessage(DeityType deity, String name, BlockPos pos){
		this.deity = deity != null ? deity.toString() : "";
		this.name = name;
		this.pos = pos;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		deity = ByteBufUtils.readUTF8String(buffer);
		name = ByteBufUtils.readUTF8String(buffer);
		pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeUTF8String(buffer, deity);
		ByteBufUtils.writeUTF8String(buffer, name);
		buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		List<EntityPlayer> players = player.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).grow(16, 16, 16));
		DisruptionEntry disruption = DisruptionHandler.instance().disruptionFromName(name);
		if(!MinecraftForge.EVENT_BUS.post(new DisruptionEvent(!deity.isEmpty() ? DeityType.valueOf(deity) : null, player.world, pos, players, disruption)))
			disruption.disrupt(player.world, pos, players);
	}
}
