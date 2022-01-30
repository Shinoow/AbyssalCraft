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

import com.shinoow.abyssalcraft.common.inventory.ContainerConfigurator;
import com.shinoow.abyssalcraft.common.inventory.ContainerStateTransformer;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class UpdateModeMessage extends AbstractServerMessage<UpdateModeMessage> {

	private int mode, container;

	public UpdateModeMessage(){}

	public UpdateModeMessage(int mode) {
		this(mode, 0);
	}

	public UpdateModeMessage(int mode, int container){
		this.mode = mode;
		this.container = container;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		mode = ByteBufUtils.readVarInt(buffer, 5);
		container = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		ByteBufUtils.writeVarInt(buffer, mode, 5);
		ByteBufUtils.writeVarInt(buffer, container, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(container == 0 && player.openContainer instanceof ContainerStateTransformer || container == 1 && player.openContainer instanceof ContainerConfigurator){
			player.openContainer.enchantItem(player, mode);
			player.openContainer.detectAndSendChanges();
		}
	}
}
