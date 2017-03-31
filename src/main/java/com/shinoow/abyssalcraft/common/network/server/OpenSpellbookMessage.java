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
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;
import com.shinoow.abyssalcraft.lib.ACLib;

public class OpenSpellbookMessage extends AbstractServerMessage<OpenSpellbookMessage> {

	public OpenSpellbookMessage(){}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

	}

	@Override
	public void process(EntityPlayer player, Side side) {
		FMLNetworkHandler.openGui(player, AbyssalCraft.instance, ACLib.necronomiconspellbookGuiID, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
	}
}
