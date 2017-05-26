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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import com.shinoow.abyssalcraft.common.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.common.caps.NecroDataCapabilityProvider;
import com.shinoow.abyssalcraft.common.caps.NecroDataCapabilityStorage;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

public class NecroDataCapMessage extends AbstractClientMessage<NecroDataCapMessage> {

	NBTTagCompound properties = new NBTTagCompound();

	public NecroDataCapMessage() {}

	public NecroDataCapMessage(EntityPlayer player){
		INecroDataCapability cap = player.getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null);

		properties = (NBTTagCompound) NecroDataCapabilityStorage.instance.writeNBT(NecroDataCapabilityProvider.NECRO_DATA_CAP, cap, null);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		properties = ByteBufUtils.readTag(buffer);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeTag(buffer, properties);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		INecroDataCapability cap = player.getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null);

		NecroDataCapabilityStorage.instance.readNBT(NecroDataCapabilityProvider.NECRO_DATA_CAP, cap, null, properties);
	}
}
