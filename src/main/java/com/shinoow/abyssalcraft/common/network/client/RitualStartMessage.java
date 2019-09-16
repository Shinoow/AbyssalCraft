/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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
	private String name;
	private int[][] offerData;
	private boolean[] hasOffer;
	private int sacrifice;

	public RitualStartMessage(){}

	public RitualStartMessage(BlockPos pos, String name, int sacrifice, int[][] offerData, boolean[] hasOffer) {
		this.pos = pos;
		this.name = name;
		this.hasOffer = hasOffer;
		this.offerData = offerData;
		this.sacrifice = sacrifice;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		pos = buffer.readBlockPos();
		name = ByteBufUtils.readUTF8String(buffer);
		offerData = new int[8][2];
		hasOffer = new boolean[8];
		for(int i = 0; i < 8; i++) {
			offerData[i] = buffer.readVarIntArray();
			hasOffer[i] = buffer.readBoolean();
		}
		sacrifice = buffer.readVarInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		buffer.writeBlockPos(pos);
		ByteBufUtils.writeUTF8String(buffer, name);
		for(int i = 0; i < 8; i++) {
			buffer.writeVarIntArray(offerData[i]);
			buffer.writeBoolean(hasOffer[i]);
		}
		buffer.writeVarInt(sacrifice);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		NecronomiconRitual ritual = null;
		for(NecronomiconRitual r : RitualRegistry.instance().getRituals())
			if(r.getUnlocalizedName().equals(name)) {
				ritual = r;
				break;
			}

		TileEntity te = player.world.getTileEntity(pos);
		if(te instanceof IRitualAltar) {
			IRitualAltar altar = (IRitualAltar)te;
			altar.setRitualFields(ritual, offerData, hasOffer, (EntityLiving)player.world.getEntityByID(sacrifice));
		}
	}
}
