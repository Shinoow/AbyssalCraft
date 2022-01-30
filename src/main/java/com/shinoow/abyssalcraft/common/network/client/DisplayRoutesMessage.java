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
import java.util.Iterator;
import java.util.List;

import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractClientMessage;
import com.shinoow.abyssalcraft.lib.util.ParticleUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class DisplayRoutesMessage extends AbstractClientMessage<DisplayRoutesMessage> {

	private List<BlockPos[]> routes;
	private NBTTagCompound nbt = new NBTTagCompound();

	public DisplayRoutesMessage(){}

	public DisplayRoutesMessage(List<BlockPos[]> routes){
		this.routes = routes;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		routes = new ArrayList<>();
		nbt = ByteBufUtils.readTag(buffer);
		NBTTagList list = nbt.getTagList("Routes", NBT.TAG_LIST);
		for(Iterator<NBTBase> i = list.iterator(); i.hasNext();) {
			NBTTagList list1 = (NBTTagList) i.next();
			List<BlockPos> temp = new ArrayList<>();
			for(Iterator<NBTBase> i1 = list1.iterator(); i1.hasNext();)
				temp.add(BlockPos.fromLong(((NBTTagLong)i1.next()).getLong()));
			routes.add(temp.toArray(new BlockPos[0]));
		}
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		NBTTagList list = new NBTTagList();
		for(BlockPos[] route : routes) {
			NBTTagList list1 = new NBTTagList();
			for(BlockPos pos : route)
				list1.appendTag(new NBTTagLong(pos.toLong()));
			list.appendTag(list1);
		}
		nbt.setTag("Routes", list);
		ByteBufUtils.writeTag(buffer, nbt);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		BlockPos prevPos = null;
		if(!routes.isEmpty())
			for(BlockPos[] route : routes) {
				prevPos = null;
				for(BlockPos pos : route)
					if(prevPos == null)
						prevPos = pos;
					else {
						ParticleUtil.spawnParticleLine(prevPos, pos, 15, (v1, v2) -> {
							player.world.spawnParticle(EnumParticleTypes.REDSTONE, v2.x, v2.y, v2.z, v1.x * .1, .15, v1.z * .1);
						});
						prevPos = pos;
					}
			}
	}
}
