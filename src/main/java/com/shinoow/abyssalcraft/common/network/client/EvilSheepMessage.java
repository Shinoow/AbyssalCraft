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
import java.util.UUID;

import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilSheep;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class EvilSheepMessage extends AbstractClientMessage<EvilSheepMessage> {

	private UUID playerUUID;
	private String playerName;
	private int id;

	public EvilSheepMessage(){}

	public EvilSheepMessage(UUID uuid, String name, int id){
		playerUUID = uuid;
		playerName = name;
		this.id = id;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		playerUUID = buffer.readUniqueId();
		playerName = ByteBufUtils.readUTF8String(buffer);
		id = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		buffer.writeUniqueId(playerUUID);
		ByteBufUtils.writeUTF8String(buffer, playerName);
		ByteBufUtils.writeVarInt(buffer, id, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		Entity entity = player.world.getEntityByID(id);
		if(entity == null || !(entity instanceof EntityEvilSheep)) return;
		((EntityEvilSheep)entity).setKilledPlayer(playerUUID, playerName);
	}
}
