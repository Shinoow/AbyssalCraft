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
import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;
import com.shinoow.abyssalcraft.common.world.data.NecromancyWorldSavedData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class SyncNecromancyDataMessage extends AbstractClientMessage<SyncNecromancyDataMessage> {

	private NBTTagCompound properties = new NBTTagCompound();

	public SyncNecromancyDataMessage(){}

	public SyncNecromancyDataMessage(World world) {
		NBTTagList list = new NBTTagList();
		for(Tuple<String, NBTTagCompound> data : NecromancyWorldSavedData.get(world).getData()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("name", data.getFirst());
			tag.setInteger("size", data.getSecond().getInteger("ResurrectionRitualCrystalSize"));
			list.appendTag(tag);
		}
		properties.setTag("data", list);
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

		NBTTagList list = properties.getTagList("data", NBT.TAG_COMPOUND);

		List<Tuple<String, Integer>> clientData = new ArrayList<>();
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			clientData.add(new Tuple<>(tag.getString("name"), tag.getInteger("size")));
		}

		NecromancyWorldSavedData.get(player.world).populateClientData(clientData);
	}
}
