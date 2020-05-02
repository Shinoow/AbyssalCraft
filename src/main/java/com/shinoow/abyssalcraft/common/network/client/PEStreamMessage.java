/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;

public class PEStreamMessage extends AbstractClientMessage<PEStreamMessage> {

	private BlockPos posFrom, posTo;

	public PEStreamMessage(){}

	public PEStreamMessage(BlockPos posFrom, BlockPos posTo){
		this.posFrom = posFrom;
		this.posTo = posTo;
	}

	public PEStreamMessage(BlockPos posFrom, Entity target){
		this.posFrom = posFrom;
		posTo = target.getPosition();
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		posFrom = buffer.readBlockPos();
		posTo = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(posFrom);
		buffer.writeBlockPos(posTo);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		Vec3d vec = new Vec3d(posTo.subtract(posFrom)).normalize();

		double d = Math.sqrt(posTo.distanceSq(posFrom));

		int j = 1+Minecraft.getMinecraft().gameSettings.particleSetting;
		for(int i = 0; i < d * 15; i+=j){
			double i1 = i / 15D;
			double xp = posFrom.getX() + vec.x * i1 + .5;
			double yp = posFrom.getY() + vec.y * i1 + .5;
			double zp = posFrom.getZ() + vec.z * i1 + .5;
			AbyssalCraft.proxy.spawnParticle("PEStream", xp, yp, zp, vec.x * .1, .15, vec.z * .1);
		}
	}
}
