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

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

public class PEStreamMessage extends AbstractClientMessage<PEStreamMessage> {

	int x, y, z, x1, y1, z1;

	public PEStreamMessage(){}

	public PEStreamMessage(BlockPos posFrom, BlockPos posTo){
		x = posFrom.getX();
		y = posFrom.getY();
		z = posFrom.getZ();
		x1 = posTo.getX();
		y1 = posTo.getY();
		z1 = posTo.getZ();
	}

	public PEStreamMessage(BlockPos posFrom, Entity target){
		x = posFrom.getX();
		y = posFrom.getY();
		z = posFrom.getZ();
		x1 = target.getPosition().getX();
		y1 = target.getPosition().getY();
		z1 = target.getPosition().getZ();
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		x = ByteBufUtils.readVarInt(buffer, 5);
		y = ByteBufUtils.readVarInt(buffer, 5);
		z = ByteBufUtils.readVarInt(buffer, 5);
		x1 = ByteBufUtils.readVarInt(buffer, 5);
		y1 = ByteBufUtils.readVarInt(buffer, 5);
		z1 = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		ByteBufUtils.writeVarInt(buffer, x, 5);
		ByteBufUtils.writeVarInt(buffer, y, 5);
		ByteBufUtils.writeVarInt(buffer, z, 5);
		ByteBufUtils.writeVarInt(buffer, x1, 5);
		ByteBufUtils.writeVarInt(buffer, y1, 5);
		ByteBufUtils.writeVarInt(buffer, z1, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		BlockPos pos = new BlockPos(x, y, z);
		BlockPos pos1 = new BlockPos(x1, y1, z1);

		Vec3d vec = new Vec3d(pos1.subtract(pos)).normalize();

		double d = Math.sqrt(pos1.distanceSq(pos));

		for(int i = 0; i < d * 15; i++){
			double i1 = i / 15D;
			double xp = pos.getX() + vec.xCoord * i1 + .5;
			double yp = pos.getY() + vec.yCoord * i1 + .5;
			double zp = pos.getZ() + vec.zCoord * i1 + .5;
			AbyssalCraft.proxy.spawnParticle("PEStream", player.world, xp, yp, zp, vec.xCoord * .1, .15, vec.zCoord * .1);
		}
	}
}
