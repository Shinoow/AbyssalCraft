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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;
import com.shinoow.abyssalcraft.lib.util.ParticleUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
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

		int j = 1+Minecraft.getMinecraft().gameSettings.particleSetting;

		ParticleUtil.spawnParticleLine(posFrom, posTo, 15, j, (v1, v2) -> {
			if(AbyssalCraft.proxy.getParticleCount() < 10000)
				AbyssalCraft.proxy.spawnParticle("PEStream", v2.x, v2.y, v2.z, v1.x * .1, .15, v1.z * .1);
		});
	}
}
