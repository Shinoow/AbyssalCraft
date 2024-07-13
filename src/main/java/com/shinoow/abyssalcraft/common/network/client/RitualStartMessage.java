/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;
import com.shinoow.abyssalcraft.lib.util.blocks.IRitualAltar;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class RitualStartMessage extends AbstractClientMessage<RitualStartMessage> {

	private BlockPos pos;
	private String id;
	private int sacrifice, timerMax;

	public RitualStartMessage(){}

	public RitualStartMessage(BlockPos pos, String id, int sacrifice, int timerMax) {
		this.pos = pos;
		this.id = id;
		this.sacrifice = sacrifice;
		this.timerMax = timerMax;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		pos = buffer.readBlockPos();
		id = ByteBufUtils.readUTF8String(buffer);
		sacrifice = buffer.readVarInt();
		timerMax = buffer.readVarInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		buffer.writeBlockPos(pos);
		ByteBufUtils.writeUTF8String(buffer, id);
		buffer.writeVarInt(sacrifice);
		buffer.writeVarInt(timerMax);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		NecronomiconRitual ritual = RitualRegistry.instance().getRitualById(id);

		TileEntity te = player.world.getTileEntity(pos);
		if(ritual != null && te instanceof IRitualAltar) {
			IRitualAltar altar = (IRitualAltar)te;
			altar.setRitualFields(ritual, (EntityLiving)player.world.getEntityByID(sacrifice), timerMax);
		}
	}
}
