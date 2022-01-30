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
package com.shinoow.abyssalcraft.common.network.server;

import java.io.IOException;

import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;

public class ToggleStateMessage extends AbstractServerMessage<ToggleStateMessage> {

	private BlockPos pos;

	public ToggleStateMessage(){}

	public ToggleStateMessage(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		boolean hadTe = false;
		TileEntity te = player.world.getTileEntity(pos);
		if(te != null) {
			IItemTransferCapability cap = ItemTransferCapability.getCap(te);
			if(cap != null) {
				cap.setRunning(!cap.isRunning());
				hadTe = true;
				((WorldServer)player.world).spawnParticle(cap.isRunning() ? EnumParticleTypes.VILLAGER_HAPPY : EnumParticleTypes.VILLAGER_ANGRY, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 1, 0, 0, 0, 1.0);
			}
		}
		if(!hadTe) {
			MutableBlockPos pos1 = new MutableBlockPos();
			for(int x = pos.getX() - 16; x < pos.getX() + 16; x++)
				for(int y = pos.getY() - 16; y < pos.getY() + 16; y++)
					for(int z = pos.getZ() - 16; z < pos.getZ() + 16; z++) {
						pos1.setPos(x, y, z);
						TileEntity te1 = player.world.getTileEntity(pos1);
						if(te1 != null) {
							IItemTransferCapability cap = ItemTransferCapability.getCap(te1);
							if(cap != null) {
								cap.setRunning(!cap.isRunning());
								((WorldServer)player.world).spawnParticle(cap.isRunning() ? EnumParticleTypes.VILLAGER_HAPPY : EnumParticleTypes.VILLAGER_ANGRY, pos1.getX() + 0.5, pos1.getY() + 1, pos1.getZ() + 0.5, 1, 0, 0, 0, 1.0);
							}
						}
					}
		}
	}
}
