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

import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.*;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class NecroDataCapMessage extends AbstractClientMessage<NecroDataCapMessage> {

	NBTTagCompound properties = new NBTTagCompound();

	public NecroDataCapMessage() {}

	public NecroDataCapMessage(EntityPlayer player){
		INecroDataCapability cap = NecroDataCapability.getCap(player);

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
		INecroDataCapability cap = NecroDataCapability.getCap(player);

		NecroDataCapabilityStorage.instance.readNBT(NecroDataCapabilityProvider.NECRO_DATA_CAP, cap, null, properties);
		cap.setLastSyncTime(System.currentTimeMillis());
	}
}
