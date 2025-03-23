/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.transfer.caps;

import java.util.Iterator;

import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemTransferCapabilityStorage implements IStorage<IItemTransferCapability> {

	public static IStorage<IItemTransferCapability> instance = new ItemTransferCapabilityStorage();

	@Override
	public NBTBase writeNBT(Capability<IItemTransferCapability> capability, IItemTransferCapability instance, EnumFacing side) {

		NBTTagCompound properties = new NBTTagCompound();

		NBTTagList list = new NBTTagList();
		instance.getTransferConfigurations().stream().map(ItemTransferConfiguration::serializeNBT).forEach(list::appendTag);

		properties.setTag("configurations", list);
		properties.setBoolean("isRunning", instance.isRunning());

		return properties;
	}

	@Override
	public void readNBT(Capability<IItemTransferCapability> capability, IItemTransferCapability instance, EnumFacing side, NBTBase nbt) {

		NBTTagCompound properties = (NBTTagCompound)nbt;

		if(properties.hasKey("configurations")) {
			NBTTagList list = properties.getTagList("configurations", NBT.TAG_COMPOUND);
			for(Iterator<NBTBase> iterator = list.iterator(); iterator.hasNext();) {
				ItemTransferConfiguration config = new ItemTransferConfiguration();
				config.deserializeNBT((NBTTagCompound)iterator.next());
				instance.addTransferConfiguration(config);
			}
		}
		instance.setRunning(properties.getBoolean("isRunning"));
	}

}
