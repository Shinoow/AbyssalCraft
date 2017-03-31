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
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import com.shinoow.abyssalcraft.common.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.common.caps.NecroDataCapabilityProvider;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;

public class NecroDataCapMessage extends AbstractClientMessage<NecroDataCapMessage> {

	NBTTagCompound properties = new NBTTagCompound();

	public NecroDataCapMessage() {}

	public NecroDataCapMessage(EntityPlayer player){
		INecroDataCapability cap = player.getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null);

		NBTTagList l = new NBTTagList();
		for(String name : cap.getEntityTriggers())
			l.appendTag(new NBTTagString(name));
		properties.setTag("entityTriggers", l);
		l = new NBTTagList();
		for(String name : cap.getBiomeTriggers())
			l.appendTag(new NBTTagString(name));
		properties.setTag("biomeTriggers", l);
		l = new NBTTagList();
		for(int id : cap.getDimensionTriggers())
			l.appendTag(new NBTTagInt(id));
		properties.setTag("dimensionTriggers", l);
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

		NBTTagList l = properties.getTagList("entityTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			cap.triggerEntityUnlock(l.getStringTagAt(i));
		l = properties.getTagList("biomeTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			cap.triggerBiomeUnlock(l.getStringTagAt(i));
		l = properties.getTagList("dimensionTriggers", 3);
		for(int i = 0; i < l.tagCount(); i++)
			cap.triggerDimensionUnlock(l.getIntAt(i));
	}


}
