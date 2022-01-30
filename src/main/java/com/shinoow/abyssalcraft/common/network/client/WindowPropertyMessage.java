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

import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class WindowPropertyMessage extends AbstractClientMessage<WindowPropertyMessage>{

	private int windowId;
	private int property;
	private int value;

	public WindowPropertyMessage() {}

	public WindowPropertyMessage(int windowIdIn, int propertyIn, int valueIn)
	{
		windowId = windowIdIn;
		property = propertyIn;
		value = valueIn;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		windowId = ByteBufUtils.readVarInt(buffer, 5);
		property = ByteBufUtils.readVarInt(buffer, 5);
		value = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		ByteBufUtils.writeVarInt(buffer, windowId, 5);
		ByteBufUtils.writeVarInt(buffer, property, 5);
		ByteBufUtils.writeVarInt(buffer, value, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (player.openContainer != null && player.openContainer.windowId == windowId)
			player.openContainer.updateProgressBar(property, value);
	}

}
