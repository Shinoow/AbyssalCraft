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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class ConfiguratorMessage extends AbstractServerMessage<ConfiguratorMessage> {

	private int mode1, mode2;
	private boolean openFilter, clearPath;

	public ConfiguratorMessage(){}

	public ConfiguratorMessage(int mode1, int mode2) {
		this.mode1 = mode1;
		this.mode2 = mode2;
		openFilter = false;
		clearPath = false;
	}

	public ConfiguratorMessage(boolean openFilter) {
		this.openFilter = openFilter;
		clearPath = false;
		mode1 = -1;
		mode2 = -1;
	}

	/**
	 *
	 * @param n Yes, this parameter does nothing
	 */
	public ConfiguratorMessage(boolean clearPath, int n) {
		this.clearPath = clearPath;
		openFilter = false;
		mode1 = -1;
		mode2 = -1;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		mode1 = ByteBufUtils.readVarInt(buffer, 5);
		mode2 = ByteBufUtils.readVarInt(buffer, 5);
		openFilter = buffer.readBoolean();
		clearPath = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		ByteBufUtils.writeVarInt(buffer, mode1, 5);
		ByteBufUtils.writeVarInt(buffer, mode2, 5);
		buffer.writeBoolean(openFilter);
		buffer.writeBoolean(clearPath);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		if(clearPath) {
			ItemStack mainStack = player.getHeldItem(EnumHand.MAIN_HAND);
			ItemStack offStack = player.getHeldItem(EnumHand.OFF_HAND);
			if(!mainStack.isEmpty() && mainStack.getItem() == ACItems.configurator) {
				if(!mainStack.hasTagCompound())
					mainStack.setTagCompound(new NBTTagCompound());
				mainStack.getTagCompound().removeTag("Path");
			}
			if(!offStack.isEmpty() && offStack.getItem() == ACItems.configurator) {
				if(!offStack.hasTagCompound())
					offStack.setTagCompound(new NBTTagCompound());
				offStack.getTagCompound().removeTag("Path");
			}
		}
		else if(openFilter)
			player.openGui(AbyssalCraft.instance, ACLib.configuratorGuiID, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
		else {
			ItemStack mainStack = player.getHeldItem(EnumHand.MAIN_HAND);
			ItemStack offStack = player.getHeldItem(EnumHand.OFF_HAND);
			if(!mainStack.isEmpty() && mainStack.getItem() == ACItems.configurator && mode1 > -1) {
				if(!mainStack.hasTagCompound())
					mainStack.setTagCompound(new NBTTagCompound());
				mainStack.getTagCompound().setInteger("Mode", mode1);
			}
			if(!offStack.isEmpty() && offStack.getItem() == ACItems.configurator && mode2 > -1) {
				if(!offStack.hasTagCompound())
					offStack.setTagCompound(new NBTTagCompound());
				offStack.getTagCompound().setInteger("Mode", mode2);
			}
		}
	}
}
