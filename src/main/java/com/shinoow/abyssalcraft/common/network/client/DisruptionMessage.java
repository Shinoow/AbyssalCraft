/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionHandler;
import com.shinoow.abyssalcraft.api.event.ACEvents.DisruptionEvent;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

public class DisruptionMessage extends AbstractClientMessage<DisruptionMessage> {

	private DeityType deity;
	private String name;
	private int x, y, z;

	public DisruptionMessage(){}

	public DisruptionMessage(DeityType deity, String name, BlockPos pos){
		this.deity = deity;
		this.name = name;
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		deity = DeityType.valueOf(ByteBufUtils.readUTF8String(buffer));
		name = ByteBufUtils.readUTF8String(buffer);
		x = ByteBufUtils.readVarInt(buffer, 5);
		y = ByteBufUtils.readVarInt(buffer, 5);
		z = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeUTF8String(buffer, deity.name());
		ByteBufUtils.writeUTF8String(buffer, name);
		ByteBufUtils.writeVarInt(buffer, x, 5);
		ByteBufUtils.writeVarInt(buffer, y, 5);
		ByteBufUtils.writeVarInt(buffer, z, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		BlockPos pos = new BlockPos(x, y, z);
		List<EntityPlayer> players = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).expand(16, 16, 16));
		DisruptionEntry disruption = DisruptionHandler.instance().disruptionFromName(name);
		if(!MinecraftForge.EVENT_BUS.post(new DisruptionEvent(deity, player.worldObj, pos, players, disruption)))
			disruption.disrupt(player.worldObj, pos, players);
	}
}
