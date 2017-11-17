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
package com.shinoow.abyssalcraft.common.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import com.shinoow.abyssalcraft.common.inventory.ContainerMaterializer;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

public class TransferStackMessage extends AbstractServerMessage<TransferStackMessage> {

	public TransferStackMessage(){}

	private int slot;
	private ItemStack stack;

	public TransferStackMessage(int slot, ItemStack stack){
		this.slot = slot;
		this.stack = stack;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		slot = ByteBufUtils.readVarInt(buffer, 5);
		stack = ByteBufUtils.readItemStack(buffer);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		ByteBufUtils.writeVarInt(buffer, slot, 5);
		ByteBufUtils.writeItemStack(buffer, stack);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if(player.openContainer instanceof ContainerMaterializer){
			player.openContainer.putStackInSlot(slot, stack);
			player.openContainer.detectAndSendChanges();
		}
	}

}
